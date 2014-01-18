package tropicalescape.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import tropicalescape.physics.Hitbox;

public class SleepingIsland extends Island {
	
	private Animation sleep;
	private Animation fallAsleep;
	private Animation up;
	private Animation wakeUp;
	
	private Animation animation;
	
	public static final int SLEEPING = 0 ;
	public static final int AWAKE = 1 ;
	public static final int TIMER_DURATION = 1000; 

	private int state =  SleepingIsland.AWAKE; 
	private int timer =  SleepingIsland.TIMER_DURATION; 
	
	private void switchState(){
		switch (this.state){
			case SleepingIsland.AWAKE : 
				this.state = SleepingIsland.SLEEPING;
				this.animation = this.fallAsleep; 
				break;
			
			case SleepingIsland.SLEEPING : 
				this.state = SleepingIsland.AWAKE;
				this.animation = this.wakeUp; 
				break;
			
		}
		
	}
	
	private void switchAnimation(){
		switch (this.state){
			case  SleepingIsland.AWAKE : 
				this.animation = this.up;
				break;
			case SleepingIsland.SLEEPING:
				this.animation = this.sleep;
				break;
		}
		
	}
	
	public SleepingIsland(Animation anim,Hitbox rectangles) {
		super(anim, rectangles);
	}
	
	
	@Override 
	public void update(int delta) {
		
		this.timer -= delta;
		if(timer <= 0){
			this.switchState();
			this.timer = this.TIMER_DURATION;		
		}
		
		if (this.animation.isStopped()){
			this.switchAnimation();
		}
		super.update(delta);
	}
	
	
}
