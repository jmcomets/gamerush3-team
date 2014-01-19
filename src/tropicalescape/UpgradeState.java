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
import tropicalescape.ship.upgrades.UpgradeManager;

public class UpgradeState extends BasicGameState {

	public static final int ID = 6;
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
		PlayState playState = PlayState.getInstance();
		g.setColor(Color.white);
		
		String message = "SELECT YOUR UPGRADES";

		int x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		int y = 50;
		g.drawString(message, x, y);

		UpgradeManager<HealthUpgrade> healthUpgradesManager = playState.getHealthUpgradesManager();
		message = "Upgrade ship HPs - Cost : " + healthUpgradesManager.getNextUpgradeCost();
		message = "\nCurrent level : " + healthUpgradesManager.getCurrentUpgrade().getName() + ")";
		message = "\nNext level : +" + healthUpgradesManager.getCurrentUpgrade().getName() + ")";

		x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		y = 50;
		g.drawString(message, x, y);

		UpgradeManager<ArmorUpgrade> armorUpgradesManager = playState.getArmorUpgradesManager();
		message = "Upgrade ship ARMOR - Cost : " + armorUpgradesManager.getNextUpgradeCost();
		message = "\nCurrent level : " + armorUpgradesManager.getCurrentUpgrade().getName() + ")";
		message = "\nNext level : " + armorUpgradesManager.getCurrentUpgrade().getName() + ")";

		x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		y = 50;
		g.drawString(message, x, y);

		UpgradeManager<SpeedUpgrade> speedUpgradesManager = playState.getSpeedUpgradesManager();
		message = "Upgrade ship SPEED - Cost : " + speedUpgradesManager.getNextUpgradeCost();
		message = "\nCurrent level : " + speedUpgradesManager.getCurrentUpgrade().getName() + ")";
		message = "\nNext level : " + speedUpgradesManager.getCurrentUpgrade().getName() + ")";

		x = (container.getWidth() - g.getFont().getWidth(message)) / 2;
		y = 50;
		g.drawString(message, x, y);

		g.drawString("Press Enter to replay", x, y + 100);
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER) {
			replay = true;
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		replay = false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (replay) {
			game.enterState(PlayState.ID);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
