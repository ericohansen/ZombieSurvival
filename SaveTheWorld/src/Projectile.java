import java.awt.event.ActionEvent;


public class Projectile extends Character {

	private static int delay = 100;

	public Projectile(int x, int y, String filePath) {
		
		super(x, y, filePath, delay);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		y-=10;
	}	
}
