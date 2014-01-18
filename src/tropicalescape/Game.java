package tropicalescape;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class Game extends BasicGame {

	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public Game(String title) {
		super(title);
	}
	
	private void addGameObject(GameObject obj) {
	}

	public void computePath(Ship ship, Flag nextFlag) {
		
		float vectorX = ship.getX() - nextFlag.getX();
		float vectorY = ship.getY() - nextFlag.getY();
		
		//normalise le vecteur
		float norme = (float) Math.sqrt(vectorX*vectorX + vectorY * vectorY);
		
		vectorX = vectorX/norme;
		vectorY = vectorY/norme;
		
		Vector2f vector = new Vector2f();
		vector.x = vectorX;
		vector.y = vectorY;
		
		ship.vector.set(vector);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
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
