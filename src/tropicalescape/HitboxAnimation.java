package tropicalescape;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Hitbox;

public class HitboxAnimation extends Animation {

	private List<Hitbox> hitboxes = new ArrayList<Hitbox>();
	private float angle = 0; 
	private Vector2f offset = new Vector2f() ;
	
	public Vector2f getOffset() {
		return offset;
	}

	public void setOffset(Vector2f offset) {
		this.offset = offset;
	}

	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Hitbox getHitbox() {
		int frame = getFrame();
		if (hitboxes.size() == 0) {
			return new Hitbox();
		} else if (hitboxes.size() < (frame + 1)) {
			return hitboxes.get(hitboxes.size() - 1);
		} else {
			return hitboxes.get(frame);
		}
	}
	
	

	public void addHitbox(Hitbox hitbox) {
		hitboxes.add(hitbox);
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.translate(offset.x, offset.y);
		g.rotate(getWidth()/2, getHeight()/2, angle);
		draw();
		//getHitbox().render(g);
		g.popTransform();
	}
	
	
}
