#ifndef TASK_4_2_H_
#define TASK_4_2_H_

/* INCLUDES *******************************************************************/

#include "ses_lcd.h"
#include "ses_led.h"
#include "ses_button.h"
#include "ses_common.h"
#include "ses_timer.h"
#include "ses_scheduler.h"

/* PROTOTYPES *****************************************************************/

/**
 * debounce the buttons
 */
void button_debounce(void * param);

/**
 * toggle the LED and schedule the task to turn off LED
 */
void led_yellowJoystick(void);

/**
 * toggle yellow LED and reset the joystick button flag
 */
void led_yellowAutoOff(void * param);

/**
 * update the LCD with stopwatch time
 */
void stopwatch_update(void*);

/**
 * A function to toggle the stopWatch on the LCD (resume / stop).
 */
void stopwatch_toggle(void);

/**
 * A function to toggle the specified led using an enum
 *
 * @param	color	void pointer to the color of LED
 */
void led_toggle(void * color);

/**
 * enum to specify the color of the led
 */
typedef enum {
	RED = 0,
	GREEN = 1,
	YELLOW = 2
}color;

#endif	/* TASK_4_2_H_ */
