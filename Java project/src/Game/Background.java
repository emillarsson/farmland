package Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	private int x = 0;
	private int y = 0;
	
	public Background(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getMinimapImage(int x, int y) {
		return (BufferedImage) image.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH);
		
	}
	
	public void setPosition(int x, int y) {
		this.x = -x;
		this.y = -y;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}

}
