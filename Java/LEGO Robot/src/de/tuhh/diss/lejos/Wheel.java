package de.tuhh.diss.lejos;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * The Wheel class used to control the wheel movement of the plotbot 
 * this class implements methods to: 
 * convert the distance to be covered by plotbot into motor angle using gear ratio
 * calculate the motor angle required to move a certain distance by the plotbot
 * move the wheel motor while waiting for the process to stop
 * set the speed of the motor of wheels
 * stop the motor of the wheels
 * move the plotting robot backwards
 * create setters and getters for the wheel motor and light sensor used
 */

public class Wheel {
	
	private NXTRegulatedMotor wheelMotor;
	private LightSensor wheelLightSensor;
	private static int WHEEL_DIAMETER = 56;
	private static double PI = 3.14;
	private static int WHEELMOTOR_GEAR_RATIO = 5;
	
	/**
	 * Wheel constructor initializes the PlotBot wheel motor and 
	 * the light sensor used to stop the wheel motors
     * it also configures the ports of the motor and the light sensor
	 */
	
	public Wheel() {
		this.setWheelMotor(Motor.C);
		this.setWheelLightSensor(new LightSensor(SensorPort.S3));
		
	}
	
	/** 
	 * wheelMove method with one argument used to rotate the wheels of the plotbot and wait
	 *  for the rotation to be completed. 
	 * @param distance,the required movement distance by the plotbot
	 * @param status, a boolean flag to determine the waiting process of wheel rotations
	 * @return int returns the distance to be moved
	 */
	
	public int wheelMove(int distance) {
		this.getWheelMotor().rotate(this.distanceToMotorAngle(distance));
		this.getWheelMotor().stop();
		this.getWheelMotor().resetTachoCount();
		return distance;
	}
	
	public int wheelMove(int distance, boolean status) {
		this.getWheelMotor().rotate(this.distanceToMotorAngle(distance), status);
		this.getWheelMotor().resetTachoCount();
		return distance;
	}
	
	/**
	 * setWheelMotorSpeed method used to set the speed of the motor wheels
	 * @param speed, the selected applied speed on the plotbot wheels
	 */
	
	public void setWheelMotorSpeed(int speed) {
		this.getWheelMotor().setSpeed(speed);
	}
	
	/*                                MotorGear                 Wheel             Distance
	 * wheel gear ratio=5 means   5 Revolutions (5*360)      1 Revolutions      PI. Diameter
	 *                              theta motor angle                            distance
	 */
	
	/**
	 * distanceToMotorAngle converts the distance required by the plotbot to move to wheel motor angle
	 * @param distance, the required destination by the plotbot
	 * @return int returns the motor angle corresponding to the required distance
	 */
	
	public int distanceToMotorAngle(int distance) {
		int fullRotation = 360;
		double oneAngleMotorToDist = ((Wheel.WHEEL_DIAMETER * Wheel.PI) / Wheel.WHEELMOTOR_GEAR_RATIO) / fullRotation;
		return (int) (distance / oneAngleMotorToDist);
	}
	
	/**
	 * MotorAngleToDistance converts the motor angle to the required destination by the plotbot
	 * @param angle, the angle of the motor gear of the wheels
	 * @return int returns the distance corresponding to the required angle
	 */
	
	public int motorAngleToDistance(int angle) {
		int fullRotation = 360;
		double oneAngleMotorToDist = ((Wheel.WHEEL_DIAMETER * Wheel.PI) / Wheel.WHEELMOTOR_GEAR_RATIO) / fullRotation;
		return (int) (angle * oneAngleMotorToDist);
	}
	
	/**
	 * wheelMotorStop method is used to stop the wheels of the plotbot
	 */
	
	public void wheelMotorStop() {
		this.getWheelMotor().stop();
	}
	

	/**
	 * getWheelMotor method is a getter for the NXTRegulated Motor of plotbot wheels
	 * @return NXTRegulatedMotor returns the plotbot wheels motor port 
	 */
	public NXTRegulatedMotor getWheelMotor() {
		return this.wheelMotor;
	}

	/**
	 * setWheelMotor method is a setter to set the NXTRegulated Motor of robot wheels
	 * @param wheelMotor, an instance of NXTRegulatedMotor Class that initializes the motor wheel port
	 */
	public void setWheelMotor(NXTRegulatedMotor differentialPilot) {
		this.wheelMotor = differentialPilot;
	}

	/**
	 * getWheelLightSensor method is a getter for the light sensor 
	 * @return LightSensor returns the light sensor port
	 */
	public LightSensor getWheelLightSensor() {
		return wheelLightSensor;
	}

	/**
	 * setWheelLightSensor method is a setter to set the light sensor used to stop the plotbot
	 * @param wheelLightSensor, an instance of LightSensor Class that intializes the light sensor port
	 */
	public void setWheelLightSensor(LightSensor wheelLightSensor) {
		this.wheelLightSensor = wheelLightSensor;
	}

}
