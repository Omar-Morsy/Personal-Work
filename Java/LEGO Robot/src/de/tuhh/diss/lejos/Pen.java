package de.tuhh.diss.lejos;

import lejos.nxt.Motor;


import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

/**
 * The Pen class used to control the movement of the pen 
 * this class implements methods to: 
 * control the pen to move upwards until it touches the touch sensor
 * control the pen to move downwards until it touches the plotting sketch
 * set the motor speed of the pen
 * create setters and getters for the pen motor, touch sensor used to stop pen motor and 
 * the maximum motor angle that can be covered by the pen motor
 */

public class Pen {
	
	private NXTRegulatedMotor penMotor;
	private TouchSensor penTouchSensor;
	private int motorAngle;
	private boolean reverse;
	
	/**
	 * Pen constructor initializes the Pen motor and the touch sensor used to stop the pen movement
     * configure the ports of the pen motor and the touch sensor
	 * @param motorAngle the angle covered by the pen motor
	 * @param reverse a flag condition to check the current positve/negative rotation direction of pen motor
	 */
	
	public Pen (int motorAngle, boolean reverse) {
		this.setPenMotor(Motor.B);
		this.setPenTouchSensor(new TouchSensor(SensorPort.S2));
		this.setMotorAngle(motorAngle);
		this.setReverse(reverse);
	}
	
	/**
	 * penUp method used to control the movement of the pen upwards by moving  
	 * till it presses the touch sensor and then stop the pen motor
	 * @return boolean checks if the pen is touching the touch sensor
	 */
	
	public boolean penUp() {
		if (reverse == false) {
		while (!this.getPenTouchSensor().isPressed()) {
			this.getPenMotor().backward();
		}
		this.getPenMotor().stop();
		return true;
		} else {
			while (!this.getPenTouchSensor().isPressed()) {
				this.getPenMotor().forward();
			}
			this.getPenMotor().stop();
			return true;
		}
	}
	
	/**
	 * penDown method used to control the movement of the pen downwards by 
	 * first checking if the pen is pressing the touch sensor, rotating it by
	 * the calculated pen motor angle till it touches the sketch and then the pen motor stops
	 * @return boolean checks if the pen is touching the sketch and not pressing the touch sensor
	 */
	
	public boolean penDown() {
		if (reverse == false) {
		if (this.getPenTouchSensor().isPressed()) {
			this.getPenMotor().rotate(this.getMotorAngle());
			this.getPenMotor().stop();
			return true;
		} else {
			this.getPenMotor().stop();
			return false;
		}
		} else {
			if (this.getPenTouchSensor().isPressed()) {
				this.getPenMotor().rotate(-this.getMotorAngle());
				this.getPenMotor().stop();
				return true;
			} else {
				this.getPenMotor().stop();
				return false;
			}
		}
	}
	
	/**
	 * setPenMotorSpeed method to set the applied pen motor speed
	 * @param speed the applied speed on the pen motor
	 */
	
	public void setPenMotorSpeed(int speed) {
		this.getPenMotor().setSpeed(speed);
	}
	
	/**
	 * penMotorStop method to stop the motor of the pen
	 */
	
	public void penMotorStop() {
		this.getPenMotor().stop();
	}
	

	/**
	 * getPenMotor is a getter to get the motor used to control the Pen
	 * @return NXTRegulatedMotor returns the motor of the Pen
	 */
	
	public NXTRegulatedMotor getPenMotor() {
		return this.penMotor;
	}

	/**
	 * setPenMotor is a setter to set the Pen motor to be used
	 * @param nxtRegulatedMotor the motor used to control the Pen movement
	 */
	
	public void setPenMotor(NXTRegulatedMotor penMotor) {
		this.penMotor = penMotor;
	}

	/**
	 * getPenTouchSensor is a getter to get the port of Pen touch sensor
	 * @return TouchSensor returns the Touch Sensor port used by the PlotBot to stop the pen
	 */
	public TouchSensor getPenTouchSensor() {
		return penTouchSensor;
	}

	/**
	 * setPenTouchSensor is a setter to set the touch sensor port used to stop the Pen
	 * @param penTouchSensor the touch sensor used to stop the movement of Pen
	 */
	public void setPenTouchSensor(TouchSensor penTouchSensor) {
		this.penTouchSensor = penTouchSensor;
	}

	/**
	 * getMotorAngle is a getter to get the angle of the pen motor
	 * @return int returns the motor angle that can be covered
	 */
	
	public int getMotorAngle() {
		return motorAngle;
	}

	/**
	 * setMotorAngle is a setter to set the angle of the motor
	 * @param motorAngle the  angle to be covered by the pen motor
	 */
	
	public void setMotorAngle(int motorAngle) {
		this.motorAngle = motorAngle;
	}

	/**
	 * isReverse is a getter to get the current value of direction flag condition
	 * that determines the positive/negative direction of pen motor
	 * @return boolean returns the value of flag
	 */
	
	public boolean isReverse() {
		return reverse;
	}

	/**
	 * setReverse is a setter to set the boolean flag condition that determines 
	 * the positive/negative direction of pen motor 
	 * @param reverse boolean flag to check the direction 
	 */
	
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
}
