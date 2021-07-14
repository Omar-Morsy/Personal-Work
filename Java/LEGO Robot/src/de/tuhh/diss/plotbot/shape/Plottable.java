package de.tuhh.diss.plotbot.shape;

import de.tuhh.diss.lejos.PlotbotControl;

/**
 * a Plottable interface that is used to mark a shape which can be plotted by the Plotbot,
 * implements plot method that is used to  draw different shapes,
 * @param pc, an instance of PlotbotControl that controls the robot 
 * algorithm to draw different shapes.
 */

public interface Plottable {
	public void plot(PlotbotControl pc);
}
