import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	ArrayList<Ship> ships;
	ArrayList<Flag> flags;

	public Game(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
	}

	@Override
	public void update(GameContainer gc, int frame) throws SlickException {

		for (Ship ship : ships) {

			Flag flag = ship.getNextFlag();

			// TODO : peut être le remplacer par un rectangle de colision si
			// ship trop rapide
			if (flag.getX() == ship.getX() && flag.getY() == ship.getY()) {
				int i = flags.indexOf(flag);
				if (i == flags.size()) // Dernier flag atteint
				{
					ship.setNextFlag(null);
				} else {
					ship.setNextFlag(flags.get(i + 1));
				}
			}
		}

	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Tropical Escape !"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
