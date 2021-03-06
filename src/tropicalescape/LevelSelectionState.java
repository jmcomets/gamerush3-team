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

public class LevelSelectionState extends BasicGameState {

	static final int ID = 4;
	private static final String PATH = "res/levels/";
	private static final float PADDING = 35;
	private static final float MARGIN_TOP = 70;
	private static final int LINE_HEIGHT = 10;
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
			img = new Image("res/animations/background/carte-large.png");
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		img.draw(0, scrolling);
		
		String message = "============= Select your level ============";
		float cx = (container.getWidth() / 2),
				ty = scrolling + MARGIN_TOP + PADDING - 10;
		g.drawString(message, cx - g.getFont().getWidth(message)/2, ty);

		cx -= 60;
		int i = 0;
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
			g.drawString(valeur.toString(), cx - PADDING, ty + (i+1) * (HEIGHT + LINE_HEIGHT) + HEIGHT / 2);
			g.drawString(clef, cx, ty + (i+1) * (HEIGHT + LINE_HEIGHT) + HEIGHT / 2);

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
		
		if ((current+1) * (HEIGHT + LINE_HEIGHT) + PADDING - MARGIN_TOP + scrolling <= 60 / 2) {
			scrolling += 10;
		} else if ((current+1) * (HEIGHT + LINE_HEIGHT) + PADDING + MARGIN_TOP + scrolling + HEIGHT >= containerHeight - 60 / 2) {
			scrolling -= 10;
		}

	}

	public List<Level> getListLevels() {
		return listLevels;
	}

	@Override
	public int getID() {
		return ID;
	}

}
