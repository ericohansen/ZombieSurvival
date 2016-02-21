package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject{

	private Bitmap image;
	private int bgX, bgY, speedX, speedY, bgWidth, bgHeight;

	private boolean isCollide = false;
	
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
	}
	
	public void update(){
		if(!isCollide) {
			bgX += speedX;
			bgY += speedY;
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

	public boolean isCollide() {
		return isCollide;
	}

	public void setIsCollide(boolean isCollide) {
		this.isCollide = isCollide;
	}
}
