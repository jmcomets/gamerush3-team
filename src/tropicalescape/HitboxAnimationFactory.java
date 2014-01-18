package tropicalescape;

import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tropicalescape.physics.HitboxLoader;

public class HitboxAnimationFactory {
	
	public static HitboxAnimation create(String [] imageFiles, String [] hitboxFiles, int duration) {
		HitboxAnimation ha = new HitboxAnimation();
		for (String file : imageFiles) {
			try {
				ha.addHitbox(HitboxLoader.loadFromTXT(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String file : imageFiles) {
			try {
				ha.addFrame(new Image(file), duration);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return ha;
	}
	
}
