package tropicalescape.enemies;

import org.newdawn.slick.Graphics;

import tropicalescape.GameObject;
import tropicalescape.HitboxAnimation;
import tropicalescape.Ship;

public abstract class Enemy extends GameObject {
	
	private boolean alive = true;
	private int level = 1;
	
	public Enemy(HitboxAnimation ha) {
		super(ha);
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public void render(Graphics g) {
//		Font font = g.getFont();
//		String levelStr = "[" + level + "]";
//		g.drawString(levelStr, getHitboxAnimation().getWidth(),
//				-font.getHeight(levelStr));
	}

	public abstract void onHitShip(Ship ship, int delta);
}
