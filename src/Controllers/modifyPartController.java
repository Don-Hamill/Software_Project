package Controllers;

import Resources.InHouse;
import Resources.Inventory;
import Resources.Outsourced;
import Resources.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyPartController implements Initializable {

        Part modifiedPart;
        Part selectedPart;
        int selectedIndex;


        @FXML
        private TextField ModifyPartID;

        @FXML
        private RadioButton InhouseRadioButton;

        @FXML
        private RadioButton OutsourcedRadioButton;

        @FXML
        private TextField NameModifyPart;

        @FXML
        private TextField InventoryModifyPart;

        @FXML
        private TextField PriceCostModifyPart;

        @FXML
        private TextField MaxModifyPart;

        @FXML
        private TextField MinModifyPart;

        @FXML
        private TextField MachineIDModifyPart;

        @FXML
        private Button CancelButton;

        @FXML
        private Button SaveButton;

    /**
     *
     * @param event passes in event when in-house is selected and sets label to Machine ID:
     */
        @FXML
        void InhouseHandler(ActionEvent event) {
            machineIDLabel.setText("Machine ID:");

        }

        @FXML
        void InventoryModifyPart(ActionEvent event) {

        }

        @FXML
        void MachineIDModifyPart(ActionEvent event) {

        }

        @FXML
        void MaxModifyPart(ActionEvent event) {

        }

        @FXML
        void MinModifyPart(ActionEvent event) {

        }

        @FXML
        private Label machineIDLabel;

        @FXML
        void NameInhouseModifyPart(ActionEvent event) {

        }

    /**
     *
     * @param event passes in event when outsourced is selected and sets label to Company Name:
     */
    @FXML
        void OutsourcedHandler(ActionEvent event) {

            machineIDLabel.setText("Company Name:");
        }

        @FXML
        void PriceCostModifyPart(ActionEvent event) {

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *
     * @param event passes in event when user presses cancel, does not save changes and returns to main page
     * @throws IOException
     */
    @FXML
    void cancelHandler(ActionEvent event) throws IOException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Continue?");

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
     * @param event passes in event when user presses save that ensure integrity of data and saves the modified part
     * @throws IOException
     */
    @FXML
    void saveHandler(ActionEvent event) throws IOException {

        int Id = selectedPart.getId();
        String name = NameModifyPart.getText();
        int inventory = Integer.parseInt(InventoryModifyPart.getText());
        double price = Double.parseDouble(PriceCostModifyPart.getText());
        int max = Integer.parseInt(MaxModifyPart.getText());
        int min = Integer.parseInt(MinModifyPart.getText());

        if (InhouseRadioButton.isSelected()) {

            int machineID = Integer.parseInt(MachineIDModifyPart.getText());

            InHouse inHousePart = new InHouse(Id, name, price, inventory, min, max, machineID);
            Inventory.getAllParts().set(selectedIndex, inHousePart);
        }

        if (OutsourcedRadioButton.isSelected()) {

            String companyName = MachineIDModifyPart.getText();

            Outsourced outsourcedPart = new Outsourced(Id, name, price, inventory, min, max, companyName);
            Inventory.getAllParts().set(selectedIndex, outsourcedPart);
        }
        try{
            //grabs value for price cost and ensures it is a double or throws an error
            if(!checkDouble(PriceCostModifyPart)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();}

            //min and max field check
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            }

            //min and max field check
            else if (inventory > max || inventory < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else{Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/MainForm.fxml")));
                stage.setScene(new Scene((Parent) scene));
                stage.show();}

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
     * @param part passes in part and sets it based on index and if it is outsourced or in-house
     * @param index pass in index of part
     */
    public void setPart(Part part, int index) {
        selectedPart = part;
        selectedIndex = index;

        if (part instanceof InHouse) {

            InHouse newPart = (InHouse) part;
            InhouseRadioButton.setSelected(true);
            machineIDLabel.setText("Machine ID:");
            this.ModifyPartID.setText(Integer.toString(newPart.getId()));
            this.NameModifyPart.setText(newPart.getName());
            this.InventoryModifyPart.setText((Integer.toString(newPart.getStock())));
            this.PriceCostModifyPart.setText((Double.toString(newPart.getPrice())));
            this.MinModifyPart.setText((Integer.toString(newPart.getMin())));
            this.MaxModifyPart.setText((Integer.toString(newPart.getMax())));
            this.MachineIDModifyPart.setText((Integer.toString(newPart.getMachineID())));

        }

        if (part instanceof Outsourced) {

            Outsourced newPart = (Outsourced) part;
            OutsourcedRadioButton.setSelected(true);
            machineIDLabel.setText("Company Name:");
            this.ModifyPartID.setText(Integer.toString(newPart.getId()));
            this.NameModifyPart.setText(newPart.getName());
            this.InventoryModifyPart.setText((Integer.toString(newPart.getStock())));
            this.PriceCostModifyPart.setText((Double.toString(newPart.getPrice())));
            this.MinModifyPart.setText((Integer.toString(newPart.getMin())));
            this.MaxModifyPart.setText((Integer.toString(newPart.getMax())));
            this.MachineIDModifyPart.setText(newPart.getCompanyName());
        }
    }

    /**
     *
     * @param t passes in textfield to check if it is a double, otherwise throws exception
     * @return
     */
    private boolean checkDouble(TextField t) {
        try{Double.parseDouble(t.getText());}
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();}
        return true;
    }
}
