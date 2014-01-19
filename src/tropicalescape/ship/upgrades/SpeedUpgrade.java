package tropicalescape.ship.upgrades;

public class SpeedUpgrade extends Upgrade {

	private float speedBonusPercent;

	public SpeedUpgrade(int cost, String name, float speedBonusPercent) {
		super(cost, name);
		this.speedBonusPercent = speedBonusPercent;
	}

	public float getSpeedBonusPercent() {
		return speedBonusPercent;
	}

	@Override
	public String getDescription() {
		return getName() + " +" + speedBonusPercent + "%";
	}

}
