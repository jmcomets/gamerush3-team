package tropicalescape.physics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;

public class CompositeRectangleFactory {
	public static CompositeRectangle loadFromTXT(String path)
			throws IOException {
		CompositeRectangle cr = new CompositeRectangle();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] array = text.split(" ", -1);
				System.out.println(array);
				if (array.length < 4) {
					throw new IOException("Need 4 rectangles");
				}
				try {
					float x = Float.parseFloat(array[0]);
					float y = Float.parseFloat(array[1]);
					float w = Float.parseFloat(array[2]);
					float h = Float.parseFloat(array[3]);
					cr.addRectangle(new Rectangle(x, y, w, h));
				} catch (NumberFormatException e) {
					throw new IOException(e);
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return cr;
	}
}
