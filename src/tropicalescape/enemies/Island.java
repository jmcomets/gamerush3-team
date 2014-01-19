package tropicalescape.enemies;
import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;


public class Island extends Enemy {

	static String [] ISLAND_IMAGES = { "res/animations/island/1.png","res/animations/island/2.png","res/animations/island/3.png" };
	static String [] ISLAND_HITBOXES = { "res/hitboxes/island/1.txt" };

	public Island() {
		super(makeHitboxAnimation());
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(ISLAND_IMAGES, ISLAND_HITBOXES, 500);
	}

	@Override
	public void onHitShip(Ship ship, int delta) {
		ship.kill();
	}
}
