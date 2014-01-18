package tropicalescape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.physics.Collidable;
import tropicalescape.physics.Hitbox;

public class GameObject implements Collidable {
	
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
	public void baseUpdate(int delta) {
		Vector2f speed2 = new Vector2f(speed);
		speed2.x *= (float) delta;
		speed2.y *= (float) delta;
		position.add(speed2);
		hitboxAnimation.getHitbox().setOrigin(position);
		this.update(delta);
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

	public boolean intersects(Shape shape) {
		return hitboxAnimation.intersects(shape);
	}

	public boolean intersects(Collidable collidable) {
		return hitboxAnimation.intersects(collidable);
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
	public void update(int delta) {
	}
}
