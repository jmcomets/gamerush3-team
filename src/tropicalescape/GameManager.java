package tropicalescape;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameManager extends StateBasedGame{
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
    
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			GameManager game = new GameManager("Tropical Escape !");
			appgc = new AppGameContainer(game);
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			// appgc.setFullscreen(true);
			appgc.setShowFPS(false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(PlayState.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    
    public GameManager(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MenuGameState());
		addState(PlayState.getInstance(WIDTH, HEIGHT));
		addState(new WinState());
		addState(new LoosedState());
	}

}
