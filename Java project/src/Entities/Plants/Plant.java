package Entities.Plants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Entities.ImageButton;
import Game.GamePanel;
import GameState.TestState;

public abstract class Plant {

	protected int x;
	protected int y;
	protected int size;
	protected int xmap;
	protected int ymap;
	protected Rectangle bounds;
	protected BufferedImage[] sprites;
	protected int currentBuildSprite = 0;
	protected double selectAnimation = 0;

	protected Color c;
	protected Color c_minimap;

	protected boolean delete = false;
	protected double currentTime = 0;
	protected boolean building = true;
	protected boolean built = false;
	protected boolean selected = false;
	protected boolean upgrade = false;

	protected double buildTime;
	protected int cost;
	protected double reward;
	protected String name;
	protected String title1;
	protected String title2;
	protected String title3 = "Scrap plant";
	protected String description1;
	protected String description2;
	protected String description3 = "Scrap plant without compensation";
	protected String hotkey1;
	protected String hotkey2;
	protected String hotkey3 = "S";
	protected int autoHarvestCost;
	
	public abstract Plant newPlant(int x, int y, double buildTimeBonus, double rewardBonus);

	public abstract String getName();

	public abstract double getReward();

	public abstract int getUpgradeCost();

	public abstract boolean getAutoHarvest();

	public abstract int getCost();

	public abstract String getTitle1();

	public abstract String getTitle2();

	public String getTitle3() {
		return title3;
	}

	public abstract String getHotkey1();

	public abstract String getHotkey2();

	public String getHotkey3() {
		return hotkey3;
	}

	public abstract String getDescription1();

	public abstract String getDescription2();

	public String getDescription3() {
		return description3;
	}

	public abstract double getGps();

	public abstract void built();

	public boolean inBounds(Point p) {
		Point p1 = new Point(p.x + TestState.x_offset, p.y + TestState.y_offset);
		return bounds.contains(p1);
	}

	public void update(boolean paused) {
		if (selected) {
			selectAnimation += 0.15;
			c_minimap = Color.GREEN;
		} else if (building && !built) {
			selectAnimation = 0;
			c_minimap = Color.RED;
		} else {
			selectAnimation = 0;
			c_minimap = Color.YELLOW;
		}
		if (!paused) {
			if (building) {
				currentTime += (int) TestState.elapsedTime;
			}
		}
		if (currentTime / buildTime > 0.25) {
			currentBuildSprite = 1;
		}
		if (currentTime / buildTime > 0.5) {
			currentBuildSprite = 2;
		}
		if (currentTime / buildTime > 0.75) {
			currentBuildSprite = 3;
		}
		if (currentTime >= buildTime) {
			building = false;
			built = true;
			built();
		}

		this.xmap = x - TestState.x_offset;
		this.ymap = y - TestState.y_offset;
	}

	public void harvest() {
		building = true;
		currentTime = 0;
		currentBuildSprite = 0;
	}

	public void mouseClicked(MouseEvent e) {
		this.selected = true;
	}

	public Color getColor() {
		return c;
	}

	public Color getMiniMapColor() {
		return c_minimap;
	}

	public boolean getBuilding() {
		return building;
	}

	public boolean getBuilt() {
		return built;
	}

	public void upgradeBuildTime(double percent) {
		buildTime *= percent;
	}

	public void upgradeReward(double percent) {
		reward *= percent;
	}

	public int getBuildingPercent() {
		return (int) (currentTime * 100 / buildTime);
	}

	public boolean getDelete() {
		return delete;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean outOfScreen() {
		return (this.xmap > GamePanel.WIDTH + size || this.xmap < -size
				|| this.ymap > GamePanel.HEIGHT + size || this.ymap < -size);
	}

	public void draw(Graphics2D g) {
		if (!outOfScreen()) {
			if (building) {
				g.drawImage(sprites[currentBuildSprite], xmap, ymap, size,
						size, null);
				g.setColor(Color.BLACK);
				g.fillRect(xmap, ymap, 40, 5);
				g.setColor(Color.GREEN);
				g.fillRect(xmap + 1, ymap + 1,
						(int) (currentTime / buildTime * 40) - 2, 5 - 2);
			} else {
				g.drawImage(sprites[currentBuildSprite], xmap, ymap, size,
						size, null);
			}
			if (selected) {
				Graphics2D g2d = (Graphics2D) g.create();
				Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_BEVEL, 0, new float[] { 4 },
						(int) selectAnimation);
				g2d.setStroke(dashed);
				g2d.setColor(Color.WHITE);
				g2d.drawRect(xmap, ymap, size - 1, size - 1);
				g2d.dispose();
			}

		}
	}

	public void upgrade() {
		upgrade = true;
	}

	public boolean getUpgrade() {
		return upgrade;
	}

}
