package tropicalescape;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.ship.upgrades.ArmorUpgrade;
import tropicalescape.ship.upgrades.HealthUpgrade;
import tropicalescape.ship.upgrades.SpeedUpgrade;
import tropicalescape.ship.upgrades.UpgradeManager;

public class Ship extends GameObject {

	private static final int HPBAR_POSX = 50;
	private static final int HPBAR_POSY = 10;
	static final int HPBAR_HEIGHT = 10;
	static final int HPBAR_WIDHT = 60;
	static final int MAX_HP = 50;
	static final int EPSILON = 5;
	static final int SLOW_FACTOR = 10;
	static final int FRAME_DURATION = 125;

	private int invincibilyPeriod = 0;
	private int hp;
	private float armor;
	private float speedBonus;
	private Flag nextFlag;
	private Direction dir;
	private boolean arrived = false;
	private HealthBar healthBar = new HealthBar(HPBAR_HEIGHT, HPBAR_WIDHT,
			MAX_HP, MAX_HP);

	static String[] N_IMG_FILES = { "res/animations/ship/Boat1-up.png",
			"res/animations/ship/Boat2-up.png",
			"res/animations/ship/Boat3-up.png" };
	static String[] NE_IMG_FILES = { "res/animations/ship/Boat1-rightup.png",
			"res/animations/ship/Boat2-rightup.png",
			"res/animations/ship/Boat3-rightup.png" };
	static String[] NW_IMG_FILES = { "res/animations/ship/Boat1-leftup.png",
			"res/animations/ship/Boat2-leftup.png",
			"res/animations/ship/Boat3-leftup.png" };
	static String[] S_IMG_FILES = { "res/animations/ship/Boat1-down.png",
			"res/animations/ship/Boat2-down.png",
			"res/animations/ship/Boat3-down.png" };
	static String[] SW_IMG_FILES = { "res/animations/ship/Boat1-leftdown.png",
			"res/animations/ship/Boat2-leftdown.png",
			"res/animations/ship/Boat3-leftdown.png" };
	static String[] SE_IMG_FILES = { "res/animations/ship/Boat1-rightdown.png",
			"res/animations/ship/Boat2-rightdown.png",
			"res/animations/ship/Boat3-rightdown.png" };
	static String[] E_IMG_FILES = { "res/animations/ship/Boat1-right.png",
			"res/animations/ship/Boat2-right.png",
			"res/animations/ship/Boat3-right.png" };
	static String[] W_IMG_FILES = { "res/animations/ship/Boat1-left.png",
			"res/animations/ship/Boat2-left.png",
			"res/animations/ship/Boat3-left.png" };

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
		super(new HitboxAnimation());

		hp = MAX_HP;
		UpgradeManager<HealthUpgrade> healthUpgradesManager = PlayState
				.getInstance().getHealthUpgradesManager();
		HealthUpgrade currentUpgrade = healthUpgradesManager
				.getCurrentUpgrade();
		hp += currentUpgrade.getBonusHp();

		UpgradeManager<ArmorUpgrade> armorUpgradesManager = PlayState
				.getInstance().getArmorUpgradesManager();
		ArmorUpgrade armorUpgrade = armorUpgradesManager.getCurrentUpgrade();
		armor = armorUpgrade.getArmorPercent();

		UpgradeManager<SpeedUpgrade> speedUpgradesManager = PlayState
				.getInstance().getSpeedUpgradesManager();
		SpeedUpgrade speedUpgrade = speedUpgradesManager.getCurrentUpgrade();
		speedBonus = speedUpgrade.getSpeedBonusPercent();

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

		setHitboxAnimation(animationMap.get(this.dir));
		healthBar.setPosition(new Vector2f(HPBAR_POSX, HPBAR_POSY));
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

				speed.x = vectorX * (1f + speedBonus / 100f);
				speed.y = vectorY * (1f + speedBonus / 100f);
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
		hp -= (float) dmgValue * (1f - armor / 100f);
	}

	public boolean isAlive() {
		if (hp > 0) {
			return true;
		}
		return false;
	}

	public boolean hasArrivedToNextFlag() {
		return arrived;
	}

	public Flag getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(Flag nextFlag) {
		this.nextFlag = nextFlag;
		arrived = false;
	}

	public int getInvincibilyPeriod() {
		return invincibilyPeriod;
	}

	public void setInvincibilyPeriod(int invincibilyPeriod) {
		this.invincibilyPeriod = invincibilyPeriod;
	}

	@Override
	public void render(Graphics g) {
		healthBar.baseRender(g);

		super.render(g);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		computePath();
		healthBar.setHp(hp);
		healthBar.baseUpdate(gc, delta);

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
