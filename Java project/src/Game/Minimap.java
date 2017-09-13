package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entities.Plants.Plant;
import Entities.Plants.Potato;
import GameState.TestState;

public class Minimap {

	private ArrayList<Plant> plants;
	private int x = (int) (GamePanel.WIDTH / 5);
	private int y = (int) (GamePanel.HEIGHT / 5);
	private int x_camera;
	private int y_camera; 
	private double dx;
	private double dy;
	private int x_offset;
	private int y_offset;
	private Rectangle bounds;
	private Image image;

	public Minimap(int x, int y, String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
		image = image.getScaledInstance(x, y, image.SCALE_DEFAULT);
		bounds = new Rectangle(10,GamePanel.HEIGHT - this.y - 10, this.x, this.y);
		this.dx = (double) this.x / x;
		this.dy = (double) this.y / y;
		x_camera = (int)(this.x * ((double)GamePanel.WIDTH/x));
		y_camera = (int)(this.y * ((double)GamePanel.HEIGHT/y));
	}

	public void update(ArrayList<Plant> plants, int x_offset, int y_offset) {
		this.plants = plants;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
	}
	
	public boolean inBounds(Point p) {
		return bounds.contains(p);
	}
	
	public double[] getRate(Point p)	{
		double x_new = p.x - 10;
		double y_new = p.y - (GamePanel.HEIGHT - this.y - 10);
		double x_rate = x_new/x;
		double y_rate = y_new/y;
		return new double[] {x_rate, y_rate};
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, 10, GamePanel.HEIGHT - y - 10, x, y, null);
		
		for (int i = 0; i < plants.size(); i++) {
			g.setColor(plants.get(i).getMiniMapColor());
			g.fillOval((int) (10 + plants.get(i).getX() * dx),
					(int) ((GamePanel.HEIGHT - y - 10) + plants.get(i).getY()
							* dy), 3, 3);
		}
		g.setColor(Color.DARK_GRAY);
		g.drawRect((int) (10 + (x_offset * dx)),
				(int) ((GamePanel.HEIGHT - y - 10) + y_offset * dy), x_camera, y_camera);
		g.setColor(Color.BLACK);
		g.drawRect(10, GamePanel.HEIGHT - y - 10, x-1, y-1);
	}

}
