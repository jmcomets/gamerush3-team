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

	private static WinState instance;

	public static WinState getInstance() {
		if (instance == null) {
			instance = new WinState();
		}
		return instance;
	}

	WinState() {

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		String message = "YOU'RE BETTER THAN UNICORNS§§";

		int x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		int y = 50;
		g.drawString(message, x, y);

		g.drawString("Press Enter to continue", x, y + 100);
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
	}

	@Override
	public int getID() {
		return ID;
	}

}
