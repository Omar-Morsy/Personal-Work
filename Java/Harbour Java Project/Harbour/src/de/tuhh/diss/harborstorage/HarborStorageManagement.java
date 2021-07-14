package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.HighBayStorage;
import de.tuhh.diss.harborstorage.sim.PhysicalHarborStorage;
import de.tuhh.diss.harborstorage.sim.StorageException;
import de.tuhh.diss.harborstorage.sim.StoragePlace;

/**
 * HarborStorageManagement class implements the interface HighBayStorage and implements methods to
 * find a suitable place in the storage area to store packets,
 * store a packet with given dimensions and description from the loading position to the storage area,
 * retrieve a packet with a given dimensions from the storage area to the loading position
 * an array of strings with all the packets stored in the storage area and 
 * shutdown the crane when needed
 */

public class HarborStorageManagement implements HighBayStorage {
	private Packet packets[]; // an array to store all packets stored in the storage area
	private Slot slots[]; // an array that has all slots in the storage area
	private PhysicalHarborStorage physicalHarborStorage; // an instance of PhysicalHarborStorage 
	private CraneControl craneControl; // an instance of CraneControl class to control the crane
	private static int NO_SLOT_AVAILABLE = -1; // variable to check for the first packet stored in the storage area
	private int packetId; // generic id for each packet stored in the storage area
	
	/**
	 * HarborStorageManagement constructor to make an instance of PhysicalHarborStorage, an instance of
	 * CraneControl class and makes an array of Slots to store the architecture of storage area with the 
	 * dimensions and specifications of each slot and makes an array of packets with the array size of the 
	 * array of slots.
	 */
	
	public HarborStorageManagement() {
		this.physicalHarborStorage = new PhysicalHarborStorage();
		this.craneControl = new CraneControl(this.physicalHarborStorage.getCrane());
		
		StoragePlace e[] = this.physicalHarborStorage.getStoragePlacesAsArray();
		this.slots = new Slot[e.length];
		
		for (int i = 0; i < e.length ; i++) {
			Slot temp = new Slot(e[i].getNumber(), e[i].getPositionX(), e[i].getPositionY(), e[i].getWidth(), e[i].getHeight(), e[i].getDepth(), e[i].getLoadCapacity());
			this.slots[i] = temp;
		}
		
		this.packets = new Packet [slots.length];
	}
	
	/**
	 * findSuitableSlot method takes an argument of the packet needed to be stored in the storage area
	 * and finds the best suitable slot for this packet according to the dimensions of the given packet
	 * and the fastest way to serve the customer by placing the packet in the most suitable slot and the most near
	 * slot to the loading position.
	 * @param p This is the packet where this method finds the most suitable place for it in the storage area
	 * @return int returns the index of the best suitable slot in the array of Slots
	 */
	
	private int findSuitableSlot(Packet p) {
		int betterSolIndex = NO_SLOT_AVAILABLE;
		for (int i = 0; i < this.slots.length; i++) { 
			if ((this.slots[i].getContainedPacket() == null) &&
					(this.slots[i].getDepth() >= p.getDepth()) &&
					(this.slots[i].getHeight() >= p.getHeight()) &&
					(this.slots[i].getWidth() >= p.getWidth()) &&
					(this.slots[i].getLoadCapacity() >= p.getWeight())) { // checks if the 
				if (betterSolIndex == NO_SLOT_AVAILABLE) { // 
					betterSolIndex = i;
				}
				if ((this.slots[betterSolIndex].getPositionX() + this.slots[betterSolIndex].getPositionY()) >= (this.slots[i].getPositionX() + this.slots[i].getPositionY()) &&
						(this.slots[betterSolIndex].getContainedPacket()) == null &&
						this.slots[betterSolIndex].getDepth() >= p.getDepth() &&
						this.slots[betterSolIndex].getHeight() >= p.getHeight() &&
						this.slots[betterSolIndex].getWidth() >= p.getWidth() &&
						this.slots[betterSolIndex].getLoadCapacity() >= p.getWeight()) {
					if ((this.slots[betterSolIndex].getPositionX() + this.slots[betterSolIndex].getPositionY()) == (this.slots[i].getPositionX() + this.slots[i].getPositionY())) {
						if ((this.slots[betterSolIndex].getWidth() > this.slots[i].getWidth()) ||
								(this.slots[betterSolIndex].getHeight() > this.slots[i].getHeight()) ||
								(this.slots[betterSolIndex].getDepth() > this.slots[i].getDepth()) ||
								(this.slots[betterSolIndex].getLoadCapacity() > this.slots[i].getLoadCapacity())) {
							betterSolIndex = i;
						} 
					} else {
						betterSolIndex = i;
					}
				}
			}
		}
		return betterSolIndex;
	}
	
	/**
	 * storePacket method takes arguments of the width, height, depth, description and weight of the packet required 
	 * to be stored in the storage area. This method creates a new instance of Packet with the given dimensions and 
	 * description, gives the packet a new generic id, takes the packet from the loading area, 
	 * finds the suitable slot for this packet in the storage area, store it in it, update the slot where the 
	 * packet is stored in and update the Packets array by adding it to it.
	 * @param width This is the width of the required packet to be stored in the storage area
	 * @param height This is the height of the required packet to be stored in the storage area
	 * @param depth This is the depth of the required packet to be stored in the storage area
	 * @param description This is the description of the required packet to be stored in the storage area
	 * @param weight This is the weight of the required packet to be stored in the storage area
	 * @return int returns the id of the stored packet
	 * @exception StorageException if no slot is available for this packet at the storage area
	 */
	
	public int storePacket(int width, int height, int depth, String description, int weight) throws StorageException {
		packetId ++;
		Packet p = new Packet(packetId, width, height, depth, description, weight);
		    int slotNumber = this.findSuitableSlot(p);
		    if (slotNumber == NO_SLOT_AVAILABLE) {
		    	throw new StorageException("No Slot available for the Packet you want to add");
		    } else {
		    	p.setLocation(slots[slotNumber]);
				this.craneControl.storePacket(this.slots[slotNumber].getPositionX(), this.slots[slotNumber].getPositionY(), p);
				this.slots[slotNumber].setContainedPacket(p);
				this.packets = this.getPackets();
			    return p.getId();
		    }
	}
	
	/**
	 * retreivePacket method takes an argument of the string of description of the required packet to be
	 * retrieved from the storage area, placing the package in the loading position, update the slot where 
	 * the packet is retrieved and update the packets array by removing the packet from it.
	 * @param description This is the description of the packet required to be retrieved from storage area
	 * @return nothing
	 * @exception StorageException if the packet required to retrieve is not found at the storage area
	 */
	
	public void retrievePacket(String description) throws StorageException {
		boolean found = false;
		for(int i = 0; i < this.slots.length; i++) {
			if (this.slots[i].getContainedPacket() != null) {
				if (this.slots[i].getContainedPacket().getDescription() == description) {
					this.craneControl.retrievePacket(this.slots[i].getPositionX(), this.slots[i].getPositionY());
					this.packets[this.slots[i].getContainedPacket().getId() - 1] = null;
					this.slots[i].setContainedPacket(null);
					this.packets = this.getPackets();
					found = true;
				}
			}
			}
		if (found == false) {
			throw new StorageException("Packet you want to retrieve is not found in the Storage Area");
		}

	}
	
	/**
	 * getPacket method takes no arguments and returns the packets stored in the storage area 
	 * without any null entries or repetition of the same packet.
	 * @return Packets[] returns an array of Packet which has all packets stored at the storage area
	 */
	
	public Packet[] getPackets() {
		int packetID = 0;
		this.packets = new Packet[this.slots.length];
		boolean repeatedPacket = false;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].getContainedPacket() != null) {
				for (int j = 0; j < this.packets.length; j++) {
						if (slots[i].getContainedPacket() == this.packets[j]) {
							repeatedPacket = true;
						}
				}
				if (repeatedPacket == false) {
				this.packets[packetID] = slots[i].getContainedPacket();
				packetID++;
				} 
			}
			repeatedPacket = false;
		}
		return this.packets;
	}
	
	/**
	 * shutdown method shuts down the crane 
	 * @return nothing
	 */
	
	public void shutdown() {
	this.craneControl.shutdown();
	}
	
	/**
	 * setPackets method is a setter for the packet array of this class with the given array of Packet
	 * @param packets
	 * @return nothing
	 */

	public void setPackets(Packet packets[]) {
		this.packets = packets;
	}
	
	/**
	 * getSlots method is a getter for the array slots 
	 * @return Slot[]
	 */

	public Slot[] getSlots() {
		return slots;
	}
	
	/**
	 * setSlots method is a setter for the slots array of this class with the given array of Slot
	 * @param slots
	 */

	public void setSlots(Slot slots[]) {
		this.slots = slots;
	}
}
