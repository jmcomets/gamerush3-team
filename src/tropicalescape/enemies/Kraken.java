package tropicalescape.enemies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.Ship;

public class Kraken extends Enemy {

	/* TODO : Make it configurable */
	private static int MAX_NB_KILLS = 2;
	private int nbMaxKill = MAX_NB_KILLS;
	private int coolDown = 3;
	/*--------------------------*/

	private static final String[] KRAKEN_IMAGE_FILES = {
			"res/animations/kraken/Kraken1-head.png",
			"res/animations/kraken/Kraken2-head.png",
			"res/animations/kraken/Kraken3-head.png",
			"res/animations/kraken/Kraken3-head.png" };
	private static final String[] KRAKEN_HITBOX_FILES = { "res/hitboxes/kraken/kraken-head.txt" };
	private static final String[] KRAKEN_KILL_IMAGE_FILES = { "res/animations/kraken/dummy.png" };
	private static int KRAKEN_ANIM_DURATION = 300;
	private static int END_KRAKEN_ANIM_DURATION = 300;

	private Animation killAnimation;
	protected static final String[] emptyArray = {};

	public Kraken() {
		super(makeHitboxAnimation());
		killAnimation = HitboxAnimationFactory.create(
				KRAKEN_KILL_IMAGE_FILES, emptyArray, END_KRAKEN_ANIM_DURATION);

	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(KRAKEN_IMAGE_FILES,
				KRAKEN_HITBOX_FILES, KRAKEN_ANIM_DURATION);
	}
	
	List<Integer> coolDowns = new ArrayList<Integer>();

	@Override
	public void onHitShip(Ship s, int delta) {
		if (nbMaxKill > 0) {
			s.kill();
			coolDowns.add(coolDown);
			nbMaxKill--;
		}
	}

	//TODO : voir pk delta est en sec
	@Override
	public void update(GameContainer gc, int delta) {
		Iterator<Integer> it = coolDowns.iterator();
		System.out.println(delta);
		while (it.hasNext()) {
			Integer iObj = it.next();
			iObj -= delta;
			System.out.println("iObj = " + iObj);
			if (iObj <= 0) {
				System.out.println("cooldown finished");
				it.remove();
				nbMaxKill++;
			}
		}
	}

}
