package Resources;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int partID, String name, double price, int stock, int min, int max, String companyName) {
        super(partID, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
