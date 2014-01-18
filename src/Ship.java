import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Ship implements GameObject{
	
	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;
	
	private int height;
	private int width;
	private int hp;
	private float x;
	private float y;
	
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
	
	public void loseHealh(int dmgValue){
		hp-=dmgValue;
	}

	@Override
	public void render(Graphics g) {
		img.draw(x, y);
	}

	@Override
	public void update(int delta) {
		//
		
	}
	

}
