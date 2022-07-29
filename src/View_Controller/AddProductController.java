package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Class of the Controller for the AddProduct fxml screen.
 *
 *  FUTURE ENHANCEMENT: I would like to update the saveAPro to be more organized with the if statements and using
 *  less code. Right now, it seems like a bit of a mess, but I know if I had more time/knowledge, I would probably make
 *  it less messy. Also change some of the methods name around to match other classes. Looking at different webinar and
 *  tutorials made me name some methods differently.
 *  */
public class AddProductController implements Initializable {

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

    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<Part, Integer> addIDColumn;
    @FXML private TableColumn<Part, String> addNameColumn;
    @FXML private TableColumn<Part, Integer> addCountColumn;
    @FXML private TableColumn<Part, Double> addPriceCostColumn;

    @FXML private TableView<Part> addProductTable2;
    @FXML private TableColumn<Part, Integer> addIDColumn2;
    @FXML private TableColumn<Part, String> addNameColumn2;
    @FXML private TableColumn<Part, Integer> addCountColumn2;
    @FXML private TableColumn<Part, Double> addPriceCostColumn2;

    @FXML private TextField addProductSearchBox;
    @FXML private Button addSaveBtn;
    @FXML private Button addCancelButton;
    @FXML private Button removeBtn;
    @FXML private Button addBtn;
    @FXML private Button addSearchBtn;
    boolean partExists = false;
    private ObservableList<Part> productPart = FXCollections.observableArrayList();

    Stage stage;
    Parent par;
    Scene scene;

    /**Sets the bottom table with products given. */
    void updateProductPartTable2(){
        addProductTable2.setItems(productPart);
    }
    /** Adds part to the second table if it already does not exist.
        If it does exist, it will give out a warning. */
    @FXML
    void addAPro(ActionEvent event) {
        Part part = addProductTable.getSelectionModel().getSelectedItem();
        for (int x = 0; productPart.size() > x; x++){
            if (productPart.get(x).getId() == part.getId()) {
                partExists = true;
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
    void cancelAPro(ActionEvent event) {
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
    void removeAPro(ActionEvent event) {
        Part part = addProductTable2.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Remove Part");
        alert.setHeaderText("Confirm removing part");
        alert.setContentText("Are you sure you want to remove the part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            productPart.remove(part);
            updateProductPartTable2();
        }
    }
    /** This actions saves the inputs from the fields that the user has inputted with the save button.
        Includes warnings if any field has wrong data entered, if everything is correct it saves the given data.
        Then switches to the main screen window. */
    @FXML
    void saveAPro(ActionEvent event) {
        int proID = (Inventory.getAllProducts().size() + 1);
        String proName = nameField.getText();
        int proInv = 0;
        double propriceCost = 0;
        int proMax = 0;
        int proMin = 0;
        ObservableList<Part> savedParts = FXCollections.observableArrayList();
        savedParts.addAll(addProductTable2.getItems());

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
            Inventory.addProduct(new Product(proID, proName, propriceCost, proInv, proMin, proMax, savedParts));
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
    void searchAPro(ActionEvent event) {
        String searchTextModPart = addProductSearchBox.getText().trim();
        int searchId = 0;
        if(!searchTextModPart.isEmpty())  {
            if (searchTextModPart.matches("[0-9]+")){
                searchId = Integer.parseInt(searchTextModPart);
                addProductTable.setItems(Inventory.lookupPart(searchId));
            }
            else {
                addProductTable.setItems(Inventory.lookupPart(searchTextModPart));
            }
        }
        else {
            addProductTable.setItems(Inventory.getAllParts());
        }
    }
    /**Sets the the fields and fills the table when first initialized. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);
        idField.setPromptText("Auto Gen Disabled");
        nameField.setPromptText("Product Name");
        invField.setPromptText("Inventory Stock");
        priceField.setPromptText("Product Price/Cost");
        maxField.setPromptText("Maximum");
        minField.setPromptText("Minimum");

        addIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCountColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductTable.setItems(Inventory.getAllParts());

        addIDColumn2.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCountColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCostColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductTable2.setItems(productPart);
    }

}
