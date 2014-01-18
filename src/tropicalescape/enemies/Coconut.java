package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.PlayState;

public class Coconut extends OneHitMonster {

	private static final int COCONUT_DAMAGE = 30;
	private static int COCONUT_ANIM_DURATION = 300;
	private static int END_COCONUT_DURATION = 300;

	private static final String[] COCONUT_IMAGE_FILES = { "res/animations/coconut/dummy.png" };
	private static final String[] COCONUT_HITBOX_FILES = { "res/hitboxes/coconut/dummy.txt" };
	private static final String[] END_COCONUT_IMAGE_FILES = { "res/animations/coconut/dummy.png" };

	public Coconut() {
		super(makeHitboxAnimation());
		endAnimation = HitboxAnimationFactory.create(END_COCONUT_IMAGE_FILES,
				emptyArray, END_COCONUT_DURATION);
		damageOnHit = COCONUT_DAMAGE;
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(COCONUT_IMAGE_FILES,
				COCONUT_HITBOX_FILES, COCONUT_ANIM_DURATION);
	}

	@Override
	public void update(GameContainer gc, int delta) {

		PlayState gameInstance = PlayState.getInstance(0, 0);

		if (getPosition().x < 0 || getPosition().x > gameInstance.height
				|| getPosition().y < 0 || getPosition().y > gameInstance.width) {
			setAlive(false);
		}

		super.update(gc, delta);
	}

}
