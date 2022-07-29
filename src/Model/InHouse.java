package Model;
/**
 *
 * @author Jose Arvizu
 */

/** Class for Inhouse information.*/
public class InHouse extends Part {

    private int machineID;
    /** InHouse constructor. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /** Getter for machineID.
        @return the machineID */
    public int getMachineID() {
        return machineID;
    }
    /** Setter for machineID.
        @param machineID the machineID to set */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
