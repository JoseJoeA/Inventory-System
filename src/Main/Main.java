package Main;
/**
 *
 * @author Jose Arvizu
 *
 * Javadocs is located in: javadocs/index.html
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**Application for the inventory system.*/
public class Main extends Application {
    /** Opens the mainscreen window of the inventory system. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /** Main launch
     * @param args main command to launch
     */
    public static void main(String[] args) {
        launch(args);
    }
}
