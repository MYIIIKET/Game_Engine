import Engine.Engine;
import Engine.GameLogic;
import Game.SimpleGame;

import java.util.Properties;
import java.util.Set;

public class GameTest {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            GameLogic gameLogic = new SimpleGame();
            Engine gameEng = new Engine("GAME", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
