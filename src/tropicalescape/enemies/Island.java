package tropicalescape.enemies;
import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;


public abstract class Island extends Enemy {

	static String [] ISLAND_IMAGES = {"res/animations/island/dummy.png"};
	static String [] ISLAND_HITBOXES = {"res/hitboxes/island/dummy.txt"};

	public Island() {
		super(makeHitboxAnimation());
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(ISLAND_IMAGES, ISLAND_HITBOXES, 1);
	}

	@Override
	public void onHitShip(Ship ship) {
		ship.kill();
	}
	
}
