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
	private StartFlag startFlag;
	private FinishFlag finishFlag;
	private List<Flag> userFlags = new ArrayList<Flag>();

	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Ship> ships = new ArrayList<Ship>();
	private Stack<Ship> shipStack = new Stack<Ship>();
	private int shipPopDelay = 1000;
	private int shipPopTimer = 0;
	private Vector2f shipPopPosition = new Vector2f();

	private boolean shouldQuit = false;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private int minToWin;
	private int nArrivedShips;

	private boolean won;
	private boolean loosed;

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
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		this.won = false;
		this.loosed = false;

		this.nArrivedShips = 0;
		this.shouldQuit = false;
		emptyEntities();

		String lvlName = "res/levels/test.lvl";
		try {
			loadLevel(lvlName);
		} catch (IOException e) {
			new SlickException("Problème au chargement du niveau " + lvlName
					+ " : " + e.getMessage());
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

	public void init(GameContainer container) throws SlickException {
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
		Iterator<GameObject> it = gameObjects.iterator();
		while (it.hasNext()) {
			GameObject obj = it.next();
			obj.baseRender(g);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		if (won) {
			game.enterState(WinState.ID);
		}
		if (loosed) {
			game.enterState(LoosedState.ID);
		}

		handleInput(container);
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

			resolveShipCollision(ship);
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

		// Gestion des inputs, a mettre toujours APRES les MAJ des objets
		Input input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (GameObject.getSelectedObject() == null) {
				Flag flag = new Flag("" + (userFlags.size() + 1));
				int mouseX = input.getMouseX();
				int mouseY = input.getMouseY();
				flag.setPosition(new Vector2f(mouseX, mouseY));
				userFlags.add(flag);
				gameObjects.add(flag);
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
						recomputeShipPath(ship, shipNextFlag);
					}
				}
				userFlags.remove(selectedObject);
				gameObjects.remove(selectedObject);

				// Refaire la numérotation
				for (int i = 0; i < userFlags.size(); i++) {
					userFlags.get(i).setDescription("" + (i + 1));
				}
			}
			GameObject.setSelectedObject(null);
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

	private void recomputeShipPath(Ship ship, Flag previousFlag) {
		int i = userFlags.indexOf(previousFlag);

		// Dernier user flag atteint
		if (i == userFlags.size() - 1) {
			ship.setNextFlag(finishFlag);
		} else {
			ship.setNextFlag(userFlags.get(i + 1));
		}
	}

	private void checkForWin() {
		if (nArrivedShips >= minToWin) {
			this.won = true;
		}
	}

	private void checkForLose() {
		if (!won && ships.size() + shipStack.size() < minToWin) {
			loosed = true;
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
