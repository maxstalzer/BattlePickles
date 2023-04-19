import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class mainGui extends Application {
    int size = 10;
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        double s = 100; // side of rectangle
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                grid.add(r, j, i);
            }
        }


        primaryStage.setTitle("Battleboard");
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){ launch(args);
    } }




