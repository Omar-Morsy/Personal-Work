/** INCLUDES **********************************************************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <stdint.h>
#include <string.h>

/** DEFINES *************************************************************************************************************************/
#define MAX_INPUT_NO    15    ///Maximum number allowed of input codes
#define MIN_INPUT_NO     3    ///Minimum number allowed of input codes

/** STUCTURES *********************************************************************************************************************/
/** Structure of a node in a linked list **/
struct node{
    char code;
    struct node * next;
    struct node * previous;
};
struct node * head = NULL; /// Point to the head of the linked list

/** FUNCTIONS *********************************************************************************************************************/
/**
 * @param toAdd              New node to be added to the linked list
 * @return                   True if a the node is successfully added to the linked list
 * Add a new node to the linked list
 */
 bool nodeAdd(struct node * toAdd) {
    if(toAdd == NULL) { /// Empty node check
        return false;
    }
    if(head == NULL) { /// First entry check
        head = toAdd;
        head->previous = NULL;
        head->next = NULL;
        return true;
    }
    if(head->next == NULL) { /// Single noded list
        head->next = toAdd;
        head->next->previous = head;
        head->next->next = NULL;
        return true;
    } else { /// Add node to the end of the list
        struct node * current = head;
        while (current->next != NULL) { /// Loop on the linked list
            current = current->next;
        }
        current->next = toAdd;   /// Add the node to end of the list
        current->next->previous = current;
        current->next->next = NULL;
        return true;
    }
 }

 /**
  * @return                   True if a the the input codes sequence is valid
  * Check for input code sequence validity
  */
 bool codeIsValid() {
     struct node * current = head;
     if(current->code != 'R') { /// First code must be red "R"
         return false;
     }
     current = current->next;
     while (current != NULL) { /// Loop on the linked list

        if(current->code == 'R') { /// Search for R
            if(current->next != NULL) {
                if((current->next->code != 'G') && (current->next->code != 'Y') && (current->next->code != 'C') && (current->next->code != 'P')) { /// R must be followed by G or Y or C or P
                    return false;
                }
            }
            if(current->previous->code == 'R') { /// Code cant be repeated twice
                return false;
            }
        }

         if(current->code == 'G') { /// Search for G
            if(current->next != NULL) {
                if(current->next->code != 'Y')  { /// G must be followed by Y
                    return false;
                }
            }
            if(current->previous->code != 'R')  { /// Code cant be repeated twice and G must be after R
                return false;
            }
        }

        if(current->code == 'Y') { /// Search for Y
            if(current->next != NULL) {
                if(current->next->code != 'R')  { /// Y must be followed by R
                    return false;
                }
            }
            if(current->previous->code != 'G')  { /// Code cant be repeated twice and Y must be after G
                return false;
            }
        }

        if((current->code == 'C') || (current->code == 'P')) { /// Search for C and P
            if(current->next != NULL) {
                if(current->next->code != 'R')  { /// C and P must be followed by R
                    return false;
                }
            }
            if(current->previous->code != 'R')  { /// Code cant be repeated twice and C and P must be after R
                return false;
            }
        }
        current = current->next;
    }
    return true;
 }

 /**
  * Print all char variables stored in the linked list
  */
 void printList() {
     struct node * current = head;
     while (current != NULL) { /// Loop on the linked list
        printf("%c\n", current->code);
        current = current->next;
    }
 }


/** MAIN ***************************************************************************************************************************/
int main() {
    printf("Enter the codes: ");
    uint8_t code_counter = 0;
    char empty = ' ';
    char new_line = '\n';
    char c;
    scanf("%c", &c);
    while(c != new_line) {
        code_counter ++;
        if(code_counter > MAX_INPUT_NO) { ///Check for exceding the no. of codes allowed
            printf("Error\n");
            return 0;
        }
        if((c!= 'R') && (c!= 'G') && (c!= 'Y') && (c!= 'C') && (c!= 'P')) {
            printf("Error\n");
            return 0;
        }
        struct node * n = NULL;
        n = (struct node*)malloc(sizeof(struct node));
        n->code = c;
        nodeAdd(n); /// Add the code as node on the linked list
        scanf("%c", &c);
        if (c == new_line)  { /// Check end of input codes
            break;
        }
        if (c != empty)  { /// Check for missing space between codes
            printf("Error\n");
            return 0;
        }
        scanf("%c", &c);
    }
    if(code_counter <= MIN_INPUT_NO) { ///Check for the minimum no. of codes allowed
        printf("Error\n");
        return 0;
    }
    bool ret_value = codeIsValid();
    if(ret_value) {
        printf("Accept\n");
    } else {
        printf("Reject\n");
    }
    return 0;
}
