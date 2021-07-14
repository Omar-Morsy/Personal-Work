package de.tuhh.diss.lejos;
/**
 * The Coord class implements the transformation between Cartesian and polar coordinate system 
 * this class implements methods to: 
 * transform from angle polar coordinate into the corresponding y Cartesian coordinate
 * transform from angle polar coordinate into the corresponding x Cartesian coordinate
 * transform from x-y coordinate system into the corresponding angle 
 */
public class Coord {
	
	private int x;
	private int y;
	private int angle;
	private static int distanceToPen = 80;
	
	/**
	 * Coord constructor initializes the x and y Cartesian coordinate points 
	 * @param x the x coordinate 
	 * @param y the y coordinate
	 */
	
	public Coord (int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * angleToY method used to convert from angle polar coordinate measured 
	 * in degrees to the corresponding y cartesian coordinate 
	 * @param angleInDegrees
	 * @return int returns the corresponding y coordinate
	 */
	
	public int angleToY (int angleInDegrees) {
		int s = (int) (distanceToPen * Math.cos(Math.toRadians(angleInDegrees)));
		return s;
	}
	
	/**
	 * angleToX method used to convert from angle polar coordinate measured
	 * in degrees to the corresponding x Cartesian coordinate 
	 * @param angleInDegrees
	 * @return int returns the corresponding x coordinate
	 */
	
	public int angleToX (int angleInDegrees) {
		int s = (int) (distanceToPen * Math.sin(Math.toRadians(angleInDegrees)));
		return s;
	}
	
	/**
	 * xyToAngle method used to convert from x-y Cartesian coordinate
	 * to the corresponding angle polar coordinate measured in degrees
	 * @param angleInDegrees
	 * @return int returns the corresponding angle
	 */
	
	public int xyToAngle (int x, int y) {
		int s = (int) Math.toDegrees(Math.atan2(x, y));
		return s;
	}

	/**
	 * getX method is a getter for the x Cartesian coordinate 
	 * @return int returns the x Cartesian coordinate 
	 */
	
	public int getX() {
		return x;
	}

	/**
	* setX method is a setter to set the x Cartesian coordinate
    * @param x the x Cartesian coordinate 
    */
	
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * getY method is a getter for the y Cartesian coordinate 
	 * @return int returns the y Cartesian coordinate 
	 */
	
	public int getY() {
		return y;
	}

	/**
	 * setY method is a setter to set the y coordinate
	 * @param y the y coordinate 
	 */
	
	public void setY(int y) {
		this.y = y;
	}
	
	

	/**
	 * getAngle method is a getter for the angle in polar coordinate system
	 * @return int returns the angle measured in degrees 
	 */
	
	public int getAngle() {
		return angle;
	}

	/**
	 * setAngle method is a setter to set the angle of the polar coordinate system
     * @param angle the angle measured in degrees
     */
	
	public void setAngle(int angle) {
		this.angle = angle;
	}
	


}
