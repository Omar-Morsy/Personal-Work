package de.tuhh.diss.lejos;

import lejos.nxt.LCD;
import lejos.nxt.Motor;


import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

/**
 * The SwivelArm class used to control the movement of the Swivel Arm 
 * this class implements methods to: 
 * convert the SwivelArm angle to be covered into the corresponding motor angle using gear ratio,
 * convert the motor angle to the corresponding SwiveArm angle,
 * rotate the SwivelArm while waiting for the movement to complete and stop processing,
 * set the configuration speed of the motor of SwivelArm,
 * stop the SwivelArm,
 * create setters and getters for the SwivelArm motor, touch sensor used to stop the SwivelArmand
 * maximum motor angle covered by the SwivelArm,
 * create setter and getter for a flag to check the current positive/negative direction of the 
 * used SwivelArm motor 
 */

public class SwivelArm {
	
	private NXTRegulatedMotor swivelArmMotor;
	private TouchSensor swivelArmTouchSensor;
	private static int ARMMOTOR_GEAR_RATIO = 84;
	private int motorAngleMax;
	private boolean reverse;
	
	/**
	 * SwivelArm constructor initializes the SwivelArm motor and the touch sensor
	 * used to stop the arm movement, it configures the ports of the motor and the touch sensor
	 * @param motorAngleMax, the maximum angle that can be covered by the swivel arm
	 * @param reverse , a boolean flag to check the current positive/negative direction of 
	 * the used SwivelArm Motor rotation
	 */
	
	public SwivelArm(int motorAngleMax, boolean reverse) {
		this.setSwivelArmMotor(Motor.A);
		this.setSwivelArmTouchSensor(new TouchSensor(SensorPort.S1));
		this.setMotorAngleMax(motorAngleMax);
		this.setReverse(reverse);
	}
	
	/**
	 * swivelArmMove method with two arguments used to control the rotation of the SwivelArm
	 * while waiting for the movement process to be completed. it controls the movement of the
	 * swivel arm by comparing the summation of the current and target angles with the the maximum
	 * angle of the swivel arm motor and the swivel arm motor rotates accordingly
	 * @param angle the next rotation angle of the SwivelArm 
	 * @param anglePrev the current rotation angle of the SwivelArm
	 * @return int returns the motor angle corresponding to the SwivelArm angle
	 */
	
	public int swivelArmMove(int angle, int anglePrev) {
		if (reverse == false) {
		  if (((this.armAngleToMotorAngle(angle) + this.armAngleToMotorAngle(anglePrev)) < this.getMotorAngleMax()) && ((this.armAngleToMotorAngle(angle) + this.armAngleToMotorAngle(anglePrev)) > -this.getMotorAngleMax())) { 
		      this.getSwivelArmMotor().rotate(this.armAngleToMotorAngle(angle));
		      this.getSwivelArmMotor().stop();
		      return angle;
		  } else {
			  LCD.drawString("You have exceeded max. limit for motor", 0, 3);
			  return 0;
		  }
		} else {
			if (this.armAngleToMotorAngle(angle) + this.armAngleToMotorAngle(anglePrev) < -this.getMotorAngleMax() && (this.armAngleToMotorAngle(angle) + this.armAngleToMotorAngle(anglePrev)) > this.getMotorAngleMax()) { 
				this.getSwivelArmMotor().rotate(-this.armAngleToMotorAngle(angle));
				this.getSwivelArmMotor().stop();
				return angle;
				} else {
					LCD.drawString("You have exceeded max. limit for motor", 0, 3);
					return 0;
				}
		 }
	}
	
	/**
	 * setSwivelArmMotorSpeed method to set the speed of the SwivelArm motor
	 * @param speed the applied speed on the SwivelArm motor
	 */
	
	public void setSwivelArmMotorSpeed(int speed) {
		this.getSwivelArmMotor().setSpeed(speed);
	}
	
	/*
     *                                   motor                            arm
     * 84 arm motor gear ratio        84*revolutions (84*360)      1 revolutions(1*360)
     *                                   theta1                          theta2
     */

	/**
	 * armAngleToMotorAngle converts the SwivelArm angle to corresponding motor angle 
	 * @param angle the angle of the SwivelArm corresponding to the motor angle
	 * @return int returns the angle of the motor
	 */
	
	public int armAngleToMotorAngle(int angle) {
		return (int) (angle * ARMMOTOR_GEAR_RATIO);
	}
	
	/**
	 * motorAngleToArmAngle converts the motor angle to the corresponding SwivelArm angle
	 * @param angle the angle of the motor corresponding to the required angle by the SwivelArm
	 * @return int returns the angle of the SwivelArm
	 */
	
	public int motorAngleToArmAngle(int angle) {
		return (int) (angle / ARMMOTOR_GEAR_RATIO);
	}
	
	/**
	 * swivelArmStop method to stop the movement of the SwivelArm
	 */
	
	public void swivelArmStop() {
		this.getSwivelArmMotor().stop();
	}
	
	/**
	 * getSwivelArmMotor is a getter to get the motor used to control the SwivelArm
	 * @return NXTRegulatedMotor returns the motor of the SwivelArm
	 */
	
	public NXTRegulatedMotor getSwivelArmMotor() {
		return this.swivelArmMotor;
	}

	/**
	 * setSwivelArmMotor is a setter to set the SwivelArm motor to be used
	 * @param nxtRegulatedMotor the motor used to control the SwivelArm movement
	 */
	
	public void setSwivelArmMotor(NXTRegulatedMotor nxtRegulatedMotor) {
		this.swivelArmMotor = nxtRegulatedMotor;
	}

	/**
	 * getSwivelArmTouchSensor is a getter to get the port of SwivelArm touch sensor
	 * @return TouchSensor returns the Touch Sensor port used by the PlotBot 
	 */
	
	public TouchSensor getSwivelArmTouchSensor() {
		return swivelArmTouchSensor;
	}

	/**
	 * setSwivelArmTouchSensor is a setter to set the touch sensor port used to stop the SwivelArm
	 * @param swivelArmTouchSensor the touch sensor used to stop the movement of SwivelArm
	 */
	
	public void setSwivelArmTouchSensor(TouchSensor swivelArmTouchSensor) {
		this.swivelArmTouchSensor = swivelArmTouchSensor;
	}

	/**
	 * getMotorAngleMax is a getter to get the maximum angle of the motor
	 * @return int returns the maximum motor angle that can be covered
	 */
	
	public int getMotorAngleMax() {
		return motorAngleMax;
	}

	/**
	 * setMotorAngleMax is a setter to set the maximum angle of the motor
	 * @param motorAngleMax the maximum angle to be covered by the SwivelArm motor
	 */
	
	public void setMotorAngleMax(int motorAngleMax) {
		this.motorAngleMax = motorAngleMax;
	}

	/**
	 * isReverse is a getter to get the current value of direction flag condition
	 * @return boolean returns the value of flag
	 */
	
	public boolean isReverse() {
		return reverse;
	}

	/**
	 * setReverse is a setter to set the boolean flag condition that determines 
	 * the direction of SwivelArm motor 
	 * @param reverse boolean flag to check the direction 
	 */
	
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
}
