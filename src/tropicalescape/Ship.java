package tropicalescape;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Ship extends GameObject {

	static final String IMG_FILE = "res/ship.png";
	static final int MAX_HP = 50;
	static final int EPSILON = 5;
	static final int SLOW_FACTOR = 10;

	private int hp;
	private Flag nextFlag;
	private Direction dir;
	private boolean arrived = false;

	static String[] N_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] NE_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] NW_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] S_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] SW_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] SE_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] E_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	static String[] W_IMG_FILES = {"res/ship/Boat2-down.png", "res/ship/Boat2-down.png","res/ship/Boat2-down.png"};
	
	static String[] N_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] NE_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] NW_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] S_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] SW_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] SE_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] E_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	static String[] W_HB_FILES = {"res/hitboxes/ship/dummy.txt", "res/hitboxes/ship/dummy.txt","res/hitboxes/ship/dummy.txt"};
	
	Map<Direction,HitboxAnimation> animationMap;
	

	public enum Direction {
		E, NE, N, NW, W, SW, S, SE
	}

	Ship(float x, float y) {
		super(new HitboxAnimation()); // TODO
		hp = MAX_HP;
		dir = Direction.E;
		Vector2f speed = new Vector2f(0f,0f);
		setSpeed(speed);
		Vector2f position = new Vector2f(x,y);
		setPosition(position);
		
		animationMap = new HashMap<Direction,HitboxAnimation>();
		animationMap.put(Direction.N, HitboxAnimationFactory.create(N_IMG_FILES, N_HB_FILES, 10));
		animationMap.put(Direction.NE, HitboxAnimationFactory.create(NE_IMG_FILES, NE_HB_FILES, 10));
		animationMap.put(Direction.NW, HitboxAnimationFactory.create(NW_IMG_FILES, NW_HB_FILES, 10));
		animationMap.put(Direction.S, HitboxAnimationFactory.create(S_IMG_FILES, S_HB_FILES, 10));
		animationMap.put(Direction.SE, HitboxAnimationFactory.create(SE_IMG_FILES, SE_HB_FILES, 10));
		animationMap.put(Direction.SW, HitboxAnimationFactory.create(SW_IMG_FILES, SW_HB_FILES, 10));
		animationMap.put(Direction.E, HitboxAnimationFactory.create(E_IMG_FILES, E_HB_FILES, 10));
		animationMap.put(Direction.W, HitboxAnimationFactory.create(W_IMG_FILES, W_HB_FILES, 10));
		
		this.setHitboxAnimation(animationMap.get(this.dir));
	}

	private void computePath() {
		Vector2f speed = new Vector2f();
		if (nextFlag == null) {
			speed.x = 0;
			speed.y = 0;
		} else {
			float vectorX = nextFlag.getPosition().x - getPosition().x;
			float vectorY = nextFlag.getPosition().y - getPosition().y;

			// normalise le vecteur
			float norme = (float) Math.sqrt(vectorX * vectorX + vectorY
					* vectorY);
			if (norme > EPSILON){
				vectorX = vectorX / (norme*SLOW_FACTOR);
				vectorY = vectorY / (norme*SLOW_FACTOR);

				speed.x = vectorX;
				speed.y = vectorY;
			} else {
				speed.x = 0;
				speed.y = 0;
				arrived = true;
			}


		}

		setSpeed(speed);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void loseHealth(int dmgValue) {
		hp -= dmgValue;
	}

	public boolean isAlive() {
		if (hp > 0) {
			return true;
		}
		return false;
	}
	
	public boolean hasArrived() {
		return arrived;
	}

	public Flag getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(Flag nextFlag) {
		this.nextFlag = nextFlag;
		arrived = false;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	@Override
	public void update(int delta) {
		computePath();

		double angle = getSpeed().getTheta();
		if (getSpeed().x == 0 && getSpeed().y==0){
			//on ne change pas l'orientation si on n'a pas de vitesse
		}else {
			if (337.5 <= angle || angle < 22.5) {
				dir = Direction.E;
			} else if (22.5 <= angle && angle < 67.5) {
				dir = Direction.SE;
			} else if (67.5 <= angle && angle < 112.5) {
				dir = Direction.S;
			} else if (112.5 <= angle && angle < 157.5) {
				dir = Direction.SW;
			} else if (157.5 <= angle && angle < 202.5) {
				dir = Direction.W;
			} else if (202.5 <= angle && angle < 247.5) {
				dir = Direction.NW;
			} else if (247.5 <= angle && angle < 292.5) {
				dir = Direction.N;
			} else if (292.5 <= angle && angle < 337.5) {
				dir = Direction.NE;
			}

			this.setHitboxAnimation(animationMap.get(this.dir));
			getHitboxAnimation().restart();
		}
	}
	
	public void kill(){
		this.hp = 0;
	}
}
