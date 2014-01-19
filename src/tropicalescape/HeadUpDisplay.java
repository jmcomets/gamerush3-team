package tropicalescape;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

public class HeadUpDisplay {
	private static final int HEIGHT = 20;
	private static final int WIDTH = 150;
	private static final int PADDING = 10;
	private PlayState state;
	private Image img;

	public HeadUpDisplay(PlayState state){
		this.state = state;
		try {
			img = new Image("res/animations/background/back.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void draw(Graphics g, float x, float y){
		//background
		for(int i = 0; i < 1 ; i++){
			for(int j = 0; j < 1 ; j++){
				img.draw(x+i*img.getWidth(),y+j*img.getHeight());
			}
		}

		
		g.setColor(Color.white);

		g.drawString("Flags # : " + state.getNbrShips(), x + 10, 1*HEIGHT);

		g.drawString("Monsters # : " + state.getNbrEnemies(), x + 10, 2*HEIGHT);
		
		g.drawString("To win : " + state.getMinToWin(), x + 10, 3*HEIGHT);
		
		String mode;
		if(state.isNiceModeActivated()){
			mode = "NOOB";
		}else{
			mode = "Normal";
		}
		g.drawString("Mode : " + mode, x + 10, 5*HEIGHT);
		
		g.drawString("Arrived : " + state.getnArrivedShips(), x + 10,4*HEIGHT);
	}

	public int getWidth() {
		return WIDTH;
	}

}
