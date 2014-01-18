package tropicalescape;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;



public class Ship implements GameObject, Collidable{
	
	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;
	
	private int height;
	private int width;
	private int hp;
	private float x;
	private float y;
	
	public Vector2f vector;
	
	static Image img;
	
	Ship(float x, float y) {
		this.x = x;
		this.y = y;
		hp = MAX_HP;
	}

	static {
		try {
			img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
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

	@Override
	public void render(Graphics g) {
		img.draw(x, y);
	}

	@Override
	public void update(int delta) {
			
	}

	@Override
	public boolean intersects(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}
		
	}
	
