#ifndef _TASK_6_5_H_
#define _TASK_6_5_H_

/* INCLUDES ***********************************************************/
#include "ses_common.h"
#include "ses_uart.h"
#include "ses_button.h"
#include "ses_led.h"
#include <avr/boot.h>
#include <avr/pgmspace.h>

/* FUNCTION PROTOTYPES ************************************************/
/**
 * @param	mem		memory type (Flash or EEPROM)
 * @param	size	size of block
 * @param	address	address inside the page
 * Read from memory block
 */
 void BlockRead(unsigned char mem, unsigned int size, uint16_t * address);

/**
 * @param	mem		memory type (Flash or EEPROM)
 * @param	size	size of block
 * @param	address	address inside the page
 * Load to memory block
 */
 unsigned char BlockLoad(unsigned char mem, unsigned int size, uint16_t *address);

#endif /* _TASK_6_5_H_ */
