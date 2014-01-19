package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;

public class Wave extends Enemy {
	
	private static final int SHIP_INVINCIBILITY_AFTER_HIT = 1000;

	protected static final int DAMAGE_ON_HIT = 1;

	static String[] WAVE_IMAGES = { "res/animations/waves/Vagues-1.png",
			"res/animations/waves/Vagues-2.png",
			"res/animations/waves/Vagues-3.png" };
	static String[] WAVE_HITBOXES = { "res/hitboxes/waves/Vague.txt" };

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(WAVE_IMAGES, WAVE_HITBOXES, 500);
	}

	public Wave() {
		super(makeHitboxAnimation());
	}

	@Override
	public void onHitShip(Ship s, int delta) {
		if (s.getInvincibilyPeriod() == 0) {
			s.loseHealth(DAMAGE_ON_HIT);
			s.setInvincibilyPeriod(SHIP_INVINCIBILITY_AFTER_HIT);
		} else {
			int invincibilyPeriod = s.getInvincibilyPeriod() - delta;
			if(invincibilyPeriod < 0) {
				invincibilyPeriod = 0;
			}
			s.setInvincibilyPeriod(invincibilyPeriod);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {

	}
}
