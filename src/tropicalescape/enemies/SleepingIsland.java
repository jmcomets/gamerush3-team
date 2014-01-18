package tropicalescape.enemies;

import org.newdawn.slick.GameContainer;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class SleepingIsland extends Island {

	private HitboxAnimation up = HitboxAnimationFactory.create(new String[] {
			"res/animations/sleeping-island/island1.png",
			"res/animations/sleeping-island/island2.png",
			"res/animations/sleeping-island/island3.png",
			"res/animations/sleeping-island/island4.png",
			"res/animations/sleeping-island/island5.png",}, new String[] {
			"res/hitboxes/sleeping-island/island1.txt",
			"res/hitboxes/sleeping-island/island2.txt",
			"res/hitboxes/sleeping-island/island3.txt",
			"res/hitboxes/sleeping-island/island4.txt",
			"res/hitboxes/sleeping-island/island5.txt"}, 500);
	private HitboxAnimation sleep = HitboxAnimationFactory.create(new String[] {
			"res/animations/sleeping-island/island5.png",
			"res/animations/sleeping-island/island4.png",
			"res/animations/sleeping-island/island3.png",
			"res/animations/sleeping-island/island2.png",
			"res/animations/sleeping-island/island1.png" }, new String[] {
			"res/hitboxes/sleeping-island/island5.txt",
			"res/hitboxes/sleeping-island/island4.txt",
			"res/hitboxes/sleeping-island/island3.txt",
			"res/hitboxes/sleeping-island/island2.txt",
			"res/hitboxes/sleeping-island/island1.txt"}, 500);

	public static final int SLEEPING = 0;
	public static final int AWAKE = 1;
	public static final int TIMER_DURATION = 5000;

	private int state = SleepingIsland.AWAKE;
	private int timer = SleepingIsland.TIMER_DURATION;

	private void switchState() {
		switch (this.state) {
		case SleepingIsland.AWAKE:
			this.state = SleepingIsland.SLEEPING;
			this.setHitboxAnimation(this.sleep);
			break;

		case SleepingIsland.SLEEPING:
			this.state = SleepingIsland.AWAKE;
			this.setHitboxAnimation(this.up);
			break;
		}
	}

	public SleepingIsland() {
		super();
		this.up.setLooping(false);
		this.sleep.setLooping(false);
		this.setHitboxAnimation(this.up);
	}

	@Override
	public void update(GameContainer gc, int delta) {

		this.timer -= delta;
		if (timer <= 0) {
			this.switchState();
			this.timer = TIMER_DURATION;
		}
	}

}
