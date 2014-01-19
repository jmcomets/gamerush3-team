package tropicalescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tropicalescape.enemies.CoconutThrower;
import tropicalescape.enemies.GiantLobster;
import tropicalescape.enemies.Island;
import tropicalescape.enemies.Kraken;
import tropicalescape.enemies.OneHitMonster;
import tropicalescape.enemies.SleepingIsland;
import tropicalescape.enemies.Wave;

class Level {
	public int difficulty;
	public String name;

	public Level(String name2, int difficulty2) {
		name = name2;
		difficulty = difficulty2;
	}
}

public class LevelSelectionState extends BasicGameState {

	static final int ID = 4;
	private static final String PATH = "res/levels/";
	private static final float PADDING = 35;
	private static final int MARGIN = 10;
	private static final int HEIGHT = 40;
	private List<Level> listLevels;
	private int current = 0;
	private boolean loadLevel = false;
	private boolean returnToMenu = false;
	private int scrolling = 0;
	private int containerHeight;

	LevelSelectionState() {
		listLevels = new ArrayList<Level>();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		File folder = new File("res/levels");
		File[] listOfFiles = folder.listFiles();

		BufferedReader reader = null;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					reader = new BufferedReader(new FileReader(listOfFiles[i]));
					String text = null;
					int difficulty = 0;
					while ((text = reader.readLine()) != null) {
						String[] tokens = text.split("\\s+");
						if (tokens.length < 1) {
							System.err.println("Need at least 3 tokens");
						}

						GameObject obj = null;
						if (tokens[0].equals("DIFFICULTY")) {
							difficulty = Integer.parseInt(tokens[1]);
						}
					}
					Level tmpLevel = new Level(listOfFiles[i].getName(),
							difficulty);
					listLevels.add(tmpLevel);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_DOWN) {
			setCurrent(false);
		} else if (key == Input.KEY_UP) {
			setCurrent(true);
		} else if (key == Input.KEY_ENTER || key == Input.KEY_SPACE) {
			loadLevel = true;
		} else if (key == Input.KEY_Q || key == Input.KEY_ESCAPE) {
			returnToMenu = true;
		}
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		super.mouseWheelMoved(newValue);
		if (newValue > 0) {
			setCurrent(true, false);
		} else {
			setCurrent(false, false);

		}

	}

	private void setCurrent(boolean up) {
		setCurrent(up, true);
	}

	private void setCurrent(boolean up, boolean loop) {
		if (up && (loop || current > 0)) {
			if (--current < 0) {
				current = listLevels.size() - 1;
			}
		} else if (!up && (loop || current < listLevels.size() - 1)) {
			if (++current >= listLevels.size()) {
				current = 0;
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		loadLevel = false;
		returnToMenu = false;
		super.enter(container, game);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		int i = 0;

		for (Level entry : listLevels) {
			if (i == current) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.white);
			}
			String clef = entry.name;
			Integer valeur = entry.difficulty;

			g.drawRect(PADDING, i * (HEIGHT + MARGIN) + PADDING + scrolling,
					container.getWidth() - PADDING * 2, HEIGHT);
			g.drawString(valeur.toString(), PADDING, i * (HEIGHT + MARGIN)
					+ HEIGHT / 2 + scrolling + PADDING - 10);
			g.drawString(clef, PADDING * 2, i * (HEIGHT + MARGIN) + HEIGHT / 2
					+ scrolling + PADDING - 10);

			i++;
		}
		g.setColor(Color.black);
		g.setLineWidth(60);
		g.drawRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);
		g.setLineWidth(1);
		g.drawString("Q : return to menu", PADDING, container.getHeight()
				- PADDING);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		containerHeight = container.getHeight();

		if (loadLevel) {
			((GameManager) game).launchLevel(PATH
					+ listLevels.get(current).name);
		} else if (returnToMenu) {
			game.enterState(MenuState.ID);
		}

		if (current * (HEIGHT + MARGIN) + PADDING + scrolling <= 60 / 2) {
			scrolling += 10;
		} else if (current * (HEIGHT + MARGIN) + PADDING + scrolling + HEIGHT >= containerHeight - 60 / 2) {
			scrolling -= 10;
		}

	}

	@Override
	public int getID() {
		return ID;
	}

}
