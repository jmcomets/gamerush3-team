package tropicalescape.physics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;

public class HitboxLoader {
	public static Hitbox loadFromTXT(String path)
			throws IOException {
		Hitbox cr = new Hitbox();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] array = text.split("\\s+");
				if (array.length < 4) {
					throw new IOException("Need 4 numbers");
				}
				try {
					float x = Integer.parseInt(array[0]);
					float y = Integer.parseInt(array[1]);
					float w = Integer.parseInt(array[2]);
					float h = Integer.parseInt(array[3]);
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
