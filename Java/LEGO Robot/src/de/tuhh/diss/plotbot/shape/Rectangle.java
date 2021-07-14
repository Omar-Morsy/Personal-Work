package de.tuhh.diss.plotbot.shape;

import de.tuhh.diss.lejos.Coord;
import de.tuhh.diss.lejos.PlotbotControl;

/**
 * The rectangle class implements the Plottable interface
 * that creates two instances of the Coord Class; given the height and width input
 * from the user, the lower left and upper right corners are then calculated based
 * on the user input and an instance of the PlotBot control control used to control 
 * how to draw the rectangle, this class implements methods to: 
 * plot any rectangle of specified user-defined dimensions in the Cartesian coordinate system
 * create setters and getters for the lower left and upper right corners coordinates, 
 * the plotbot control, the width and height of rectangle.
 */

public class Rectangle implements Plottable {
	private Coord lowerLeftCorner;
	private Coord upperRightCorner;
	private PlotbotControl pc;
	private int width;
	private int height;
	
	/**
	 * Rectangle constructor initializes the rectangle parameters with the given 
	 * coordinates of the lower left and the upper right corners
	 * it also initializes the PlotBot control used to control the robot movements
	 * setting the values of the rectangle user defined dimensions initializing the 
	 * plotting process
	 * @param lowerLeftCorner : the lower left corner of the Rectangle, 
	 * @param upperRightCorner: the upper right corner of the Rectangle,
	 */
	
	public Rectangle (Coord lowerLeftCorner, Coord upperRightCorner) {
		this.setLowerLeftCorner(lowerLeftCorner);
		this.setUpperRightCorner(upperRightCorner);
		this.setPc(new PlotbotControl());
		this.setHeight(this.getUpperRightCorner().getY() - this.getLowerLeftCorner().getY());
		this.setWidth(this.getUpperRightCorner().getX() - this.getLowerLeftCorner().getX());
		plot(pc);
	}

	/**
	 * plot method plots the required Rectangle with given lower left and upper right
	 * corners coordinates 
	 * the method takes one argument of PlotbotControl class that is used to control 
	 * how the robot will plot a rectangle with specified dimensions by passing the values
	 * of the target coordinates and controlling the pen upward and downward movements
	 * @param pc, an instance of PlotbotControl that is used to draw the rectangle 
	 * based on the given coordinates
	 */
	
	public void plot(PlotbotControl pc) {
		pc.calibration();
		pc.penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
		pc.penDown();
		pc.penToXy(this.getUpperRightCorner().getX(), this.getLowerLeftCorner().getY());
		pc.penToXy(this.getUpperRightCorner().getX(), this.getUpperRightCorner().getY());
		pc.penToXy(this.getLowerLeftCorner().getX(), this.getUpperRightCorner().getY());
		pc.penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
		pc.penUp();
		
	}

	/**
	 * getLowerLeftCorner method is a getter for lower left corner of rectangle
	 * @return Coord returns the coordinate of the lower left corner  
	 */
	public Coord getLowerLeftCorner() {
		return lowerLeftCorner;
	}

	/**
	 * setLowerLeftCorner method is a setter to set the lower left corner of rectangle
	 * @param lowerLeftCorner, an instance of Coord Class that defines the lower left corner 
	 * of the rectangle 
	 */
	public void setLowerLeftCorner(Coord lowerLeftCorner) {
		this.lowerLeftCorner = lowerLeftCorner;
	}

	/**
	 * getUpperRightCorner method is a getter for upper right corner of rectangle 
	 * @return Coord returns the coordinate of the upper right corner 
	 */
	public Coord getUpperRightCorner() {
		return upperRightCorner;
	}

	/**
	 * setUpperRightCorner method is a setter to set the upper right corner of rectangle
	 * @param upperRightCorner, an instance of Coord Class that defines the upper right corner 
	 * of the rectangle 
	 */
	public void setUpperRightCorner(Coord upperRightCorner) {
		this.upperRightCorner = upperRightCorner;
	}

	/**
	 * getPc method is a getter to get the plotbot control used to draw the rectangle
	 * @return PlotbotControl returns the current control used to draw 
	 */
	public PlotbotControl getPc() {
		return pc;
	}

	/**
	 * setPc is a setter to set the plotbot control used to draw the rectangle
	 * @param pc, an instance of PlotbotControl that defines the plotter controller
	 */
	public void setPc(PlotbotControl pc) {
		this.pc = pc;
	}

	/**
	 * getWidth is a getter to get the specified width of the rectangle 
	 * @return int returns the width of the rectangle
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * setWidth is a setter to set the width of the rectangle
	 * @param width the width of the rectangle to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * getHeight method is a getter to get the specified height of the rectangle
	 * @return int returns the height of the rectangle
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * setHeight method is a setter to set the height of the rectangle
	 * @param height the height of rectangle to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
