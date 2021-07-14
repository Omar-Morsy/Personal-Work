#ifndef SES_LED_H_
#define SES_LED_H_

/* INCLUDES ******************************************************************/
#include "ses_common.h"

/* DEFINES & MACROS **********************************************************/
// LED wiring on SES board
#define LED_RED_PORT       	PORTG
#define LED_RED_PIN         	1

#define LED_YELLOW_PORT 	PORTF
#define LED_YELLOW_PIN      	7

#define LED_GREEN_PORT 		PORTF
#define LED_GREEN_PIN       	6

/* FUNCTION PROTOTYPES *******************************************************/
/**
 * Initializes red led
 */
void led_redInit(void);

/**
 * Toggles red led
 */
void led_redToggle(void);

/**
 * Enables red led
 */
void led_redOn(void);

/**
 * Disables red led
 */
void led_redOff(void);

/**
 * Initializes yellow led
 */
void led_yellowInit(void);

/**
 * Toggles yellow led
 */
void led_yellowToggle(void);

/**
 * Enables yellow led
 */
void led_yellowOn(void);

/**
 * Disables yellow led
 */
void led_yellowOff(void);

/**
 * Initializes green led
 */
void led_greenInit(void);

/**
 * Toggles green led
 */
void led_greenToggle(void);

/**
 * Enables green led
 */
void led_greenOn(void);

/**
 * Disables green led
 */
void led_greenOff(void);

#endif /* SES_LED_H_ */
