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

	private int maxTimer = TIMER_DURATION;
	private int timer;
	private int state;

	public SleepingIsland() {
		super();
		up.setLooping(false);
		sleep.setLooping(false);
		state = AWAKE;
		setHitboxAnimation(up);
		timer = maxTimer;
	}

	public void setState(int state) {
		switch (state) {
		case SleepingIsland.AWAKE:
			this.state = SleepingIsland.AWAKE;
			setHitboxAnimation(up);
			break;

		case SleepingIsland.SLEEPING:
			this.state = SleepingIsland.SLEEPING;
			setHitboxAnimation(sleep);
			break;
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {
		timer -= delta;
		if (timer <= 0) {
			setState(1 - state);
			timer = maxTimer;
		}
	}

	public void setMaxTimer(int maxTimer) {
		if (timer == this.maxTimer) {
			timer = maxTimer;
		}
		this.maxTimer = maxTimer;
	}

}
