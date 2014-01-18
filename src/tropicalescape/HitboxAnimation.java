package tropicalescape;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import tropicalescape.physics.Collidable;
import tropicalescape.physics.Hitbox;

public class HitboxAnimation extends Animation implements Collidable {

	private List<Hitbox> hitboxes = new ArrayList<Hitbox>();

	public Hitbox getHitbox() {
		return hitboxes.get(getFrame());
	}

	@Override
	public boolean intersects(Shape shape) {
		return getHitbox().intersects(shape);
	}

	public boolean intersects(Hitbox hb) {
		return getHitbox().intersects(hb);
	}

	public boolean intersects(HitboxAnimation ha) {
		return intersects(ha.getHitbox());
	}

	@Override
	public boolean intersects(Collidable collidable) {
		if (collidable instanceof HitboxAnimation) {
			intersects((HitboxAnimation) collidable);
		}
		return getHitbox().intersects(collidable);
	}

	public void addHitbox(Hitbox hitbox) {
		hitboxes.add(hitbox);
	}

	public void render(Graphics g) {
		draw();
		getHitbox().render(g);
	}
}
