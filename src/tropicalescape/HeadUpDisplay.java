package tropicalescape;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;

public class HeadUpDisplay {
	private static final int HEIGHT = 40;
	private static final int WIDTH = 230;
	private static final int PADDING = 10;
	private PlayState state;

	public HeadUpDisplay(PlayState state) {
		this.state = state;

	}

	public void draw(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y + 1 * HEIGHT, WIDTH - PADDING, HEIGHT);
		g.drawString(
				"Level : "
						+ state.getLvlName().substring(
								state.getLvlName().lastIndexOf('/') + 1,
								state.getLvlName().length()), x + 10, y + 1
						* HEIGHT + HEIGHT / 2);
		g.drawRect(x, y + 2 * HEIGHT, WIDTH - PADDING, HEIGHT);
		g.drawString("Number of flags : " + state.getNbrShips(), x + 10, +2
				* HEIGHT + HEIGHT / 2);
		
		g.drawRect(x, y + 3 * HEIGHT, WIDTH - PADDING, HEIGHT);
		g.drawString("Number of monsters : " + state.getNbrEnemies(), x + 10, +3
				* HEIGHT + HEIGHT / 2);
		
		g.drawRect(x, y + 4 * HEIGHT, WIDTH - PADDING, HEIGHT);
		g.drawString("Ships to win : " + state.getMinToWin(), x + 10, +4
				* HEIGHT + HEIGHT / 2);
		
		g.drawRect(x, y + 6 * HEIGHT, WIDTH - PADDING, HEIGHT);
		String mode;
		if(state.isNiceModeActivated()){
			mode = "NOOB";
		}else{
			mode = "Normal";
		}
		g.drawString("Mode : " + mode, x + 10, +6
				* HEIGHT + HEIGHT / 2);
		
		g.drawRect(x, y + 5 * HEIGHT, WIDTH - PADDING, HEIGHT);
		g.drawString("Ships at bay : " + state.getnArrivedShips(), x + 10, +5
				* HEIGHT + HEIGHT / 2);
	}

	public int getWidth() {
		return WIDTH;
	}

}
