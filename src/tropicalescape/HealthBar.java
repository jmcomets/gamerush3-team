package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class HealthBar {

	private static final int BORDER_WIDTH = 2;

	private Vector2f position = new Vector2f();

	private static final float HP_DROP_PER_FRAME = 0.0005f; // Percents of
															// maxHp;

	private int height;
	private int width;
	private int maxHp;
	private int hp;
	private int lastStableHp; // Permet d'afficher la barre orange
	private float renderHp; // On veut pas de healthbar qui descende par acoup

	public HealthBar(int height, int width, int maxHp, int hp) {
		this.height = height;
		this.width = width;
		this.setMaxHp(maxHp);
		this.setHp(hp);
		this.renderHp = hp;
		this.lastStableHp = hp;
	}

	public void baseUpdate(GameContainer gc, int delta) {
		float hpToLoseTotal = renderHp - hp;
		if (hpToLoseTotal == 0) {
			lastStableHp = hp;
		} else {
			float hpDropRate = HP_DROP_PER_FRAME * (float) maxHp
					* (float) delta;
			if (renderHp != hp) {
				if (hpDropRate > hpToLoseTotal) {
					renderHp = hp;
				} else {
					renderHp -= hpDropRate;
				}
			}
		}
	}

	public void baseRender(Graphics g) {

		if (hp == maxHp) {
			return;
		}
		
		float oldLineWidth = g.getLineWidth();
		Color oldColor = g.getColor();

		g.setLineWidth(BORDER_WIDTH);

		g.setColor(Color.black);
		g.drawRect(position.x, position.y, width, height);

		float orangeBarWidth = lastStableHp * (float) (width - BORDER_WIDTH)
				/ (float) maxHp;

		g.setColor(Color.orange);
		g.fillRect(position.x + BORDER_WIDTH, position.y + BORDER_WIDTH,
				orangeBarWidth, height - BORDER_WIDTH);

		float hpWidth = renderHp * (float) (width - BORDER_WIDTH)
				/ (float) maxHp;

		g.setColor(Color.red);
		g.fillRect(position.x + BORDER_WIDTH, position.y + BORDER_WIDTH,
				hpWidth, height - BORDER_WIDTH);

		g.setColor(oldColor);
		g.setLineWidth(oldLineWidth);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

}
