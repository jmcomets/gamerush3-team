package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tropicalescape.ship.upgrades.ArmorUpgrade;
import tropicalescape.ship.upgrades.HealthUpgrade;
import tropicalescape.ship.upgrades.SpeedUpgrade;
import tropicalescape.ship.upgrades.Upgrade;
import tropicalescape.ship.upgrades.UpgradeManager;

public class UpgradeState extends BasicGameState {

	public static final int ID = 6;
	private boolean changeLevel = false;
	private boolean replay = false;

	UpgradeState() {

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		Player player = Player.getInstance();
		g.setColor(Color.white);

		String message = "SELECT YOUR UPGRADES (You have " + player.getGolds()
				+ " gold coins)";

		int x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		int y = 50;
		g.drawString(message, x, y);

		x = 50;
		y += g.getFont().getLineHeight() + 30;

		UpgradeManager<HealthUpgrade> healthUpgradesManager = player
				.getHealthUpgradesManager();
		Upgrade currentUpgrade = healthUpgradesManager.getCurrentUpgrade();
		Upgrade nextUpgrade = healthUpgradesManager.getNextUpgrade();

		message = "(1) Upgrade ship HPs - Cost : ";
		message += nextUpgrade != null ? nextUpgrade.getCost() : "0";
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Current level : " + currentUpgrade.getDescription();
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Next level : ";
		message += nextUpgrade != null ? nextUpgrade.getDescription() : "MAXED";
		g.drawString(message, x, y);

		y += g.getFont().getLineHeight() + 30;

		UpgradeManager<SpeedUpgrade> speedUpgradesManager = player
				.getSpeedUpgradesManager();
		currentUpgrade = speedUpgradesManager.getCurrentUpgrade();
		nextUpgrade = speedUpgradesManager.getNextUpgrade();

		message = "(2) Upgrade ship SPEED - Cost : ";
		message += nextUpgrade != null ? nextUpgrade.getCost() : "0";
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Current level : " + currentUpgrade.getDescription();
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Next level : ";
		message += nextUpgrade != null ? nextUpgrade.getDescription() : "MAXED";
		g.drawString(message, x, y);

		y += g.getFont().getLineHeight() + 30;

		UpgradeManager<ArmorUpgrade> armorUpgradesManager = player
				.getArmorUpgradesManager();
		currentUpgrade = armorUpgradesManager.getCurrentUpgrade();
		nextUpgrade = armorUpgradesManager.getNextUpgrade();

		message = "(3) Upgrade ship DEF - Cost : ";
		message += nextUpgrade != null ? nextUpgrade.getCost() : "0";
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Current level : " + currentUpgrade.getDescription();
		g.drawString(message, x, y);
		y += g.getFont().getLineHeight() + 10;
		message = "Next level : ";
		message += nextUpgrade != null ? nextUpgrade.getDescription() : "MAXED";
		g.drawString(message, x, y);

		y += g.getFont().getLineHeight() + 10;
		g.drawString("Press Enter to replay", x, y + 100);
		y += g.getFont().getLineHeight() + 50;
		g.drawString("Press Escape to select level", x, y);
		;
	}

	@Override
	public void keyPressed(int key, char c) {

		Player player = Player.getInstance();
		int spent = 0;
		UpgradeManager<HealthUpgrade> healthUpgradesManager = player
				.getHealthUpgradesManager();
		UpgradeManager<SpeedUpgrade> speedUpgradesManager = player
				.getSpeedUpgradesManager();
		UpgradeManager<ArmorUpgrade> armorUpgradesManager = player
				.getArmorUpgradesManager();

		if (key == Input.KEY_1 || key == Input.KEY_NUMPAD1) {
			if (healthUpgradesManager.canBuyNextUpgrade(player.getGolds())) {
				healthUpgradesManager.applyNextUpgrade();
				spent = healthUpgradesManager.getCurrentUpgrade().getCost();
			}
		} else if (key == Input.KEY_2 || key == Input.KEY_NUMPAD2) {
			if (speedUpgradesManager.canBuyNextUpgrade(player.getGolds())) {
				speedUpgradesManager.applyNextUpgrade();
				spent = speedUpgradesManager.getCurrentUpgrade().getCost();
			}
		} else if (key == Input.KEY_3 || key == Input.KEY_NUMPAD3) {
			if (armorUpgradesManager.canBuyNextUpgrade(player.getGolds())) {
				armorUpgradesManager.applyNextUpgrade();
				spent = armorUpgradesManager.getCurrentUpgrade().getCost();
			}
		} else if (key == Input.KEY_ENTER || key == Input.KEY_NUMPADENTER) {
			replay = true;
		} else if (key == Input.KEY_ESCAPE) {
			changeLevel = true;
		}
		
		player.decreaseGolds(spent);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		replay = false;
		changeLevel = false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (replay) {
			game.enterState(PlayState.ID);
		} else if (changeLevel) {
			game.enterState(LevelSelectionState.ID);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
