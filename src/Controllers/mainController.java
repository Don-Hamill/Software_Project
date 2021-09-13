package Controllers;

import Resources.Inventory;
import Resources.Part;
import Resources.Product;
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
import static Resources.Part.searchProduct;
import static Controllers.modifyProductController.associatedPartList;


public class mainController implements Initializable {

    private static Product productToBeModified;

    @FXML
    private TextField SearchPartText;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> Id;

    @FXML
    private TableColumn<Part, String> PartName;

    @FXML
    private TableColumn<Part, Integer> PartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> PartPricePerUnit;

    @FXML
    private Button AddPart;

    @FXML
    private Button ModifyPart;

    @FXML
    private TextField SearchProductText;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> ProductID;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryLevel;

    @FXML
    private TableColumn<Product, Double> ProductPricePerUnit;

    @FXML
    private Button ModifyProduct;

    @FXML
    private Button Exit;

    /**
     *
     * @param url initializes data on main screen with products and parts from inventory
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //populate tables on main screen
        productTable.setItems(Inventory.getAllProducts());

        ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());

        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     *
     * @param event searches parts to check for matching string or ID
     */
    @FXML
    void SearchTextPartHandler(ActionEvent event) {
        //string search to determine if desired part is present in listed parts, returns error if it does not exist.

        String part = SearchPartText.getText();
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
     *
     * @param event searches products to check for matching string or ID
     */

    @FXML
    void SearchTextProductHandler(ActionEvent event) {
        //string search to determine if desired product is present in listed products

        String prod = SearchProductText.getText();
        ObservableList<Product> searchedProducts = searchProduct(prod);
        productTable.setItems(searchedProducts);
        if(searchedProducts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No matching product exists.");
            alert.setTitle("Error");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                productTable.setItems(Inventory.getAllProducts());
            }
        }


    }

    /**
     *
     * @param event sends user to add part window
     * @throws IOException
     */

    @FXML
    void addPartHandler(ActionEvent event) throws IOException {
        //change scenes to add Part
        Stage stage;
        Parent root;
        stage=(Stage) AddPart.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();


    }

    /**
     *
     * @param event sends user to add Product window
     * @throws IOException
     */

    @FXML
    void addProductHandler(ActionEvent event) throws IOException {
        //Change windows to Add product window

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddProduct.fxml")));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    /**
     *
     * @param event passes in event after user selects delete part. Prompts with warning if nothing is chosen and another warning before deletion.
     */
    @FXML
    void deletePartHandler(ActionEvent event) {

        //Let user know to select a part
        if(partsTable.getSelectionModel().getSelectedItems().isEmpty()){
            Alert noneSelected = new Alert(Alert.AlertType.CONFIRMATION, "You must select a part to Delete. ");
            noneSelected.setTitle("Select a part.");
            Optional<ButtonType> result = noneSelected.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                return;
            }
        }
        else{
        //Present alert to ensure the desired deletion, then delete the product if confirmed.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Part. Do you wish to continue?");
        alert.setTitle("Part deleted.");
        Optional<ButtonType> result = alert.showAndWait();



        if(result.isPresent() && result.get() == ButtonType.OK) {

            ObservableList<Part> allParts, singlePart;
            allParts = partsTable.getItems();
            singlePart = partsTable.getSelectionModel().getSelectedItems();
            singlePart.forEach(allParts::remove);

        }}
    }

    /**
     *
     * @param event deletes product while ensuring data integreity by checking for associated parts and that a product is selected
     */
    @FXML
    void deleteProductHandler(ActionEvent event) {

        //Let user know to select a product
        if(productTable.getSelectionModel().getSelectedItems().isEmpty()){
            Alert noneSelected = new Alert(Alert.AlertType.CONFIRMATION, "You must select a product to Delete. ");
            noneSelected.setTitle("Select a product.");
            Optional<ButtonType> result = noneSelected.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                return;
            }
        }
        //Check if Product has associated parts with it and prevent deletion if so.
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> associatedParts = selectedProduct.getAssociatedParts();
        if(associatedParts.size() != 0){
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Cannot delete product that has associated parts.");
            alert2.setTitle("ERROR");
            Optional<ButtonType> result = alert2.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){return;
        }}

        //If product does not have any associated parts, permit deletion.
        if(associatedParts.size() == 0){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Product. Continue?");
        alert.setTitle("Product deleted.");
        Optional<ButtonType> result2 = alert.showAndWait();
        if(result2.isPresent() && result2.get() == ButtonType.OK) {

            if(associatedParts.size() == 0){
            ObservableList<Product> allProducts, singlePart;
            allProducts = productTable.getItems();
            singlePart = productTable.getSelectionModel().getSelectedItems();
            singlePart.forEach(allProducts::remove);

        }
    }}}

    /**
     *
     * @param event passes in when user selects to exit the application and closes it
     */
    @FXML
    void exitHandler(ActionEvent event) {

        System.exit(0);

    }

    /**
     *
     * @param event passes in event from user selecting modify part, sends part over to other scene
     * @throws IOException
     */
    @FXML
    void modifyPartHandler(ActionEvent event) throws IOException {

        //Let user know to select a part
        if(partsTable.getSelectionModel().getSelectedItems().isEmpty()){
            Alert noneSelected = new Alert(Alert.AlertType.CONFIRMATION, "You must select a part to Modify. ");
            noneSelected.setTitle("Select a part.");
            Optional<ButtonType> result = noneSelected.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                return;
            }
        }

        //change windows to modify part window and send selected part over to modify part controller to be edited.
        Stage stage;
        Parent root;
        stage=(Stage) ModifyPart.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/ModifyPart.fxml"));

        root =loader.load();
        modifyPartController controller = loader.getController();
        Part part = partsTable.getSelectionModel().getSelectedItem();
        int index = partsTable.getSelectionModel().getSelectedIndex();


        if(part != null) {
            controller.setPart(part, index);
        }
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();





    }

    /**
     *
     * @return product to be modified on modify product screen
     */
    public static Product getProductToBeModified() {
        //get selected product for modify product
        return productToBeModified;
    }

    /**
     *
     * @param event sends user to modify product scene with selectedd product
     * @throws IOException
     */
    @FXML
    void modifyProductHandler(ActionEvent event) throws IOException {
        //Let user know to select a product
        if(productTable.getSelectionModel().getSelectedItems().isEmpty()){
            Alert noneSelected = new Alert(Alert.AlertType.CONFIRMATION, "You must select a product to Modify. ");
            noneSelected.setTitle("Select a product.");
            Optional<ButtonType> result = noneSelected.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                return;
            }
        }

        //load up Modify Product FXML document
        Stage stage;
        Parent root;
        stage=(Stage) ModifyProduct.getScene().getWindow();


        //Grab product to send to Modify product controller from main and send it with associated parts
        productToBeModified = productTable.getSelectionModel().getSelectedItem();
        int index = productTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ModifyProduct.fxml"));
        root =loader.load();
        modifyProductController controller;
        controller = loader.getController();
        if(productToBeModified != null && controller != null) {
            controller.setProduct(productToBeModified, index);
            controller.associatedPartTable.setItems(associatedPartList);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

