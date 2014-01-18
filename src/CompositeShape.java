import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Shape;


public class CompositeShape implements Collidable {

	private List<Shape> shapes = new ArrayList<Shape>();
	
	@Override
	public boolean intersects(Shape shape) {
		for (Shape s : shapes)  {
			if (s.intersects(shape))  {
				return true;
			}
		}
		return false;
	}
	
	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}
}
