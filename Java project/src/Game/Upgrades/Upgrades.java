package Game.Upgrades;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entities.Plants.Plant;

public class Upgrades {

	private int currentUpgrade;
	private ArrayList<Upgrade> upgrades;
	private boolean fullyUpgraded;

	public Upgrades(int x, int y) {
		upgrades = new ArrayList<Upgrade>();
		currentUpgrade = 0;
		fullyUpgraded = false;
		//Upgrade: Cost, Name, Description, Icon name, x, y, Build speed bonus (0 - 1 = 0 - 100% of current), rewardbonus
		upgrades.add(new Upgrade(100, "Water", "The plants need watering wtf.", "/tempButton.jpg", x, y, 0.9, 1));
		upgrades.add(new Upgrade(200, "Tractor", "Amazing plowing bruh.", "/tempButton.jpg", x, y, 1, 2));
		upgrades.add(new Upgrade(400, "Scarecrow", "Keep the crows away bruh.", "/tempButton.jpg",x,y,0.8,1));
		upgrades.add(new Upgrade(800, "Valmet Valtra", "Gusten fo helvede", "/tempButton.jpg", x, y, 1, 3));
		upgrades.add(new Upgrade(1600, "Leos pågar", "Ut ur flyglarna!!", "/tempButton.jpg", x, y, 0.8, 2));
	}

	public void upgrade(ArrayList<Plant> plants) {
		if (!fullyUpgraded) {
			upgrades.get(currentUpgrade).upgrade(plants);
			currentUpgrade++;
		}
		if (currentUpgrade == upgrades.size()) {
			fullyUpgraded = true;
			currentUpgrade--;
		}
		
	}

	public boolean inBounds(Point p) {
		return upgrades.get(currentUpgrade).inBounds(p);
	}

	public boolean inBounds(int x, int y) {
		return upgrades.get(currentUpgrade).inBounds(x, y);
	}

	public void setHover(boolean hover) {
		upgrades.get(currentUpgrade).setHover(hover);
	}
	
	public boolean getFullyUpgraded() {
		return fullyUpgraded;
	}

	public int getCost() {
		return upgrades.get(currentUpgrade).getCost();
	}
	
	public double getBuildtimeBonus() {
		return upgrades.get(currentUpgrade).getBuildtimeBonus();
	}
	
	public double getRewardBonus() {
		return upgrades.get(currentUpgrade).getRewardBonus();
	}

	public void draw(Graphics2D g) {
		upgrades.get(currentUpgrade).draw(g);
	}

}
