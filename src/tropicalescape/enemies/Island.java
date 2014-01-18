package tropicalescape.enemies;
import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;


public class Island extends Enemy {

	static String [] ISLAND_IMAGES = { "res/animations/island/1.png" };
	static String [] ISLAND_HITBOXES = { "res/hitboxes/island/1.txt" };

	public Island() {
		super(makeHitboxAnimation());
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(ISLAND_IMAGES, ISLAND_HITBOXES, 1);
	}

	@Override
	public void onHitShip(Ship ship) {
		System.out.println("ship hit, my pos was " + getPosition());
		ship.kill();
	}
}
