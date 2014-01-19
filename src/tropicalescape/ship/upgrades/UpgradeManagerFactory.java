package tropicalescape.ship.upgrades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UpgradeManagerFactory {

	private UpgradeManager armorUpgradesManager;
	private UpgradeManager healthUpgradesManager;
	private UpgradeManager speedUpgradesManager;

	public void loadFromFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = null;
		ArrayList<Upgrade> armorUpgrades = new ArrayList<Upgrade>();
		ArrayList<Upgrade> healthUpgrades = new ArrayList<Upgrade>();
		ArrayList<Upgrade> speedUpgrades = new ArrayList<Upgrade>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] tokens = text.split("\\s+");

				if ("ARMORUP".equals(tokens[0])) {
					armorUpgrades.add(new ArmorUpgrade(Integer
							.parseInt(tokens[1]), tokens[2], Float
							.parseFloat(tokens[3])));
				} else if ("HPUP".equals(tokens[0])) {
					healthUpgrades.add(new HealthUpgrade(Integer
							.parseInt(tokens[1]), tokens[2], Integer
							.parseInt(tokens[3])));
				} else if ("SPEEDUP".equals(tokens[0])) {
					speedUpgrades.add(new SpeedUpgrade(Integer
							.parseInt(tokens[1]), tokens[2], Float
							.parseFloat(tokens[3])));
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		armorUpgradesManager = new UpgradeManager(armorUpgrades, armorUpgrades.get(0));
		healthUpgradesManager = new UpgradeManager(healthUpgrades, healthUpgrades.get(0));
		speedUpgradesManager = new UpgradeManager(speedUpgrades, speedUpgrades.get(0));
	}

	public UpgradeManager getArmorUpgradesManager() {
		return armorUpgradesManager;
	}

	public UpgradeManager getHpUpgradesManager() {
		return healthUpgradesManager;
	}

	public UpgradeManager getSpeedUpgradesManager() {
		return speedUpgradesManager;
	}

}
