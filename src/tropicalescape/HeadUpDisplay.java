package tropicalescape;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;


public class HeadUpDisplay{
	private static final int HEIGHT = 40;
	private static final int WIDTH = 300;
	private static final int PADDING = 10;
	private PlayState state;

	public HeadUpDisplay(PlayState state){
		this.state=state;
		
	}
	
	public void draw(Graphics g, float x, float y) {
		g.setColor(Color.white);
		g.drawRect(x, y + 1* HEIGHT, WIDTH-PADDING , HEIGHT);
		g.drawString("Level : " + state.getLvlName(), x + 10, y + 1* HEIGHT + HEIGHT/2);
	}
	
	public void update(){

	}

	public int getWidth() {
		return WIDTH;
	}

}
