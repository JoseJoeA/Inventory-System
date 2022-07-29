package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class of the Controller for the MainScreen fxml screen.
 *
 * FUTURE ENHANCEMENT: Would like to change the OnActionPartSearch and OnActionProductSearch to use less code if possible.
 * Right now it seems like I added to much unnecessary code for those methods.
 * (applies to the AddProduct/ModifyProduct)
 * */
public class MainScreenController implements Initializable {

    @FXML private Button exitBtn;
    @FXML private TextField partSearchBox;
    @FXML private Button partAddButton;
    @FXML private Button partModifyButton;
    @FXML private Button partDeleteButton;
    @FXML private Button partSearchButton;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partCountColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    @FXML private TextField productSearchBox;
    @FXML private Button productAddButton;
    @FXML private Button productModifyButton;
    @FXML private Button productDeleteButton;
    @FXML private Button productSearchButton;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productCountColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    Stage stage;
    Parent par;
    Scene scene;
    /**Opens the ModifyPart window when it selects a part from the table. */
    @FXML
    void OnActionPartModify(ActionEvent event) throws IOException {
        Part partMod = partsTable.getSelectionModel().getSelectedItem();
        ModifyPartController.getPart(partMod);
        par = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,600,400);
        stage.setScene(scene);
        stage.show();
    }
    /**Opens the AddProduct window. */
    @FXML
    void OnActionProductAdd(ActionEvent event) throws IOException {

        par = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,700,800);
        stage.setScene(scene);
        stage.show();


    }
    /** Deletes the product if selected when the Delete button is clicked. If the product has an associated part, it cannot be removed.

        RUNTIME ERROR: Had trouble figuring out a way to implement the product so it would delete if it had a part
        associated with it. At the end I forgot to add a getSelectedItem(), which pretty much made everything not work as intended. */
    @FXML
    void OnActionProductDelete(ActionEvent event) {
        if (productsTable.getSelectionModel().getSelectedItem() != null && productsTable.getItems() != null) {
            Product productDelete = productsTable.getSelectionModel().getSelectedItem();
            if (!productDelete.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Error");
                alert.setHeaderText("Product cannot not be deleted due to an associated part!");
                alert.setContentText("Remove associated part to delete product.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Delete Box");
                alert.setHeaderText("Product will be deleted");
                alert.setContentText("Are you sure you want to delete?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productDelete);
                }
            }
        }
    }
    /**Opens the ModifyProduct window. */
    @FXML
    void OnActionProductModify(ActionEvent event) throws IOException {
        Product proMod = productsTable.getSelectionModel().getSelectedItem();
        ModifyProductController.getPart(proMod);
        par = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(par,700,800);
        stage.setScene(scene);
        stage.show();
    }

    /**Exits the inventory program if the button is clicked and confirms if they are sure to exit. */
    @FXML
    void exitProgramButton(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Program");
        alert.setContentText("Are you sure you exit program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**Opens the AddPart window. */
    @FXML
    void onActionPartAdd(ActionEvent event) throws IOException {

          par = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
          stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
          scene = new Scene(par,600,400);
          stage.setScene(scene);
          stage.show();
    }
    /**Deletes the part if selected when the Delete button is clicked and confirms if the are sure to delete. */
    @FXML
    void onActionPartDelete(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if(part == null)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Delete Box");
        alert.setHeaderText("Part will be deleted");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Inventory.deletePart(part);
        }
    }
    /**Searches for what the user inputted.
       If the user inputted a number, it will use the lookuppart with an integer.
       If the user inputted a string, it will use the lookuppart with strings.
       Else it will return with all the parts if blank. */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        String searchTextPart = partSearchBox.getText().trim();
        int searchId = 0;
        if(!searchTextPart.isEmpty())  {
            if (searchTextPart.matches("[0-9]+")){
                searchId = Integer.parseInt(searchTextPart);
                partsTable.setItems(Inventory.lookupPart(searchId));
            }
            else {
                partsTable.setItems(Inventory.lookupPart(searchTextPart));
            }
        }
        else {
            partsTable.setItems(Inventory.getAllParts());
        }
    }
    /**Searches for what the user inputted.
     If the user inputted a number, it will use the lookupproduct with an integer.
     If the user inputted a string, it will use the lookupproduct with strings.
     Else it will return with all the parts if blank. */
    @FXML
    void onActionProductSearch(ActionEvent event) {

        String searchTextPart = productSearchBox.getText().trim();
        int searchId;
        if(!searchTextPart.isEmpty())  {
            if (searchTextPart.matches("[0-9]+")){
                searchId = Integer.parseInt(searchTextPart);
                productsTable.setItems(Inventory.lookupProduct(searchId));
            }
            else {
                productsTable.setItems(Inventory.lookupProduct(searchTextPart));
            }
        }
        else {
            productsTable.setItems(Inventory.getAllProducts());
        }
    }
    /**When the screen initializes, the table populates. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partCountColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productCountColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
}