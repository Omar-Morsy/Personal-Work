/* INCLUDES ***********************************************************/
#include "main.h"

/* GLOBAL VARIABLES *****************************************************************/
static Fsm clkFsm; //Instance of the finite state machine

static taskDescriptor task1; // Toggle Green led with the clock seconds
static taskDescriptor task2; // Checks if the alarm time = the actual time to fire the alarm
static taskDescriptor task3; // Update the display with the current time
static taskDescriptor task4; // Task 4 turn off the alarm after 5 seconds
static taskDescriptor task5; // Toggle the red LED for 4 Hz (alarm)
static taskDescriptor task6; // Button debouncing
static taskDescriptor task7; // Rotary directions debouncing

/* DISPATCH FUNCTIONS ****************************************************************/
static void joystickPressedDispatch(void * param) {
    Event e = {.signal = JOYSTICK_PRESSED};
    fsm_dispatch(&clkFsm, &e);
}

static void rotaryPressedDispatch(void * param) {
    Event e = {.signal = ROTARY_PRESSED};
    fsm_dispatch(&clkFsm, &e);
}

static void rotaryClockwiseDispatch(void * param) {
    Event e = {.signal = ROTARY_CLOCKWISE};
    fsm_dispatch(&clkFsm, &e);
}

static void rotaryCounterClockwiseDispatch(void * param) {
    Event e = {.signal = ROTARY_COUNTERCLOCKWISE};
    fsm_dispatch(&clkFsm, &e);
}

static void alarmCheckDispatch(void * param) {
    Event e = {.signal = ALARM_CHECK};
    fsm_dispatch(&clkFsm, &e);
}

/* MAIN ******************************************************************************/
int main(void) {
	//Initializations of LED and clear them off
	sei();
	button_init(true);
	lcd_init();
	led_redInit();
	led_greenInit();
	led_yellowInit();
	led_redOff();
	led_greenOff();
	led_yellowOff();

	//set the callback functions for the buttons
	button_setJoystickButtonCallback(&joystickPressedDispatch);
	button_setRotaryButtonCallback(&rotaryPressedDispatch);

	//set the callback functions for the rotary directions
	rotary_setClockwiseCallback(&rotaryClockwiseDispatch);
	rotary_setCounterClockwiseCallback(&rotaryCounterClockwiseDispatch);

	//FSM initializations
	fsm_init((Fsm*)&clkFsm, clk_init);


	//Task 1 to toggle Green LED every 1 second with the clock's seconds
	task1.task = (task_t)&led_greenToggle;
	task1.param = NULL;
	task1.expire = 1000;
	task1.period = 1000;

	//Task 2 to check the alarm with time every 1 second
	task2.task = (task_t)&alarmCheckDispatch;
	task2.param = NULL;
	task2.expire = 1000;
    task2.period = 1000;

    //Task 3 to update the display with the current time
    task3.task = (task_t)&lcd_print_updateClk;
    task3.param = &clkFsm;
    task3.expire = 1000;
    task3.period = 1000;

	//Task 4 turn off the alarm after 5 seconds
    task4.task = (task_t)&led_redOff;
    task4.param = NULL;
    task4.expire = 5000;
    task4.period = 0;

    //Task 5 to toggle the red LED for 4 Hz
    task5.task = (task_t)&led_redToggle;
    task5.param = NULL;
    task5.expire = 250;
    task5.period = 250;

	//Task 6 to add button debouncing to the scheduler
	task6.task = (task_t)(&button_checkState);
	task6.param = NULL;
	task6.expire = (uint16_t)5;
	task6.period = (uint16_t)5;
	task6.execute = 0;

	//Task 7 to add rotary directions debouncing to the scheduler
	task7.task = (task_t)(&rotary_checkState);
	task7.param = NULL;
	task7.expire = (uint16_t)5;
	task7.period = (uint16_t)5;
	task7.execute = 0;

	//initialize, add tasks and run the scheduler
	scheduler_init();
	scheduler_add(&task6);
	scheduler_add(&task7);
	scheduler_run();

}

/* STATE MACHINE ********************************************************************/
fsmReturnStatus clk_init(Fsm* fsm, const Event* e) {
	switch(e->signal) {
	    case ENTRY:
	    	//Reset the clock and alarm time and print set time on display
	    	fsm->isAlarmEnabled = false;
	    	fsm->timeSet.hour = 0;
	    	fsm->timeSet.minute = 0;
	    	fsm->timeSet.second = 0;
	    	fsm->timeSet.milli = 0;
	    	lcd_print_updateClkCfg(fsm);
	    	return RET_HANDLED; break;

	    case EXIT:
	    	//Do nothing
	    	return RET_HANDLED; break;

		case ROTARY_PRESSED:
			//increment the hour of setting the time and move to the next state to set the hour
			fsm->timeSet.hour ++;
			return TRANSITION(clk_setHour); break;

		case ROTARY_CLOCKWISE:
			//increment the hour of setting the time and move to the next state to set the hour
			fsm->timeSet.hour ++;
			return TRANSITION(clk_setHour); break;

		case ROTARY_COUNTERCLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//move to the next state to set the minutes
			return TRANSITION(clk_setMin); break;

		case ALARM_CHECK:
			//Do nothing
			return RET_HANDLED; break;

		default: return RET_IGNORED; break;
	}
}

fsmReturnStatus clk_setHour(Fsm* fsm, const Event* e) {
	switch(e->signal) {
		case ENTRY:
			//update the configuration of the clock
			lcd_print_updateClkCfg(fsm);
			return RET_HANDLED; break;

		case EXIT:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_PRESSED:
			//increment the clock hours and update the display
			fsm->timeSet.hour++;
			if (fsm->timeSet.hour >= 24) {
				fsm->timeSet.hour = 0;
			}
			lcd_print_updateClkCfg(fsm);
			return RET_HANDLED; break;

		case ROTARY_CLOCKWISE:
			//increment the clock hours and update the display
			fsm->timeSet.hour++;
			if (fsm->timeSet.hour >= 24) {
				fsm->timeSet.hour = 0;
			}
			lcd_print_updateClkCfg(fsm);
			return RET_HANDLED; break;

		case ROTARY_COUNTERCLOCKWISE:
			//decrement the clock hours and update the display
			fsm->timeSet.hour--;
			if (fsm->timeSet.hour < 0) {
				fsm->timeSet.hour = 0;
			}
			lcd_print_updateClkCfg(fsm);
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//move to the next state to set the clock minutes
			return TRANSITION(clk_setMin); break;

		case ALARM_CHECK:
			//Do nothing
			return RET_HANDLED; break;

		default: return RET_IGNORED; break;

	}
}

fsmReturnStatus clk_setMin(Fsm* fsm, const Event* e) {
	switch(e->signal) {
			case ENTRY:
				//update the configuration of the clock
				lcd_print_updateClkCfg(fsm);
				return RET_HANDLED; break;

			case EXIT:
				//Set the scheduler time with the time given by the user
				scheduler_setTime((fsm->timeSet.hour * 60 * 60 * 1000) + (fsm->timeSet.minute * 60 * 1000));
				return RET_HANDLED; break;

			case ROTARY_PRESSED:
				//increment the minutes and update the display
				fsm->timeSet.minute++;
				if (fsm->timeSet.minute >= 60) {
					fsm->timeSet.minute = 0;
				}
				lcd_print_updateClkCfg(fsm);
				return RET_HANDLED; break;

			case ROTARY_CLOCKWISE:
				//increment the minutes and update the display
				fsm->timeSet.minute++;
				if (fsm->timeSet.minute >= 60) {
					fsm->timeSet.minute = 0;
				}
				lcd_print_updateClkCfg(fsm);
				return RET_HANDLED; break;

			case ROTARY_COUNTERCLOCKWISE:
				//decrement the minutes and update the display
				fsm->timeSet.minute--;
				if (fsm->timeSet.minute < 0) {
					fsm->timeSet.minute = 0;
				}
				lcd_print_updateClkCfg(fsm);
				return RET_HANDLED; break;

			case JOYSTICK_PRESSED:
				//move to the next state to run the clock using the specified minutes and hours
				return TRANSITION(clk_run); break;

			case ALARM_CHECK:
				//Do nothing
				return RET_HANDLED; break;

			default: return RET_IGNORED; break;

		}
}

fsmReturnStatus clk_run(Fsm* fsm, const Event* e) {
	switch(e->signal) {
				case ENTRY:
					//add task 1, 2 and 3 to the scheduler
					scheduler_add(&task1);
					scheduler_add(&task3);
					return RET_HANDLED; break;

				case EXIT:
					//Do nothing
				    return RET_HANDLED; break;

				case ROTARY_PRESSED:
					//move to the next state to enable the alarm
					return TRANSITION(clk_alarmEnabled); break;

				case ROTARY_CLOCKWISE:
					//Do nothing
					return RET_HANDLED; break;

				case ROTARY_COUNTERCLOCKWISE:
					//Do nothing
					return RET_HANDLED; break;

				case JOYSTICK_PRESSED:
					//move to the next state to configure the alarm + disable task 2 (alarm) + disable task 3 (display time)
					scheduler_remove(&task3);
					scheduler_remove(&task2);
					return TRANSITION(clk_setAlarmHour); break;

				case ALARM_CHECK:
					//check if alarm is enabled and alarm = actual time then go to then next state to fire the alarm else do nothing
					if (check_alarm(fsm) == true && fsm->isAlarmEnabled == true) {
						return TRANSITION(clk_alarmRun);
					} else {
						return RET_HANDLED;
					}
					break;

				default: return RET_IGNORED; break;

			}
}

fsmReturnStatus clk_alarmEnabled(Fsm* fsm, const Event* e) {
	switch(e->signal) {
		case ENTRY:
			//Turn yellow led on and set the alarmEnabled boolean to true + add task 2 to the scheduler
			led_yellowOn();
			scheduler_add(&task2);
			fsm->isAlarmEnabled = true;
			return RET_HANDLED; break;

		case EXIT:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_PRESSED:
			//move to the next state to disable the alarm
			return TRANSITION(clk_alarmDisabled); break;

		case ROTARY_CLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_COUNTERCLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//move to the next state to configure the alarm + disable task 2 (alarm) + disable task 3 (display time)
			scheduler_remove(&task3);
			scheduler_remove(&task2);
			return TRANSITION(clk_setAlarmHour); break;

		case ALARM_CHECK:
			//check if alarm is enabled and alarm = actual time then go to then next state to fire the alarm else do nothing
			if (check_alarm(fsm) == true && fsm->isAlarmEnabled == true) {
				return TRANSITION(clk_alarmRun);
			} else {
				return RET_HANDLED;
			}
		    break;

		default: return RET_IGNORED; break;
	}
}

fsmReturnStatus clk_alarmDisabled(Fsm* fsm, const Event* e) {
	switch(e->signal) {
		case ENTRY:
			//Turn yellow led off and set the alarmEnabled boolean to false + remove task 2 from scheduler
			scheduler_remove(&task2);
			led_yellowOff();
			fsm->isAlarmEnabled = false;
			return RET_HANDLED; break;

		case EXIT:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_PRESSED:
			//move to the next state to enable the alarm
			return TRANSITION(clk_alarmEnabled); break;

		case ROTARY_CLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_COUNTERCLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//move to the next state to configure the alarm + disable task 2 (alarm) + disable task 3 (display time)
			scheduler_remove(&task3);
			scheduler_remove(&task2);
			return TRANSITION(clk_setAlarmHour); break;

		case ALARM_CHECK:
			//Do nothing (alarm is disabled)
			return RET_HANDLED; break;

		default: return RET_IGNORED; break;
	}
}

fsmReturnStatus clk_setAlarmHour(Fsm* fsm, const Event* e) {
	switch(e->signal) {
		case ENTRY:
			//reset the struct of timeSet
	    	fsm->alarmSet.hour = 0;
	    	fsm->alarmSet.minute = 0;
	    	fsm->alarmSet.second = 0;
	    	fsm->alarmSet.milli = 0;

			//update the configuration of the alarm
			lcd_print_updateAlarmCfg(fsm);
			return RET_HANDLED; break;

		case EXIT:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_PRESSED:
			//increment the alarm hours and update the display
			fsm->alarmSet.hour ++;
			if (fsm->alarmSet.hour >= 24) {
				fsm->alarmSet.hour = 0;
			}
			lcd_print_updateAlarmCfg(fsm);
			return RET_HANDLED; break;

		case ROTARY_CLOCKWISE:
			//increment the alarm hours and update the display
			fsm->alarmSet.hour ++;
			if (fsm->alarmSet.hour >= 24) {
				fsm->alarmSet.hour = 0;
			}
			lcd_print_updateAlarmCfg(fsm);
			return RET_HANDLED; break;

		case ROTARY_COUNTERCLOCKWISE:
			//decrement the alarm hours and update the display
			fsm->alarmSet.hour --;
			if (fsm->alarmSet.hour < 0) {
				fsm->alarmSet.hour = 0;
			}
			lcd_print_updateAlarmCfg(fsm);
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//move to the next state to set the clock minutes
			return TRANSITION(clk_setAlarmMin); break;

		case ALARM_CHECK:
			//Do nothing
			return RET_HANDLED; break;

		default: return RET_IGNORED; break;

	}
}

fsmReturnStatus clk_setAlarmMin(Fsm* fsm, const Event* e) {
	switch(e->signal) {
			case ENTRY:
				//Do nothing
				return RET_HANDLED; break;

			case EXIT:
				//Do nothing
				return RET_HANDLED; break;

			case ROTARY_PRESSED:
				//increment the alarm minutes and update the display
				fsm->alarmSet.minute ++;
				if (fsm->alarmSet.minute >= 60) {
					fsm->alarmSet.minute = 0;
				}
				lcd_print_updateAlarmCfg(fsm);
				return RET_HANDLED; break;

			case ROTARY_CLOCKWISE:
				//increment the alarm minutes and update the display
				fsm->alarmSet.minute ++;
				if (fsm->alarmSet.minute >= 60) {
					fsm->alarmSet.minute = 0;
				}
				lcd_print_updateAlarmCfg(fsm);
				return RET_HANDLED; break;

			case ROTARY_COUNTERCLOCKWISE:
				//decrement the alarm minutes and update the display
				fsm->alarmSet.minute --;
				if (fsm->alarmSet.minute < 0) {
					fsm->alarmSet.minute = 0;
				}
				lcd_print_updateAlarmCfg(fsm);
				return RET_HANDLED; break;

			case JOYSTICK_PRESSED:
				//move to the next state to run the clock using the specified minutes and hours
				return TRANSITION(clk_run); break;

			case ALARM_CHECK:
				//Do nothing
				return RET_HANDLED; break;

			default: return RET_IGNORED; break;

		}
}

fsmReturnStatus clk_alarmRun(Fsm* fsm, const Event* e) {
	switch(e->signal) {
		case ENTRY:
		    //add task 4 and 5 to the scheduler
		    scheduler_add(&task4);
		    scheduler_add(&task5);
		    return RET_HANDLED; break;

		case EXIT:
			//remove task 4 and 5 from the scheduler + turn red led off
			led_redOff();
			scheduler_remove(&task4);
			scheduler_remove(&task5);
			return RET_HANDLED; break;

		case JOYSTICK_PRESSED:
			//go to the clock run state
			return TRANSITION(clk_run); break;

		case ROTARY_PRESSED:
			//go to the clock run state
			return TRANSITION(clk_run); break;

		case ROTARY_CLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case ROTARY_COUNTERCLOCKWISE:
			//Do nothing
			return RET_HANDLED; break;

		case ALARM_CHECK:
			//Do nothing
			return RET_HANDLED; break;

		default: return RET_IGNORED; break;
	}
}


/* FUNCTION DEFINITION ********************************************************************/
bool check_alarm(Fsm* fsm) {
	//Ask for the actual time from the scheduler and compare it with the alarm time
	struct time_t actualTime = scheduler_getTimeFormated();
	if(actualTime.hour == fsm->alarmSet.hour && actualTime.minute == fsm->alarmSet.minute && fsm->alarmSet.minute > 0) {
		return true;
	}
	return false;
}

void lcd_print_updateClkCfg(Fsm* fsm){
	//clear the screen and print the time to be set on the first line
	lcd_clear();
	lcd_setCursor(0,0);
	fprintf(lcdout, "%d : %d", fsm->timeSet.hour, fsm->timeSet.minute);

	//print a message to the user on the second line
	lcd_setCursor(0,1);
	fprintf(lcdout, "Please set the time");
}

void lcd_print_updateClk(Fsm* fsm){
	//clear the screen and print the actual time on the first line
	struct time_t actualTime = scheduler_getTimeFormated();
	lcd_clear();
	lcd_setCursor(0,0);
	fprintf(lcdout, "%d : %d : %d", actualTime.hour, actualTime.minute, actualTime.second);

	//print a message to the user on the second line
	lcd_setCursor(0,1);
	fprintf(lcdout, "Clock Running");
}

void lcd_print_updateAlarmCfg(Fsm* fsm){
	//clear the screen and print the clock time to be set on the first line
	lcd_clear();
	lcd_setCursor(0,0);
	fprintf(lcdout, "%d : %d", fsm->alarmSet.hour, fsm->alarmSet.minute);

	//print a message to the user on the second line
	lcd_setCursor(0,1);
	fprintf(lcdout, "please set the alarm");

}








