package tropicalescape.ship.upgrades;

import java.util.ArrayList;

public class UpgradeManager<T extends Upgrade> {

	protected ArrayList<T> allUpgrades;
	protected T currentUpgrade;

	public UpgradeManager(ArrayList<T> allUpgrades, T currentUpgrade) {
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
	
	public T getNextUpgrade() {
		int i = allUpgrades.indexOf(currentUpgrade);
		if (i != allUpgrades.size() - 1) {
			T nextUpgrade = allUpgrades.get(i + 1);
			return nextUpgrade;
		}
		else {
			return null;
		}
	}

	public void applyNextUpgrade() {
		int i = allUpgrades.indexOf(currentUpgrade);
		if (i != allUpgrades.size() - 1) {
			T nextUpgrade = allUpgrades.get(i + 1);
			currentUpgrade = nextUpgrade;
		}
	}

	public T getCurrentUpgrade() {
		return currentUpgrade;
	}

}
