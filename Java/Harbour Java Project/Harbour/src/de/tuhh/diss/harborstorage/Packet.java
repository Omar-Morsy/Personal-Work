package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StorageElement;

/**
 * Packet class implements the interface StorageElement and implements setters and getters 
 * for the parameters of this class
 */

public class Packet implements StorageElement{
	private Slot location; // the location of the packet which is stored in the storage area
	private int id; // generic id that is given to the packet upon storing it in a slot in the storage area
	private int width; // The width of the packet
	private int height; // The height of the packet
	private int depth; // The depth of the packet
	private String description; // The description of the packet
	private int weight; // The weight of the packet
	
	/**
	 * Packet constructor initializes the parameters of this class by the given parameters to
	 * the constructor. It initializes the Packet's specifications, dimensions and description
	 * @param id This is the generic id of the packet
	 * @param width This is the width of the packet
	 * @param height This is the height of the packet
	 * @param depth This is the depth of the packet
	 * @param description This is the description of the packet
	 * @param weight This is the weight of the packet
	 */
	
	public Packet(int id, int width, int height, int depth, String description, int weight) {
		this.id = id;
		this.width = width; 
		this.height = height; 
		this.depth = depth; 
		this.description = description; 
		this.weight = weight; 
	}
	
	/**
	 * getWidth method is a getter for the width of the packet
	 * @return int returns the width of the packet
	 */
	
	public int getWidth() {
		return this.width; 
	}
	
	/**
	 * getHeight method is a getter for the height of the packet
	 * @return int returns the height of the packet
	 */

	public int getHeight() {
		return this.height; 
	}
	
	/**
	 * getDepth method is a getter for the depth of the packet
	 * @return int returns the depth of the packet
	 */

	public int getDepth() {
		return this.depth;  
	}
	
	/**
	 * getId method is a getter for the id of the packet
	 * @return int returns the generic id of the packet
	 */

	public int getId() {
		return this.id;  
	}
	
	/**
	 * getDescription method is a getter for the description of the packet
	 * @return String returns the description of the packet
	 */
	
	public String getDescription() {
		return this.description; 
	}
	
	/**
	 * getWeight method is a getter for the weight of the packet
	 * @return int returns the weight of the packet
	 */
	
	public int getWeight() {
		return this.weight; 
	}
	
	/**
	 * getLocation method is a getter for the location of the packet 
	 * @return Slot returns the slot where the packet is stored in
	 */

	public Slot getLocation() {
		return location;
	}
	
	/**
	 * setLocation method is a setter for the location of the packet
	 * @return nothing
	 */

	public void setLocation(Slot location) {
		this.location = location;
	}
	
	/**
	 * setId method is a setter for the generic id of the packet
	 * @return nothing
	 */

	public void setId(int id) {
		this.id = id;
	}
}
