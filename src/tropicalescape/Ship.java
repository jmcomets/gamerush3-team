package tropicalescape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Collidable;
import tropicalescape.physics.CompositeRectangle;



public class Ship extends GameObject {
	
	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;
	
	private int hp;
	private Flag nextFlag;
	
	static Image img;
	
	Ship(float x, float y) {
		super(new CompositeRectangle()); // TODO
		hp = MAX_HP;
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
		
		if (nextFlag != null){	
			speed.x=0;
			speed.y=0;
		}
		else{
			float vectorX = getPosition().x - nextFlag.getX();
			float vectorY = getPosition().y - nextFlag.getY();
			
			//normalise le vecteur
			float norme = (float) Math.sqrt(vectorX*vectorX + vectorY * vectorY);
			
			vectorX = vectorX/norme;
			vectorY = vectorY/norme;
			
			speed.x = vectorX;
			speed.y = vectorY;
		}
		
		setSpeed(speed);
	}

	public int loseHealh(int dmgValue){
		hp-=dmgValue;
		return hp;
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
		
	}
		
}
	
