package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Class  of the Controller for the AddPart fxml screen.
 *
 *  FUTURE ENHANCEMENT: I would like to update the saveAddPart to be more organized with the if statements and using
 *  less code. Right now, it seems like a bit of a mess, but I know if I had more time/knowledge, I would probably make
 *  it less messy.
 * */
public class AddPartController implements Initializable {

    @FXML private RadioButton inhouseRB;
    @FXML private RadioButton outsourcedRB;

    @FXML private Label idLbl;
    @FXML private Label pricecostLbl;
    @FXML private Label nameLbl;
    @FXML private Label maxLbl;
    @FXML private Label invLbl;
    @FXML private Label machinecompanyLbl;
    @FXML private Label minLbl;

    @FXML private TextField idField;
    @FXML private TextField minField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField pricecostField;
    @FXML private TextField maxField;
    @FXML private TextField machinecompanyField;

    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
    @FXML private ToggleGroup tgInOut;
    Stage stage;
    Parent par;
    Scene scene;
    /**Adds the action for the cancel button to switch to the main screen window. */
    @FXML void cancelAddPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Exit part");
        alert.setHeaderText("Part confirm cancel");
        alert.setContentText("Are you sure you want to cancel");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){

            try {
                par = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(par,900,600);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**If the radio button is selected, it changes the last label and field to the inhouse options. */
    @FXML
    void inHouseToggle(ActionEvent event) {

        machinecompanyLbl.setText("Machine ID");
        machinecompanyField.setPromptText("Machine ID");
    }
    /**If the radio button is selected, it changes the last label and field to the outsourced options. */
    @FXML
    void outsourcedToggle(ActionEvent event) {

        machinecompanyLbl.setText("Company Name");
        machinecompanyField.setPromptText("Company Name");
    }
    /** This actions saves the inputs from the fields that the user has inputted with the save button.
     Includes warnings if any field has wrong data entered, if everything is correct it saves the given data.
     Then switches to the main screen window.

     RUNTIME ERROR: Corrected several attributes where the wrong information would be compared, quick fix, but caused
     some confusion. */
    @FXML
    void saveAddPart(ActionEvent event) {
        int partID = (Inventory.getAllParts().size() + 1);
        String partName = nameField.getText();
        int partInv = 0;
        double partpriceCost;
        int partMax = 0;
        int partMin= 0;
        String companyName = null;
        int machineID = 0;

        try {
            if (inhouseRB.isSelected()) {
                machineID = Integer.parseInt(machinecompanyField.getText());
            }

            partInv = Integer.parseInt(invField.getText());
            partpriceCost = Double.parseDouble(pricecostField.getText());
            partMax = Integer.parseInt(maxField.getText());
            partMin = Integer.parseInt(minField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("You have entered wrong data in "+ e + "!");
            alert.showAndWait();
            return;
        }

        if (outsourcedRB.isSelected()) {
             companyName = machinecompanyField.getText();
        }

        if (partMax < partMin){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Max/Min Error");
            alert.setContentText("The minimum is greater than the maximum!");
            alert.showAndWait();
            return;
        }
        if (partInv > partMax){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("The Inventory is greater than the maximum!");
            alert.showAndWait();
            return;
        }
        if (partInv < partMin){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("The Inventory is less than the minimum!");
            alert.showAndWait();
            return;
        }

        if((partMax >= partMin) && (partInv <= partMax) && (partInv >= partMin)){
            if (inhouseRB.isSelected()){
                Inventory.addPart(new InHouse(partID, partName, partpriceCost, partInv, partMin, partMax, machineID));
            }
            else if (outsourcedRB.isSelected()){
                Inventory.addPart(new OutSourced(partID, partName, partpriceCost, partInv, partMin, partMax, companyName));
            }
        }

        try {
            par = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,900,600);
        stage.setScene(scene);
        stage.show();
    }
    /**Sets the the fields when first initialized. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText("Auto Gen Disabled");
        nameField.setPromptText("Part Name");
        invField.setPromptText("Part Inventory");
        pricecostField.setPromptText("Part Price/Cost");
        maxField.setPromptText("Max");
        minField.setPromptText("Min");
        machinecompanyField.setPromptText("Machine ID");

    }
}

