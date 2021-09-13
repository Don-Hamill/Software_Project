package Controllers;

import Resources.Inventory;
import Resources.Part;
import Resources.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class addProductController implements Initializable {

    Product newProduct;

    @FXML private TextField IDAddPartText;

    @FXML private TextField NameAddPartText;

    @FXML private TextField InventoryAddPartText;

    @FXML private TextField AddPriceTextField;

    @FXML private TextField MaxAddPartText;

    @FXML private TextField MinAddPartText;

    @FXML private TextField searchPart;

    @FXML private TableView<Part> partsTable;

    @FXML private TableColumn<Part, Integer> PartID;

    @FXML private TableColumn<Part, String> PartName;

    @FXML private TableColumn<Part, Integer> PartInventoryLevel;

    @FXML private TableColumn<Part, Double> PriceCostPerUnit;

    @FXML private Button AddProduct;

    @FXML private TableView<Part> associatedPartTable;

    @FXML private TableColumn<Part, Integer> associatedPartID;

    @FXML private TableColumn<Part, String> associatedPartName;

    @FXML private TableColumn<Part, Integer> associatedPartInventory;

    @FXML private TableColumn<Part, Double> associatedPartPrice;

    @FXML private Button DeleteAssociatedPartHandler;

    @FXML private Button DeleteProduct;

    @FXML private Button SaveProduct;

    @FXML private Button CancelButton;

    //populates tables on add product scene

    /**
     *
     * @param url pass in pointer to the resource you need
     * @param rb pass in resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        populatePartTable();

        PartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        newProduct = new Product(0, null, 0.0, 0, 0, 0);
        associatedPartTable.setItems(newProduct.getAssociatedParts());

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    @FXML void AddPriceTextField(ActionEvent event) {

    }

    /**
     *
     * @param event passes in event that adds new product from selected tables on app product page
     */
    //adds product and sets associated parts
    @FXML void AddProductHandler(ActionEvent event){

        Part singlePart = partsTable.getSelectionModel().getSelectedItem();
        newProduct.setAssociatedParts(singlePart);
        associatedPartTable.setItems(newProduct.getAssociatedParts());
    }

    /**
     *
     * @param event passes in event when user presses cancel
     * @throws IOException
     */

    @FXML void CancelButton(ActionEvent event) throws IOException {
    //Alerts user that product will not be saved
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This product will not be saved. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     *
     * @param event passes in event to remove associated parts from table
     */

    @FXML void DeleteAssociatedPartHandler(ActionEvent event) {
        //deletes part selected in associated part table
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Part. Continue?");
        alert.setTitle("Part Deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            newProduct.getAssociatedParts().remove(associatedPartTable.getSelectionModel().getSelectedItem());

        }

    }

    @FXML void IDAddPartText(ActionEvent event) {

    }

    @FXML void InventoryAddPartText(ActionEvent event) {

    }

    @FXML void MaxAddPartText(ActionEvent event) {

    }

    @FXML void MinAddPartText(ActionEvent event) {

    }

    @FXML void NameAddPartText(ActionEvent event) {

    }

    /**
     *
     * @param event passes in event to generate product ID when saving product as well as all other pertinent product information
     * @throws IOException
     */

    @FXML void SaveProductHandler(ActionEvent event) throws IOException {
        //sets product ID
        int ID = 0;
        for(Product product : Inventory.getAllProducts()) {

            if(product.getProductID() > ID)
                ID = product.getProductID();

        }

        //gets all values from text fields
        String name = NameAddPartText.getText();
        int inventory = Integer.parseInt(InventoryAddPartText.getText());
        int max = Integer.parseInt(MaxAddPartText.getText());
        int min = Integer.parseInt(MinAddPartText.getText());
        IDAddPartText.setText(String.valueOf(++ID));

        try{
            //grabs value for price cost and ensures it is a double or throws an error
            double priceCost = Double.parseDouble(AddPriceTextField.getText());
            if(!checkDouble(AddPriceTextField)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();}

            //min and max field check
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            }

            //min and max field check
            else if (inventory > max || inventory < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }

            //when integrity of data is assured, product is added.
            else {

            newProduct.setProductID(ID);
            newProduct.setName(name);
            newProduct.setPrice(priceCost);
            newProduct.setMax(max);
            newProduct.setMin(min);
            newProduct.setStock(inventory);

            Inventory.addProduct(newProduct);}


            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("JavaDocs/MainForm/MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        catch(Exception e){


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();

        }
    }

    /**
     *
     * @param event passes in event when user is typing in search bar to locate a part
     */
    //searches parts and displays alert if not present, then resets table
    @FXML void searchPart(ActionEvent event) {


        String part = searchPart.getText();
        ObservableList<Part> searchedParts = searchPart(part);
        partsTable.setItems(searchedParts);
        if(searchedParts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No matching part exists.");
            alert.setTitle("Error");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * populates part table with all parts
     */

    public void populatePartTable() {
        partsTable.setItems(Inventory.getAllParts());

    }

    /**
     *
     * @param t passes in textfield for price and ensures it is a double or throws error
     * @return returns true
     */
    //method checking value of price cost to ensure it is a double
    private boolean checkDouble(TextField t) {
        try{Double.parseDouble(t.getText());}
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();}
        return true;
    }

    /**
     *
     * @param partialPart passes in part of the string for the part being searched and returns the parts that fit the criteria
     * @return the parts that fit the string
     */
    //implements searchPart
    public static ObservableList<Part> searchPart(String partialPart){

        ObservableList<Part> neededParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part p : allParts) {
            if(p.getName().contains(partialPart) || Integer.toString(p.getId()).equals(partialPart))
                neededParts.add(p);
        }
        return neededParts;
    }
}
