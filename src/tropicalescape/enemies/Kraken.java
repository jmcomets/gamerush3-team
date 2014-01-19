package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class Kraken extends OneHitMonster {

	private static final int KRAKEN_DAMAGE = 70;
	private static final String[] KRAKEN_IMAGE_FILES = {
			"res/animations/kraken/Kraken1-head.png",
			"res/animations/kraken/Kraken2-head.png",
			"res/animations/kraken/Kraken3-head.png",
			"res/animations/kraken/Kraken3-head.png"};
	private static final String[] KRAKEN_HITBOX_FILES = { "res/hitboxes/kraken/kraken-head.txt" };
	private static final String[] END_KRAKEN_IMAGE_FILES = { "res/animations/kraken/dummy.png" };
	private static int KRAKEN_ANIM_DURATION = 300;
	private static int END_KRAKEN_ANIM_DURATION = 300;

	public Kraken() {
		super(makeHitboxAnimation());
		endAnimation = HitboxAnimationFactory.create(END_KRAKEN_IMAGE_FILES,
				emptyArray, END_KRAKEN_ANIM_DURATION);
		damageOnHit = KRAKEN_DAMAGE;
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(KRAKEN_IMAGE_FILES,
				KRAKEN_HITBOX_FILES, KRAKEN_ANIM_DURATION);
	}

}
