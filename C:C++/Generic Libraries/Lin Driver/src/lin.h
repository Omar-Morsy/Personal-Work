#ifndef LIN_H_
#define LIN_H_

/* INCLUDES *********************************************************************************************************************/
#include "timers.h"

/* DEFINES & MACROS *************************************************************************************************************/
#define LIN_SERIAL                    Serial1                    //Serial used to communicate with the lin tranciever
#define LIN_BAUD_RATE                   19200                    //The baud rate of the lin serial communication
#define TX_PIN                              1                    //Tx pin of the serial communication
#define RX_PIN                              0                    //Rx pin of the serial communication
#define INTERBYTE_SPACE_DELAY               1                    //The delay required by lin protocol between bytes
#define RESPONSE_SPACE_DELAY                5                    //The delay required by lin protocol between header and response
#define SERIAL_DBG                          1                    //1 to enable serial debgug information and 0 otherwise
#define LIN_TIMEOUT_MS                   3000                    //Timeout of lin busy wait loop in ms
#define CHECKSUM_CAL                      255                    //To be used for deduction for checksum calculation
#define LIN_BREAK_DURATION                 15                    //Number of bits in the break
#define BIT(data,shift) ((pid&(1<<shift))>>shift)                //Bit shifting (To get a bit in a byte)

/*PROTOTYPES ********************************************************************************************************************/
/**
 * Lin initilization of the controller and the lin transceiver
 */
void lin_init();

/**
 * @param "pid" is the pid of type uint8_t of the given frame to write
 * @param "data" is an array with the given frame data to be sent to the slave
 * @param "data_size" is a uint8_t that represents the size of the data to be sent to the slave (without calc. the pid nor crc)
 * @param "is classic" is a boolean that indicates if the crc should be classic or enhanced.
 * Lin write data to the lin slave
 */
void lin_write(uint8_t pid, const uint8_t * data, uint8_t data_size, bool isClassic);

/**
 * @param "pid" is the pid of type uint8_t of the given frame to write
 * @param "buffer" is an array for storing the response frame from the slave
 * @param "data_size" is a uint8_t that represents the size of the data to be read from the slave (without calc. the pid nor crc)
 * @param "is classic" is a boolean that indicates if the crc should be classic or enhanced.
 * @return boolean that indicates a good or bad checksum
 * Lin read data to the lin slave
 */
bool lin_read(uint8_t pid, uint8_t * buffer, uint8_t buffer_size, bool isClassic);

#endif /* LIN_H_ */