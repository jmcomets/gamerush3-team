package tropicalescape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
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

	public void baseUpdate(int delta) {
		Vector2f speed2 = new Vector2f(speed);
		speed2.x *= (float) delta;
		speed2.y *= (float) delta;
		position.add(speed2);
		hitboxAnimation.getHitbox().setOrigin(position);
		this.update(delta);
	}

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

	public Hitbox getRectangles() {
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
	
	public abstract void render(Graphics g);
	public abstract void update(int delta);
}
