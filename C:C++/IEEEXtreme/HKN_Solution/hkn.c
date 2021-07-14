/** INCLUDES **********************************************************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <stdint.h>

/** FUNCTIONS *********************************************************************************************************************/
/**
 * @param n                       The uint32_t number to be reversed
 * @return                        The reversed uint32_t number
 * Generate a new number by reversing the original input number.
 */
uint32_t reverseNumber(uint32_t n) {
    uint32_t reverse_num = 0;
    while(n > 0) {
        reverse_num <<= 1;  /// Shift left by 1
        if(n & 1) { /// If current bit is 1
            reverse_num ^= 1;
        }
        n >>= 1;  /// Shift right by 1
    }
    return reverse_num;
}

/**
 * @param n                       The uint32_t number to be checked is it is palindrome
 * @return                        boolean to indicate whether the passed number is palindrome or not (true if plaindrome)
 * Check if a decimal number is palindrome or not.
 */
bool isPalindrome(uint32_t n) {
    return (n == reverseNumber(n));
}

/** MAIN ***************************************************************************************************************************/
int main() {
    long int lower_bound;  /// Should be uint32_t, but to check for negative value input, signed integer must be used
    long int upper_bound; /// The use of 64 bit signed integer, to give a room for positive input of 32-bits
    printf("Enter the bounds: ");
    scanf("%ld,%ld", &lower_bound, &upper_bound);  /// Read the input string from the user

    if((lower_bound > upper_bound) || (lower_bound < 0) || (upper_bound < 0) || (upper_bound >= (long int)pow(2,((sizeof(uint32_t) * 8))))) { //Check for false bounds entry
        printf("Error : Incorrect bound selection ");
        return 0;
    }

    uint32_t palindrome_counter = 0;
    if(lower_bound % 2 == 0) { /// Palindrome number can only be an odd number (Divide our search by half)
        lower_bound ++; /// Always start with an odd number to loop on odd values
    }
    for(uint32_t i = lower_bound; i <= upper_bound; i += 2) {   /// Loop on all odd values in bounds
        if(isPalindrome(i)) {
            palindrome_counter ++;
        }
    }
    printf("%d\n", palindrome_counter);
    return 0;
}
