package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class GiantLobster extends OneHitMonster {

	private static final int GIANT_LOBSTER_DAMAGE = 60;

	private static int GIANT_LOBSTER_ANIM_DURATION = 30;

	private static int END_GIANT_LOBSTER_ANIM_DURATION = 30;

	private static final String[] GIANT_LOBSTER_IMAGE_FILES = { "res/animations/giant-lobster/anim-hand-homard1.png" };
	private static final String[] GIANT_LOBSTER_HITBOX_FILES = { "res/hitboxes/giant-lobster/base.txt" };
	private static final String[] END_GIANT_LOBSTER_IMAGE_FILES = {
			"res/animations/giant-lobster/anim-hand-homard1.png", "res/animations/giant-lobster/anim-hand-homard2.png",
			"res/animations/giant-lobster/anim-hand-homard3.png", "res/animations/giant-lobster/anim-hand-homard4.png",
			"res/animations/giant-lobster/anim-hand-homard5.png", "res/animations/giant-lobster/anim-hand-homard6.png",
			"res/animations/giant-lobster/anim-hand-homard7.png", "res/animations/giant-lobster/anim-hand-homard8.png",
			"res/animations/giant-lobster/anim-hand-homard9.png", "res/animations/giant-lobster/anim-hand-homard10.png",
			"res/animations/giant-lobster/anim-hand-homard11.png", "res/animations/giant-lobster/anim-hand-homard12.png",
			"res/animations/giant-lobster/anim-hand-homard13.png", "res/animations/giant-lobster/anim-hand-homard14.png" };

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
