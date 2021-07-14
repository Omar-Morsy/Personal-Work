#ifndef SES_TIMER_H_
#define SES_TIMER_H_

/*INCLUDES *******************************************************************/

#include "ses_common.h"


/*PROTOTYPES *****************************************************************/
/**type of function pointer used as timer callback
 */
typedef void (*pTimerCallback)(void);

/**
 * @param cb        valid pointer to callback function
 * Sets a function to be called when the timer fires. If NULL is passed, no callback is executed when the timer fires.
 */
void timer2_setCallback(pTimerCallback cb);

/**
 * Starts hardware timer 2 of MCU with a period of 1 ms.
 */
void timer2_start(void);

/**
 * Stops timer 2.
 */
void timer2_stop(void);

/**
 * @param cb        pointer to the callback function; if NULL, no callback will be executed.
 * Sets a function to be called when the timer fires.
 */
void timer1_setCallback(pTimerCallback cb);

/**
 * Start timer 1 of MCU to trigger on compare match every 5ms.
 */
void timer1_start(void);

/**
 * Stops timer 1 of the MCU if it is no longer needed.
 */
void timer1_stop(void);

#endif /* SES_TIMER_H_ */
