
#ifndef SES_ROTARY_H_
#define SES_ROTARY_H_

/* INCLUDES ***********************************************************/
#include <util/atomic.h>
#include "ses_common.h"
#include "ses_timer.h"
#include "ses_scheduler.h"

/* FUNCTION PROTOTYPES ************************************************/
/**
 * Type of function pointer used as rotary encoder call back
 */
typedef void (*pTypeRotaryCallback)();

/**
 * A function to initialize the rotary encoder
 */
void rotary_init();

/**
 * @param cb         Pointer Callback function to be called when joystick is pressed rotary rotates clockwise
 * A function to set the call back function of the clockwise
 */
void rotary_setClockwiseCallback(pTypeRotaryCallback cb);

/**
 * @param cb         Pointer Callback function to be called when rotary rotates counter clockwise
 * A function to set the call back function of the counter-clockwise
 */
void rotary_setCounterClockwiseCallback(pTypeRotaryCallback cb);

/**
 * A function to check for the debouncing of the rotary in both directions
 */
void rotary_checkState(void);


#endif /* SES_ROTARY_H_ */
