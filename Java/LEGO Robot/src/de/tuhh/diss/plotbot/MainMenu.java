package de.tuhh.diss.plotbot;

import de.tuhh.diss.plotbot.shape.Ship;

import de.tuhh.diss.lejos.Coord;
import de.tuhh.diss.lejos.PlotbotControl;
import de.tuhh.diss.plotbot.shape.Rectangle;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

/**
 * the class defines the user interaction, based on different user input selections, 
 * and the selected shape dimensions, the required shape is drawn.
 */

public class MainMenu {

	private static final String[] ITEMS = {"Rectangle", "Ship", "Calibration"};	//add new text menu entries here
	private	static final String TITLE = "Choose Shape to draw:";
	private TextMenu menu;

	/**
	 * MainMenu constructor creates a new instance of TextMenu Class with predefined parameters
	 * to be shown on the LCD 
	 */
	
	public MainMenu() {
		menu = new TextMenu(ITEMS, 1, TITLE);
	}
	
	/**
	 * Start Method used to decide on the drawing shape,
	 * based on the user selection the following instances can be called:
	 * a rectangle to be drawn by the plotbot,
	 * a ship to be drawn by the plotbot,
	 * a calibration process.
	 */
	
	public void start() {
		int selection = -1;
		
		do {
			selection = menu.select();
		}while(selection < 0);

		
		while(Button.ENTER.isDown()) {
		}
		
		
		if (selection == 0) {
			int height = getDimension("Enter height of rectangle");
			int width = getDimension("Enter width of rectangle");
			Coord upperRightCorner = new Coord(width, 230 + 25);
			Coord lowerLeftCorner = new Coord(0, (230 + 25) - height);
			new Rectangle(lowerLeftCorner, upperRightCorner);
		
		} else if (selection == 1) {
			int height = getDimension("Enter height of ship");
			int width = height;
			Coord lowLeft = new Coord(0, (230 + 25) - height);
			new Ship(lowLeft, width);
		
		} else if (selection == 2) {
			PlotbotControl pc = new PlotbotControl();
			pc.calibration();
		}
	}
	
	
	/**
     * dimensionInput method is defined with one argument that determines the dimensions of the shape
     * to be drawn and selected by the user
     * two buttons are used to increase/decrease the dimension values from the user and a third one for 
     * entering the final dimension value.
     * @param s, a string that asks the user for the required dimensions of the selected shape
     * @return the width and/or the height dimensions of the selected shape
     */
	
    public int getDimension(String s) {
	    int dimension = 0;
	    LCD.clear();
		LCD.drawString(s, 0, 0);
		while (!Button.ENTER.isDown()) {
			LCD.drawInt(dimension, 0, 1);
			if (Button.RIGHT.isDown()) {
				while(!Button.RIGHT.isUp()) {
				}
				dimension ++;
			} else if (Button.LEFT.isDown()) {
				while (!Button.LEFT.isUp()){
				}
				dimension -- ;
			}
		}
		while (!Button.ENTER.isUp()) {
		}
		return dimension;
   }
}
