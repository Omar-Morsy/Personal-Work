package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.CraneException;
import de.tuhh.diss.harborstorage.sim.PhysicalCrane;
import de.tuhh.diss.harborstorage.sim.StorageElement;

/**
 * The CraneControl class makes an instance of the PhysicalCrane and implements methods to
 * store a packet from the loading position into the storage area,
 * retrieve a packet from the storage area to the loading position,
 * shutdown the physical crane,
 * drive the crane to a given x and y coordinates,
 * move the crane to a given x coordinate and
 * move the crane to a given y coordinate
 */

public class CraneControl {
	private PhysicalCrane cr; // an instance of PhysicalCrane class

	/**
	 * CraneControl constructor to initialize the instance of PhysicalCrane class with the 
	 * given physical crane
	 * @param cr
	 */
	
	public CraneControl(PhysicalCrane cr) {
		this.cr = cr;
	}
	
	/**
	 * storePacket method take arguments of x and y coordinates of the place in the storage 
	 * area and an instance of the StorageElement to be loaded from the loading position
	 * and stored in the given coordinates in the storage area by the crane
	 * @param x This is the x coordinate of the required place in the storage area to place the packet in
	 * @param y This is the y coordinate of the required place in the storage area to place the packet in
	 * @param packet This is the packet required to be placed in the given x and y coordinate in the storage area
	 * @return nothing
	 * @throws CraneException if the crane not in the required position for loading or unloading
	 */
	
	public void storePacket(int x, int y, StorageElement packet) throws CraneException{
		this.driveCrane(this.cr.getLoadingPosX(), this.cr.getLoadingPosY());
		
		if (this.cr.getLoadingPosX() == this.cr.getPositionX() &&
				this.cr.getLoadingPosY() == this.cr.getPositionY()) {
			this.cr.stopX();
			this.cr.stopY();
			this.cr.loadElement(packet);
		} else {
			throw new CraneException();
		}
		
		this.driveCrane(x, y);
		
		if (x == this.cr.getPositionX() && y == this.cr.getPositionY()) {
			this.cr.stopX();
			this.cr.stopY();
			this.cr.storeElement();
		} else {
			throw new CraneException();
		}
	}
	
	/**
	 * retrievePacket method takes arguments of the x and y coordinates of the packet in the
	 * storage area to be retrieved by the crane and unloaded in the loading position by
	 * the crane
	 * @param x This is the x coordinate where the packet needed to be retrieved from the storage area
	 * @param y This is the y coordinate where the packet needed to be retrieved from the storage area
	 * @return StorageElement returns the packet retrieved and unloaded in the loading position
	 * @throws CraneException if the crane not in the required position for loading or unloading
	 */
	
	public StorageElement retrievePacket(int x, int y) throws CraneException{
		
		 this.driveCrane(x, y);
		 
		 if (this.cr.getPositionX() == x && this.cr.getPositionY() == y) {
			 this.cr.stopX();
			 this.cr.stopY();
			 this.cr.retrieveElement();
		 } else {
			 throw new CraneException();
		 }
		 
		 this.driveCrane(this.cr.getLoadingPosX(), this.cr.getLoadingPosY());
		 
		 if (this.cr.getPositionX() == this.cr.getLoadingPosX() &&
				 this.cr.getPositionY() == this.cr.getLoadingPosY()) {
			 this.cr.stopX();
			 this.cr.stopY();
			 return this.cr.unloadElement();
		 } else {
			 throw new CraneException();
		 }
	}
	
	/**
	 * shutdown Method to shut down the crane when needed
	 * @return nothing
	 */
	
	public void shutdown() {
		this.cr.shutdown();
	}
	
	/**
	 * DriveCrane method drives the crane into the given x and y coordinates
	 * @param x This is the required x coordinate where the crane needs to go
	 * @param y This is the required y coordinate where the crane needs to go
	 * @return boolean true if the crane goes to the required x and y coordinates and false otherwise
	 * @throws CraneException if the crane doesn't go to the required x and y coordinates
	 */
	
	private boolean driveCrane(int x, int y) throws CraneException{
		if (!this.cr.isStalledX() && !this.cr.isStalledY() ) {
			this.cr.start();
			while (x != this.cr.getPositionX() || y != this.cr.getPositionY()) {
				this.moveToX(x);
				this.moveToY(y);
			}
			
			if (this.cr.getPositionX() == x && this.cr.getPositionY() == y) {
				return true;
			} else {
				throw new CraneException();
			}
		} else {
			throw new CraneException();
		}
	}
	
	/**
	 * moveToX method drives the crane to the required x coordinate
	 * @param x This is the required x coordinate where the crane needs to go
	 * @return nothing
	 */
	
	private void moveToX(int x) {
		if (x > this.cr.getPositionX()) {
			this.cr.forward();
		} else if (x < this.cr.getPositionX()) {
			this.cr.backward();
		} else {
			this.cr.stopX();
		}
	}
	
	/**
	 * moveToY method drives the crane to the required y coordinate
	 * @param y This is the required y coordinate where the crane needs to go
	 * @return nothing
	 */
	
	private void moveToY(int y) {
		if (y > this.cr.getPositionY()) {
			this.cr.up();
		} else if (y < this.cr.getPositionY()) {
			this.cr.down();
		} else {
			this.cr.stopY();
		}
	}
}
