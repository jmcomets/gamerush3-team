package tropicalescape;

public class SmallWave extends GameObject {

	public SmallWave(HitboxAnimation hitboxAnimation) {
		super(hitboxAnimation);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

}
