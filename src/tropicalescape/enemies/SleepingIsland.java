package tropicalescape.enemies;

import tropicalescape.HitboxAnimation;
import tropicalescape.HitboxAnimationFactory;

public class SleepingIsland extends Island {
	
	
	
	private HitboxAnimation up = HitboxAnimationFactory.create(new String[] {"res/animations/sleeping-island/1.png","res/animations/sleeping-island/2.png"}, new String[] {"res/hitboxes/sleeping-island/1.txt","res/hitboxes/sleeping-island/2.txt"}, 500);
	private HitboxAnimation sleep = HitboxAnimationFactory.create(new String[] {"res/animations/sleeping-island/2.png","res/animations/sleeping-island/1.png"}, new String[] {"res/hitboxes/sleeping-island/2.txt","res/hitboxes/sleeping-island/1.txt"}, 500);
	
	public static final int SLEEPING = 0 ;
	public static final int AWAKE = 1 ;
	public static final int TIMER_DURATION = 1000; 

	private int state =  SleepingIsland.AWAKE; 
	private int timer =  SleepingIsland.TIMER_DURATION; 
	
	private void switchState(){
		switch (this.state){
			case SleepingIsland.AWAKE : 
				this.state = SleepingIsland.SLEEPING;
				this.setHitboxAnimation(this.sleep);
				break;
			
			case SleepingIsland.SLEEPING : 
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
	public void update(int delta) {
		
		this.timer -= delta;
		if(timer <= 0){
			this.switchState();
			this.timer = TIMER_DURATION;		
		}
		super.update(delta);
	}
	
}
