/* INCLUDES *********************************************************************************************************************************************/
#include "ATMEGA_scheduler.h"

/* PRIVATE VARIABLES ************************************************************************************************************************************/
static taskDescriptor * taskList = NULL;                            //List with all the tasks

/*FUNCTION DEFINITION ***********************************************************************************************************************************/
static void scheduler_update(void) {
	//set the head of the task list to a variable current
	taskDescriptor* current = taskList;

	//iterate over the task list and update the expire time
	while (current != NULL) {
		if((current->expire == 0) && (current->period == 0)) {
			current->execute = 1;
		} else {
			current->expire--;
			if (current->expire == 0) {
				current->execute = 1;
				if (current->period > 0) {
					current->expire = current->period;
				}
			}
		}
		current = current->next;
	}
	//set the head of the task list back to the variable current
	current = taskList;
}

void scheduler_init() {
	//enable interrupts
	sei();

	//set the callback of timer1 ISR to the function scheduler_update
	timer1_setCallback(&scheduler_update);

	//start timer 1 firing every 100 mills
	timer1_start();
}

void scheduler_run() {
	//set variable current to the head of the task
	taskDescriptor * current = taskList;

	task_t task = NULL;		//task to be executed
	void * param = NULL;	//parameter to be passed
	bool execute = false;	//execution flag

	//run the scheduler in a superloop
	while (1) {
		//iterate over the tasks list to find any task to be executed
		while (current != NULL) {
			ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
				if (current->execute == 1) {
					task = current->task;		//store the task
					param = current->param;		//store the parameter
					execute = true;				//set the flag
					current->execute = 0;		//reset the flag for the task
					//if the task is not periodic, remove it
					if (current->period == 0) {
						scheduler_remove(current);
					}
				}
				current = current->next;
			}

			//execute the task if it's marked for execution
			if(execute) {
				task(param);
				execute = false;
			}
		}
		//set the variable current to the head of the list
		current = taskList;
	}
}

bool scheduler_add(taskDescriptor * toAdd) {
	//check if the added task to the linkedList is NULL
	if (toAdd == NULL) {
		return false;
	}
	//check if the added task to the linked list is the first entry
	ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
		if (taskList == NULL) {
			taskList = toAdd;
			taskList->next = NULL;
			return true;
		}
		//check if the list is only one-noded
		if (taskList->next == NULL) {
			//check if the task is already scheduled, otherwise add it
			if (taskList == toAdd) {
				return false;
			} else {
				taskList->next = toAdd;
				taskList->next->next = NULL;
				return true;
			}
		} else {
			//set the current to the head of the linkedlist
			taskDescriptor* current = taskList;

			//add the task to the end of the list if it's not already on it
			while (current->next != NULL) {
				if (current->next == toAdd) {
					return false;
				}
				current = current->next;
			}
			current->next = toAdd;
			current->next->next = NULL;
			return true;
		}
	}
	return false;
}

void scheduler_remove(taskDescriptor * toRemove) {
	//set the head of the linkedlist to a variable current
	taskDescriptor* current = taskList;

	//check if the task to be removed from the linked list is invalid
	if (toRemove == NULL) {
		return;
	}
	ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
		//Iterate over the linked list until the struct to be removed is found
		if (taskList == toRemove) {
			taskList = taskList->next;
		} else if (current != NULL) {
			while (current->next != NULL) {
				if (current->next == toRemove) {
					current->next = current->next->next;
					return;
				}
				current = current->next;
			}
		}
	}
}


