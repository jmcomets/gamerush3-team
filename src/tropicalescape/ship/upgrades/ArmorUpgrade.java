package tropicalescape.ship.upgrades;

public class ArmorUpgrade extends Upgrade {

	private float armorPercent;

	public ArmorUpgrade(int cost, String name, float armorPercent) {
		super(cost, name);
		this.armorPercent = armorPercent;
	}

	public float getArmorPercent() {
		return armorPercent;
	}

	@Override
	public String getDescription() {
		return getName() + " +" + armorPercent + "DEF";
	}

}
