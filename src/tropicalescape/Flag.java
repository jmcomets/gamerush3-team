package tropicalescape;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Flag extends GameObject {

	private static final int DESC_HEIGHT_SHIFT = 15;

	private static int DURATION = 1;
	private static final String[] IMG_FILES = { "res/animations/flag/base.png" };
	private static final String[] HITBOX_FILES = { "res/hitboxes/flag/base.txt" };

	private String description;
	private boolean selected;

	Flag(String description) {
		super(HitboxAnimationFactory.create(IMG_FILES, HITBOX_FILES, DURATION));
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

	@Override
	public void update(GameContainer gc, int delta) {
		
		boolean leftClicked = isLeftClicked(gc);
		if (leftClicked || selected) {
			HitboxAnimation hitAnim = getHitboxAnimation();
			Image frame = hitAnim.getCurrentFrame();
			Input input = gc.getInput();
			int x = input.getMouseX() - frame.getWidth() / 2;
			int y = input.getMouseY() - frame.getHeight() / 2;

			setPosition(new Vector2f(x, y));

			if (!leftClicked
					&& !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				selected = false;
			} else {
				selected = true;
			}
		}
		
	}
}
