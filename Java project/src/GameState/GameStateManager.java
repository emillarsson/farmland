package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Game.GamePanel;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState;
	private GamePanel gp;
	
	public static final int MENUSTATE = 0;
	public static final int TESTSTATE = 1;
	public static final int LEVEL1STATE = 2;
	
	
	public GameStateManager(GamePanel gp) {
		this.gp = gp;
		init();
	}
	
	public void init() {
		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this, gp));
		gameStates.add(new TestState(this, gp));
		gameStates.get(MENUSTATE).init();
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void pause() {
		gameStates.get(currentState).pause();
	}
	
	public void resume() {
		gameStates.get(currentState).resume();
	}
	
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}

	public void mouseMoved(MouseEvent e) {
		gameStates.get(currentState).mouseMoved(e);
	}

	public void mouseExited() {
		gameStates.get(currentState).mouseExited();
	}

	public void mouseEntered() {
		gameStates.get(currentState).mouseEntered();
	}

	public void mouseClicked(MouseEvent e) {
		gameStates.get(currentState).mouseClicked(e);		
	}

	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
		
	}

	public void mouseDragged(MouseEvent e) {
		gameStates.get(currentState).mouseDragged(e);
		
	}

	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
}
