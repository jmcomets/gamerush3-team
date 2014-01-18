package tropicalescape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Flag extends GameObject {

	private static final int DESC_HEIGHT_SHIFT = 15;
	
	private static int DURATION = 1;
	private static final String [] IMG_FILES = {"res/animations/flag/base.png"};
	private static final String [] HITBOX_FILES = {"res/hitboxes/flag/base.txt"};

	private String description;

	Flag(String description) {
		super(HitboxAnimationFactory.create(IMG_FILES, HITBOX_FILES, DURATION));
		this.description = description;
	}

	@Override
	public void render(Graphics g) {
		Vector2f pos = getPosition();
		if (pos.y - DESC_HEIGHT_SHIFT < 0) {
			int h = getHitboxAnimation().getHeight();
			g.drawString(description, pos.x, h);
		} else {
			g.drawString(description, pos.x, pos.y - DESC_HEIGHT_SHIFT);
		}
	}

	@Override
	public void update(int delta) {
	}
}
