/*INCLUDES ************************************************************************************************************************/
#include "ESP_scheduler.h"

/*GLOBAL VARIABLES ****************************************************************************************************************/
static taskDescriptor task_print;

/*PROGRAM *************************************************************************************************************************/
static void print(void * parameter) {
    Serial.println(millis());
}

void setup() {
  Serial.begin(115200);
  while(!Serial);

  scheduler_init();

  task_print.task = (task_t)&print;
  task_print.param = NULL;
  task_print.expire = 1000 / 100;  //Scheduler is running every 100ms, if else change divisor
  task_print.period = 1000 / 100;  //Scheduler is running every 100ms, if else change divisor

  scheduler_add(&task_print);

  scheduler_run();
}

void loop() {
}