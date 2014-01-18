package tropicalescape;

import org.newdawn.slick.Color;
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
		g.setColor(Color.white);
		if (getPosition().y - DESC_HEIGHT_SHIFT < 0) {
			g.drawString(description, 0, getHitboxAnimation().getHeight());
		} else {
			g.drawString(description, 0, -DESC_HEIGHT_SHIFT);
		}
	}

	@Override
	public void update(int delta) {
	}
}
