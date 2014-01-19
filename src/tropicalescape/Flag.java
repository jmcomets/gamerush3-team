package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Flag extends GameObject {

	private static final int DESC_HEIGHT_SHIFT = 15;

	private static int DURATION = 1;
	private static final String[] IMG_FILES = { "res/animations/flag/base.png" };
	private static final String[] HITBOX_FILES = { "res/hitboxes/flag/base.txt" };

	protected String description;

	Flag(HitboxAnimation hitboxAnim, String description) {
		super(hitboxAnim);
		this.description = description;
	}

	Flag(String description) {
		this(HitboxAnimationFactory.create(IMG_FILES, HITBOX_FILES, DURATION),
				description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void render(Graphics g) {
		Vector2f pos = getPosition();
		int h = getHitboxAnimation().getHeight();
		g.setColor(Color.white);
		if (pos.y - DESC_HEIGHT_SHIFT < 0) {
			g.drawString(description, 0, h);
		} else {
			g.drawString(description, 0, -DESC_HEIGHT_SHIFT);
		}
	}
}
