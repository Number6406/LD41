package engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Number6406
 */
public class LD41 extends StateBasedGame {

    /* Game states */
    public static final int GAME_STATE_MENU = 1;
    public static final int GAME_STATE_GAME = 2;
    public static final int GAME_STATE_ENDSCREEN = 3;
    public static final int GAME_STATE_CREDIT = 4;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;
    public static final String VERSION = "0.1.2";
    
    private static AppGameContainer app;

    public LD41(String gamename) {
        super(gamename);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            app = new AppGameContainer(new LD41("Boring RPG - " + VERSION));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(false);
            app.setAlwaysRender(true);
            app.setIcon("assets/logo.png");
            app.start();
            
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new GameState());
        this.enterState(GAME_STATE_GAME);
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);

        try {
            if (key == Input.KEY_F) {
                if (!app.isFullscreen()) {
                    app.setDisplayMode(
                            Display.getDesktopDisplayMode().getWidth(),
                            Display.getDesktopDisplayMode().getHeight(),
                            true);
                } else {
                    app.setDisplayMode(
                            LD41.WIDTH,
                            LD41.HEIGHT,
                            false);
                }
            }
            if (key == Input.KEY_ESCAPE) {
                app.exit();
            }
        } catch (SlickException ex) {
            Logger.getLogger(LD41.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
