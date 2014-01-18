package tropicalescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tropicalescape.enemies.Enemy;
import tropicalescape.enemies.Island;
import tropicalescape.enemies.OneHitMonster;
import tropicalescape.enemies.SleepingIsland;

public class PlayState extends BasicGameState {

	private static PlayState instance;

	private static final Color BG_COLOR = new Color(45, 85, 117);
	public static final int ID = 2;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private StartFlag startFlag;
	private FinishFlag finishFlag;
	private List<Flag> userFlags = new ArrayList<Flag>();

	private List<Ship> ships = new ArrayList<Ship>();
	private Stack<Ship> shipStack = new Stack<Ship>();
	private int shipPopDelay = 1000;
	private int shipPopTimer = 0;
	private Vector2f shipPopPosition = new Vector2f();

	private boolean shouldQuit = false;
	private List<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>(
			new ArrayList<GameObject>());
	private int minToWin;

	static int nextFlagNum = 1;

	public int width;

	public int height;

	private PlayState(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public static PlayState getInstance(int width, int height) {
		if (instance == null) {
			instance = new PlayState(width, height);
		}
		return instance;
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			this.shouldQuit = true;
		}
	}

	public void loadLevel(String path) throws IOException {
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
				if (tokens[0].equals("MIN-WIN")) {
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
					OneHitMonster ohm = new OneHitMonster(
							OneHitMonster.Type.COCONUT_THROWER);
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
					OneHitMonster ohm = new OneHitMonster(
							OneHitMonster.Type.KRAKEN);
					enemies.add(ohm);
					obj = ohm;
				} else if (tokens[0].equals("GIANT_LOBSTER")) {
					OneHitMonster ohm = new OneHitMonster(
							OneHitMonster.Type.GIANT_LOBSTER);
					enemies.add(ohm);
					obj = ohm;
				}
				if (obj != null) {
					obj.setPosition(new Vector2f(Float
							.parseFloat(tokens[tokens.length - 2]), Float
							.parseFloat(tokens[tokens.length - 1])));
					gameObjects.add(obj);
				}
			}

			if (startFlag == null) {
				startFlag = new StartFlag("Start");
			}
			if (finishFlag == null) {
				finishFlag = new FinishFlag("Finish");
				finishFlag.setPosition(new Vector2f(600, 440));
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public void addEnnemy(Enemy enemy) {
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

	public void init(GameContainer container) throws SlickException {
		String lvlName = "res/levels/test.lvl";
		try {
			loadLevel(lvlName);
		} catch (IOException e) {
			new SlickException("Problème au chargement du niveau " + lvlName
					+ " : " + e.getMessage());
		}
	}

	private void handleInput(GameContainer gc) {
		if (shouldQuit) {
			gc.exit();
		}
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

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// background color
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());

		// Draw all game objects
		for (GameObject obj : gameObjects) {
			obj.baseRender(g);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		handleInput(container);
		if (shipStack.size() > 0) {
			shipPopTimer -= delta;
			if (shipPopTimer <= 0) {
				addShip(shipStack.pop());
			}
		}

		for (GameObject obj : gameObjects) {
			obj.baseUpdate(container, delta);
		}

		List<Ship> deadShips = new ArrayList<Ship>();
		for (Ship ship : ships) {

			resolveShipCollision(ship);
			if (!ship.isAlive()) {
				deadShips.add(ship);
				continue;
			}

			Flag flag = ship.getNextFlag();
			if (flag != null) {
				if (ship.hasArrived()) {
					updateShipFlag(ship, flag);
				}
			}
		}

		// Handle ship death toll
		ships.removeAll(deadShips);
		gameObjects.removeAll(deadShips);
		checkForLose();

		// Handle enemy death toll
		List<Enemy> deadEnemies = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (!enemy.isAlive()) {
				deadEnemies.add(enemy);
			}
		}
		enemies.removeAll(deadEnemies);
		gameObjects.removeAll(deadEnemies);

		// Gestion des inputs, a mettre toujours APRES les MAJ des objets
		Input input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (GameObject.getSelectedObject() == null) {
				Flag flag = new Flag("" + nextFlagNum++);
				int mouseX = input.getMouseX();
				int mouseY = input.getMouseY();
				flag.setPosition(new Vector2f(mouseX, mouseY));
				userFlags.add(flag);
				for (Ship ship : ships) {
					Flag shipNextFlag = ship.getNextFlag();
					if (shipNextFlag == finishFlag) {
						ship.setNextFlag(flag);
					}
				}
			}
		} else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			GameObject selectedObject = GameObject.getSelectedObject();
			if (selectedObject instanceof Flag
					&& !(selectedObject instanceof StartFlag || selectedObject instanceof FinishFlag)) {
				// Rediriger les ships vers leur prochaine destination
				for (Ship ship : ships) {
					Flag shipNextFlag = ship.getNextFlag();
					if (shipNextFlag == selectedObject) {
						updateShipFlag(ship, shipNextFlag);
					}
				}
				userFlags.remove(selectedObject);
			}
			GameObject.setSelectedObject(null);
		}
	}

	private void updateShipFlag(Ship ship, Flag flag) {
		if (ship.getNextFlag() == finishFlag) {
			checkForWin();
		} else {
			int i = userFlags.indexOf(flag);
			// Dernier user flag atteint
			if (i == userFlags.size() - 1) {
				ship.setNextFlag(finishFlag);
			} else {
				ship.setNextFlag(userFlags.get(i + 1));
			}
		}
	}

	private void checkForWin() {
		if (ships.size() >= minToWin) {
			System.out.println("C'est gagné !");
		}
	}

	private void checkForLose() {
		if (ships.size() + shipStack.size() < minToWin) {
			System.out.println("C'est perdu !");
		}
	}

	private void resolveShipCollision(Ship ship) {
		for (Enemy enemy : enemies) {
			if (enemy.intersects(ship)) {
				enemy.onHitShip(ship);
				if (!ship.isAlive()) {
					break;
				}
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public List<Ship> getShips() {
		return ships;
	}
}
