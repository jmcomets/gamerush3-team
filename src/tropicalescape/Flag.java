package tropicalescape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tropicalescape.physics.CompositeRectangle;

public class Flag extends GameObject {

	static final int DESC_HEIGHT_SHIFT = 15;
	static final String IMG_FILE = "res/flag.png";

	String m_desc;
	static Image m_img;

	static {
		try {
			m_img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	Flag(String desc, float x, float y) {
		super(new CompositeRectangle());
		m_desc = desc;
		getPosition().x = x;
		getPosition().y = y;
	}

	@Override
	public void render(Graphics g) {
		m_img.draw();
		if (getPosition().y - DESC_HEIGHT_SHIFT < 0) {
			g.drawString(m_desc, 0, m_img.getHeight());
		} else {
			g.drawString(m_desc, 0, -DESC_HEIGHT_SHIFT);
		}
	}

	@Override
	public void update(int delta) {
	}
}
