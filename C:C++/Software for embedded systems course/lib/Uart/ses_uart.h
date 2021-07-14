#ifndef SES_UART_H_
#define SES_UART_H_

/* INCLUDES ********************************************************************/
#include <stdio.h>
#include <stdint.h>

/* EXTERNALS ********************************************************************/
/**
 * File descriptor for UART. You can use this in conjunction with fprintf.
 * Example fprintf(uartout, "Hello World %d\n",2017);
 */
extern FILE* uartout;

/* PROTOTYPES ********************************************************************/
/**
 * @param baudrate	    baudrate of uart (e.g. 57600)
 * Initializes UART with given baud rate. By default, 8 databits and 1 stop bit are used.
 */
void uart_init(uint32_t baudrate);

/**
 * @return character
 * Reads a character from UART.
 */
uint8_t uart_getc();

/**
 * @param chr           character to write
 * Writes a character to UART.
 */
void uart_putc(uint8_t chr);

#endif /* SES_UART_H_ */
