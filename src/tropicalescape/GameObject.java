package tropicalescape;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Collidable;
import tropicalescape.physics.Hitbox;

public abstract class GameObject implements Collidable {

	private Vector2f position = new Vector2f();
	private Vector2f speed = new Vector2f();

	private HitboxAnimation hitboxAnimation;

	public GameObject(HitboxAnimation hitboxAnimation) {
		this.hitboxAnimation = hitboxAnimation;
	}

	/**
	 * Base updating method called by the Game
	 * 
	 * @param delta
	 */
	public void baseUpdate(GameContainer gc, int delta) {
		Vector2f speed2 = new Vector2f(speed);
		speed2.x *= (float) delta;
		speed2.y *= (float) delta;
		position.add(speed2);
		update(gc, delta);
	}

	public boolean isMouseOver(GameContainer gc) {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		Image img = hitboxAnimation.getCurrentFrame();
		Vector2f pos = getPosition();
		Rectangle rect = new Rectangle(pos.x, pos.y, img.getWidth(),
				img.getHeight());
		return rect.contains(mouseX, mouseY);
	}

	public boolean isLeftClicked(GameContainer gc) {
		return isMouseOver(gc)
				&& gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
	}

	public boolean isRightClicked(GameContainer gc) {
		return isMouseOver(gc)
				&& gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}

	/**
	 * Base drawing method called by the Game
	 * 
	 * @param g
	 */
	public void baseRender(Graphics g) {
		g.pushTransform();
		g.translate(position.x, position.y);
		hitboxAnimation.render(g);
		render(g);
		g.popTransform();
	}

	Hitbox getTranslatedHitbox() {
		Hitbox hitbox = (Hitbox) getHitboxAnimation().getHitbox();
		Hitbox translatedHitbox = new Hitbox();
		for (Rectangle rect : hitbox.getRectangles()) {
			Rectangle copiedRect = new Rectangle(rect.getX() + position.x,
					rect.getY() + position.y, rect.getWidth(), rect.getHeight());
			translatedHitbox.addRectangle(copiedRect);
		}
		return translatedHitbox;
	}

	public boolean intersects(GameObject o) {
		return getTranslatedHitbox().intersects(o.getTranslatedHitbox());
	}

	@Override
	public boolean intersects(Collidable collidable) {
		if (collidable instanceof GameObject) {
			intersects((GameObject) collidable);
		}
		return false;
	}

	public HitboxAnimation getHitboxAnimation() {
		return hitboxAnimation;
	}

	public void setHitboxAnimation(HitboxAnimation hitboxAnimation) {
		this.hitboxAnimation = hitboxAnimation;
	}

	public Hitbox getHitbox() {
		return hitboxAnimation.getHitbox();
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}
	
	/**
	 * 
	 * @return if the GameObject shouldn't be updated/rendered
	 */
	public abstract boolean isAlive();

	/**
	 * Event called when this object is rendered (after the baseRender)
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
	}

	/**
	 * Event called when this object is updated (after the baseUpdate)
	 * 
	 * @param delta
	 */
	public void update(GameContainer gc, int delta) {
	}
}
