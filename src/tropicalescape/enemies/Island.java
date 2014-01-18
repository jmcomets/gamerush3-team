package tropicalescape.enemies;
import org.newdawn.slick.Graphics;

import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;


public abstract class Island extends Enemy {

	public Island() {
		super(makeHitboxAnimation());
	}
	
	public Island(HitboxAnimation hb){
		super(hb);
	}

	private static HitboxAnimation makeHitboxAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onHitShip(Ship ship) {
		ship.kill();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}
	
	
}
