package Game;


import Engine.MouseInput;
import Engine.Window;

public interface GameLogic {
    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanup();
}
