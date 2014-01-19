package tropicalescape.enemies;

import tropicalescape.GameObject;
import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;

public abstract class Enemy extends GameObject {
	
	boolean alive = true;
	
	public Enemy(HitboxAnimation ha) {
		super(ha);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public abstract void onHitShip(Ship ship, int delta);
}
