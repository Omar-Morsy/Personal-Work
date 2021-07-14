#ifndef ADA4558_H_
#define ADA4558_H_

/* INCLUDES *********************************************************************************************************************/
#include "lin.h"

/* DEFINES & MACROS *************************************************************************************************************/
#define RESPONSE_FRAME_DELAY              500                    //The delay required by lin protocol between 2 frames

/*PROTOTYPES ********************************************************************************************************************/
/**
 * @param "nad" the nad of the slave
 * @param "address" the eeprom address that needs to be read of the ada4558
 * @param "buffer" an array to store the response frame
 * @param "bufferSize" the size of the buffer array
 * @return boolean that represents the validation of the checksum
 * Reads the given address from the eeprom of the ada4558 and the return the checksum validation and the response frame
 */
bool ada4558_read_eeprom(uint8_t nad, uint8_t address, uint8_t * buffer, uint8_t bufferSize);

/**
 * @param "nad" the nad of the slave
 * @return boolean for a positive or a negative response from ada4558
 * Sends the password to ada4558 to enter configuration mode
 */
bool ada4558_write_password(uint8_t nad);

/**
 * @param "nad" the nad of the slave
 * @param "enable" disable or enable test mode
 * @return boolean for a positive or a negative response from ada4558
 * Sends the password to ada4558 to enter configuration mode
 */
bool ada4558_write_testMode(uint8_t nad, bool enable);

/**
 * @param "nad" the nad of the slave
 * @param "address" address of ada4558 eeprom to be modified
 * @param "data" an array data to be sent (4 bytes)
 * @return boolean for a positive or a negative response from ada4558
 * Sends the password to ada4558 to enter configuration mode
 */
bool ada4558_write_eeprom(uint8_t nad, uint8_t address, uint8_t * data);

/**
 * @param "nad" the nad of the slave
 * @return byte for the eeprom status (in case of bad checksum, 0xFF is returned)
 * checks for the eeprom status
 */
uint8_t ada4558_check_eepromStatus(uint8_t nad);

/**
 * @param "nad" the nad of the slave
 * @param "supplierIdByte1" byte 1 of the new supplierid
 * @param "supplierIdByte2" byte 2 of the new supplierid
 * @return bool if the writing to the eeprom is completed
 * update the eeprom to a new supplier id (It only take effect after reset since the cache will be updated)
 */
bool ada4558_update_supplier_id(uint8_t nad, uint8_t supplierIdByte1, uint8_t suplierIdByte2);

/**
 * @param "nad" the nad of the slave
 * @param "functionIdByte1" byte 1 of the new functionid
 * @param "functionIdByte2" byte 2 of the new functionid
 * @return bool if the writing to the eeprom is completed
 * update the eeprom to a new function id (It only take effect after reset since the cache will be updated)
 */
bool ada4558_update_function_id(uint8_t nad, uint8_t functionIdByte1, uint8_t functionIdByte2);

/**
 * @param "nad" the nad of the slave
 * @param "inad" the inad of the slave
 * @return bool if the writing to the eeprom is completed
 * clears the IDLE_SLP bit in the eeprom (sleep enabled) (It only take effect after reset since the cache will be updated)
 * Assigns the lin protocol to 2.0
 */
bool ada4558_enable_sleep(uint8_t nad, uint8_t inad);


/**
 * @param "nad" the nad of the slave
 * @param "address" is the address in the eeprom to be modified
 * @return bool if the writing to the eeprom is completed
 * writes 4 data bytes to the given address in eeprom 
 * disables NVM while writting then returns back to operation mode after writing
 */
bool ada4558_write_eeprom_nvmCacheDisabled(uint8_t nad, uint8_t address, uint8_t * data, uint8_t dataSize);


/**
 * @param "nad" the nad of the slave
 * @param "inad" the inad of the slave
 * @return bool if the writing to the eeprom is completed
 * sets the IDLE_SLP bit in the eeprom (sleep disabled) 
 * (It only take effect after reset since the cache will be updated)
 * Assigns the lin protocol to 2.0
 */
bool ada4558_disable_sleep(uint8_t nad, uint8_t inad);

/**
 * @param "nad" the nad of the slave
 * @param "newNad" the new nad assigned to the slave
 * @return bool if the writing to the eeprom is completed
 * assign a new nad to the slave 
 * (It only take effect after reset since the cache will be updated)
 */
bool ada4558_update_nad(uint8_t nad, uint8_t newNad);

/**
 * @param "nad" the nad of the slave
 * @param "linpid" the new linpid assigned to the slave
 * @return bool if the writing to the eeprom is completed
 * assign a new linpid to the slave 
 * (It only take effect after reset since the cache will be updated)
 */
bool ada4558_update_linPid1(uint8_t nad, uint8_t linpid1);

/**
 * @param "nad" the nad of the slave
 * @return byte of the linpid1 and 0 in case of incorrect reading
 * Read the linpid1 stored in the eeprom
 */
uint8_t ada4558_get_linPid1(uint8_t nad);

/**
 * @param "linpid1" the linpid1 assigned to the slave
 * @return the sensor data (12 bits)
 * Read the sensor data from the slave
 */
uint16_t ada4558_get_sensorData(uint8_t linpid1);

/**
 * @param "nad" the nad of the slave
 * @return bool if the writing to the eeprom is completed
 * Assigns a 32.5 pga gain to the sensor (It only take effect after reset since the cache will be updated)
 */
bool ada4558_update_pgaGain(uint8_t nad);

#endif /* AdA4558_H_ */