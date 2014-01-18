import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame
{
	private Ship s1;
	private Flag f1;
	private Flag f2;
	private Flag f3;

	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		f1 = new Flag("Flag1", 50, 0);
		f2 = new Flag("flag2", 100, 100);
		f3 = new Flag("pepito", 150, 150);
		s1 = new Ship(200, 200);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		s1.update(delta);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		s1.render(g);
		f1.render(g);
		f2.render(g);
		f3.render(g);
		g.drawString("Howdy!", 10, 10);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}