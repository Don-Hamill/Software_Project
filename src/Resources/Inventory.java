package Resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static boolean deleteProduct(Product product) {
        if (allProducts.contains(product)) {
            allProducts.remove(product);
            return true;
        }
        else {
            return false;
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




}

