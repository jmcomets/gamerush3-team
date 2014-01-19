package tropicalescape.enemies;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;
import tropicalescape.PlayState;
import tropicalescape.Ship;

public class CoconutThrower extends OneHitMonster {

	private static final int COCONUT_THROWER_DAMAGE = 50;
	private static final String[] COCONUT_THROWER_IMAGE_FILES = { "res/animations/coconut-thrower/dummy.png" };
	private static final String[] COCONUT_THROWER_HITBOX_FILES = { "res/hitboxes/coconut-thrower/dummy.txt" };
	private static final String[] END_COCONUT_THROWER_IMAGE_FILES = { "res/animations/coconut-thrower/dummy.png" };
	private static int COCONUT_THROWER_DURATION = 300;
	private static int END_COCONUT_THROWER_DURATION = 300;

	private static int SLOW_FACTOR = 14;

	private int nextAttack;
	private static final int COCONUT_THROWER_ATTACK_COOLDOWN = 2000;
	private static final int COCONUT_THROWER_ATTACK_RADIUS = 300;

	public CoconutThrower() {
		super(makeHitboxAnimation());
		endAnimation = HitboxAnimationFactory.create(
				END_COCONUT_THROWER_IMAGE_FILES, emptyArray,
				END_COCONUT_THROWER_DURATION);
		damageOnHit = COCONUT_THROWER_DAMAGE;
	}

	private static HitboxAnimation makeHitboxAnimation() {
		return HitboxAnimationFactory.create(COCONUT_THROWER_IMAGE_FILES,
				COCONUT_THROWER_HITBOX_FILES, COCONUT_THROWER_DURATION);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		PlayState gameInstance = PlayState.getInstance();
		// Un ship a proximité ? On envoie des noix de coco !
		if (nextAttack <= 0) {
			Circle circle = new Circle(getPosition().x, getPosition().y,
					COCONUT_THROWER_ATTACK_RADIUS);
			List<Ship> ships = gameInstance.getShips();
			for (Ship s : ships) {
				if (circle.contains(s.getPosition().x, s.getPosition().y)) {
					// Attack
					OneHitMonster coconut = new Coconut();
					coconut.setPosition(getPosition().copy());

					float vectorX = (s.getPosition().x + s.getHitboxAnimation()
							.getWidth() / 2) - coconut.getPosition().x;
					float vectorY = (s.getPosition().y + s.getHitboxAnimation()
							.getHeight() / 2) - coconut.getPosition().y;

					float norme = (float) Math.sqrt(vectorX * vectorX + vectorY
							* vectorY);
					vectorX = vectorX / (norme * SLOW_FACTOR);
					vectorY = vectorY / (norme * SLOW_FACTOR);
					Vector2f speed = new Vector2f(vectorX, vectorY);

					coconut.setSpeed(speed);
					gameInstance.addEnemy(coconut);
					nextAttack = COCONUT_THROWER_ATTACK_COOLDOWN;
				}
				break;
			}
		} else {
			nextAttack -= delta;
		}

		super.update(gc, delta);
	}

}
