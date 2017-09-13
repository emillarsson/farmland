package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entities.Plants.Plant;
import Game.GamePanel;
import Game.Upgrades.Upgrades;
import GameState.TestState;

public class HUD {

	private BufferedImage image;
	private Rectangle boundsHUD;
	private Rectangle boundsSelected;
	private int x = (int) (GamePanel.WIDTH / 5);
	private int y = (int) (GamePanel.HEIGHT / 5);
	public int selectedX = GamePanel.WIDTH - this.x - 10;
	public int selectedY = GamePanel.HEIGHT - this.y - 10;
	public ImageButton button3 = new ImageButton("/SellButton.jpg", (int)(GamePanel.WIDTH - 20), GamePanel.HEIGHT - 20);
	public ImageButton button2 = new ImageButton("/tempButton.jpg", (int)(GamePanel.WIDTH + 10 - x/2), GamePanel.HEIGHT - 20);
	public ImageButton button1 = new ImageButton("/tempButton.jpg", (int)(GamePanel.WIDTH + 40 - x), GamePanel.HEIGHT - 20);
	public ImageButton stackButton = new ImageButton("/tempButton.jpg", selectedX - 10, GamePanel.HEIGHT - 10);
	public Upgrades upgrades;
	
	public HUD(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.boundsSelected = new Rectangle(selectedX, selectedY, this.x, this.y);
		this.boundsHUD = new Rectangle(0, GamePanel.HEIGHT - image.getHeight(), GamePanel.WIDTH, image.getHeight());
		this.stackButton.setTitle("Upgrade stack");
		this.stackButton.setDescription("Buy 5 more stacks");
		this.upgrades = new Upgrades(selectedX - 10, GamePanel.HEIGHT - 60);
	}

	public boolean inBounds(Point p) {
		return boundsHUD.contains(p) || boundsSelected.contains(p);
	}
	
	public void hoverButton(int x, int y) {
		if (button1.inBounds(x, y)) {
			button1.setHover(true);
		} else {
			button1.setHover(false);
		}
		if (button2.inBounds(x, y)) {
			button2.setHover(true);
		} else {
			button2.setHover(false);
		}
		if (button3.inBounds(x, y)) {
			button3.setHover(true);
		} else {
			button3.setHover(false);
		}
		if (stackButton.inBounds(x, y)) {
			stackButton.setHover(true); 
		} else {
			stackButton.setHover(false);
		}
		if (upgrades.inBounds(x, y)) {
			upgrades.setHover(true);
		} else {
			upgrades.setHover(false);
		}
	}
	
	public int getX() {
		return boundsHUD.width;
	}
	
	public int getY() {
		return boundsHUD.height;
	}

	public void draw(Graphics2D g, Plant plant) {
		//information square
		g.drawImage(image, 0, GamePanel.HEIGHT - image.getHeight(),
				GamePanel.WIDTH, image.getHeight(), null);
		g.setColor(Color.BLACK);
		g.fillRect(boundsSelected.x, boundsSelected.y, boundsSelected.width,
				boundsSelected.height);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(boundsSelected.x, boundsSelected.y, boundsSelected.width,
				boundsSelected.height);
		
		upgrades.draw(g);
		stackButton.setCost(-TestState.stackCost);
		stackButton.draw(g);
		//plant information
		if (plant != null) {
			g.setColor(Color.WHITE);
			g.drawString(plant.getName(), boundsSelected.x + 10,
					boundsSelected.y + 30);
			if (plant.getBuilding() && !plant.getBuilt()) {
				g.drawString("" + plant.getBuildingPercent() + "%", boundsSelected.x + 10, boundsSelected.y + 60);
			} else {
				//formatting for two decimals
			g.drawString("GPS: " + String.format("%.2f", (double) Math.round(plant.getGps() * 10) / 10) , boundsSelected.x + 10,
					boundsSelected.y + 60);
			}
			
			button1.setTitle(plant.getTitle1());
			button1.setDescription(plant.getDescription1());
			button1.setHotkey(plant.getHotkey1());
			button1.setCost((int)+plant.getReward());
			button1.draw(g);
			button2.setTitle(plant.getTitle2());
			button2.setDescription(plant.getDescription2());
			button2.setHotkey(plant.getHotkey2());
			button2.setCost(-plant.getUpgradeCost());
			button2.draw(g);
			button3.setTitle(plant.getTitle3());
			button3.setDescription(plant.getDescription3());
			button3.setHotkey(plant.getHotkey3());
			button3.draw(g);
			
		}
		
		
	}

}
