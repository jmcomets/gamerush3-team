package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuGameState extends BasicGameState {
	
	private static final int ID = 1;
	private boolean launchGame = false;
	private boolean quitGame = false;
	private boolean levelSelect = false;

	MenuGameState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	        throws SlickException {
	    g.setColor(Color.white);
	    g.drawString("Menu", 50, 10);

	    g.drawString("1. Level Selection", 50, 100);
	    g.drawString("2. Quit", 50, 140);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_1){
			levelSelect  = true;   
		} else if(key == Input.KEY_2){
			quitGame = true;	    
		}


	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		launchGame  = false;
		levelSelect = false;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
	    if(launchGame) {
	    	game.enterState(PlayState.ID);	    	
	    }
	    if(quitGame) {
	    	container.exit();	    	
	    }
	    if(levelSelect) {
	    	game.enterState(LevelSelectionState.ID);	    	
	    }

	}

	@Override
	public int getID() {
		return ID;
	}

}
