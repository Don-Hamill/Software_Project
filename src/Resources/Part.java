package Resources; /**
* Supplied class Part.java 
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Don Hamill
 */
public abstract class Part {
    private int Id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int Id, String name, double price, int stock, int min, int max) {
        this.Id = Id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return Id;
    }

    /*
     * @param id the id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    public static ObservableList<Part> searchPart(String partialPart){

    ObservableList<Part> neededParts = FXCollections.observableArrayList();

    ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part p : allParts) {
            if(p.getName().contains(partialPart) || Integer.toString(p.getId()).equals(partialPart))
                neededParts.add(p);
        }
        return neededParts;
    }

    public static ObservableList<Product> searchProduct(String partialProduct){

        ObservableList<Product> neededProducts = FXCollections.observableArrayList();

        ObservableList<Product> allParts = Inventory.getAllProducts();

        for (Product p : allParts) {
            if(p.getName().contains(partialProduct) || Integer.toString(p.getProductID()).equals(partialProduct))
                neededProducts.add(p);
        }
        return neededProducts;
    }}