import Gui.GameView;
import javafx.application.Application;


public class Main
{
    public static void main( String[] args ) {
        GameView gameView = new GameView();
        Application.launch(gameView.getClass());
    }
}

