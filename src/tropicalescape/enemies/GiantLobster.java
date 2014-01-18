package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class GiantLobster extends OneHitMonster {

	private static final int GIANT_LOBSTER_DAMAGE = 60;

	private static int GIANT_LOBSTER_ANIM_DURATION = 30;

	private static int END_GIANT_LOBSTER_ANIM_DURATION = 30;

	private static final String[] GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };
	private static final String[] GIANT_LOBSTER_HITBOX_FILES = { "res/hitboxes/giant-lobster/dummy.txt" };
	private static final String[] END_GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/dummy.png" };

	public GiantLobster() {
		super(makeHitboxAnimation());
		endAnimation = HitboxAnimationFactory.create(
				END_GIANT_LOBSTER_IMAGE_FILES, emptyArray,
				END_GIANT_LOBSTER_ANIM_DURATION);
		damageOnHit = GIANT_LOBSTER_DAMAGE;
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(GIANT_LOBSTER_IMAGE_FILES,
				GIANT_LOBSTER_HITBOX_FILES, GIANT_LOBSTER_ANIM_DURATION);
	}

}
