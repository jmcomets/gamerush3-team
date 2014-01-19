package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;

public abstract class Monster extends Enemy {

	public Monster(HitboxAnimation ha) {
		super(ha);
	}

	@Override
	public void onHitShip(Ship ship) {
	}

}
