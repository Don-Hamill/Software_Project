package Resources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

// Java Docs Location
// ../Software 1 project/JavaDocs

public class Main extends Application {
    /**
     *
     * @param primaryStage sets main page for application
     * @throws Exception
     */
    @Override
    public void start( Stage primaryStage) throws Exception{
        //loads main screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/MainForm.fxml")));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 950, 425));
        primaryStage.show();
    }

    /**
     *
     * @param args main method
     */
    public static void main(String[] args)
    {

        //parts and products to populate table at launch

        Part Wheel = new InHouse(1, "Wheel", 35.99, 100, 5, 500, 1);
        Inventory.addPart(Wheel);

        Part Spoke = new InHouse(2, "Spoke", 6.99, 100, 10, 1000, 5);
        Inventory.addPart(Spoke);

        Part Seat = new Outsourced(3, "Seat", 25.99, 25, 10, 100, "Hamill Time industries");
        Inventory.addPart(Seat);


        Product Unicycle = new Product(1, "Unicycle", 149.99, 10, 5, 20);
        Inventory.addProduct(Unicycle);

        Product Bicycle = new Product(2, "Bicycle", 599.99, 25, 10, 100);
        Inventory.addProduct(Bicycle);

        Product Tricycle= new Product(3, "Tricycle", 99.99, 8, 5, 15);
        Inventory.addProduct(Tricycle);
        launch(args);
    }
}
