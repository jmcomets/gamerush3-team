package tropicalescape.enemies;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import tropicalescape.Ship;
import tropicalescape.physics.CompositeRectangle;


public abstract class Island extends Enemy {
	
	private Animation animation;
	
	public Island(Animation anim, CompositeRectangle rectangles) {
		super(rectangles);
	}

	@Override
	public void onHitShip(Ship ship) {
		
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