package tropicalescape.enemies;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.PlayState;
import tropicalescape.Ship;

public class OneHitMonster extends Enemy {

	private static final int COCONUT_DAMAGE = 30;
	private static final int COCONUT_THROWER_DAMAGE = 50;
	private static final int GIANT_LOBSTER_DAMAGE = 60;
	private static final int KRAKEN_DAMAGE = 70;

	private static final int COCONUT_THROWER_ATTACK_RADIUS = 300;

	private static int COCONUT_DURATION = 300;
	private static int COCONUT_THROWER_DURATION = 300;
	private static int KRAKEN_DURATION = 300;
	private static int GIANT_LOBSTER_DURATION = 30;

	private static int SLOW_FACTOR = 10;

	private static final String[] KRAKEN_IMAGE_FILES = {
			"res/animations/kraken/dummy1.png",
			"res/animations/kraken/dummy2.png",
			"res/animations/kraken/dummy3.png" };
	private static final String[] KRAKEN_HITBOX_FILES = { "res/hitboxes/kraken/dummy.txt" };
	private static final String[] END_KRAKEN_IMAGE_FILES = { "res/animations/kraken/dummy.png" };

	private static final String[] GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };
	private static final String[] GIANT_LOBSTER_HITBOX_FILES = { "res/hitboxes/giant-lobster/dummy.txt" };
	private static final String[] END_GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };

	private static final String[] COCONUT_THROWER_IMAGE_FILES = { "res/animations/coconut-thrower/dummy.png" };
	private static final String[] COCONUT_THROWER_HITBOX_FILES = { "res/hitboxes/coconut-thrower/dummy.txt" };
	private static final String[] END_COCONUT_THROWER_IMAGE_FILES = { "res/animations/coconut-thrower/dummy.png" };

	private static final String[] COCONUT_IMAGE_FILES = { "res/animations/coconut/dummy.png" };
	private static final String[] COCONUT_HITBOX_FILES = { "res/hitboxes/coconut/dummy.txt" };
	private static final String[] END_COCONUT_IMAGE_FILES = { "res/animations/coconut/dummy.png" };

	public enum Type {
		KRAKEN, GIANT_LOBSTER, COCONUT_THROWER, COCONUT
	}

	private boolean hitDone = false;
	private Type type;
	private HitboxAnimation endAnimation;

	private int coconutThrowerNextAttack;
	private static final int COCONUT_THROWER_ATTACK_COOLDOWN = 1000;

	private static HitboxAnimation makeHitboxAnimation(Type type) {
		if (type == Type.KRAKEN) {
			return HitboxAnimationFactory.create(KRAKEN_IMAGE_FILES,
					KRAKEN_HITBOX_FILES, KRAKEN_DURATION);
		} else if (type == Type.GIANT_LOBSTER) {
			return HitboxAnimationFactory.create(GIANT_LOBSTER_IMAGE_FILES,
					GIANT_LOBSTER_HITBOX_FILES, GIANT_LOBSTER_DURATION);
		} else if (type == Type.COCONUT_THROWER) {
			return HitboxAnimationFactory.create(COCONUT_THROWER_IMAGE_FILES,
					COCONUT_THROWER_HITBOX_FILES, COCONUT_THROWER_DURATION);
		} else {
			return HitboxAnimationFactory.create(COCONUT_IMAGE_FILES,
					COCONUT_HITBOX_FILES, COCONUT_DURATION);
		}
	}

	public OneHitMonster(Type type) {
		super(makeHitboxAnimation(type));
		this.type = type;
		if (type == Type.KRAKEN) {
			endAnimation = HitboxAnimationFactory.create(
					END_KRAKEN_IMAGE_FILES, emptyArray, KRAKEN_DURATION);
		} else if (type == Type.GIANT_LOBSTER) {
			endAnimation = HitboxAnimationFactory.create(
					END_GIANT_LOBSTER_IMAGE_FILES, emptyArray,
					GIANT_LOBSTER_DURATION);
		} else if (type == Type.COCONUT_THROWER) {
			endAnimation = HitboxAnimationFactory.create(
					END_COCONUT_THROWER_IMAGE_FILES, emptyArray,
					COCONUT_THROWER_DURATION);
		} else {
			endAnimation = HitboxAnimationFactory.create(
					END_COCONUT_IMAGE_FILES, emptyArray, COCONUT_DURATION);
		}
	}

	private static final String[] emptyArray = {};

	@Override
	public void onHitShip(Ship ship) {
		if (type == Type.KRAKEN) {
			ship.loseHealth(KRAKEN_DAMAGE);
		} else if (type == Type.GIANT_LOBSTER) {
			ship.loseHealth(GIANT_LOBSTER_DAMAGE);
		} else if (type == Type.COCONUT_THROWER) {
			ship.loseHealth(COCONUT_THROWER_DAMAGE);
		} else {
			ship.loseHealth(COCONUT_DAMAGE);
		}
		hitDone = true;

		// Start end animation
		setHitboxAnimation(endAnimation);
		getHitboxAnimation().setLooping(false);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		PlayState gameInstance = PlayState.getInstance(0, 0);
		if (type == Type.COCONUT_THROWER) {
			// Un ship a proximité ? On envoie des noix de coco !
			if (coconutThrowerNextAttack <= 0) {
				Circle circle = new Circle(getPosition().x, getPosition().y,
						COCONUT_THROWER_ATTACK_RADIUS);
				List<Ship> ships = gameInstance.getShips();
				for (Ship s : ships) {
					if (circle.contains(s.getPosition().x, s.getPosition().y)) {
						// Attack
						OneHitMonster coconut = new OneHitMonster(Type.COCONUT);
						coconut.setPosition(getPosition().copy());

						float vectorX = (s.getPosition().x + s
								.getHitboxAnimation().getWidth() / 2)
								- coconut.getPosition().x;
						float vectorY = (s.getPosition().y + s
								.getHitboxAnimation().getHeight() / 2)
								- coconut.getPosition().y;

						float norme = (float) Math.sqrt(vectorX * vectorX
								+ vectorY * vectorY);
						vectorX = vectorX / (norme * SLOW_FACTOR);
						vectorY = vectorY / (norme * SLOW_FACTOR);
						Vector2f speed = new Vector2f(vectorX, vectorY);

						coconut.setSpeed(speed);
						gameInstance.addEnemy(coconut);
						coconutThrowerNextAttack = COCONUT_THROWER_ATTACK_COOLDOWN;
					}
					break;
				}
			} else {
				coconutThrowerNextAttack -= delta;
			}
		} else if (type == Type.COCONUT) {
			if (getPosition().x < 0 || getPosition().x > gameInstance.height
					|| getPosition().y < 0 || getPosition().y > gameInstance.width) {
				setAlive(false);
			}
		}
		if (hitDone && getHitboxAnimation().isStopped()) {
			setAlive(false);
		}
	}

}
