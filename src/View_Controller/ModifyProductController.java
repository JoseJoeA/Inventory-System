package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Class  of the Controller for the ModifyProduct fxml screen.*/
public class ModifyProductController implements Initializable {

    @FXML private Label idLbl;
    @FXML private Label nameLbl;
    @FXML private Label invLbl;
    @FXML private Label priceLbl;
    @FXML private Label maxLbl;
    @FXML private Label minLbl;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;

    @FXML private TableView<Part> modProductTable;
    @FXML private TableColumn<?, ?> modIDColumn;
    @FXML private TableColumn<?, ?> modNameColumn;
    @FXML private TableColumn<?, ?> modCountColumn;
    @FXML private TableColumn<?, ?> modPriceCostColumn;
    @FXML private TableView<Part> modProductTable2;
    @FXML private TableColumn<?, ?> modIDColumn2;
    @FXML private TableColumn<?, ?> modNameColumn2;
    @FXML private TableColumn<?, ?> modCountColumn2;
    @FXML private TableColumn<?, ?> modPriceCostColumn2;
    @FXML private TextField modProductSearchBox;

    @FXML private Button modSaveBtn;
    @FXML private Button modCancelButton;
    @FXML private Button removeBtn;
    @FXML private Button addBtn;
    @FXML private Button modSearchBtn;
    boolean partExists = false;
    private static Product proMod;
    private ObservableList<Part> productPart = proMod.getAllAssociatedParts();

    Stage stage;
    Parent par;
    Scene scene;
    /**Quick class in calling the products already stored. */
    public static void getPart(Product product) {
        proMod = product;
    }
    /**Sets the bottom table with products given.  */
    void updateProductPartTable2(){
        modProductTable2.setItems(productPart);
    }
    /** Adds part to the second table if it already does not exist.
     If it does exist, it will give out a warning. */
    @FXML
    void addMPro(ActionEvent event) {
        Part part = modProductTable.getSelectionModel().getSelectedItem();
        for (int x = 0; productPart.size() > x; x++){
            if (productPart.get(x).getId() == part.getId()) {
                partExists = true;
                break;
            }
        }
        if (!partExists){
            productPart.add(part);
            updateProductPartTable2();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Warning Error");
            alert.setHeaderText("Part Exists");
            alert.setContentText("The part already exists!");
            alert.showAndWait();
        }
    }
    /**Adds the action for the cancel button to switch to the main screen window. */
    @FXML
    void cancelMPro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Exit Product");
        alert.setHeaderText("Product confirm cancel");
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
    /** Removes the selected part when the remove button is clicked. Gives a confirmation whether to remove or not. */
    @FXML
    void removeMPro(ActionEvent event) {
        Part part = modProductTable2.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Remove Part");
        alert.setHeaderText("Confirm removing part");
        alert.setContentText("Are you sure you want to remove the part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            productPart.remove(part);
            updateProductPartTable2();
        }
    }
    /** This actions saves the inputs from the fields that the user has inputted with the save button.
     Includes warnings if any field has wrong data entered, if everything is correct it saves the given data.
     Then switches to the main screen window.

     RUNTIME ERROR: Had an error when saving the modified product, which was adding instead of updating the given data.
     It was just a small error fixed by using a correct method from the Inventory class.
     */
    @FXML
    void saveMPro(ActionEvent event) {
        int proID = proMod.getId();
        String proName = nameField.getText();
        int proInv = 0;
        double propriceCost = 0;
        int proMax = 0;
        int proMin = 0;
        ObservableList<Part> savedParts = FXCollections.observableArrayList();
        savedParts.addAll(modProductTable2.getItems());

        try {
            proInv = Integer.parseInt(invField.getText());
            propriceCost = Double.parseDouble(priceField.getText());
            proMax = Integer.parseInt(maxField.getText());
            proMin = Integer.parseInt(minField.getText());
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

        if (proMax < proMin){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Max/Min Error");
            alert.setContentText("The minimum is greater than the maximum!");
            alert.showAndWait();
            return;
        }
        if (proInv > proMax){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("The Inventory is greater than the maximum!");
            alert.showAndWait();
            return;
        }

        if (proInv < proMin){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("The Inventory is less than the minimum!");
            alert.showAndWait();
            return;
        }

        if(proMax >= proMin && proInv <= proMax && proInv >= proMin){
            Inventory.updateProduct(proMod.getId(),new Product(proID, proName, propriceCost, proInv, proMin, proMax, savedParts));
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
    /** Searches for what the user inputted.
        If the user inputted a number, it will use the lookuppart with an integer.
        If the user inputted a string, it will use the lookuppart with strings.
        Else it will return with all the parts if blank. */
    @FXML
    void searchMPro(ActionEvent event) {

        String searchTextModPart = modProductSearchBox.getText().trim();
        int searchId = 0;
        if(!searchTextModPart.isEmpty())  {
            if (searchTextModPart.matches("[0-9]+")){
                searchId = Integer.parseInt(searchTextModPart);
                modProductTable.setItems(Inventory.lookupPart(searchId));
            }
            else {
                modProductTable.setItems(Inventory.lookupPart(searchTextModPart));
            }
        }
        else {
            modProductTable.setItems(Inventory.getAllParts());
        }
    }
    /** Sets the the fields and fills the table when first initialized.
        Gets information already entered before. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText(Integer.toString(proMod.getId()));
        nameField.setText(proMod.getName());
        invField.setText(Integer.toString(proMod.getStock()));
        priceField.setText(Double.toString(proMod.getPrice()));
        maxField.setText(Integer.toString(proMod.getMax()));
        minField.setText(Integer.toString(proMod.getMin()));

        modIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modCountColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProductTable.setItems(Inventory.getAllParts());

        modIDColumn2.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        modCountColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPriceCostColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProductTable2.setItems(productPart);
    }
}
