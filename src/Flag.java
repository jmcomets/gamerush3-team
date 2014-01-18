import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Flag implements GameObject {

	static final int DESC_HEIGHT_SHIFT = 15;
	static final String IMG_FILE = "res/ship.png";

	String m_desc;
	float m_x;
	float m_y;
	static Image m_img;

	static {
		try {
			m_img = new Image(IMG_FILE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	Flag(String desc, float x, float y) {
		m_desc = desc;
		m_x = x;
		m_y = y;
	}

	@Override
	public void render(Graphics g) {
		m_img.draw(m_x, m_y);
		if (m_y - DESC_HEIGHT_SHIFT < 0) {
			g.drawString(m_desc, m_x, m_y + m_img.getHeight());
		} else {
			g.drawString(m_desc, m_x, m_y - DESC_HEIGHT_SHIFT);
		}
	}

	@Override
	public void update(int delta) {
		//
	}

	public float getX() {
		return m_x;
	}

	public float getY() {
		return m_y;
	}
}
