package tropicalescape.enemies;

import tropicalescape.GameObject;
import tropicalescape.Ship;
import tropicalescape.physics.Hitbox;

public abstract class Enemy extends GameObject {
	public Enemy(Hitbox rectangles) {
		super(rectangles);
	}

	public abstract void onHitShip(Ship ship);
}
