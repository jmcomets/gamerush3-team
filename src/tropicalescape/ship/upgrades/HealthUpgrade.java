package tropicalescape.ship.upgrades;

public class HealthUpgrade extends Upgrade {

	private int bonusHp;

	public HealthUpgrade(int cost, String name, int bonusHp) {
		super(cost, name);
		this.bonusHp = bonusHp;
	}

	public int getBonusHp() {
		return bonusHp;
	}

	@Override
	public String getDescription() {
		return getName() + " +" + bonusHp + "HP";
	}

}
