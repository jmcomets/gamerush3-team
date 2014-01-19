package tropicalescape;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class MenuState extends BasicGameState {

	static final int ID = 1;
	private boolean launchGame = false;
	private boolean quitGame = false;
	private boolean levelSelect = false;
	private TrueTypeFont font;
	private TrueTypeFont font2;
	private Image img;

	MenuState() {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			img = new Image("res/animations/background/isle.jpg");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("res/fonts/PiratesBay.ttf");

			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(30f); // set font size
			font = new TrueTypeFont(awtFont, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("res/fonts/PiratesBay.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(60f); // set font size
			font2 = new TrueTypeFont(awtFont2, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		img.draw(0,0);
		g.setColor(Color.white);
		font2.drawString(10, 50, "Tropical Escape");
		font.drawString(10, 150, "1 Level Selection");
		font.drawString(10, 200, "2 Quit");
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_1 || key == Input.KEY_NUMPAD0
				|| key == Input.KEY_ENTER || key == Input.KEY_NUMPADENTER
				|| key == Input.KEY_SPACE) {
			levelSelect = true;
		} else if (key == Input.KEY_2 || key == Input.KEY_ESCAPE) {
			quitGame = true;
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		launchGame = false;
		levelSelect = false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (launchGame) {
			game.enterState(PlayState.ID);
		}
		if (quitGame) {
			container.exit();
		}
		if (levelSelect) {
			game.enterState(LevelSelectionState.ID);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
