package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 *
 * @author Jose Arvizu
 */
/** Inventory class that stores methods to add, update, delete, search parts/products.*/
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** Adds the parts.
        @param newPart part added */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /** Adds the products.
        @param newProduct product added */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /** Looks up part using partID.
     * If the number given matches the number in the parts list, it returns the number. */
    public static ObservableList<Part> lookupPart(int partId) {
        ObservableList<Part> obtainParts = FXCollections.observableArrayList();
        if(!allParts.isEmpty()){
            for (int x = 0; x < allParts.size(); x++){
                int searchId = allParts.get(x).getId();
                if (searchId == partId){
                    obtainParts.add(allParts.get(x));
                }
            }
            return obtainParts;
        }
           return null;
    }

    /**Looks at product using the id.
     * If the number given matches the number in the products list, it returns the number. */
    public static ObservableList<Product> lookupProduct(int productId) {
        ObservableList<Product> obtainProducts = FXCollections.observableArrayList();
        if(!allProducts.isEmpty()){
            for (int x = 0; x < allProducts.size(); x++){
                int searchId = allProducts.get(x).getId();
                if (searchId == productId){
                    obtainProducts.add(allProducts.get(x));
                }
            }
            return obtainProducts;
        }
        return null;
    }


    /**Looks up part using the name.
     * If the name given matches the name of the part in the list, it returns the name. */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> obtainParts = FXCollections.observableArrayList();
        partName = partName.toLowerCase();
        if(!allParts.isEmpty()) {
            for (int x = 0; x < allParts.size(); x++) {
                String lowCasePart = allParts.get(x).getName().toLowerCase();
                if (lowCasePart.contains(partName)) {
                    obtainParts.add(allParts.get(x));
                }
            }
            return obtainParts;
        }
        return null;
    }
    /**Looks up product using the name.
     * If the name given matches the name of the product in the list, it returns the name. */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> obtainProducts = FXCollections.observableArrayList();
        productName = productName.toLowerCase();
        if(!allProducts.isEmpty()) {
            for (int x = 0; x < allProducts.size(); x++) {
                String lowCasePart = allProducts.get(x).getName().toLowerCase();
                if (lowCasePart.contains(productName)) {
                    obtainProducts.add(allProducts.get(x));
                }
            }
            return obtainProducts;
        }
        return null;
    }
    /**Updates the part already filled with information given before. */
    public static void updatePart(int id, Part selectedPart){
        for(int x = 0; allParts.size() > x; x++){
           if (allParts.get(x).getId() == selectedPart.getId()){
               allParts.set(x, selectedPart);
           }
        }
    }
    /** Updates the product already filled with information given before.

        RUNTIME ERROR: Had trouble finding a way to update the information correctly, realized I could use the index
        of the stored information, and update each data one by one. */
    public static void updateProduct(int id, Product newProduct) {
        for(int x = 0; allProducts.size() > x; x++){
            if (allProducts.get(x).getId() == newProduct.getId()){
                allProducts.set(x, newProduct);
            }
        }
    }
    /** Removes the part from table. */
    public static boolean deletePart(Part selectedPart){
        for(Part p: getAllParts()){
            getAllParts().remove(selectedPart);
                return true;
            }
        return false;
    }
    /** Removes the product from table. */
    public static boolean deleteProduct(Product selectedProduct){
        for (Product p: getAllProducts()){
            getAllProducts().remove(selectedProduct);
            return true;
        }
        return false;
    }
    /** Returns all parts of the observablelist.
        @return parts from the list */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** Returns all products of the observablelist.
        @return products from the list */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
