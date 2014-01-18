package tropicalescape.physics;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Hitbox implements Collidable {

	private Vector2f origin = new Vector2f();
	private List<Rectangle> rectangles = new ArrayList<Rectangle>();

	public boolean intersects(Hitbox hitbox) {

		for (Rectangle rect : hitbox.rectangles) {
			Rectangle r = new Rectangle(rect.getX(), rect.getY(),
					rect.getWidth(), rect.getHeight());
			r.setX(hitbox.origin.getX() + r.getX());
			r.setY(hitbox.origin.getY() + r.getY());
			if (intersects(r)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersects(Shape shape) {

		for (Rectangle rect : rectangles) {
			Rectangle r = new Rectangle(rect.getX(), rect.getY(),
					rect.getWidth(), rect.getHeight());
			r.setX(origin.getX()+ r.getX());
			r.setY(origin.getY()+ r.getY());
			System.out.println("X : " + r.getX() + " Y : " + r.getY() + " W : "
					+ r.getWidth() + " H : " + r.getHeight());
			System.out.println("X : " + shape.getX() + " Y : " + shape.getY()
					+ " W : " + shape.getWidth() + " H : " + shape.getHeight());
			if (r.intersects(shape)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersects(Collidable collidable) {
		if (collidable instanceof Hitbox) {
			return intersects((Hitbox) collidable);
		}
		return false;
	}

	public void addRectangle(Rectangle rectangle) {
		this.rectangles.add(rectangle);
	}

	public List<Rectangle> getRectangles() {
		return rectangles;
	}

	public void setOrigin(Vector2f origin) {
		this.origin = origin;
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(Color.red);
		for (Rectangle r : rectangles) {
			g.draw(r);
		}
		g.popTransform();
	}
}
