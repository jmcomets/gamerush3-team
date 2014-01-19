package tropicalescape.ship.upgrades;

import java.util.ArrayList;

public class UpgradeManager {

	protected ArrayList<Upgrade> allUpgrades;
	protected Upgrade currentUpgrade;

	public UpgradeManager(ArrayList<Upgrade> allUpgrades, Upgrade currentUpgrade) {
		this.allUpgrades = allUpgrades;
		this.currentUpgrade = currentUpgrade;
	}

	public boolean canBuyNextUpgrade(int gold) {
		int i = allUpgrades.indexOf(currentUpgrade);

		if (i == allUpgrades.size() - 1) {
			return false;
		} else {
			Upgrade nextUpgrade = allUpgrades.get(i + 1);
			return gold - nextUpgrade.getCost() >= 0;
		}
	}

	public void applyNextUpgrade() {
		int i = allUpgrades.indexOf(currentUpgrade);
		if (i != allUpgrades.size() - 1) {
			Upgrade nextUpgrade = allUpgrades.get(i + 1);
			currentUpgrade = nextUpgrade;
		}
	}

	public Upgrade getCurrentUpgrade() {
		return currentUpgrade;
	}

}
