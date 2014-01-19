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
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
	private Image img;

	LevelSelectionState() {
		listLevels = new ArrayList<Level>();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			img = new Image("res/animations/background/water.jpg");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int difficulty = 0;

		File folder = new File("res/levels");
		File[] listOfFiles = folder.listFiles();

		BufferedReader reader = null;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

				try {
					reader = new BufferedReader(new FileReader(listOfFiles[i]));
					String text = null;
					text = reader.readLine();
					String[] array = text.split("\\s+");
					if (array.length < 2) {
						throw new IOException("Difficulty isn't set correctly");
					}
					try {
						difficulty = Integer.parseInt(array[1]);
						Level tmpLevel = new Level(listOfFiles[i].getName(),
								difficulty);
						listLevels.add(tmpLevel);

					} catch (NumberFormatException e) {
						throw new IOException(e);
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
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
		img.draw(0,0);

		int i = 0;
		
		g.drawString("Select your level : ", PADDING , container.getHeight()/2);

		for (Level entry : listLevels) {
			if (i == current) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}
			String clef = entry.name;
			Integer valeur = entry.difficulty;

			//g.drawRect(PADDING, i * (HEIGHT + MARGIN) + PADDING + scrolling,
			//		container.getWidth() - PADDING * 2, HEIGHT);
			g.drawString(valeur.toString(), PADDING*10, i * (HEIGHT + MARGIN)
					+ HEIGHT / 2 + scrolling + PADDING - 10);
			g.drawString(clef, PADDING * 12, i * (HEIGHT + MARGIN) + HEIGHT / 2
					+ scrolling + PADDING - 10);

			i++;
		}
		g.setColor(Color.black);
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
