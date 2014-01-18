package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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

	@Override
	public void update(GameContainer gc, int delta) {

		Input input = gc.getInput();

		boolean leftClicked = isLeftClicked(gc);
		if (leftClicked
				&& (getSelectedObject() == this || getSelectedObject() == null)
				|| !leftClicked && getSelectedObject() == this) {
			HitboxAnimation hitAnim = getHitboxAnimation();
			Image frame = hitAnim.getCurrentFrame();
			int x = input.getMouseX() - frame.getWidth() / 2;
			int y = input.getMouseY() - frame.getHeight() / 2;

			setPosition(new Vector2f(x, y));

			setSelectedObject(this);
		}
		if (getSelectedObject() == this
				&& !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			setSelectedObject(null);
		}

		if (isRightClicked(gc)
				&& input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			setSelectedObject(this);
		}

	}
}
