#ifndef MAIN_H_
#define MAIN_H_

/* INCLUDES ***********************************************************/
#include "ses_button.h"
#include "ses_scheduler.h"
#include "ses_lcd.h"
#include "ses_led.h"
#include "ses_uart.h"
#include "ses_rotary.h"

/* FSM MACROS *********************************************************/
#define TRANSITION(newState) (fsm->state = newState, RET_TRANSITION)

enum{ ENTRY = (uint8_t)0, EXIT = (uint8_t)1, JOYSTICK_PRESSED = (uint8_t)2, ROTARY_PRESSED = (uint8_t)3, ALARM_CHECK = (uint8_t)4, ROTARY_CLOCKWISE = (uint8_t)5,ROTARY_COUNTERCLOCKWISE = (uint8_t)6};

typedef struct fsm_s Fsm;     //< typedef for alarm clock state machine
typedef struct event_s Event; //< event type for alarm clock fsm

/** return values */
enum {
    RET_HANDLED,    //< event was handled
    RET_IGNORED,    //< event was ignored; not used in this implementation
    RET_TRANSITION  //< event was handled and a state transition occurred
};

typedef uint8_t fsmReturnStatus;                         //< typedef to be used with above enum
typedef fsmReturnStatus (*State)(Fsm *, const Event*);   //< for state event handler functions


struct fsm_s {
    State state;             //< current state, pointer to event handler
    bool isAlarmEnabled;     //< flag for the alarm status
    struct time_t timeSet;   //< var for the clock
    struct time_t alarmSet;   //< var for the alarm
};


typedef struct event_s {
    uint8_t      signal;     //< identifies the type of event
} Event;

/* dispatches events to state machine, called in application*/
inline static void fsm_dispatch(Fsm* fsm, const Event* event) {
    static Event entryEvent = {.signal = ENTRY};
    static Event exitEvent = {.signal = EXIT};
    State s = fsm->state;
    fsmReturnStatus r = fsm->state(fsm, event);
    if (r==RET_TRANSITION) {
        s(fsm, &exitEvent);           //< call exit action of last state
        fsm->state(fsm, &entryEvent); //< call entry action of new state
    }
}
/* sets and calls initial state of state machine */
inline static void fsm_init(Fsm* fsm, State init) {
    //... other initialization
    Event entryEvent = {.signal = ENTRY};
    fsm->state = init;
    fsm->state(fsm, &entryEvent);
}
/* FUNCTION PROTOTYPES ************************************************/
/**
 * @param fsm       Reference/Pointer to the state machine 
 * @return          bool true if alarm matches the actual scheduler time, false otherwise
 * Ask for the actual time from the scheduler and compare it with the alarm time
 */
bool check_alarm(Fsm * fsm);

/**
 * @param fsm       Reference/Pointer to the state machine 
 * Clear the screen and print the time to be set on the first line on LCD
 */
void lcd_print_updateClkCfg(Fsm * fsm);

/**
 * @param fsm       Reference/Pointer to the state machine 
 * Clear the screen and print the actual time on the first line on LCD
 */
void lcd_print_updateClk(Fsm *  fsm);

/**
 * @param fsm       Reference/Pointer to the state machine 
 * Clear the screen and print the clock time to be set on the first line on LCD
 */
void lcd_print_updateAlarmCfg(Fsm * fsm);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Reset clock and alarm time. Wait for user key press to initialize the actual clock time
 */
fsmReturnStatus clk_init(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Configure the hour value of the actual clock time
 */
fsmReturnStatus clk_setHour(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Configure the minute value of the actual clock time
 */
fsmReturnStatus clk_setMin(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Start the previously defined actual clock time
 */
fsmReturnStatus clk_run(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Enable alarm
 */
fsmReturnStatus clk_alarmEnabled(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Disable alarm
 */
fsmReturnStatus clk_alarmDisabled(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Configure the hour value of the alarm
 */
fsmReturnStatus clk_setAlarmHour(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Configure the minute value of the alarm
 */
fsmReturnStatus clk_setAlarmMin(Fsm * fsm, const Event * e);

/**
 * @param fsm       Reference/Pointer to the state machine
 * @param e         Reference/Pointer to the event structure
 * Initiate the alarm settings
 */
fsmReturnStatus clk_alarmRun(Fsm * fsm, const Event * e);

#endif /* MAIN_H_ */
