package biz.hardcoregaming.zombiesurvival;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {


    private MainThread thread;

    //Screen information
    public static int screenHeight = 0;
    public static int screenWidth = 0;

    //Background information
    public Background bg;
    public Background bg2;
    public boolean isBgMove = true;
    public int speedX, speedY, moveSpeed = 20;

    //Player information
    public Player player;
    public boolean isPlayerMove = true;

    public GamePanel(Context context) {
        super(context);

        // add the callback to the surfaceholder to intercept events (finger presses)
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //Gets screen resolution and sets it to a global variable
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        setFocusable(true);

        //Creates the sensor objects
        SensorManager mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Sensor listener
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bg2.draw(canvas);
        bg.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //creates the player and background objects
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playersquare), 200, 200, 3);
        bg2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.treebackground), -1500, -1000);
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background), 0, 0);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("X: " + event.getRawX() + " Y: " + event.getRawY());

        //rotates player sprite based on user touch
        //takes the slope between two points and calculates the angle for player to rotate to
        float angle = (float) Math.toDegrees(Math.atan2((event.getY() - player.getY()), (event.getX() - player.getX())));
        System.out.println("ANGLE: " + angle);
        //sets the angle when finger tap
        player.setAngle(angle);

        return super.onTouchEvent(event);
    }

    public void update() {
        bg2.update();
        bg.update();
        if (bg.getSpeedX() != 0 || bg.getSpeedY() != 0)
            player.update();//stops the player sprite frames from transitioning while player not moving
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            //float z = event.values[2];
            //System.out.println("Sensor X: " + x);
            //System.out.println("Sensor Y: " + y);

            //checks if background object exists (was getting a null reference error without this check)
            if (bg != null) {
                //gets current speed
                speedX = bg.getSpeedX();
                speedY = bg.getSpeedY();

                if (y > 0.5) {
                    //move right
                    setBgMove(-moveSpeed, speedY, 6);
                    speedX = -moveSpeed;
                } else if (y < -0.5) {
                    //move left
                    setBgMove(moveSpeed, speedY, -6);
                    speedX = moveSpeed;
                } else {
                    bg.setSpeedX(0);
                    bg2.setSpeedX(0);
                    speedX = 0;
                }

                if (x > 0.5) {
                    //move down
                    setBgMove(speedX, -moveSpeed, 6);
                    speedY = -moveSpeed;
                } else if (x < -0.5) {
                    //move up
                    setBgMove(speedX, moveSpeed, -6);
                    speedY = moveSpeed;
                } else {
                    bg.setSpeedY(0);
                    bg2.setSpeedY(0);
                    speedY = 0;
                }

                playerCollide();

                //changes the direction player is moving towards based on direction background is moving
                if (speedX < 0) {
                    if (speedY < 0) {
                        //down right
                        player.setAngle((float) 135);
                    } else if (speedY > 0) {
                        //up right
                        player.setAngle((float) 45);
                    } else {
                        //right
                        player.setAngle((float) 90);
                    }
                } else if (speedX > 0) {
                    if (speedY < 0) {
                        //down left
                        player.setAngle((float) -135);
                    } else if (speedY > 0) {
                        //up left
                        player.setAngle((float) -45);
                    } else {
                        //left
                        player.setAngle((float) -90);
                    }
                } else {
                    if (speedY > 0) {
                        //up
                        player.setAngle((float) 0);
                    } else if (speedY < 0) {
                        //down
                        player.setAngle((float) 180);
                    }
                }
            }
        }
    }

    public void playerCollide() {
        int bgX = bg.getBgX();
        int bgY = bg.getBgY();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int playerMidX = player.getWidth() / 2;
        int playerMidY = player.getHeight() / 2;
        boolean bgCollideX = bg.isCollideX();
        boolean bgCollideY = bg.isCollideY();

        System.out.println("BG: " + bgX + " : " + bgY + " bg X: " + bgCollideX + " bg Y: " + bgCollideY);

        if (!bgCollideX) {
            //sets collide true if center of screen hits right most bound of map
            //sets collide true if center of screen hits left most bound of map
            if (bgX + speedX <= -(2000 - centerX - playerMidX) || bgX + speedX >= centerX + playerMidX) {
                player.setIsCollide(true);
                bg.setIsCollideX(true);
                bg2.setIsCollideX(true);
                bg.setSpeedX(0);
                bg2.setSpeedX(0);
            }
        } else {//else collide is true
            //sets collide false if player within right most bound and left most bound of map
            if (bgX + speedX + 1 >= -(2000 - centerX - playerMidX) || bgX + speedX <= centerX + playerMidX) {
                player.setIsCollide(false);
                bg.setIsCollideX(false);
                bg2.setIsCollideX(false);
            }else {
                //set background speed to 0 if collide true
                bg.setSpeedX(0);
                bg2.setSpeedX(0);
            }

        }

        //check if background y collides with player
        if (!bgCollideY) {
            if (bgY + speedY <= -(2000 - centerY - playerMidY) || bgY + speedY >= centerY + playerMidY) {
                player.setIsCollide(true);
                bg.setIsCollideY(true);
                bg2.setIsCollideY(true);
                bg.setSpeedY(0);
                bg2.setSpeedY(0);
            }
        } else {//else collide is true
            //sets collide false if player within down most bound and up most bound of map
            if (bgY + speedY + 1 >= -(2000 - centerY - playerMidY) || bgY + speedY <= centerY + playerMidY) {
                player.setIsCollide(false);
                bg.setIsCollideY(false);
                bg2.setIsCollideY(false);
            }else {
                //set background speed to 0 if collide true
                bg.setSpeedY(0);
                bg2.setSpeedY(0);
            }
        }
    }

    //checks movement and adjust speed of background objects
    public void setBgMove(int speedX, int speedY, int adjustment) {
        if (isBgMove) {
            //base case if both speeds are 0
            if (speedX == 0 && speedY == 0) {
                bg.setSpeedX(0);
                bg.setSpeedY(0);
                bg2.setSpeedY(0);
                bg2.setSpeedX(0);
            } else {//else speedX or speedY doesn't equal 0
                //check for change in speed x
                if (speedX != 0) {
                    //checks for diagonal movement
                    if (speedY != 0) {
                        //adjust speed
                        speedX += adjustment;
                        //sets the adjusted speedX of background
                        bg.setSpeedX(speedX);
                        bg2.setSpeedX(speedX);
                    } else {
                        //sets the inputed speedX of background
                        bg.setSpeedX(speedX);
                        bg2.setSpeedX(speedX);
                    }
                } else {
                    bg.setSpeedX(0);
                    bg2.setSpeedX(0);
                }

                //check for change in speed y
                if (speedY != 0) {
                    //checks for diagonal movement
                    if (speedX != 0) {
                        //adjusts speed
                        speedY += adjustment;
                        bg.setSpeedY(speedY);
                        bg2.setSpeedY(speedY);
                    } else {
                        bg.setSpeedY(speedY);
                        bg2.setSpeedY(speedY);
                    }
                } else {
                    bg.setSpeedY(0);
                    bg2.setSpeedY(0);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
