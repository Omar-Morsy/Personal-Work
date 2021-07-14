package de.tuhh.diss.lejos;

import lejos.nxt.LCD;

/**
 * The PlotbotControl class used to control the plotting robot 
 * this class implements methods to: 
 * calibrate the robot to the predefined starting position
 * control the robot to plot only vertical and horizontal lines
 * control the robot to plot only diagonal lines
 * move the pen upwards till it reaches the touch sensor
 * move the pen downwards till it reaches the plotting sketch
 * create setters and getters for the swivel arm, pen and wheels of the robot
 * create setters and getters for current coordinate of the pen, the maximum angle
 * of rotation of swivel arm motor and pen motor 
 */
public class PlotbotControl {
	
	private SwivelArm swivelArm;
	private Pen pen;
	private Wheel wheel;
	private Coord penCoord;
	private int motorArmAngleMax;
	private int motorPenAngle;
	private static final int LIGHTSENSOR_THRESHOLD = 500;
	private static final int DISTANCE_ARM_PEN = 80;
	private static final int SPEED = 450;
	private  int prevY; 
	private  int prevX;
	private int prevAngle;
	private static boolean SWIVEL_ARM_DIREC = true;
	private static boolean PEN_DIREC = true;
	private static int CLEARANCE  = 6;
	
	/**
	 * PlotbotControl constructor initializes the plotbot control used to control the robot
	 * the constructor configures the wheels of the robot, the SwivelArm and pen used to plot
	 * it then initializes the coordinate of the pen, the motor angles at the starting zero position 
	 * 
	 */
		
	public PlotbotControl() {
			this.setWheel(new Wheel());
			this.setSwivelArm(new SwivelArm(0, PlotbotControl.SWIVEL_ARM_DIREC));
			this.setPen(new Pen(0, PlotbotControl.PEN_DIREC));
			this.setPenCoord(new Coord(0,0));
			this.setMotorArmAngleMax(0);
			this.setMotorPenAngle(0);
			this.prevX = 0;
			this.prevY = 0;
			this.prevAngle = 0;
			
			
			/*while(true) {
				this.getSwivelArm().getSwivelArmMotor().forward();
			}*/
			
	}
		
	/**
	 * calibration method used to set the applied speeds of the motors and
	 * initial position of the robot in its driving direction perpendicular to a black bar
	 * the method brings the robot motors in the predefined starting position
	 * for calibration, the robot drives backwards until the light sensor detects 
	 * the black bar marking the initial y=0 position and then the robot stops moving
	 * it initializes the swivel arm zero position while it is facing straight in the 
	 * robot driving direction before starting any drawing task,
	 * the swivel arm motor is then rotated till it presses the touch sensor and the maximum
	 * angle can then be calculated 
	 * the pen motor starting from the predefined zero position touching the plotting sketch
	 * and then rotated till it presses the touch sensor from which the maximum angle of the 
	 * pen motor is then calculated
	 */	
	
	public void calibration() {
			int motorAngleMax = 0;
			
			this.getWheel().setWheelMotorSpeed(SPEED);
			this.getSwivelArm().setSwivelArmMotorSpeed(SPEED );
			this.getPen().setPenMotorSpeed(SPEED);
			
			this.getPen().getPenMotor().resetTachoCount();
			this.getSwivelArm().getSwivelArmMotor().resetTachoCount();
			this.getWheel().getWheelMotor().resetTachoCount();
			
			while (!this.getPen().getPenTouchSensor().isPressed()) {
				if (PlotbotControl.PEN_DIREC == true) {
				this.getPen().getPenMotor().forward();
				} else {
					
				}
			}
			
			this.setMotorPenAngle(this.getPen().getPenMotor().getTachoCount());
			this.getPen().setMotorAngle(this.getMotorPenAngle());
			this.getPen().penMotorStop();
			this.getPen().getPenMotor().resetTachoCount();
			
			while(this.getWheel().getWheelLightSensor().readNormalizedValue() != PlotbotControl.LIGHTSENSOR_THRESHOLD) {
				this.getWheel().getWheelMotor().backward();
			}
			
			this.getWheel().wheelMotorStop();
			this.getWheel().getWheelMotor().resetTachoCount();
			
			
			while (!this.getSwivelArm().getSwivelArmTouchSensor().isPressed()) {
				if (this.getSwivelArm().isReverse()) {
				this.getSwivelArm().getSwivelArmMotor().backward();
				} else {
					this.getSwivelArm().getSwivelArmMotor().forward();
				}
			}
			
			this.getSwivelArm().getSwivelArmMotor().stop();
			motorAngleMax = this.getSwivelArm().getSwivelArmMotor().getTachoCount();
			this.getSwivelArm().getSwivelArmMotor().rotate(-motorAngleMax - PlotbotControl.CLEARANCE);
			this.getSwivelArm().getSwivelArmMotor().stop();
			this.getSwivelArm().getSwivelArmMotor().resetTachoCount();
			this.setMotorArmAngleMax(motorAngleMax);
			LCD.drawInt(motorAngleMax, 0, 3);
			this.getSwivelArm().setMotorAngleMax(this.getMotorArmAngleMax());
			
	}
		
	/**
	 * penToXy method used to control the plotting of vertical and horizontal lines
	 * first by comparing the current and target y coordinate and moving the wheels motor
	 * accordingly, followed by comparing the current and target x coordinates, calculating 
	 * the angle needed and moving the swivel arm motor accordingly.
	 * At the same time, comparing the y movements while moving the swivel arm to avoid having
	 * arcs in straight lines and updating the x and y coordinates
	 * @param x the target x coordinate
	 * @param y the target y coordinate
	 */
	
	public void penToXy(int x, int y) {
			int period = 1;
			if (this.prevY != y) {
				this.getWheel().wheelMove(y - this.prevY);
				this.prevY = y;
			}
			if (this.prevX != x) {
				int angle = this.getPenCoord().xyToAngle(x - prevX, PlotbotControl.DISTANCE_ARM_PEN);
				if (angle > 0) {
					
						this.getSwivelArm().swivelArmMove(PlotbotControl.CLEARANCE, 0);
					
					int refrY = PlotbotControl.DISTANCE_ARM_PEN;
				    for (int i = 1; i <= angle ; i += period) {
					   this.getSwivelArm().swivelArmMove(period, this.prevAngle);
					   this.prevAngle += period;
					   if (this.getPenCoord().angleToY(i + period) != refrY) {
						    this.getWheel().wheelMove(refrY - this.getPenCoord().angleToY(i + period));
						   // prevY = prevY + (refrY - this.getPenCoord().angleToY(i + period));
						    refrY = this.getPenCoord().angleToY(i + period);
					   }
				   }
			} else {
				
					this.getSwivelArm().swivelArmMove(-PlotbotControl.CLEARANCE, 0);
				
				int refrY = PlotbotControl.DISTANCE_ARM_PEN;
				for (int i = -1; i >= angle ; i -= period) {
				    this.getSwivelArm().swivelArmMove(-period, this.prevAngle);
					this.prevAngle -= period;
					if (this.getPenCoord().angleToY(i - period) != refrY) {
						this.getWheel().wheelMove(refrY - this.getPenCoord().angleToY(i - period));
						//prevY = prevY + (refrY - this.getPenCoord().angleToY(i - period));
						refrY = this.getPenCoord().angleToY(i - period);
					}
				}
			}
				this.prevX = x;
		}
	}
		
	/**
	 * diagonal method used to control the robot to plot only diagonal lines 
	 * the main idea is to move the swivel arm in the x axis and the wheel motors in the
	 * y axis simultaneously.
	 * @param x the target x coordinate 
	 * @param y the target y coordinate
	 */	
	
	public void diagonal(int x, int y) {
			int periodX = 0;
			int periodY = 0;
			if (x - this.prevX > 0) {
				periodX = 1;
			} else {
				periodX = -1;
			}
			if (y - this.prevY > 0) {
				periodY = 1;
			} else {
				periodY = -1;
			}
			while(this.prevX != x || this.prevY != y) {
				int newX = this.prevX + periodX;
				int newY = this.prevY + periodY;
				plotDiagonal(newX, newY);
			}
			
	}
		
	/**
	 * This is the method called by diagonal method to plot the diagonal in steps of moving in y and x directions
	 * by the given x and y coordinates taking into consideration the addidtion of the arm clearance.
	 * @param x
	 * @param y
	 */
	
	public void plotDiagonal(int x, int y) {
				this.getWheel().wheelMove(y - this.prevY);
				this.prevY = y;
				
			int angle = this.getPenCoord().xyToAngle(x - this.prevX, PlotbotControl.DISTANCE_ARM_PEN);
			if (angle > 0) {
				this.getSwivelArm().swivelArmMove(PlotbotControl.CLEARANCE, 0);
			} else {
				this.getSwivelArm().swivelArmMove(-PlotbotControl.CLEARANCE, 0);
			}
			this.getSwivelArm().swivelArmMove(angle, this.prevAngle);
			this.prevAngle += angle;
			this.prevX = x;
	}
		
	/**
	 * penUp method used to call the upward movement of the pen
	 */
	
	public void penUp() {
			this.getPen().penUp();
	}
		
	/**
	 * penDown method used to call the downward movement of the pen 
	 */	
	
	public void penDown(){
			this.getPen().penDown();
	}
		
		
	/**
	 * getSwivelArm method is a getter to get the swivel arm used in the plotting robot
	 * @return SwivelArm returns the swivelArm used in the robot
	 */
	
	public SwivelArm getSwivelArm() {
			return swivelArm;
	}

	/**
	 * setSwivelArm method is a setter to set the swivel arm used in the plotting robot
	 * @param swivelArm the swivelArm to set
	 */
		
	public void setSwivelArm(SwivelArm swivelArm) {
			this.swivelArm = swivelArm;
	}

	/**
	 * getpen method is a getter to get the pen used to plot
	 * @return Pen returns the pen used to plot
	 */
		
	public Pen getPen() {
			return pen;
	}

	/**
	 * setPen method is a setter to set the pen used to plot
	 * @param pen the pen used to set
	 */
	
	public void setPen(Pen pen) {
			this.pen = pen;
	}

	/**
	 * getWheel method is a getter to get the wheels used in the robot
	 * @return Wheel the wheel used in the plotter robot
	 */
	
	public Wheel getWheel() {
			return wheel;
	}

	/**
	 * setWheel method is a setter to set the wheels of the robot
	 * @param wheel the wheel used in the plotter robot
	 */
	
	public void setWheel(Wheel wheel) {
			this.wheel = wheel;
	}

	/**
	 * getPenCoord method is a getter to get the current coordinates of the pen
	 * @return Coord returns the coordinates of the pen
	 */
	
	public Coord getPenCoord() {
			return penCoord;
	}

	/**
	 * setPenCoord method is a setter to set the current coordinates of the pen
	 * @param penCoord the coordinates of the pen
	 */
	
	public void setPenCoord(Coord penCoord) {
			this.penCoord = penCoord;
	}

	/**
	 * getMotorArmAngleMax method is a getter to get the maximum swivel arm motor angle
	 * @return int returns the maximum angle of swivel arm motor
	 */	
	
	public int getMotorArmAngleMax() {
			return motorArmAngleMax;
	}
	
	/**
	 * setMotorArmAngleMax method is a setter to set the maximum angle of swivel arm motor
	 * @param motorArmAngleMax the angle of swivel arm motor to set
	 */
	
	public void setMotorArmAngleMax(int motorArmAngleMax) {
			this.motorArmAngleMax = motorArmAngleMax;
	}

	/**
	 * getMotorPenAngle method is a getter to get the angle of pen motor
	 * @return int returns the motor pen angle in degrees
	 */	
	
	public int getMotorPenAngle() {
			return motorPenAngle;
	}

	/**
	 * setMotorPenAngle method is a setter to the set the angle of pen motor
	 * @param motorPenAngle the angle of pen motor to set
	 */	
	
	public void setMotorPenAngle(int motorPenAngle) {
			this.motorPenAngle = motorPenAngle;
	}
}
