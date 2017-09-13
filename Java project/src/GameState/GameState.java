package GameState;

import java.awt.event.MouseEvent;

import Game.Background;
import Game.GamePanel;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected GamePanel gp;
	protected Background bg;
	
	protected static double gold;
	protected int stack;
	protected int currentStack;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseExited();
	public abstract void mouseEntered();
	public abstract void mouseClicked(MouseEvent e);
	public abstract void pause();
	public abstract void resume();
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseDragged(MouseEvent e);
	

}
