import Base.Game;
import Gui.GameView;
import Controller.*;
import javafx.application.Application;

public class Main
{
    public static void main( String[] args ) {
        GameView gameView = new GameView();
        Application.launch(gameView.getClass());
        //Controller controller = gameView.getController();
    }
}

