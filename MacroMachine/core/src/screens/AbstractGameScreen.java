package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by Tim on 4/5/2016.
 */
public class AbstractGameScreen extends ScreenAdapter {

    private Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }
}
