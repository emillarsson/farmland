package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Entities.TextButton;
import Game.Background;
import Game.GamePanel;

public class MenuState extends GameState {

	private Font titleFont = new Font("Century Gothic", Font.BOLD, 40);
	private Font textFont = new Font("Arial", Font.PLAIN, 24);
	private int currentChoice = 0;
	private ArrayList<TextButton> buttons;
	private String help = "Plant your plants, collect profit. Press ESC to return.";
	private boolean helpActive = false;

	public MenuState(GameStateManager gsm, GamePanel gp) {
		this.gsm = gsm;
		this.gp = gp;
	}

	@Override
	public void init() {
		this.bg = new Background("/Green.jpg");
		this.buttons = new ArrayList<TextButton>();
		buttons.add(new TextButton(Color.BLACK, "New Game",
				(int) (GamePanel.WIDTH / 2 - 75),
				(int) (GamePanel.HEIGHT * 2 / 8), 150, 75));
		buttons.add(new TextButton(Color.BLACK, "Options",
				(int) (GamePanel.WIDTH / 2 - 75),
				(int) (GamePanel.HEIGHT * 4 / 8), 150, 75));
		buttons.add(new TextButton(Color.BLACK, "Quit",
				(int) (GamePanel.WIDTH / 2 - 75),
				(int) (GamePanel.HEIGHT * 6 / 8), 150, 75));
	}

	@Override
	public void update() {
		Point p1 = MouseInfo.getPointerInfo().getLocation();
		Point p2 = gp.getLocationOnScreen();
		int x = p1.x - p2.x;
		int y = p1.y - p2.y;
		hoverButton(x, y);

	}

	public void pause() {

	}

	public void resume() {

	}
	
	public void hoverButton(int x, int y) {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).inBounds(x,y)) {
				buttons.get(i).hover();
				currentChoice = i;
			} else {
				buttons.get(i).noHover();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!helpActive) {
			if (buttons.get(0).inBounds(e.getPoint())) {
				// new game
				gsm.setState(GameStateManager.TESTSTATE);
			}
			if (buttons.get(1).inBounds(e.getPoint())) {
				// help
				helpActive = true;
			}
			if (buttons.get(2).inBounds(e.getPoint())) {
				// Quit
				System.exit(0);
			}
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE && helpActive) {
			helpActive = false;
		}
	}
	
	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.GREEN);
		FontMetrics f = g.getFontMetrics();
		Rectangle2D r = f.getStringBounds("Farmland", g);
		g.drawString("Farmland",
				(int) (GamePanel.WIDTH / 2 - r.getWidth() / 2),
				(int) (GamePanel.HEIGHT / 8));
		g.setFont(textFont);
		if (helpActive) {
			g.setColor(Color.WHITE);
			g.drawString(help, 100, 300);
		} else {
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).draw(g);
			}
		}
	}
}
