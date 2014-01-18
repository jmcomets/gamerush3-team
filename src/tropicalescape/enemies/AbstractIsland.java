package tropicalescape.enemies;
import org.newdawn.slick.Animation;

import tropicalescape.physics.CompositeRectangle;


public abstract class AbstractIsland extends Enemy {
	
	private Animation animation;
	
	public AbstractIsland(Animation anim, CompositeRectangle rectangles) {
		super(rectangles);
	}
}
