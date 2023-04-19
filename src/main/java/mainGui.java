import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class mainGui extends Application {
    int size = 10;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("First GUI window!");
        GridPane grid = new GridPane();
        for (int i =0; i<size; i++) {
            for (int j=0; i<size; j++) {
                Rectangle r = new Rectangle(100,100,100,100);
                grid.add(r, i, j);
            }
        }





        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){ launch(args);
    } }




