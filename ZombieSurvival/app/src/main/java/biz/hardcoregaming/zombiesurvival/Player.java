package biz.hardcoregaming.zombiesurvival;

public class Player {
	//Constants
	final int MOVESPEED = 5;
	
	private int centerX = 62;
	private int centerY = 125;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingUp = false;
	private boolean movingDown = false;

	//private static Background bg1 = StartingClass.getBg1();
	//private static Background bg2 = StartingClass.getBg2();

	private int speedX = 0;
	private int speedY = 0;

	public void update() {
		
		/*//stops the background from scrolling
		if (speedX == 0 && speedY == 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
			bg1.setSpeedY(0);
			bg2.setSpeedY(0);
		}
		
		/////////////////////////////////////////////
		
		// change x position of player and background
		
		//if player is moving right and hits the center of the screen the players position
		//is pinned to the center of the screen.
		if(speedX > 0 && centerX >= 400){
			//if the background position moves far enough right it pins and holds its position
			//until player moves back the opposite direction.
			if(bg1.getBgX() > -1538){
				centerX -= speedX;
				bg1.setSpeedX(-speedX);
				bg2.setSpeedX(-speedX);
			}else{
				centerX -= speedX;
				bg1.setSpeedX(0);
				bg2.setSpeedX(0);
			}
		}
		
		if (speedX < 0) {
			centerX += speedX;
		}
		
		if(speedX < 0 && centerX <= 62){
			System.out.println(bg1.getBgX());
			if(bg1.getBgX() < 0){
				//System.out.println(bg1.getBgX());
				centerX += -speedX;
				bg1.setSpeedX(-speedX);
				bg2.setSpeedX(-speedX);
			}else{
				centerX += -speedX;
				bg1.setSpeedX(0);
				bg2.setSpeedX(0);
			}
		}
		
		if(speedX > 0 && centerX >= 1538){
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
			if(centerX >= 1938){
				centerX -= speedX;
			}else{
				centerX += speedX;
			}
		}
				
		if(speedX > 0){
			centerX += speedX;
		}
		
		//////////////////////////////////////////////
		//TODO: add scrolling up
		// 		fix y mid point snap
		
        // Updates Y position of player and background
		if(speedY > 0){
			if(centerY < 430){
				centerY += speedY;
			}
		}
		// moves player up the screen
		if(speedY < 0){
			if(centerY > 0){
				centerY += speedY;
			}
		}
		
		// stops background scroll when
		if(speedY > 0 && centerY >= 2000){
			bg1.setSpeedY(0);
			bg2.setSpeedY(0);
		}
		
		if(speedY < 0 && centerY <= 0){
			bg1.setSpeedY(0);
			bg2.setSpeedY(0);
		}
		
		if(speedY > 0 && centerY >= 240){
			if(bg1.getBgY() > -1520){
				centerY -= speedY;	
			}
			bg1.setSpeedY(-speedY);
			bg2.setSpeedY(-speedY);
		}
		
		if(bg1.getBgY() <= -1520 && speedY > 0){
			System.out.println(centerY);
			bg1.setSpeedY(0);
			bg2.setSpeedY(0);
		}
		
		if(speedY < 0 && centerY <= 240){
			if(bg1.getBgY() <= 0){
				centerY += -speedY;	
			}
			bg1.setSpeedY(-speedY);
			bg2.setSpeedY(-speedY);
		}*/

	}

	public void moveRight() {
		speedX = MOVESPEED;
	}

	public void moveLeft() {
		speedX = -MOVESPEED;
	}
	
	public void moveUp(){
		speedY = -MOVESPEED;
	}
	
	public void moveDown(){
		speedY = MOVESPEED;
	}
	
	public void stopRight(){
		setMovingRight(false);
		stop();
	}
	
	public void stopLeft(){
		setMovingLeft(false);
		stop();
	}
	
	public void stopUp(){
		setMovingUp(false);
		stop();
	}
	
	public void stopDown(){
		setMovingDown(false);
		stop();
	}

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }

        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }

        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }
        
        if(isMovingUp() == false && isMovingDown() == false){
        	speedY = 0;
        }
        
        if (isMovingUp() == false && isMovingDown() == true) {
            moveDown();
        }

        if (isMovingUp() == true && isMovingDown() == false) {
            moveUp();
        }
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
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