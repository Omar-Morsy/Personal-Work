package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StorageException;

public class HarborStorageApp {
	
	/**
	 * This is the main method to test our program. The test is mainly to test the retrievePacket method 
	 * by adding packets to the storage area and then retrieving one of them then adding another then
	 * printing all the packets stored in the storage area
	 * @param args
	 * @throws StorageException
	 */
	
	public static void main (String[] args) throws StorageException {
		HarborStorageManagement a = new HarborStorageManagement();
		a.storePacket(4, 1, 1, "Hermes", 1);
		a.storePacket(2, 1, 1, "DHL", 1);
		a.storePacket(1, 1, 1, "Amazon", 1);
		a.retrievePacket("DHL");
		a.storePacket(4, 1, 1, "AliExpress", 1);
		a.storePacket(3, 1, 1, "DPD", 1);
		a.retrievePacket("AliExpress");
		a.storePacket(4, 1, 1, "Ali", 1);
		showAvailablePackets(a);
	}
	
	/**
	 * This method is implemented to print all packets stored in the storage area with their description,
	 * id and location to the console
	 * @param a
	 * @return nothing
	 */
	
	public static void showAvailablePackets(HarborStorageManagement a) {
		System.out.println("The Packets Stored :");
		for(int i = 0; i < a.getPackets().length; i++) {
			if (a.getPackets()[i] != null) {
				System.out.println("Packet " + (i + 1) + ": Description = " + a.getPackets()[i].getDescription() + "   Slot Location = " + a.getPackets()[i].getLocation().getNumber() + "  ID = " + a.getPackets()[i].getId());
			}
		}
		
	}
}
