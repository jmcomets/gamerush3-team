package tropicalescape.physics;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Hitbox implements Collidable {

	private List<Rectangle> rectangles = new ArrayList<Rectangle>();

	public boolean intersects(Hitbox hitbox) {
		for (Rectangle rect : hitbox.rectangles) {
			if (intersects(rect)) {
				return true;
			}
		}
		return false;
	}

	public boolean intersects(Shape shape) {
		for (Rectangle rect : rectangles) {
			if (rect.intersects(shape)) {
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

	public void render(Graphics g) {
		g.pushTransform();
		g.setColor(Color.red);
		for (Rectangle r : rectangles) {
			g.draw(r);
		}
		g.popTransform();
	}
}
