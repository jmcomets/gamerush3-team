package tropicalescape.ship.upgrades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UpgradeManagerFactory {

	private UpgradeManager<ArmorUpgrade> armorUpgradesManager;
	private UpgradeManager<HealthUpgrade> healthUpgradesManager;
	private UpgradeManager<SpeedUpgrade> speedUpgradesManager;

	public void loadFromFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader reader = null;
		ArrayList<ArmorUpgrade> armorUpgrades = new ArrayList<ArmorUpgrade>();
		ArrayList<HealthUpgrade> healthUpgrades = new ArrayList<HealthUpgrade>();
		ArrayList<SpeedUpgrade> speedUpgrades = new ArrayList<SpeedUpgrade>();
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

		armorUpgradesManager = new UpgradeManager<ArmorUpgrade>(armorUpgrades,
				armorUpgrades.get(0));
		healthUpgradesManager = new UpgradeManager<HealthUpgrade>(
				healthUpgrades, healthUpgrades.get(0));
		speedUpgradesManager = new UpgradeManager<SpeedUpgrade>(speedUpgrades,
				speedUpgrades.get(0));
	}

	public UpgradeManager<ArmorUpgrade> getArmorUpgradesManager() {
		return armorUpgradesManager;
	}

	public UpgradeManager<HealthUpgrade> getHealthUpgradesManager() {
		return healthUpgradesManager;
	}

	public UpgradeManager<SpeedUpgrade> getSpeedUpgradesManager() {
		return speedUpgradesManager;
	}

}
