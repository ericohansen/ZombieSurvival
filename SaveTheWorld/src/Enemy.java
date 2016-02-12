import java.awt.event.ActionEvent;
import java.util.Random;
/*
 * This class just allows us to create multiple characters
 */
public class Enemy extends Character {

	private Random myRand = new Random();
	
	
	public Enemy(int x, int y, String filePath, int delay) {
	
		super(x,y,filePath, delay);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		y+=10;
		
	}

}
