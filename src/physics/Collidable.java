package physics;
import org.newdawn.slick.geom.Shape;

public interface Collidable {
	public boolean intersects(Collidable collidable);
	public boolean intersects(Shape shape);
}
