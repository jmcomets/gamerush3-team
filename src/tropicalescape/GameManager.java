package tropicalescape;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameManager extends StateBasedGame{

    
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			GameManager game = new GameManager("Tropical Escape !");
			appgc = new AppGameContainer(game);
			appgc.setDisplayMode(640, 480, false);
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
		addState(new PlayState());
		
	}

}
