package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.TestState;

public class ImageButton {

	private BufferedImage image;
	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private Rectangle bounds;
	private boolean hover;
	private String title = "";
	private String description = "";
	private int cost = 0;
	private String hotkey = "";

	public ImageButton(String s, int x, int y) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
		x_size = image.getWidth();
		this.x = x - x_size;
		y_size = image.getHeight();
		this.y = y - y_size;

		bounds = new Rectangle(x - x_size, y - y_size, x_size, y_size);
	}

	public boolean inBounds(Point p) {
		return bounds.contains(p);
	}

	public boolean inBounds(int x, int y) {
		return bounds.contains(x, y);
	}
	
	public void setImage(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, x_size, y_size, null);
		if (hover) {
			g.setColor(new Color(0, 0, 0, 170));
			g.fillRect(x - 200 + x_size, y - 105, 200, 100);
			g.setColor(new Color(200, 200, 200, 170));
			g.drawRect(x - 200 + x_size, y - 105, 200, 100);
			g.setColor(Color.WHITE);
			g.setFont(TestState.titleFont);
			g.drawString(title, x - 195 + x_size, y - 80);
			g.setFont(TestState.descriptionFont);
			g.drawString(description, x - 195 + x_size, y - 60);
			g.setFont(TestState.titleFont);
			g.setColor(Color.YELLOW);
			g.drawString(hotkey, x + x_size - 20, y - 80);
			g.drawString("" + cost, x - 195 + x_size, y - 10);

		}
	}

}
