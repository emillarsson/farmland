package Game;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, MouseMotionListener,
		KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 900;
	public static int HEIGHT = 640;

	public static long targetTime = 1000 / 60;

	private Cursor shiftCursor;
	private Image cursorImage;
	private BufferedImage image;
	private Thread thread;
	private boolean running = false;
	private Graphics2D g;

	private GameStateManager gsm;

	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();

	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void setScreen() {
		Dimension dim = this.getSize();
		WIDTH = dim.width;
		HEIGHT = dim.height;
		this.setPreferredSize(dim);
	}

	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		running = true;
		gsm = new GameStateManager(this);
		Point cursorPoint = new Point(0,0);
		try {
			cursorImage = ImageIO.read(getClass().getResourceAsStream("/Coin.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.shiftCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, cursorPoint, null);

		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		while (running) {

			start = System.nanoTime();
			setScreen();
			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;

			if (wait < 0) {
				wait = 0;
			}

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		gsm.update();
	}

	public void draw() {
		gsm.draw(g);
	}

	public void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	public Point getPositionOnScreen() {
		return this.getPositionOnScreen();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		gsm.mouseDragged(e);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// gsm.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gsm.mouseClicked(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		gsm.mouseEntered();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (this.hasFocus()) {
			gsm.mouseExited();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			// setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			setCursor(shiftCursor);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			setCursor(Cursor.getDefaultCursor());
		}

	}

}
