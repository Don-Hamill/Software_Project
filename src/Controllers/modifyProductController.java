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

import static Resources.Part.searchPart;

public class modifyProductController implements Initializable {


    Product selectedProduct;

    int selectedIndex;

    @FXML
    private TextField AddProductIDTextField;

    @FXML
    private TextField AddNameIDTextField;

    @FXML
    private TextField AddInventoryTextField;

    @FXML
    private TextField AddPriceTextField;

    @FXML
    private TextField AddMaxTextField;

    @FXML
    private TextField AddMinTextField;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> AddPartID;

    @FXML
    private TableColumn<Part, String> AddPartName;

    @FXML
    private TableColumn<Part, Integer> AddInventoryLevel;

    @FXML
    private TableColumn<Part, Double> AddPriceperUnit;

    @FXML
    public TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartID;

    @FXML
    private TableColumn<Part, String> associatedPartName;

    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;

    @FXML
    private TableColumn<Part, Double> associatedPartPrice;

    @FXML
    private Button DeletePart;

    @FXML
    private Button SaveProduct;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField SaveProductTextField;

    public static ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     *
     * @param url passes in URL for file
     * @param rb pass in resource bundle
     */
    //populates tables on modify product scene
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //sets values in part table
        populatePartTable();
        AddPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        AddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddPriceperUnit.setCellValueFactory(new PropertyValueFactory<>("price"));


        //sends selected product from main controller
        selectedProduct = mainController.getProductToBeModified();
        associatedPartList = selectedProduct.getAssociatedParts();

        //sets value of selected product
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void AddInventoryTextField(ActionEvent event) {

    }

    @FXML
    void AddMaxTextField(ActionEvent event) {

    }

    @FXML
    void AddMinTextField(ActionEvent event) {

    }

    @FXML
    void AddNameIDTextField(ActionEvent event) {

    }

    @FXML
    void AddPriceTextField(ActionEvent event) {

    }

    /**
     *
     * @param event passes in event when product is selected and add button is pressed
     */
    @FXML
    void AddProductHandler(ActionEvent event) {


            Part addPart = partsTable.getSelectionModel().getSelectedItem();
            associatedPartList.add(addPart);
            associatedPartTable.setItems(associatedPartList);

    }

    @FXML
    void AddProductIDTextField(ActionEvent event) {

    }

    /**
     *
     * @param event passes in event when user presses cancel button, does not save user input and returns to main screen
     * @throws IOException
     */
    //cancels changes made and returns user to home screen
    @FXML
    void CancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     *
     * @param event passes in event to delete part
     */
    //deletes product and associated parts
    @FXML
    void DeletePartHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Part. Continue?");
        alert.setTitle("Part Deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            selectedProduct.getAssociatedParts().remove(associatedPartTable.getSelectionModel().getSelectedItem());
    }

    /**
     *
     * @param event passes in event for saving product, grabbing information from text fields and setting values for modified product
     * @throws IOException
     */

    @FXML
    void SaveProductHandler(ActionEvent event) throws IOException {

        int id = selectedProduct.getProductID();
        String name = AddNameIDTextField.getText();
        int inventory = Integer.parseInt(AddInventoryTextField.getText());
        double price = Double.parseDouble(AddPriceTextField.getText());
        int max = Integer.parseInt(AddMaxTextField.getText());
        int min = Integer.parseInt(AddMinTextField.getText());

        Product newProduct = new Product(id, name, price, inventory, min, max);


        for (Part part : associatedPartList) {
            newProduct.setAssociatedParts(part);
        }
        Inventory.addProduct(newProduct);
        Inventory.deleteProduct(selectedProduct);

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));

        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }


    /**
     *
     * @param event passes in even when searching for parts
     */
   @FXML
    void searchPartHandler(ActionEvent event) {

       String part = SaveProductTextField.getText();
       ObservableList<Part> searchedPart = searchPart(part);
       partsTable.setItems(searchedPart);
    }

    /**
     *
     * @param product passes in product to reference
     * @param index passes in product index to specify which product from the array list
     */

    public void setProduct(Product product, int index) {
        selectedProduct = product;
        selectedIndex = index;

        if (product != null) {
            this.AddProductIDTextField.setText(Integer.toString(product.getProductID()));
            this.AddNameIDTextField.setText(product.getName());
            this.AddInventoryTextField.setText((Integer.toString(product.getStock())));
            this.AddPriceTextField.setText((Double.toString(product.getPrice())));
            this.AddMaxTextField.setText((Integer.toString(product.getMin())));
            this.AddMinTextField.setText((Integer.toString(product.getMax())));
        }
    }

    /**
     * sets part tables with all parts from inventory
     */

    public void populatePartTable() {
        partsTable.setItems(Inventory.getAllParts());

    }


}
