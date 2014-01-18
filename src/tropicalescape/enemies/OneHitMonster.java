package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;
import tropicalescape.physics.Hitbox;

public class OneHitMonster extends Enemy {

	private static final int GIANT_LOBSTER_DAMAGE = 60;
	private static final int KRAKEN_DAMAGE = 70;

	private static int KRAKEN_DURATION = 1;
	private static int GIANT_LOBSTER_DURATION = 1;

	private static final String[] KRAKEN_IMAGE_FILES = { "res/animations/kraken/dummy.png" };
	private static final String[] KRAKEN_HITBOX_FILES = { "res/hitboxes/kraken/dummy.txt" };
	private static final String[] GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };
	private static final String[] GIANT_LOBSTER_HITBOX_FILES = { "res/hitboxes/giant-lobster/dummy.txt" };

	private enum MonsterType {
		KRAKEN, GIANT_LOBSTER
	}

	private int damage;

	private static HitboxAnimation makeHitboxAnimation(MonsterType type) {
		if (type == MonsterType.KRAKEN) {
			return HitboxAnimationFactory.create(KRAKEN_IMAGE_FILES,
					KRAKEN_HITBOX_FILES, KRAKEN_DURATION);
		} else {
			return HitboxAnimationFactory.create(GIANT_LOBSTER_IMAGE_FILES,
					GIANT_LOBSTER_HITBOX_FILES, GIANT_LOBSTER_DURATION);
		}
	}

	public OneHitMonster(MonsterType type, Hitbox rectangles) {
		super(makeHitboxAnimation(type));
		if (type == MonsterType.KRAKEN) {
			damage = KRAKEN_DAMAGE;
		} else {
			damage = GIANT_LOBSTER_DAMAGE;
		}
	}

	@Override
	public void onHitShip(Ship ship) {
		ship.loseHealth(damage);
	}

	@Override
	public void update(int delta) {
	}
	
}
