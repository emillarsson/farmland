package Game.Upgrades;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entities.ImageButton;
import Entities.Plants.Plant;

public class Upgrade {
	
	private int cost;
	private double buildtimeBonus;
	private double rewardBonus;
	private String name;
	private String description;
	private ImageButton button;
	
	public Upgrade(int cost, String name, String description, String buttonImageName, int x, int y, double buildtimeBonus, double rewardBonus) {	
		this.cost = cost;
		this.name = name;
		this.description = description;
		this.button = new ImageButton(buttonImageName,x,y);
		this.button.setDescription(description);
		this.button.setTitle(name);
		this.buildtimeBonus = buildtimeBonus;
		this.rewardBonus = rewardBonus;
	}
	
	public void upgrade(ArrayList<Plant> plants) {
		for (int i = 0; i < plants.size(); i++) {
			plants.get(i).upgradeBuildTime(buildtimeBonus);
			plants.get(i).upgradeReward(rewardBonus);
		}
	}
	
	public boolean inBounds(Point p) {
		return button.inBounds(p);
	}
	
	public boolean inBounds(int x, int y) {
		return button.inBounds(x, y);
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getBuildtimeBonus() {
		return buildtimeBonus;
	}
	
	public double getRewardBonus() {
		return rewardBonus;
	}
	
	public void setHover(boolean hover) {
		button.setHover(hover);
	}
	
	public void draw(Graphics2D g) {
		button.setCost(cost);
		button.draw(g);
	}
	
}
