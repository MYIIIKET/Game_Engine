import Engine.Engine;
import Game.GameLogic;
import Game.Game;


public class GameTest {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            GameLogic gameLogic = new Game();
            Engine gameEng = new Engine("GAME", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
