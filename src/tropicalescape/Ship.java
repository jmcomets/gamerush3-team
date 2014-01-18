package tropicalescape;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Hitbox;

public class Ship extends GameObject {

	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;

	private int hp;
	private Flag nextFlag;
	private Direction dir;

	static Image img;
	
	public enum Direction {
	    E, NE, N, NW,
	    W, SW, S, SE 
	}

	Ship(float x, float y) {
		super(new Hitbox()); // TODO
		hp = MAX_HP;
		dir = Direction.E;
	}

	static {
		try {
			img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void computePath() {
		Vector2f speed = new Vector2f();

		if (nextFlag != null) {
			speed.x = 0;
			speed.y = 0;
		} else {
			float vectorX = getPosition().x - nextFlag.getPosition().x;
			float vectorY = getPosition().y - nextFlag.getPosition().y;

			// normalise le vecteur
			float norme = (float) Math.sqrt(vectorX * vectorX + vectorY
					* vectorY);

			vectorX = vectorX / norme;
			vectorY = vectorY / norme;

			speed.x = vectorX;
			speed.y = vectorY;
		}

		setSpeed(speed);
	}

	public void loseHealh(int dmgValue) {
		hp -= dmgValue;
	}

	public boolean isAlive() {
		if (hp > 0) {
			return true;
		}
		return false;
	}

	public float getX() {
		return getPosition().x;
	}

	public float getY() {
		return getPosition().y;
	}

	public Flag getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(Flag nextFlag) {
		this.nextFlag = nextFlag;
		computePath();
	}

	@Override
	public void render(Graphics g) {
		img.draw(getPosition().x, getPosition().y);
	}

	@Override
	public void update(int delta) {
		double angle = getSpeed().getTheta();
		if (337.5>=angle || angle<22.5){
			dir=Direction.E;
		} else if (22.5<=angle && angle<67.5){
			dir=Direction.NE;
		}else if (67.5<=angle && angle<112.5){
			dir=Direction.N;
		}else if (112.5<=angle && angle<157.5){
			dir=Direction.NW;
		}else if (157.5<=angle && angle<202.5){
			dir=Direction.W;
		}else if (202.5<=angle && angle<247.5){
			dir=Direction.SW;
		}else if (247.5<=angle && angle<292.5){
			dir=Direction.S;
		}else if (292.5<=angle && angle<337.5){
			dir=Direction.SE;
		}
	}
	
	public void kill(){
		this.hp = 0;
	}
}
