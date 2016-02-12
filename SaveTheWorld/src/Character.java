/*
 * Michael Cassens
 * This abstract class allows you to create a character
 * each character has it's own action listener
 */
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;


public abstract class Character implements ActionListener {
	protected int x;
	protected int y;
	protected String filePath;
	protected ImageIcon characterIcon;
	protected int DELAY;
	private Timer myTimer;
	
	public Character(int x, int y, String filePath, int delay)
	{
		this.x = x;
		this.y = y;
		this.filePath = filePath;
		setImageIcon();
		this.DELAY = delay;
		myTimer = new Timer(DELAY, this);
		
	}
	
	private void setImageIcon()
	{
		characterIcon = new ImageIcon("./src/images/" + filePath);
	}

	public ImageIcon getCharacterIcon()
	{
		return characterIcon;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getFilePath() {
		return filePath;
	}
	
	
	public Timer getTimer()
	{
		return myTimer;
	}

}
