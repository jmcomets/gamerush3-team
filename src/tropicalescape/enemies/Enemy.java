package tropicalescape.enemies;

import org.newdawn.slick.Graphics;

import tropicalescape.GameObject;
import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;

public abstract class Enemy extends GameObject {
	
	public Enemy(HitboxAnimation ha) {
		super(ha);
	}

	public abstract void onHitShip(Ship ship);
}
