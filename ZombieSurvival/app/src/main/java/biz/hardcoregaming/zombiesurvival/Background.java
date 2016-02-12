package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

	private Bitmap image;
	private int bgX, bgY, speedX, speedY, bgWidth, bgHeight;
	
	//constructor
	public Background(Bitmap res, int x, int y){
		image = res;
		bgX = x;
		bgY = y;
		speedX = 0;
		speedY = 0;
	}

	public void draw(Canvas canvas){
		canvas.drawBitmap(image, bgX, bgY, null);
		if(bgX<-GamePanel.WIDTH){
			canvas.drawBitmap(image, bgX+GamePanel.WIDTH, bgY, null);
		}
		if(bgY<-GamePanel.HEIGHT){
			canvas.drawBitmap(image, bgX, bgY+GamePanel.HEIGHT, null);
		}
	}
	
	public void update(){
		bgX += speedX;
		bgY += speedY;
		
		//meant for side scrolling game, resets after a point reached.
		if(bgX <= -2000){
			bgX += 4000;
		}
		if(bgX >= 4000){
			bgX -= 2000;
		}
		if(bgY <= -2000){
			bgY += 4000;
		}
		if(bgY >= 4000){
			bgY -= 2000;
		}
	}

	public int getBgX() {
		return bgX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
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

	public int getBgWidth() {
		return bgWidth;
	}

	public void setBgWidth(int bgWidth) {
		this.bgWidth = bgWidth;
	}

	public int getBgHeight() {
		return bgHeight;
	}

	public void setBgHeight(int bgHeight) {
		this.bgHeight = bgHeight;
	}
}
