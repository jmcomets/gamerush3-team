package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;

public abstract class OneHitMonster extends Enemy {
	
	protected int damageOnHit;
	private boolean hitDone = false;
	
	protected HitboxAnimation endAnimation;
	
	public OneHitMonster(HitboxAnimation hitboxAnimation) {
		super(hitboxAnimation);
	}

	protected static final String[] emptyArray = {};

	@Override
	public void onHitShip(Ship ship, int delta) {
		ship.loseHealth(getLevel() * damageOnHit);
		hitDone = true;

		// Start end animation
		setHitboxAnimation(endAnimation);
		getHitboxAnimation().setLooping(false);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if (hitDone && getHitboxAnimation().isStopped()) {
			setAlive(false);
		}
	}

}
