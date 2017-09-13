package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import Entities.TextButton;
import Entities.HUD;
import Entities.Plants.Plant;
import Entities.Plants.Potato;
import Game.Background;
import Game.GamePanel;
import Game.Minimap;
import Game.Time;

public class TestState extends GameState implements MouseInputListener {

	private static int XMAP = 2200 - GamePanel.WIDTH;
	private static int YMAP = 1480 - GamePanel.HEIGHT;
	public static int x_offset = 0;
	public static int y_offset = 0;

	private ArrayList<Plant> plants;
	private ArrayList<Plant> plantTypes;
	private Plant currentPlant;
	private Minimap minimap;
	public static Font titleFont = new Font("Century Gothic", Font.PLAIN, 18);
	public static Font descriptionFont = new Font("Century Gothic", Font.PLAIN, 12);
	private HUD hud;
	private TextButton optionsButton;
	private TextButton exitButton;
	private Time time;
	private double gps;
	private String gpsString;
	private double lastTime = System.currentTimeMillis();
	public static double elapsedTime;

	private int currentlySelected;
	public static int stackCost;

	public double buildtimeBonus = 1;
	public double rewardBonus = 1;

	public boolean mouseExited;
	public boolean paused;
	public boolean hudEnabled = true;
	public boolean shift = false;

	public TestState(GameStateManager gsm, GamePanel gp) {
		this.gp = gp;
		this.gsm = gsm;

		// Get visuals (TIME DEMANDING)
		this.bg = new Background("/Green.jpg");
		this.hud = new HUD("/HUD.jpg");
		this.minimap = new Minimap(XMAP + GamePanel.WIDTH, YMAP + GamePanel.HEIGHT, "/Green.jpg");

	}
	
	public void init() {
		this.gps = 0;
		gold = 1;
		this.stack = 5;
		stackCost = 10;
		this.currentStack = 0;
		this.currentlySelected = -1;
		this.plants = new ArrayList<Plant>();
		this.plantTypes = new ArrayList<Plant>();
		plantTypes.add(new Potato(-1000, -1000, buildtimeBonus, rewardBonus));
		this.currentPlant = plantTypes.get(0);

		this.paused = false;
		this.mouseExited = false;

		optionsButton = new TextButton(Color.BLACK, "Options",
				GamePanel.WIDTH / 2 - 90, GamePanel.HEIGHT / 2 - 50, 180, 80);
		exitButton = new TextButton(Color.BLACK, "Exit",
				GamePanel.WIDTH / 2 - 90, GamePanel.HEIGHT / 2 + 60, 180, 80);

		if (time != null) {
			time.reset();
		}
		time = new Time(100, 40);
		time.start();
	}

	@Override
	public void update() {

		gps = 0;
		if (gp.hasFocus()) {
			updateScreen();
		}

		if (x_offset < 0) {
			x_offset = 0;
		}
		if (x_offset > XMAP) {
			x_offset = XMAP;
		}
		if (y_offset < 0) {
			y_offset = 0;
		}
		if (y_offset > YMAP) {
			y_offset = YMAP;
		}
		elapsedTime = System.currentTimeMillis() - lastTime;
		for (int i = 0; i < plants.size(); i++) {
			if (plants.get(i).getDelete()) {
				plants.remove(i);
				currentStack--;
				i--;
			} else if (plants.get(i).getAutoHarvest()
					&& !plants.get(i).getBuilding()) {
				plants.get(i).harvest();
				gold += plants.get(i).getReward();

			} else {
				plants.get(i).update(paused);
			}
			gps += plants.get(i).getGps();
		}
		minimap.update(plants, x_offset, y_offset);
		bg.setPosition(x_offset, y_offset);

		/*
		 * if (!paused) { gold += gps * elapsedTime / 1000; }
		 */
		lastTime = System.currentTimeMillis();
	}

	private void updateScreen() {
		Point p1 = MouseInfo.getPointerInfo().getLocation();
		Point p2 = gp.getLocationOnScreen();
		int x = p1.x - p2.x;
		int y = p1.y - p2.y;
		hud.hoverButton(x, y);
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x > GamePanel.WIDTH) {
			x = GamePanel.WIDTH;
		}
		if (y > GamePanel.HEIGHT) {
			y = GamePanel.HEIGHT;
		}
		if (x == 0) {
			x_offset -= 10;
		}
		if (x == GamePanel.WIDTH) {
			x_offset += 10;
		}
		if (y == 0) {
			y_offset -= 10;
		}
		if (y == GamePanel.HEIGHT) {
			y_offset += 10;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseExited() {
		mouseExited = true;
	}

	@Override
	public void mouseEntered() {
		mouseExited = false;
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public void upgradeStack() {
		stack += 5;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (paused) {
			if (exitButton.inBounds(e.getPoint())) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
		} else {
			// Minimap clicked
			if (minimap.inBounds(e.getPoint()) && hudEnabled) {
				double[] points = minimap.getRate(e.getPoint());
				x_offset = (int) (points[0] * (XMAP + GamePanel.WIDTH) - GamePanel.WIDTH / 2);
				y_offset = (int) (points[1] * (YMAP + GamePanel.HEIGHT) - GamePanel.HEIGHT / 2);
			}
			// Hud clicked
			else if (hud.inBounds(e.getPoint()) && hudEnabled) {
				// Delete button
				if (hud.button3.inBounds(e.getPoint())) {
					plants.get(currentlySelected).setDelete(true);
					currentlySelected = -1;
				}
				// Upgrade button
				else if (hud.button2.inBounds(e.getPoint())
						&& gold >= plants.get(currentlySelected)
								.getUpgradeCost()
						&& !plants.get(currentlySelected).getUpgrade()) {

					gold -= plants.get(currentlySelected).getUpgradeCost();
					plants.get(currentlySelected).upgrade();
				}
				// Harvest Button
				else if (hud.button1.inBounds(e.getPoint())
						&& !plants.get(currentlySelected).getBuilding()) {

					harvest();
				} else if (hud.stackButton.inBounds(e.getPoint())
						&& gold >= stackCost) {
					stack += 5;
					gold -= stackCost;
					stackCost *= 2;
				} else if (hud.upgrades.inBounds(e.getPoint())
						&& gold >= hud.upgrades.getCost()
						&& !hud.upgrades.getFullyUpgraded()) {
					gold -= hud.upgrades.getCost();
					buildtimeBonus *= hud.upgrades.getBuildtimeBonus();
					rewardBonus *= hud.upgrades.getRewardBonus();
					hud.upgrades.upgrade(plants);
				}
			}
			
			// Map clicked
			
			else {
				boolean point = true;
				for (int i = 0; i < plants.size(); i++) {
					if (plants.get(i).inBounds(e.getPoint())) {
						if (currentlySelected != -1) {
							plants.get(currentlySelected).setSelected(false);
						}
						
						plants.get(i).mouseClicked(e);
						currentlySelected = i;
						if (shift) {
							harvest();
						}
						point = false;
						break;
					}
				}
				if (point && !paused) {
					Point p = e.getPoint();
					int x_snap = p.x + x_offset - ((p.x + x_offset) % 40);
					int y_snap = p.y + y_offset - ((p.y + y_offset) % 40);
					newPlant(x_snap,y_snap);
				}
			}
		}
	}
	
	public void newPlant(int x, int y) {
		if (currentStack < stack && gold >= currentPlant.getCost()) {
			gold -= currentPlant.getCost();
			currentStack++;
			plants.add(currentPlant.newPlant(x,y,buildtimeBonus,rewardBonus));
			if (currentlySelected != -1) {
				plants.get(currentlySelected).setSelected(false);
			}
			currentlySelected = plants.size() - 1;
			plants.get(currentlySelected).setSelected(true);
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (minimap.inBounds(e.getPoint())) {
			double[] points = minimap.getRate(e.getPoint());
			x_offset = (int) (points[0] * (XMAP + GamePanel.WIDTH) - GamePanel.WIDTH / 2);
			y_offset = (int) (points[1] * (YMAP + GamePanel.HEIGHT) - GamePanel.HEIGHT / 2);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			if (currentlySelected == -1) {
				time.keyPressed(k);
				if (!paused) {
					pause();
				} else {
					resume();
				}

			} else {
				plants.get(currentlySelected).setSelected(false);
				currentlySelected = -1;
			}
		}
		if (k == KeyEvent.VK_K) {
			this.stack += 200000;
			gold += 2000000;
		}
		if (k == KeyEvent.VK_U) {
			if (gold >= plants.get(currentlySelected).getUpgradeCost()
					&& !plants.get(currentlySelected).getUpgrade()) {
				gold -= plants.get(currentlySelected).getUpgradeCost();
				plants.get(currentlySelected).upgrade();
			}
		}
		if (currentlySelected != -1) {
			if (k == KeyEvent.VK_H) {
				harvest();
			}
		}
		if (k == KeyEvent.VK_E) {
			if (hudEnabled) {
				hudEnabled = false;
			} else {
				hudEnabled = true;
			}
		}
		if (k == KeyEvent.VK_SHIFT) {
			shift = true;
		}
	}

	@Override
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_SHIFT) {
			shift = false;
		}

	}

	public void harvest() {
		if (!plants.get(currentlySelected).getBuilding()) {
			plants.get(currentlySelected).harvest();
			gold += plants.get(currentlySelected).getReward();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		for (int i = 0; i < plants.size(); i++) {
			plants.get(i).draw(g);
		}
		if (hudEnabled) {
			if (plants.size() == 0 || currentlySelected == -1) {
				hud.draw(g, null);
			} else {
				hud.draw(g, plants.get(currentlySelected));
			}
			minimap.draw(g);
			time.draw(g);

			g.setColor(Color.WHITE);
			g.setFont(titleFont);
			g.drawString("" + (long) gold + " gold", minimap.getX() + 20,
					GamePanel.HEIGHT - (int) (0.75 * hud.getY()));
			g.drawString("" + currentStack + "/" + stack + " stacks",
					minimap.getX() + 20,
					GamePanel.HEIGHT - (int) (0.5 * hud.getY()));

			gps = (double) Math.round(gps * 10) / 10;
			gpsString = String.format("%.1f", gps);
			g.drawString(gpsString + " gold per second", minimap.getX() + 20,
					GamePanel.HEIGHT - (int) (0.25 * hud.getY()));
		}
		if (paused) {
			Color color = new Color(0, 0, 0, 128);
			g.setColor(color);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(GamePanel.WIDTH / 2 - 100, GamePanel.HEIGHT / 2 - 150,
					200, 300);
			g.setColor(Color.WHITE);
			g.drawString("MENU", GamePanel.WIDTH / 2 - 35,
					GamePanel.HEIGHT / 2 - 100);
			exitButton.draw(g);
			optionsButton.draw(g);
		}

	}

}
