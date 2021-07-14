#ifndef SES_MOTORFREQUENCY_H_
#define SES_MOTORFREQUENCY_H_

/* INCLUDES ***********************************************************/
#include <util/atomic.h>
#include "ses_common.h"
#include "ses_led.h"

/* FUNCTION PROTOTYPES ************************************************/
/**
 * Initialize the measurement of motor frequency
 */
void motorFrequency_init(void);

/**
 * @return	        The most recent reading of motor frequency
 * Get the most recent measurement of the frequency
 */
uint16_t motorFrequency_getRecent(void);

/**
 * @return	        The median of the frequency values
 * Get the median of the frequency readings
 */
uint16_t motorFrequency_getMedian(void);

#endif /* SES_MOTORFREQUENCY_H_ */
