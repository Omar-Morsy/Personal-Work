package de.tuhh.diss.plotbot.shape;

import de.tuhh.diss.lejos.Coord;
import de.tuhh.diss.lejos.PlotbotControl;
import de.tuhh.diss.plotbot.shape.Plottable;

/**
 * The Ship class implements the Plottable interface
 * that creates an instance of Coord Class that defines the lower left corner of
 * the ship and the width value given as inputs from the user
 * this class implements methods to: 
 * plot any ship of specified width and lower left corner in the Cartesian coordinate system,
 * the height of the ship is calculated internally based on the width input
 * create setters and getters for the lower left and upper right corners coordinates.
 */

public class Ship implements Plottable{
	
	private Coord lowerLeftCorner;
	private int width;

	/**
	 * Ship constructor initializes the ship parameters with the given 
	 * coordinate of the lower left corner and the width of the ship
	 * @param lowerLeftCorner, the lower left corner of the ship, 
	 * @param width, the width of the ship,
	 * the constructor then initializes the Plot method with an argument of 
	 * PlotbotControl that is used to control the robot movements to draw the
	 * required ship.
	 */
	
	public Ship (Coord lowerLeftCorner, int width) {	
		
		this.lowerLeftCorner = lowerLeftCorner;
		this.width = width;
		this.plot(new PlotbotControl());
	}
	
	/**
	 * the plot method draws the required Ship with given width from the user 
	 * and the calculated LowerLeftCorner coordinate
	 * the method takes one argument of PlotbotControl class that is used to control 
	 * how the robot will draw the ship
	 * @param pc, an instance of PlotbotControl that is used to draw the ship 
	 * the main idea is splitting the drawing of the ship into a series of lines and rectangles 
	 * and passing the target coordinates in the following sequence:
	 * 1) a rectangle that represents the flag of the ship with internally predefined equation
	 * to calculate the height and width of flag.
	 * 2) a vertical line that represents the height of the ship sails.
	 * 3) a horizontal line that represents the right upper base of the ship.
	 * 4) a diagonal line that represents the right edge of the base
	 * 5) a horizontal line that represents the lower base of the ship
	 * 6) a diagonal line that represents the left edge of the base
	 * 7) a horizontal line that represents the left upper base of the ship.
	 * 8) a diagonal line that represents the left smaller sail of the ship
	 * 9) a diagonal line that represents the right larger sail of the ship
	 */

	public void plot(PlotbotControl pc) {
		
	   int quarter = (int) this.getWidth() / 4;
	   int half = (int) this.getWidth() / 2;
		
       Rectangle a = new Rectangle(new Coord(this.lowerLeftCorner.getX(),245),
				     new Coord(30, 255)); 
       
       a.getPc().penDown();
       
       //draw vertical line
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
       
       //draw right upper horizontal
       a.getPc().penToXy(half, this.getLowerLeftCorner().getY());
       
       //draw left upper horizontal
       a.getPc().penUp();
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
       a.getPc().penDown();
       a.getPc().penToXy(-half, this.getLowerLeftCorner().getY());
       
       //go to lower line (width)
       a.getPc().penUp();
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
       a.getPc().penToXy(quarter, this.getLowerLeftCorner().getY());
       a.getPc().penToXy(quarter, this.getLowerLeftCorner().getY() - quarter);
       
       //draw base lower width
       a.getPc().penDown();
       a.getPc().penToXy(-quarter,this.getLowerLeftCorner().getY() - quarter);
       a.getPc().diagonal(-half, this.getLowerLeftCorner().getY());
       
       //go to the lower right
       a.getPc().penUp();
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), a.getLowerLeftCorner().getY());
       a.getPc().penToXy(quarter, this.getLowerLeftCorner().getY());
       a.getPc().penToXy(quarter, this.getLowerLeftCorner().getY() - quarter);
       a.getPc().penDown();
       a.getPc().diagonal(half, this.getLowerLeftCorner().getY());
       
       //right diagonal
       a.getPc().penUp();
       a.getPc().penToXy(quarter, this.getLowerLeftCorner().getY());
       a.getPc().penDown();
       a.getPc().diagonal(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY() + quarter);
       
       //left diagonal
       a.getPc().penUp();
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY() + quarter - 10);
       a.getPc().penDown();
       a.getPc().diagonal(-quarter, this.getLowerLeftCorner().getY());
       
       //return to initial position
       a.getPc().penUp();
       a.getPc().penToXy(this.getLowerLeftCorner().getX(), this.getLowerLeftCorner().getY());
	}

	/**
	 * getLowerLeftCorner method is a getter for lower left corner of ship
	 * @return Coord returns the coordinate of the lower left corner  
	 */
	
	public Coord getLowerLeftCorner() {
		return this.lowerLeftCorner;
	}

	/**
	 * setLowerLeftCorner method is a setter to set the lower left corner of ship
	 * @param lowerLeftCorner, an instance of Coord Class that defines the lower left corner 
	 * of the ship 
	 */
	
	public void setLowerLeftCorner(Coord lowerLeftCorner) {
		this.lowerLeftCorner = lowerLeftCorner;
	}
	
	/**
	 * getWidth method is a getter for the width of the ship
	 * @return int returns the width of the ship 
	 */
	
	public int getWidth() {
		return this.width;
	}

	/**
	 * setWidth method is a setter to set the lower left corner of ship
	 * @param width, a primitive data type int that defines the width of the ship 
	 */
	
	public void setWidth(int width) {
		this.width = width;
	}

}
