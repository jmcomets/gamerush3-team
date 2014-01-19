package tropicalescape;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import tropicalescape.physics.Hitbox;

public class HitboxAnimation extends Animation {

	private List<Hitbox> hitboxes = new ArrayList<Hitbox>();

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
		draw();
		//getHitbox().render(g);
	}
}
