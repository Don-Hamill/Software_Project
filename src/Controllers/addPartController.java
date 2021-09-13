package Controllers;


import Resources.InHouse;
import Resources.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Resources.Inventory;
import Resources.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class addPartController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

        @FXML
        private RadioButton InhouseRadioButton;

        @FXML
        private RadioButton OutsourcedRadioButton;

        @FXML
        private TextField NameAddPart;

        @FXML
        private TextField field;

        @FXML
        private TextField InventoryAddPart;

        @FXML
        private TextField PriceCostAddPart;

        @FXML
        private TextField IDAddPart;

        @FXML
        private TextField MaxAddPart;

        @FXML
        private TextField MinAddPart;

        @FXML
        private TextField MachineIDAddPart;

        @FXML
        private TextField CompanyNameAddPart;

        @FXML
        private Button CancelButton;

        @FXML
        private Button SaveButton;

        private boolean isOutsourced = true;

        @FXML
        private Label machineIDlabel;


        @FXML
        void IDAddPart(ActionEvent event) {


        }

        @FXML
        void InhouseHandler(ActionEvent event) {

            isOutsourced = false;
            machineIDlabel.setText("Machine ID:");

        }

        @FXML
        void InventoryAddPart(ActionEvent event) {

        }

        @FXML
        void MachineIDAddPart(ActionEvent event) {

        }

        @FXML
        void MaxAddPart(ActionEvent event) {

        }

        @FXML
        void MinAddPart(ActionEvent event) {

        }


        @FXML
        void NameAddPart(ActionEvent event) {

        }

        @FXML
        void OutsourcedHandler(ActionEvent event) {

            isOutsourced = true;
            machineIDlabel.setText("Company Name:");
        }

        @FXML
        void PriceCostAddPart(ActionEvent event) {

        }

        @FXML
        void cancelHandler(ActionEvent event) throws IOException {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values. Continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

            }
        }


        @FXML
        void saveHandler(ActionEvent event){
            //checks for parts for ID and sets ID
            int ID = 0;
            for(Part part : Inventory.getAllParts()) {

                if(part.getId() > ID)

                    ID = part.getId();

            }
            //grabs values from scene for adding part
            IDAddPart.setText(String.valueOf(++ID));
            String name = NameAddPart.getText();
            int inventory = Integer.parseInt(InventoryAddPart.getText());
            int max = Integer.parseInt(MaxAddPart.getText());
            int min = Integer.parseInt(MinAddPart.getText());




            try{
                //grabs value for price cost and ensures it is a double or throws an error
                double priceCost = Double.parseDouble(PriceCostAddPart.getText());
                if(!checkDouble(PriceCostAddPart)){
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
                //when integrity of data is assured, part is added.
                else {

                    if (InhouseRadioButton.isSelected()) {
                        int machineID = Integer.parseInt(MachineIDAddPart.getText());
                        InHouse addPart = new InHouse(ID, name, priceCost, inventory, min, max, machineID);

                        Inventory.addPart(addPart);
                    }
                    if (OutsourcedRadioButton.isSelected()) {
                        String companyName = MachineIDAddPart.getText();
                        Outsourced addPart = new Outsourced(ID, name, priceCost, inventory, min, max, companyName);

                        Inventory.addPart(addPart);
                    }


                    Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }

            }
            //catches exceptions and produces pop-up window
            catch(Exception e){


                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();

            }




        }

   //method checking value of price cost to ensure it is a double
    private boolean checkDouble(TextField t) {
        try{Double.parseDouble(t.getText());}
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();}
        return true;
    }}



