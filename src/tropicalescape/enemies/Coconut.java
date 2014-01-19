package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class Coconut extends OneHitMonster {

	private static final int COCONUT_DAMAGE = 30;
	private static int COCONUT_ANIM_DURATION = 300;

	private static final String[] COCONUT_IMAGE_FILES = { "res/animations/coconut/nodkoko.png" };
	private static final String[] COCONUT_HITBOX_FILES = { "res/hitboxes/coconut/base.txt" };

	public Coconut() {
		super(makeHitboxAnimation());
		endAnimation = new HitboxAnimation();
		damageOnHit = COCONUT_DAMAGE;
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(COCONUT_IMAGE_FILES,
				COCONUT_HITBOX_FILES, COCONUT_ANIM_DURATION);
	}

	@Override
	public void update(GameContainer gc, int delta) {

		if (getPosition().x < 0 || getPosition().x > gc.getHeight()
				|| getPosition().y < 0 || getPosition().y > gc.getWidth()) {
			setAlive(false);
		}

		super.update(gc, delta);
	}

}
