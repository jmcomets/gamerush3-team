import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class CompositeRectangle implements Collidable {

	private List<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	@Override
	public boolean intersects(Shape shape) {
		for (Rectangle r : rectangles)  {
			if (r.intersects(shape))  {
				return true;
			}
		}
		return false;
	}
	
	public void addRectangle(Rectangle rectangle) {
		this.rectangles.add(rectangle);
	}
}
