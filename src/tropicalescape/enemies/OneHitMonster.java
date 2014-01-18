package tropicalescape.enemies;

import org.newdawn.slick.Graphics;

import tropicalescape.Ship;
import tropicalescape.physics.Hitbox;
import tropicalescape.physics.HitboxLoader;

public class OneHitMonster extends Enemy {
	
	private enum MonsterType {
		KRAKEN, GIANT_LOBSTER
	}

	private static Hitbox makeMonsterHitbox(MonsterType type) {
		return null;
	}

	public OneHitMonster(MonsterType type, Hitbox rectangles) {
		super(makeMonsterHitbox(type));
	}

	@Override
	public void onHitShip(Ship ship) {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void update(int delta) {
	}

}
