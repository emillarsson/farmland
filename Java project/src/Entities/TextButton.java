package Entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class TextButton {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private Rectangle bounds;
	private Color color;
	private String string;
	private boolean hover = false;

	public TextButton(Color color, String string, int x, int y, int x_size,
			int y_size) {
		this.string = string;
		this.color = color;
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
		this.bounds = new Rectangle(x, y, x_size, y_size);
	}

	public boolean inBounds(Point p) {
		return bounds.contains(p);
	}
	
	public void hover() {
		hover = true;
	}
	
	public void noHover() {
		hover = false;
	}

	public void draw(Graphics2D g) {
		g.setColor(new Color(255,255,255,40));
		g.fillRect(x, y, x_size, y_size);
		g.setColor(this.color);
		g.fillRect(x+5, y+5, x_size-10, y_size-10);
		if (hover) {
			g.setColor(new Color(255,255,255,40));
			g.fillRect(x, y, x_size, y_size);
		}
		g.setColor(Color.WHITE);
		FontMetrics f = g.getFontMetrics();
		Rectangle2D r = f.getStringBounds(string, g);
		g.drawString(string, (int) (x + x_size/2 - r.getWidth()/2), (int) (y + y_size/2 - r.getHeight()/2) + f.getAscent());
	}

	public boolean inBounds(int x, int y) {
		return bounds.contains(x, y);
	}

}
