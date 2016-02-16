package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player{
    //Constants
    final int MOVESPEED = 5;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    //private static Background bg1 = GamePanel.bg;

    //Player image information
    private Bitmap spritesheet;
    private Animation animation = new Animation();
    private long startTime;

    public int getScore() {
        return score;
    }

    private int score;

    private int x;
    private int y;
    private int height;
    private int width;
    private int speedX = 0;
    private int speedY = 0;

    public Player(Bitmap res, int w, int h, int numFrames) {
        spritesheet = res;
        x = GamePanel.screenWidth/2;
        y = GamePanel.screenHeight/2;
        speedX = 0;
        speedY = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void update() {
        //long elapsed = (System.nanoTime() - startTime)/1000000;
        animation.update();

		/*//stops the background from scrolling
        if (speedX == 0 && speedY == 0) {
			bg1.setSpeedX(0);
			bg1.setSpeedY(0);
		}
		
		/////////////////////////////////////////////
		
		// change x position of player and background
		
		//if player is moving right and hits the center of the screen the players position
		//is pinned to the center of the screen.
		if(speedX > 0 && x >= 400){
			//if the background position moves far enough right it pins and holds its position
			//until player moves back the opposite direction.
			if(bg1.getBgX() > -1538){
				x -= speedX;
				bg1.setSpeedX(-speedX);
			}else{
				x -= speedX;
				bg1.setSpeedX(0);
			}
		}
		
		if (speedX < 0) {
			x += speedX;
		}
		
		if(speedX < 0 && x <= 62){
			System.out.println(bg1.getBgX());
			if(bg1.getBgX() < 0){
				//System.out.println(bg1.getBgX());
				x += -speedX;
				bg1.setSpeedX(-speedX);
			}else{
				x += -speedX;
				bg1.setSpeedX(0);
			}
		}
		
		if(speedX > 0 && x >= 1538){
			bg1.setSpeedX(0);
			if(x >= 1938){
				x -= speedX;
			}else{
				x += speedX;
			}
		}
				
		if(speedX > 0){
			x += speedX;
		}

        // Updates Y position of player and background
		if(speedY > 0){
			if(y < 430){
				y += speedY;
			}
		}
		// moves player up the screen
		if(speedY < 0){
			if(y > 0){
				y += speedY;
			}
		}
		
		// stops background scroll when
		if(speedY > 0 && y >= 2000){
			bg1.setSpeedY(0);
		}
		
		if(speedY < 0 && y <= 0){
			bg1.setSpeedY(0);
		}
		
		if(speedY > 0 && y >= 240){
			if(bg1.getBgY() > -1520){
				y -= speedY;
			}
			bg1.setSpeedY(-speedY);
		}
		
		if(bg1.getBgY() <= -1520 && speedY > 0){
			System.out.println(y);
			bg1.setSpeedY(0);
		}
		
		if(speedY < 0 && y <= 240){
			if(bg1.getBgY() <= 0){
				y += -speedY;
			}
			bg1.setSpeedY(-speedY);
		}*/

    }

    public void moveRight() {
        speedX = MOVESPEED;
    }

    public void moveLeft() {
        speedX = -MOVESPEED;
    }

    public void moveUp() {
        speedY = -MOVESPEED;
    }

    public void moveDown() {
        speedY = MOVESPEED;
    }

    public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    public void stopUp() {
        setMovingUp(false);
        stop();
    }

    public void stopDown() {
        setMovingDown(false);
        stop();
    }

    public void stop() {
        if (!isMovingRight() && !isMovingLeft()) {
            speedX = 0;
        }

        if (!isMovingRight() && isMovingLeft()) {
            moveLeft();
        }

        if (isMovingRight() && !isMovingLeft()) {
            moveRight();
        }

        if (!isMovingUp() && !isMovingDown()) {
            speedY = 0;
        }

        if (!isMovingUp() && isMovingDown()) {
            moveDown();
        }

        if (isMovingUp() && !isMovingDown()) {
            moveUp();
        }
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }
}