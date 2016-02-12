import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class GoodGuy extends Character{

	private int ninjaFrameNumber = 5;
	private static int delay = (int)1000/3;
	
	public GoodGuy(int x, int y, String filePath)
	{
		super(x,y,filePath, delay);
	
	}

	public void startTimer()
	{
		Timer myTimer = getTimer();
		myTimer.start();
	}
	public int getNinjaFrameNumber()
	{
		return ninjaFrameNumber;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ninjaFrameNumber++;
		if(ninjaFrameNumber > 10)
		{
			ninjaFrameNumber = 5;
		}
	
	}

	


}
