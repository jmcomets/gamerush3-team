package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;

public class Wave extends Enemy {
	
	protected int damageOnHit;
	
	static String [] WAVE_IMAGES = { "res/animations/waves/Vagues-1.png","res/animations/waves/Vagues-2.png","res/animations/waves/Vagues-3.png" };
	static String [] WAVE_HITBOXES = { "res/hitboxes/waves/Vague.txt" };
	
	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(WAVE_IMAGES, WAVE_HITBOXES, 500);
	}
	public Wave() {
		super(makeHitboxAnimation());
	}
	
	@Override
	public void onHitShip(Ship s){
		s.loseHealth(damageOnHit);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		
	}
}
