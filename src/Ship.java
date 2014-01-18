import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.Flag;
import tropicalescape.physics.Collidable;
import tropicalescape.physics.CompositeRectangle;



public class Ship implements Collidable{
	
	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;
	
	private int height;
	private int width;
	private int hp;
	private float x;
	private float y;
	private Flag nextFlag;
	private CompositeRectangle rectangles;
	
	private Vector2f vector;
	
	static Image img;
	
	Ship(float x, float y) {
		this.x = x;
		this.y = y;
		hp = MAX_HP;
		vector = new Vector2f();
		vector.x = 0;
		vector.y = 0;
	}

	static {
		try {
			img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void computePath() {
		
		if (nextFlag != null){
			vector.x = 0;
			vector.y = 0;
		}
		else{
			float vectorX = x - nextFlag.getX();
			float vectorY = y - nextFlag.getY();
			
			//normalise le vecteur
			float norme = (float) Math.sqrt(vectorX*vectorX + vectorY * vectorY);
			
			vectorX = vectorX/norme;
			vectorY = vectorY/norme;
			
			vector.x = vectorX;
			vector.y = vectorY;
		}
	}

	public int loseHealh(int dmgValue){
		hp-=dmgValue;
		return hp;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	
	
	public Flag getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(Flag nextFlag) {
		this.nextFlag = nextFlag;
		computePath();
	}

	@Override
	public boolean intersects(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(Collidable collidable) {
		return rectangles.intersects(collidable);
	}
		
}
	
