package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class HeadUpDisplay {
	private static final int HEIGHT = 20;
	private static final int WIDTH = 150;
	private static final int PADDING = 10;
	private static final float OPACITY = 0.70f;
	private PlayState state;
	private Image img;

	public HeadUpDisplay(PlayState state){
		this.state = state;
		try {
			img = new Image("res/animations/background/carte.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, float x, float y){
		//background
		Vector2f backgroundOffset = new Vector2f(-30, -140);
		img.setAlpha(OPACITY);
		for(int i = 0; i < 1 ; i++){
			for(int j = 0; j < 1 ; j++){
				img.draw(backgroundOffset.x + x + i * img.getWidth(),
						backgroundOffset.y + y + j * img.getHeight());
			}
		}
		
		g.setColor(new Color(0, 0, 0, OPACITY));
		
		int i = 1;
		
		if (state.getNbrRemainingFlags() >= 0) {
			g.drawString("Flags left : " + state.getNbrRemainingFlags(), x + 10, i*HEIGHT);
			i++;
		}
		g.drawString("Monsters : " + state.getNbrEnemies(), x + 10, i*HEIGHT);
		i++;
		g.drawString("Objective : " + state.getMinToWin(), x + 10, i*HEIGHT);
		i++;
		g.drawString("Arrived : " + state.getnArrivedShips(), x + 10, i*HEIGHT);
		i++;
		
		if(state.isNiceModeActivated()) {
			g.drawString("Nice mode", x + 10, i * HEIGHT);
			i++;
		} else if (state.isGodModeActivated()) {
			g.drawString("1337 |\\/|0|)3", x + 10, i * HEIGHT);
			i++;
		}
	}

	public int getWidth() {
		return WIDTH;
	}

}
