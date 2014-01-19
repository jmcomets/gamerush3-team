package tropicalescape.enemies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

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
	private static final String[] KRAKEN_KILL_IMAGE_FILES = { 
		"res/animations/kraken/Kraken1-arms.png",
		"res/animations/kraken/Kraken2-arms.png",
		"res/animations/kraken/Kraken3-arms.png",
		"res/animations/kraken/Kraken4-arms.png",
		"res/animations/kraken/Kraken5-arms.png",
		"res/animations/kraken/Kraken6-arms.png",
		"res/animations/kraken/Kraken7-arms.png",
		"res/animations/kraken/Kraken8-arms.png",
		"res/animations/kraken/Kraken9-arms.png",
		"res/animations/kraken/Kraken10-arms.png",
		"res/animations/kraken/Kraken11-arms.png",
		"res/animations/kraken/Kraken12-arms.png",
		"res/animations/kraken/Kraken13-arms.png",
		"res/animations/kraken/Kraken14-arms.png",
		"res/animations/kraken/Kraken15-arms.png",
		};
	private static int KRAKEN_ANIM_DURATION = 300;
	private static int END_KRAKEN_ANIM_DURATION = 50;
	
	private boolean renderTentacle = false;
	private HitboxAnimation killAnimation;
	protected static final String[] emptyArray = {};

	public Kraken() {
		super(makeHitboxAnimation());
		killAnimation = HitboxAnimationFactory.create(
				KRAKEN_KILL_IMAGE_FILES, emptyArray, END_KRAKEN_ANIM_DURATION);
		
		killAnimation.setLooping(false);

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
			this.renderTentacle = true;
			Vector2f v = new Vector2f(s.getPosition().getX() - this.getPosition().getX(), 
					s.getPosition().getY() - this.getPosition().getY());
			
			
			killAnimation.setAngle((float) v.getTheta());
			killAnimation.setOffset(v);
			killAnimation.restart();
		}
	}

	//TODO : voir pk delta est en sec
	@Override
	public void update(GameContainer gc, int delta) {
		if(killAnimation.isStopped()){
			renderTentacle = false;  
		}
		Iterator<Integer> it = coolDowns.iterator();
		System.out.println(delta);
		while (it.hasNext()) {
			Integer iObj = it.next();
			iObj -= delta;
			if (iObj <= 0) {
				it.remove();
				nbMaxKill++;
			}
		}
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		if(renderTentacle){
			killAnimation.render(g);
		}
	}

}
