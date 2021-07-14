#ifndef SCHEDULER_H_
#define SCHEDULER_H_

/*INCLUDES *******************************************************************/
#include <util/atomic.h>
#include "ses_common.h"
#include "ses_timer.h"

/* TYPES ********************************************************************/
/** type of function pointer for tasks */
typedef void (*task_t)(void*);

/** type of the system time */
typedef uint32_t systemTime_t;

/** structure for handing time */
struct time_t {
uint8_t hour;
uint8_t minute;
uint8_t second;
uint16_t milli;
};

/** Task structure to schedule tasks */
typedef struct taskDescriptor_s {
	task_t task;          ///< function pointer to call
	void *  param;        ///< pointer, which is passed to task when executed
	uint16_t expire;      ///< time offset in ms, after which to call the task
	uint16_t period;      ///< period of the timer after firing; 0 means exec once
	uint8_t execute:1;    ///< for internal use
	uint8_t reserved:7;   ///< reserved
	struct taskDescriptor_s * next; ///< pointer to next task, internal use
} taskDescriptor;


/* FUNCTION PROTOTYPES *******************************************************/
/**
 * Initializes the task scheduler. Uses hardware timer2 of the AVR.
 */
void scheduler_init();

/**
 * Runs scheduler in an infinite loop.
 */
void scheduler_run();

/**
 * @param td   				Pointer to taskDescriptor structure. The scheduler takes
 *             				possesion of the memory pointed at by td until the task
 *             				is removed by scheduler_remove or -- in case of a 
 *             				non-periodic task -- the task is executed. td->expire 
 *             				and td->execute are changed to by the task scheduler.
 *	
 * @return     				false, if task is already scheduled or invalid (NULL)
 *             				true, if task was successfully added to scheduler and 
 *             				will be executed after td->expire ms.
 * Adds a new task to the scheduler.
 * May be called from any context (interrupt or main program)
 */
bool scheduler_add(taskDescriptor * td);

/**
 * @param td				pointer to task descriptor to remove
 * Removes a task from the scheduler.
 * May be called from any context (interrupt or main program)
 * */
void scheduler_remove(taskDescriptor * td);

/**
 * @return systemTime_t		Time in uint32_t format (milliseconds counter)	
 * Get the current system time
 */
systemTime_t scheduler_getTime();

/**
 * @param time				Structure of time to be set to the scheduler time
 * Set the system time
 */
void scheduler_setTime(systemTime_t time);

/**
 * @return time_t			Time in structural format : h,m,s,ms
 * Get the formatted time
 */
struct time_t scheduler_getTimeFormated();

#endif /* SCHEDULER_H_ */
