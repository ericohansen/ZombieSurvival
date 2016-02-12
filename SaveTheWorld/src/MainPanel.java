import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements ActionListener, KeyListener {

	int markerX, markerY;

	String waiterFile = "goodship.png";
	String projectileFile = "fireball.png";
	String[] enemyShipFiles = { "enemyship1.png", "enemyship2.png",
			"enemyship3.png" };

	ArrayList<Enemy> ships = new ArrayList<Enemy>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	Timer updateTimer = new Timer((int) (1000 / 30), this);
	int level = 1;
	// enemy timers
	Timer myEnemyWaveTimer = new Timer((int) (5000), this);
	int wave = 1;
	int score = 0;
	int lives = 3;
	int scoreX = 50;
	int scoreY = 775;
	int livesX = 150;
	int livesY = 775;
	int levelX = 250;
	int levelY = 775;
	int regularFontSize = 12;
	int largeFontSize = 120;
	int goodGuyX = 600;
	String gameOver = "";
	final int RECSIZE = 75;
	final int WIDTH = 825;
	final int HEIGHT = 800;
	int gameOverX = 100;
	int gameOverY = WIDTH / 2;

	final int enemyMultiplier = 1;
	final int OFFSET = 10;
	final int BACKGROUNDX = 185;
	final int BACKGROUNDY = 100;
	final int SPRITEWIDTH = 50;
	final int SPRITEHEIGHT = 63;
	final int GOODSHIPOFFSET = 25;
	final int ENEMYOFFSET = 75;
	final int THRESHOLD = 600;
	GoodGuy myGoodGuy = null;

	// constructor
	public MainPanel() {

		myGoodGuy = new GoodGuy(goodGuyX, 600, waiterFile);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		setBackground(Color.gray);
		
		addKeyListener(this);
		setFocusable(true);
		updateTimer.start();

		createEnemies();

	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);

		
		page.drawImage(
				new ImageIcon("./src/images/starsbackground.jpg").getImage(),
				0, 0, null);
		
		page.drawImage(myGoodGuy.getCharacterIcon().getImage(),
				myGoodGuy.getX(), myGoodGuy.getY(), null);
		

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles
					.get(i)

					.getCharacterIcon()
					.paintIcon(this, page, projectiles.get(i).getX(),
							projectiles.get(i).getY());

			if (projectiles.get(i).getX() < 0) {
				endGame();
			}
		}

		for (int i = 0; i < ships.size(); i++) {
			ships.get(i)
					.getCharacterIcon()
					.paintIcon(this, page, ships.get(i).getX(),
							ships.get(i).getY());

			page.setColor(Color.BLACK);
			if (ships.get(i).getY() > THRESHOLD) {
				ships.remove(i);
				lives--;
				if (lives <= 0) {
					endGame();
				}
			}

		}
		
		page.setColor(Color.WHITE);

		page.setFont(new Font("Tahoma", Font.PLAIN, regularFontSize));
		page.drawString("Score: " + score, scoreX, scoreY);

		page.drawString("Lives: " + lives, livesX, livesY);

		page.drawString("Level: " + level, levelX, levelY);
		page.setFont(new Font("Tahoma", Font.PLAIN, largeFontSize));

		page.drawString(gameOver, gameOverX, gameOverY);

	}

	public void endGame() {
		for (int i = 0; i < ships.size(); i++)
			ships.get(i).getTimer().stop();

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).getTimer().stop();
		}

		myEnemyWaveTimer.stop();
		updateTimer.stop();

		gameOver = "GAME OVER";
		setFocusable(false);
	}

	public void collides() {
		int i = 0;

		for (i = 0; i < projectiles.size(); i++) {

			int topLeftX1 = projectiles.get(i).getX();
			int bottomRightX1 = projectiles.get(i).getX() + GOODSHIPOFFSET;
			int topLeftY1 = projectiles.get(i).getY();
			int bottomRightY1 = projectiles.get(i).getY() + GOODSHIPOFFSET;

			for (int j = 0; j < ships.size(); j++) {
				int topLeftX = ships.get(j).getX();
				int bottomRightX = ships.get(j).getX() + ENEMYOFFSET;
				int topLeftY = ships.get(j).getY();
				int bottomRightY = ships.get(j).getY() + ENEMYOFFSET;

				if (areRectsColliding(topLeftX, bottomRightX, topLeftY,
						bottomRightY, topLeftX1, bottomRightX1, topLeftY1,
						bottomRightY1)) {

					if (i < projectiles.size()) {
						projectiles.remove(i);
					}
					
					playExplosion();
					ships.remove(j);
					score++;
					if (ships.size() <= 0) {
						level++;
						createEnemies();
						myEnemyWaveTimer.start();
					}

				}// end collision if

			}

		}

	}

	public void createEnemies() {
		Random myRand = new Random();

		int numberOfEnemies = myRand.nextInt(3) + 1;
		int yLocation = 10;

		for (int i = 0; i < numberOfEnemies; i++) {
			int delay = myRand.nextInt(1000) + 75;

			int position = myRand.nextInt(WIDTH);

			if (position >= WIDTH - 128) {
				position = WIDTH - 100;
			}
			ships.add(new Enemy(position, yLocation + ENEMYOFFSET,
					enemyShipFiles[myRand.nextInt(enemyShipFiles.length)],
					delay));

		}

		for (int i = 0; i < ships.size(); i++) {

			ships.get(i).getTimer().start();
		}
	}

	private boolean areRectsColliding(int r1TopLeftX, int r1BottomRightX,
			int r1TopLeftY, int r1BottomRightY, int r2TopLeftX,
			int r2BottomRightX, int r2TopLeftY, int r2BottomRightY) {

		if (r1TopLeftX < r2BottomRightX && r1BottomRightX > r2TopLeftX
				&& r1TopLeftY < r2BottomRightY && r1BottomRightY > r2TopLeftY) {
			return true;
		} else {
			return false;
		}
	}

	public void playExplosion() {
		try {
			AudioClip clip = Applet.newAudioClip(new URL(
					"file:./src/media/explosion.wav"));
			clip.play();
		} catch (MalformedURLException murle) {
			System.out.println(murle);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT && goodGuyX <= WIDTH - 110) {
			goodGuyX += 25;

		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT && goodGuyX >= 0 + 10) {
			goodGuyX -= 25;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			projectiles.add(new Projectile(myGoodGuy.getX() + 40, myGoodGuy
					.getY() - 10, projectileFile));

			projectiles.get(projectiles.size() - 1).getTimer().start();

		}

		myGoodGuy.setX(goodGuyX);

		repaint();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myEnemyWaveTimer) {

			if (wave == level) {
				myEnemyWaveTimer.stop();
				wave = 0;
			} else {
				createEnemies();
			}
			wave++;
		} else if (e.getSource() == updateTimer) {
			if (ships.size() == 0) {
				createEnemies();
			}
			collides();

		}
		repaint();

	}

}
