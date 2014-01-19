package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoosedState extends BasicGameState {
	
	public static final int ID = 5;
	private boolean goToNextScreen = false;
	
	LoosedState() {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	        throws SlickException {
		g.setColor(Color.white);
	    
		String message = "YOU'RE WORSE THAN A GAY NARWAL";
		int x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		int y = (container.getHeight() - g.getFont().getHeight(message)) / 2;
		g.drawString(message, x, y);
		
		message = "Press Enter to continue";
		x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		y += g.getFont().getHeight(message) + 10;
		g.drawString(message, x, y);
	    
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_ENTER){
			goToNextScreen = true;    
		}
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		goToNextScreen  = false;
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(goToNextScreen) {
	    	game.enterState(UpgradeState.ID);	    	
	    }
	}

	@Override
	public int getID() {
		return ID;
	}

}
