package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinState extends BasicGameState {

	public static final int ID = 3;
	private boolean goToNext = false;
	
	private static final float MIN_DIAMETER = 5f;
	private static final int MAX_T = 255;
	private int t;
	private boolean ascending;
	private Color currentColor;
	
	private static WinState instance;

	public static WinState getInstance() {
		if (instance == null) {
			instance = new WinState();
		}
		return instance;
	}

	WinState() {
		t = 0;
		ascending = true;
		currentColor = Color.black;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		// Many circles
		int cx = container.getWidth() / 2,
			cy = container.getHeight() / 2;
		float progress, diameter = 2 * container.getWidth();
		int i = ((t % 6) / 2);
		Color factor = new Color(0, 0, 0);
		while (diameter > MIN_DIAMETER) {
			progress = (diameter - MIN_DIAMETER) / (2 * container.getWidth());
			factor.r = factor.b = (i % 3 == 0 ? progress : 0);
			factor.g = factor.r = (i % 3 == 1 ? progress : 0);
			factor.b = (i % 3 == 2 ? progress : 0);
			
			Color thisColor = currentColor.scaleCopy(1f);
			thisColor.multiply(currentColor);
			thisColor.add(factor);
			g.setColor(thisColor);
			g.fillOval(cx - diameter/2, cy - diameter/2, diameter, diameter);
			diameter *= 0.95;
			i++;
		}
		
		Color textColor = new Color(0, 0, 0);
		textColor.r = (float)Math.random() + 0.3f;
		textColor.g = (float)Math.random() + 0.3f;
		textColor.b = (float)Math.random() + 0.3f;
		g.setColor(textColor);
		
		String message = "YOU'RE BETTER THAN UNICORNS§§";
		int x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		int y = (container.getHeight() - g.getFont().getHeight(message)) / 2;
		g.drawString(message, x, y);
		//g.drawString("Press Enter to continue", x, y + 100);
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER) {
			goToNext = true;
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		goToNext = false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (goToNext) {
			game.enterState(UpgradeState.ID);
		}
		else {
			if (t >= MAX_T)
				ascending = false;
			else if (t < 0)
				ascending = true;

			t += (ascending ? +1 : -1);
			delta = (ascending ? +2 : -2);
			
			switch (t % 3) {
			case 0:
				currentColor.add(new Color(delta, 0, 0));				
				break;
			case 1:
				currentColor.add(new Color(0, delta, 0));		
				break;
			case 2:
				currentColor.add(new Color(0, 0, delta));				
				break;
			default:
				break;
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
