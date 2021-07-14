/*INCLUDES ************************************************************************************************************************/
#include "ATMEGA_scheduler.h"

/*GLOBAL VAR **********************************************************************************************************************/
static taskDescriptor task1; 

/*PROGRAM *************************************************************************************************************************/
void dispatch_print(void * parameter) {
  Serial.println("OK");
}

void setup() {
  Serial.begin(9600);
  while(!Serial);
  Serial.println("Program Start...");

  scheduler_init();

  //task 1 to indicate no credentials are stored
  task1.task = (task_t)(&dispatch_print);  //Call this function
	task1.param = NULL;                      //Parameter to be passed
	task1.expire = 100;                      //Call after milli seconds
	task1.period = 2000;                     //Repeat after millis seconds
	task1.execute = 0;                       //Execute now or wait

  //Add task1 to the scheduler
  scheduler_add(&task1);

  scheduler_run();

}

void loop() {
}