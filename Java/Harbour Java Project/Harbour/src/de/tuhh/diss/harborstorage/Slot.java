package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StoragePlace;

/**
 * Slot class implements the interface StoragePlace and implements setters and getters
 * for the parameters of this class. It defines a slot in the storage area
 */

public class Slot implements StoragePlace {	
	private int number; // The number of slot in the storage area
	private int positionX; // The x coordinate position of the slot in the storage area
	private int positionY; // The y coordinate position of the slot in the storage area
	private int width; // The width of the slot in the storage area
	private int height; // The height of the slot in the storage area
	private int depth; // The depth of the slot in the storage area
	private int loadCapacity; // The load capacity of the slot in the storage area
	private Packet containedPacket; // a variable of type packet that stores the packet stored in the slot, equals null if there is no packet stored in the slot
	
	/**
	 * Slot constructor initializes the slot parameters with the given specifications,
	 * dimensions and position of the slot.
	 * @param number This is the number of the slot in the storage area
	 * @param positionX This is the x coordinate position of the slot in the storage area
	 * @param positionY This is the y coordinate position the slot in the storage area
	 * @param width This is the width of the slot in the storage area
	 * @param height This is the height of the slot in the storage area
	 * @param depth This is the depth of the slot in the storage area
	 * @param loadCapacity This is the load capacity of the slot in the storage area
	 */
	
	public Slot(int number, int positionX, int positionY, int width, int height, int depth, int loadCapacity) {
		this.number = number;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.loadCapacity = loadCapacity;
		this.containedPacket = null;
	}
	
	/**
	 * getNumber method is a getter for the number of the slot in the storage area
	 * @return int returns the number of the slot in the storage area
	 */
	
	public int getNumber() {
		return this.number; 
	}
	
	/**
	 * getNumber method is a getter for the width of the slot in the storage area
	 * @return int returns the width of the slot in the storage area
	 */

	public int getWidth() {
		return this.width; 
	}
	
	/**
	 * getNumber method is a getter for the height of the slot in the storage area
	 * @return int returns the height of the slot in the storage area
	 */

	public int getHeight() {
		return this.height;  
	}
	
	/**
	 * getNumber method is a getter for the depth of the slot in the storage area
	 * @return int returns the depth of the slot in the storage area
	 */
	
	public int getDepth() {
		return this.depth; 
	}
	
	/**
	 * getNumber method is a getter for the load capacity of the slot in the storage area
	 * @return int returns the load capacity of the slot in the storage area
	 */
	
	public int getLoadCapacity() {
		return this.loadCapacity; 
	}
	
	/**
	 * getNumber method is a getter for x coordinate position of the slot in the storage area
	 * @return int returns the x coordinate position of the slot in the storage area
	 */
	
	public int getPositionX() {
		return this.positionX; 
	}
	
	/**
	 * getNumber method is a getter for y coordinate position of the slot in the storage area
	 * @return int returns the y coordinate position of the slot in the storage area
	 */
	
	public int getPositionY() {
		return this.positionY; 
	}
	
	/**
	 * getNumber method is a getter for the packet contained by the slot in the storage area
	 * and returns a null in case there is no packet stored in the slot
	 * @return Packet returns the packet contained by the slot in the storage area
	 */
	
	public Packet getContainedPacket() {
		return this.containedPacket;
	}
	
	/**
	 * setContainedPacket method is a setter to set the packet contained by the slot with the given packet
	 * @param p
	 */
	
	public void setContainedPacket(Packet p) {
		this.containedPacket = p;
	}

}
