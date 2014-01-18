package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;
import tropicalescape.physics.Hitbox;

public class OneHitMonster extends Enemy {

	private static final int GIANT_LOBSTER_DAMAGE = 60;
	private static final int KRAKEN_DAMAGE = 70;

	private static int KRAKEN_DURATION = 300;
	private static int GIANT_LOBSTER_DURATION = 30;

	private static final String[] KRAKEN_IMAGE_FILES = {
		"res/animations/kraken/dummy1.png",
		"res/animations/kraken/dummy2.png",
		"res/animations/kraken/dummy3.png"
		};
	private static final String[] KRAKEN_HITBOX_FILES = { "res/hitboxes/kraken/dummy.txt" };

	private static final String[] GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };
	private static final String[] GIANT_LOBSTER_HITBOX_FILES = { "res/hitboxes/giant-lobster/dummy.txt" };

	private static final String[] END_KRAKEN_IMAGE_FILES = { "res/animations/kraken/dummy.png" };
	private static final String[] END_GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };

	public enum Type {
		KRAKEN, GIANT_LOBSTER
	}

	private boolean hitDone = false;
	private Type type;
	private HitboxAnimation endAnimation;

	private static HitboxAnimation makeHitboxAnimation(Type type) {
		if (type == Type.KRAKEN) {
			return HitboxAnimationFactory.create(KRAKEN_IMAGE_FILES,
					KRAKEN_HITBOX_FILES, KRAKEN_DURATION);
		} else {
			return HitboxAnimationFactory.create(GIANT_LOBSTER_IMAGE_FILES,
					GIANT_LOBSTER_HITBOX_FILES, GIANT_LOBSTER_DURATION);
		}
	}

	public OneHitMonster(Type type) {
		super(makeHitboxAnimation(type));
		this.type = type;
		if (type == Type.KRAKEN) {
			endAnimation = HitboxAnimationFactory.create(END_KRAKEN_IMAGE_FILES,
					emptyArray, KRAKEN_DURATION);
		} else {
			HitboxAnimationFactory.create(END_GIANT_LOBSTER_IMAGE_FILES,
					emptyArray, GIANT_LOBSTER_DURATION);
		}
	}
	
	private static String [] emptyArray = {};

	@Override
	public void onHitShip(Ship ship) {
		if (type == Type.KRAKEN) {
			ship.loseHealth(KRAKEN_DAMAGE);
		} else {
			ship.loseHealth(GIANT_LOBSTER_DAMAGE);
		}
		hitDone = true;
		
		// Start end animation
//		setHitboxAnimation(endAnimation);
//		getHitboxAnimation().setLooping(false);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if (hitDone && getHitboxAnimation().isStopped()) {
			//setAlive(false);
		}
	}

}
