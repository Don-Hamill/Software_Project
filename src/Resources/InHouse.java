package Resources;

public class InHouse extends Part {

    private int machineID;

    public InHouse(int Id, String name, double price, int stock, int min, int max, int machineID) {
        super(Id, name, price, stock, min, max);
        this.machineID = machineID;
    }


    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }


}
