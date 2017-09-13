package Entities.Plants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import Game.GamePanel;
import GameState.TestState;

public class Potato extends Plant {

	public Potato(int x, int y, double buildTimeBonus, double rewardBonus) {
		
		this.x = x;
		this.y = y;
		this.size = 40;
		this.name = "Potatoplant";
		this.title1 = "Harvest";
		this.description1 = "PO-TA-TO";
		this.hotkey1 = "H";
		this.title2 = "Auto-harvest";
		this.description2 = "Too lazy yeah?";
		this.hotkey2 = "U";
		this.cost = 1;
		this.reward = 4 * rewardBonus;
		this.autoHarvestCost = 50;
		this.buildTime = 10000 * buildTimeBonus; //milliseconds
		this.c_minimap = Color.RED;
		this.bounds = new Rectangle(x, y, size, size);
		
		try {

			BufferedImage spridesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Plants/potatoes.jpg"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spridesheet.getSubimage(i * size, 0, size, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Plant newPlant(int x, int y, double buildTimeBonus, double rewardBonus) {
		return newPotato(x,y,buildTimeBonus,rewardBonus);
	}
	
	public Potato newPotato(int x, int y, double buildTimeBonus, double rewardBonus) {
		return new Potato(x,y,buildTimeBonus,rewardBonus);
	}
	
	public String getTitle1() {
		return title1;
	}
	
	public String getTitle2() {
		return title2;
	}
	
	public String getDescription1() {
		return description1;
	}
	
	public String getDescription2() {
		return description2;
	}
	
	public String getHotkey1() {
		return hotkey1;
	}
	
	public String getHotkey2() {
		return hotkey2;
	}

	public int getCost() {
		return cost;
	}

	public int getUpgradeCost() {
		return autoHarvestCost;
	}
	
	public boolean getAutoHarvest() {
		return upgrade;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getReward() {
		return reward;
	}

	@Override
	public double getGps() {
		if (upgrade) {
			return 1000*reward/buildTime;
		} else {
			return 0;
		}
	}

	@Override
	public void built() {
		// TODO Auto-generated method stub
		
	}

}
