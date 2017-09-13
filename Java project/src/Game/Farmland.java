package Game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Farmland {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Farmland");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - GamePanel.WIDTH/2,dim.height/2 - GamePanel.HEIGHT/2);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	

}
