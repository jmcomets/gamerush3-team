package tropicalescape;

import org.newdawn.slick.GameContainer;

public class FinishFlag extends Flag {

	private static int DURATION = 1;
	private static final String[] IMG_FILES = { "res/animations/flag/finishflag.png" };
	private static final String[] HITBOX_FILES = { "res/hitboxes/flag/base.txt" };

	FinishFlag(String description) {
		super(HitboxAnimationFactory.create(IMG_FILES, HITBOX_FILES, DURATION), description);
	}
	

	public void update(GameContainer gc, int delta) {
		// Flag indéplacable
	}

}
