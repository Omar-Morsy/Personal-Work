#ifndef SES_BUTTON_H_
#define SES_BUTTON_H_

/* INCLUDES ******************************************************************/
#include "ses_common.h"
#include "ses_timer.h"

/* DEFINES & MACROS **********************************************************/
#define BUTTONS_PORT       	    PORTB
#define BUTTON_ROTARY_PIN         	6
#define BUTTON_JOYSTICK_PIN        	7
#define BUTTON_NUM_DEBOUNCE_CHECKS  5

/* FUNCTION PROTOTYPES *******************************************************/
/**
 * @param debouncing        True if debouncing is activated
 * Initializes rotary encoder and joystick button
 */
void button_init(bool debouncing);

/** 
 * Get the state of the joystick button.
 */
bool button_isJoystickPressed(void);

/** 
 * @return bool             True if the rotary is pressed, false otherwise
 * Get the state of the rotary button.
 */
bool button_isRotaryPressed(void);

/**
 * Type definition of Pointer to function
 */
typedef void (*pButtonCallback)();

/**
 * @param callback          Pointer Callback function to be called when rotary is pressed
 * Setter function to store given pointer to function in a variable
 */
void button_setRotaryButtonCallback(pButtonCallback callback);

/**
 * @param callback          Pointer Callback function to be called when joystick is pressed
 * Setter function to store given pointer to function in a variable
 */
void button_setJoystickButtonCallback(pButtonCallback callback);

/**
 * A function to check for button debouncing (press / release)
 */
void button_checkState(void);


#endif /* SES_BUTTON_H_ */
