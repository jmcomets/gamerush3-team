package tropicalescape;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Ship extends GameObject {

	static final int MAX_HP = 50;
	static final int EPSILON = 5;
	static final int SLOW_FACTOR = 10;
	static final int FRAME_DURATION = 125;

	private int hp;
	private Flag nextFlag;
	private Direction dir;
	private boolean arrived = false;

	static String[] N_IMG_FILES = { "res/ship/resized/Boat1-up.png",
			"res/ship/resized/Boat2-up.png", "res/ship/resized/Boat3-up.png" };
	static String[] NE_IMG_FILES = { "res/ship/resized/Boat1-rightup.png",
			"res/ship/resized/Boat2-rightup.png",
			"res/ship/resized/Boat3-rightup.png" };
	static String[] NW_IMG_FILES = { "res/ship/resized/Boat1-leftup.png",
			"res/ship/resized/Boat2-leftup.png",
			"res/ship/resized/Boat3-leftup.png" };
	static String[] S_IMG_FILES = { "res/ship/resized/Boat1-down.png",
			"res/ship/resized/Boat2-down.png",
			"res/ship/resized/Boat3-down.png" };
	static String[] SW_IMG_FILES = { "res/ship/resized/Boat1-leftdown.png",
			"res/ship/resized/Boat2-leftdown.png",
			"res/ship/resized/Boat3-leftdown.png" };
	static String[] SE_IMG_FILES = { "res/ship/resized/Boat1-rightdown.png",
			"res/ship/resized/Boat2-rightdown.png",
			"res/ship/resized/Boat3-rightdown.png" };
	static String[] E_IMG_FILES = { "res/ship/resized/Boat1-right.png",
			"res/ship/resized/Boat2-right.png",
			"res/ship/resized/Boat3-right.png" };
	static String[] W_IMG_FILES = { "res/ship/resized/Boat1-left.png",
			"res/ship/resized/Boat2-left.png",
			"res/ship/resized/Boat3-left.png" };

	static String[] N_HB_FILES = { "res/hitboxes/ship/vertical.txt",
			"res/hitboxes/ship/vertical.txt", "res/hitboxes/ship/vertical.txt" };
	static String[] NE_HB_FILES = { "res/hitboxes/ship/diagSW-NE.txt",
			"res/hitboxes/ship/diagSW-NE.txt",
			"res/hitboxes/ship/diagSW-NE.txt" };
	static String[] NW_HB_FILES = { "res/hitboxes/ship/diagSE-NW.txt",
			"res/hitboxes/ship/diagSE-NW.txt",
			"res/hitboxes/ship/diagSE-NW.txt" };
	static String[] S_HB_FILES = { "res/hitboxes/ship/vertical.txt",
			"res/hitboxes/ship/vertical.txt", "res/hitboxes/ship/vertical.txt" };
	static String[] SW_HB_FILES = { "res/hitboxes/ship/diagSW-NE.txt",
			"res/hitboxes/ship/diagSW-NE.txt",
			"res/hitboxes/ship/diagSW-NE.txt" };
	static String[] SE_HB_FILES = { "res/hitboxes/ship/diagSE-NW.txt",
			"res/hitboxes/ship/diagSE-NW.txt",
			"res/hitboxes/ship/diagSE-NW.txt" };
	static String[] E_HB_FILES = { "res/hitboxes/ship/horizontal.txt",
			"res/hitboxes/ship/horizontal.txt",
			"res/hitboxes/ship/horizontal.txt" };
	static String[] W_HB_FILES = { "res/hitboxes/ship/horizontal.txt",
			"res/hitboxes/ship/horizontal.txt",
			"res/hitboxes/ship/horizontal.txt" };

	Map<Direction, HitboxAnimation> animationMap;

	public enum Direction {
		E, NE, N, NW, W, SW, S, SE
	}

	Ship() {
		super(new HitboxAnimation()); // TODO
		hp = MAX_HP;
		dir = Direction.E;

		animationMap = new HashMap<Direction, HitboxAnimation>();
		animationMap.put(Direction.N, HitboxAnimationFactory.create(
				N_IMG_FILES, N_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.NE, HitboxAnimationFactory.create(
				NE_IMG_FILES, NE_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.NW, HitboxAnimationFactory.create(
				NW_IMG_FILES, NW_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.S, HitboxAnimationFactory.create(
				S_IMG_FILES, S_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.SE, HitboxAnimationFactory.create(
				SE_IMG_FILES, SE_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.SW, HitboxAnimationFactory.create(
				SW_IMG_FILES, SW_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.E, HitboxAnimationFactory.create(
				E_IMG_FILES, E_HB_FILES, FRAME_DURATION));
		animationMap.put(Direction.W, HitboxAnimationFactory.create(
				W_IMG_FILES, W_HB_FILES, FRAME_DURATION));

		this.setHitboxAnimation(animationMap.get(this.dir));
	}

	private void computePath() {
		Vector2f speed = new Vector2f();
		if (nextFlag == null) {
			speed.x = 0;
			speed.y = 0;
		} else {
			float vectorX = nextFlag.getPosition().x
					- (getPosition().x + getHitboxAnimation().getWidth() / 2);
			float vectorY = nextFlag.getPosition().y
					- (getPosition().y + getHitboxAnimation().getHeight() / 2);

			// normalise le vecteur
			float norme = (float) Math.sqrt(vectorX * vectorX + vectorY
					* vectorY);
			if (norme > EPSILON) {
				vectorX = vectorX / (norme * SLOW_FACTOR);
				vectorY = vectorY / (norme * SLOW_FACTOR);

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
	public void update(GameContainer gc, int delta) {
		computePath();

		double angle = getSpeed().getTheta();
		if (getSpeed().x == 0 && getSpeed().y == 0) {
			// on ne change pas l'orientation si on n'a pas de vitesse
		} else {
			Direction tmpDir = dir;
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

			if (tmpDir != dir) {
				this.setHitboxAnimation(animationMap.get(this.dir));
				getHitboxAnimation().restart();
			}

		}
	}

	public void kill() {
		this.hp = 0;
	}
}
