#ifndef _TASK_5_H_
#define _TASK_5_H_

/* INCLUDES ***********************************************************/
#include "ses_pwm.h"
#include "ses_motorFrequency.h"
#include "ses_button.h"
#include "ses_scheduler.h"
#include "ses_lcd.h"
#include "ses_uart.h"

/* FUNCTION PROTOTYPES ************************************************/
/**
 * Toggle the motor by setting its duty cycle to predefined value (170)
 */
void rotary_toggleMotor(void);

/**
 * Toggle the PID controller by adding it to the scheduler
 */
void joystick_togglePid(void);

/**
 * @param	targetFrequency	the target frequency of the motor
 * PID Controller for DC Motor
 */
void pidController(void * targetFrequency);

/**
 * @param	param	unused void pointer
 * Print motor speed and median on the LCD
 */
void printMotorSpeed(void * param);

/**
 * @param	param	unused void pointer
 * plot the response of the motor to the LCD
 */
void plotMotorResponse(void * param);

#endif /* _TASK_5_H_ */
