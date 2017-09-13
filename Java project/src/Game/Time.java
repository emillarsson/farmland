package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Time implements Runnable {

	private Thread time;

	private long first;
	private long wait;
	private long elapsed;
	private long second = 1000;
	
	private int x;
	private int y;

	private boolean paused = false;
	private boolean running = false;
	
	private Color titleColor = Color.WHITE;
	private Font titleFont = new Font("Century Gothic", Font.PLAIN, 28);

	public static int SECONDS = 0;
	public static int MINUTES = 0;
	public static int HOURS = 0;

	private String seconds;
	private String minutes;
	private String hours;

	public Time(int x, int y) {
		this.x = x;
		this.y = y;
		running = true;
	}

	public void run() {
		while (running) {
			if (!paused) {
				first = System.nanoTime();
				SECONDS++;
				if (SECONDS == 60) {
					MINUTES++;
					if (MINUTES == 60) {
						HOURS++;
						MINUTES = 0;
					}
					SECONDS = 0;
				}

				elapsed = System.nanoTime() - first;
				wait = second - elapsed / 1000000;
				try {
					time.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void start() {
		if (time == null) {
			time = new Thread(this);
			time.start();
		}
	}
	
	public void reset() {
		if (time != null) {
			running = false;
			time = null;
			SECONDS = 0;
			MINUTES = 0;
			HOURS = 0;
		}
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public void draw(Graphics2D g) {
		if (SECONDS < 10) {
			seconds = "0" + SECONDS;
		} else {
			seconds = "" + SECONDS;
		}
		if (MINUTES < 10) {
			minutes = "0" + MINUTES;
		} else {
			minutes = "" + MINUTES;
		}
		if (HOURS < 10) {
			hours = "0" + HOURS;
		} else {
			hours = "" + HOURS;
		}
		String stringTime = "" + hours + ":" + minutes + ":" + seconds;
		g.setFont(titleFont);
		g.setColor(titleColor);
		g.drawString(stringTime, x, y);
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ESCAPE) {
			if (paused) {
				resume();
			} else {
				pause();
			}
		}
	}

}
