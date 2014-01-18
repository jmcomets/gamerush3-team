package tropicalescape.physics;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class CompositeRectangle implements Collidable {

	private Vector2f origin = new Vector2f();
	private List<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	public boolean intersects(CompositeRectangle cr) {
		for (Rectangle r : cr.getRectangles())  {
			if (intersects(r)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean intersects(Shape shape) {
		for (Rectangle rect : rectangles)  {
			if ((new Rectangle(rect.getX() + origin.x,
					rect.getY() + origin.y,
					rect.getWidth(), rect.getHeight())).intersects(shape))  {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersects(Collidable collidable) {
		if (collidable instanceof CompositeRectangle) {
			return intersects((CompositeRectangle) collidable);
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
		for (Rectangle r : rectangles)  {
			g.draw(r);
		}
		g.popTransform();
	}
}
