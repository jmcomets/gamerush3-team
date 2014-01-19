package tropicalescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tropicalescape.enemies.CoconutThrower;
import tropicalescape.enemies.Enemy;
import tropicalescape.enemies.GiantLobster;
import tropicalescape.enemies.Island;
import tropicalescape.enemies.Kraken;
import tropicalescape.enemies.OneHitMonster;
import tropicalescape.enemies.SleepingIsland;
import tropicalescape.enemies.Wave;

public class PlayState extends BasicGameState {

	private static PlayState instance;

	private static final Color BG_COLOR = new Color(45, 85, 117);
	public static final int ID = 2;

	private static final int MAX_PLACE_DELAY = 5000;

	public static PlayState getInstance(int width, int height) {
		if (instance == null) {
			instance = new PlayState(width, height);
		}
		return instance;
	}

	private StartFlag startFlag;
	private FinishFlag finishFlag;

	private List<Flag> userFlags = new ArrayList<Flag>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Ship> ships = new ArrayList<Ship>();
	private Stack<Ship> shipStack = new Stack<Ship>();
	private int shipPopDelay = 1000;
	private int shipPopTimer = 0;

	private Vector2f shipPopPosition = new Vector2f();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private int minToWin;

	private int nArrivedShips;
	private boolean won;

	private boolean lost;

	static int nextFlagNum = 1;
	public int width;

	public int height;
	private int placeFlagsDelay;

	private String lvlName;

	private GameObject draggingObject = null;
	private boolean godModeActivated;

	private boolean niceModeActivated;

	private static final int MAX_DELAY_INDICATOR_DIAMETER = 80;

	private static final int MIN_DELAY_INDICATOR_R = 20;

	private PlayState(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void addEnemy(Enemy enemy) {
		gameObjects.add(enemy);
		enemies.add(enemy);
	}

	private void addShip(Ship ship) {
		ship.getPosition().x = shipPopPosition.x;
		ship.getPosition().y = shipPopPosition.y;
		ship.setNextFlag(startFlag);
		ships.add(ship);
		gameObjects.add(ship);
		shipPopTimer = shipPopDelay;
	}

	private void checkForLose() {
		if (!won && nArrivedShips +  ships.size() + shipStack.size() < minToWin) {
			lost = true;
			System.out.println("C'est perdu !");
		}
	}

	private void checkForWin() {
		if (nArrivedShips >= minToWin) {
			this.won = true;
		}
	}

	private void emptyEntities() {
		this.shipStack = new Stack<Ship>();
		this.userFlags = new ArrayList<Flag>();
		this.ships = new ArrayList<Ship>();
		this.enemies = new ArrayList<Enemy>();
		this.shipStack = new Stack<Ship>();
		this.gameObjects = new ArrayList<GameObject>();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		placeFlagsDelay = MAX_PLACE_DELAY;

		// Win-lose
		won = false;
		lost = false;

		// Modes
		godModeActivated = false;
		niceModeActivated = false;
		
		nArrivedShips = 0;
		emptyEntities();
		
		placeDefaultFlags(container.getWidth(), container.getHeight());
		try {
			loadLevel(lvlName);
		} catch (IOException e) {
			new SlickException("Problème au chargement du niveau " + lvlName
					+ " : " + e.getMessage());
		}
	}
	
	public void placeDefaultFlags(int width, int height){
		if (startFlag == null) {
			startFlag = new StartFlag("Start");
			startFlag.setPosition(new Vector2f(-40, -40));
			gameObjects.add(startFlag);
		}
		if (finishFlag == null) {
			finishFlag = new FinishFlag("Finish");
			finishFlag.setPosition(new Vector2f(width, height));
			gameObjects.add(finishFlag);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public List<Ship> getShips() {
		return ships;
	}

	private void handleContinuousInput(Input input) {
	}

	private void handleWinLose(StateBasedGame game) {
		if (won) {
			game.enterState(WinState.ID);
		}
		if (lost) {
			game.enterState(LoosedState.ID);
		}
	}

	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		String lvlName = "res/levels/test.lvl";
		try {
			loadLevel(lvlName);
		} catch (IOException e) {
			new SlickException("Problème au chargement du niveau " + lvlName
					+ " : " + e.getMessage());
		}

	}

	public void loadLevel(String path) throws IOException {
		System.out.println(path);
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] tokens = text.split("\\s+");
				if (tokens.length < 1) {
					System.err.println("Need at least 3 tokens");
				}

				GameObject obj = null;
				if (tokens[0].equals("PLACE-DELAY")) {
					placeFlagsDelay = MAX_PLACE_DELAY;
					if (tokens.length > 1) {
						placeFlagsDelay = Integer.parseInt(tokens[1]);
					}
				} else if (tokens[0].equals("GODE-MODE")) {
					godModeActivated = true;
				} else if (tokens[0].equals("NICE-MODE")) {
					niceModeActivated = true;
				} else if (tokens[0].equals("MIN-WIN")) {
					minToWin = Integer.parseInt(tokens[1]);
				} else if (tokens[0].equals("SHIPS")) {
					shipPopPosition.x = Integer.parseInt(tokens[1]);
					shipPopPosition.y = Integer.parseInt(tokens[2]);
					for (int i = 0; i < Integer.parseInt(tokens[3]); i++) {
						Ship ship = new Ship();
						shipStack.add(ship);
					}
					if (tokens.length >= 5) {
						shipPopDelay = Integer.parseInt(tokens[4]);
					}
				} else if (tokens[0].equals("ISLAND")) {
					Island island = new Island();
					enemies.add(island);
					obj = island;
				} else if (tokens[0].equals("COCONUT-THROWER")) {
					OneHitMonster ohm = new CoconutThrower();
					enemies.add(ohm);
					obj = ohm;
				} else if (tokens[0].equals("SLEEPING-ISLAND")) {
					SleepingIsland sleepingIsland = new SleepingIsland();
					enemies.add(sleepingIsland);
					obj = sleepingIsland;
				} else if (tokens[0].equals("FLAG")) {
					Flag flag = new Flag(tokens[1]);
					userFlags.add(flag);
					obj = flag;
				} else if (tokens[0].equals("START")) {
					startFlag = new StartFlag(tokens[1]);
					obj = startFlag;
				} else if (tokens[0].equals("FINISH")) {
					finishFlag = new FinishFlag(tokens[1]);
					obj = finishFlag;
				} else if (tokens[0].equals("KRAKEN")) {
					OneHitMonster ohm = new Kraken();
					enemies.add(ohm);
					obj = ohm;
				} else if (tokens[0].equals("GIANT_LOBSTER")) {
					OneHitMonster ohm = new GiantLobster();
					enemies.add(ohm);
					obj = ohm;
				}else if (tokens[0].equals("WAVE")) {
					Wave w = new Wave();
					enemies.add(w);
					obj = w;
				}
				if (obj != null) {
					obj.setPosition(new Vector2f(Float
							.parseFloat(tokens[tokens.length - 2]), Float
							.parseFloat(tokens[tokens.length - 1])));
					gameObjects.add(obj);
				}
			}

		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);

		// On récupère l'objet cliqué
		GameObject clickedObject = null;
		for (GameObject obj : gameObjects) {
			if (obj.getTranslatedHitbox().contains(x, y)) {
				clickedObject = obj;
			}
		}

		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (clickedObject == null && userCanEdit()) { // création d'un
															// drapeau
				Flag flag = new Flag("" + (userFlags.size() + 1));
				flag.setPosition(new Vector2f(x, y));
				userFlags.add(flag);
				gameObjects.add(flag);
				for (Ship ship : ships) {
					Flag shipNextFlag = ship.getNextFlag();
					if (shipNextFlag == finishFlag) {
						ship.setNextFlag(flag);
					}
				}
			}
		} else if (button == Input.MOUSE_RIGHT_BUTTON) {
			if (clickedObject == null) {
				// TODO
			} else if (clickedObject instanceof Flag // Suppression d'un drapeau
					&& !(clickedObject instanceof StartFlag || clickedObject instanceof FinishFlag)
					&& userCanEdit()) {
				// Rediriger les ships vers leur prochaine destination
				for (Ship ship : ships) {
					Flag shipNextFlag = ship.getNextFlag();
					if (shipNextFlag == clickedObject) {
						recomputeShipPath(ship, shipNextFlag);
					}
				}
				userFlags.remove(clickedObject);
				gameObjects.remove(clickedObject);

				// Refaire la numérotation
				for (int i = 0; i < userFlags.size(); i++) {
					userFlags.get(i).setDescription("" + (i + 1));
				}
			}
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		if (draggingObject != null && userCanEdit()) {
			draggingObject.setPosition(new Vector2f(newx, newy));
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if (button == Input.MOUSE_LEFT_BUTTON) {
			for (GameObject obj : gameObjects) {
				if (obj instanceof Flag || godModeActivated) {
					if (obj.getTranslatedHitbox().contains(x, y)) {
						draggingObject = obj;
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		if (button == Input.MOUSE_LEFT_BUTTON) {
			draggingObject = null;
		}
	}
	private void recomputeShipPath(Ship ship, Flag previousFlag) {
		int i = userFlags.indexOf(previousFlag);

		// Dernier user flag atteint
		if (i == userFlags.size() - 1) {
			ship.setNextFlag(finishFlag);
		} else {
			ship.setNextFlag(userFlags.get(i + 1));
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// background color
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());

		// Draw all game objects
		Iterator<GameObject> it = gameObjects.iterator();
		while (it.hasNext()) {
			GameObject obj = it.next();
			obj.baseRender(g);
		}

		// Draw time delay if delay still active
		if (placeFlagsDelay > 0) {
			String secondsStr = "" + (1 + placeFlagsDelay / 1000);
			g.setColor(new Color(0.2f, 0.2f, 0.2f));
			float diameter = MIN_DELAY_INDICATOR_R + MAX_DELAY_INDICATOR_DIAMETER * placeFlagsDelay / MAX_PLACE_DELAY;
			float x = container.getWidth() - (MAX_DELAY_INDICATOR_DIAMETER + diameter / 2);
			float y = container.getHeight() - (MAX_DELAY_INDICATOR_DIAMETER + diameter / 2);
			g.fillOval(x, y, diameter, diameter);
			g.setColor(Color.white);
			Font font = g.getFont();
			g.drawString(secondsStr, x + (diameter - font.getWidth(secondsStr)) / 2, y
					+ (diameter - font.getHeight(secondsStr)) / 2);
		}
	}

	private void resolveShipCollision(Ship ship, int delta) {
		for (Enemy enemy : enemies) {
			if (enemy.intersects(ship)) {
				enemy.onHitShip(ship, delta);
				if (!ship.isAlive()) {
					break;
				}
			}
		}
	}

	public void setLvlName(String lvlName) {
		this.lvlName = lvlName;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Continuous
		handleContinuousInput(container.getInput());

		// Wait for the user to place his flags
		if (placeFlagsDelay > 0) {
			placeFlagsDelay -= delta;
			System.out.println("il reste " + placeFlagsDelay / 1000
					+ " secondes");
			return;
		}
		// System.out.println(placeFlagsDelay);

		// Win or lost ?
		handleWinLose(game);

		if (shipStack.size() > 0) {
			shipPopTimer -= delta;
			if (shipPopTimer <= 0) {
				addShip(shipStack.pop());
			}
		}

		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject obj = gameObjects.get(i);
			obj.baseUpdate(container, delta);
		}

		List<Ship> shipsToRemove = new ArrayList<Ship>();
		for (Ship ship : ships) {
			resolveShipCollision(ship, delta);
			if (!ship.isAlive()) {
				shipsToRemove.add(ship);
				// ship died
				continue;
			}

			Flag flag = ship.getNextFlag();
			if (flag != null) {
				if (ship.hasArrivedToNextFlag()) {
					if (flag == finishFlag) {
						nArrivedShips++;
						shipsToRemove.add(ship);
						checkForWin();
					} else {
						recomputeShipPath(ship, flag);
					}
				}
			}
		}

		// Handle ships to remove
		ships.removeAll(shipsToRemove);
		gameObjects.removeAll(shipsToRemove);
		checkForLose();

		// Handle enemies to remove
		List<Enemy> enemiesToRemove = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (!enemy.isAlive()) {
				enemiesToRemove.add(enemy);
			}
		}
		enemies.removeAll(enemiesToRemove);
		gameObjects.removeAll(enemiesToRemove);
	}

	private boolean userCanEdit() {
		return placeFlagsDelay > 0 || niceModeActivated;
	}
}
