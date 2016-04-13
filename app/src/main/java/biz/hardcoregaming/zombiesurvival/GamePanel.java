package biz.hardcoregaming.zombiesurvival;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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

    //Thread information
    private MainThread thread;

    //Screen information
    public static int screenHeight = 0;
    public static int screenWidth = 0;

    //Background information
    public Background bg;
    //public Background bg2;
    public boolean isBgMove = true;
    public int speedX, speedY, moveSpeed = 10;

    //Map Objects
    public int numEnemyOnMap = 5;
    //public List<Enemy> EnemyCollection;
    private Enemy enemy;

    //Projectile information
    public Projectile bullet = new Projectile();

    //Player information
    public Player player;

    //Base information
    private Base base;

    //Game information
    public int waveNumber = 1;

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
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Sensor listener
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //bg2.draw(canvas);
        bg.draw(canvas);
        base.draw(canvas);
        enemy.draw(canvas);
        //draw enemy list
        if(bullet != null)
            if(bullet.isActive())bullet.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //creates the player and background objects
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playersquare), 200, 200, 3);
        //bg2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.treebackground), -1500, -1000);
        enemy = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.zombiesprite3), 200, 200, 4);

        bg = new Background(0, 0);
        base = new Base(bg.getX() + 800, bg.getY() + 800, 100, 1, 400, 400);
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
        //rotates player sprite based on user touch
        //System.out.println("Player x,y: (" + center.x + "," + center.y + ") :: Touch Event x,y: (" + event.getX() + "," + event.getY() + ")");
        player.setAngle(getAngle(screenWidth / 2, screenHeight / 2, (int) event.getX(), (int)event.getY()));
        //System.out.println(player.getAngle());
        if(bullet != null) {
            //System.out.println("Not Null");
            if (!bullet.isActive()) {
                bullet = new Projectile(2, 2, 300, 10, player.getAngle() - 90);
                //System.out.println("Is Active");
            }
        }
        //System.out.println("NULL");
        return super.onTouchEvent(event);
    }

    public float getAngle(int x, int y, int tx, int ty){
        return (float)Math.toDegrees(Math.atan2((ty - y), (tx - x))) + 90;
    }

    public void update() {
        //bg2.update();
        bg.update();
        base.update();

        System.out.println("E: " + enemy.getX() + bg.getBgX() + ":" + enemy.getY() + bg.getBgX() + " P: " + player.getX() + bg.getBgX() + ":" + player.getY() + bg.getBgY());

        enemy.setAngle(getAngle(enemy.getX() + bg.getBgX(), enemy.getY() + bg.getBgX(), player.getX() + bg.getBgX(), player.getY() + bg.getBgY()));
        enemy.update();

        if (bg.getSpeedX() != 0 || bg.getSpeedY() != 0)
            player.update();//stops the player sprite frames from transitioning while player not moving
        if(bullet != null) {
            isCollide();
            bullet.update();
        }
    }

    public void isCollide(){
        if (bullet.getRectangle().intersect(enemy.getRectangle())){
            System.out.println("Collide");
            bullet.setActive(false);
            enemy.setHealth(-50);
        }
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
                // changes are from x onto y and y onto x because of landscape views
                // changes move speed on player if y axis changes
                if (y > 0.5) {
                    //move right
                    if(y > 2) y = 2;
                    speedX = -moveSpeed*(int)y;
                } else if (y < -0.5) {
                    //move left
                    if(y < -2) y = -2;
                    speedX = -moveSpeed*(int)y;
                } else {
                    bg.setSpeedX(0);
                    //bg2.setSpeedX(0);
                    speedX = 0;
                }

                // changes move speed on player if x axis changes
                if (x > 0.5) {
                    //move down
                    if(x > 2) x = 2;
                    speedY = -moveSpeed*(int)x;
                } else if (x < -0.5) {
                    //move up
                    if(x < -2) x = -2;
                    speedY = -moveSpeed*(int)x;
                } else {
                    bg.setSpeedY(0);
                    //bg2.setSpeedY(0);
                    speedY = 0;
                }

                playerCollide();
                //player.setAngle(getAngle(center, new Point((center.x - speedX), (center.y - speedY))));
                setBgMove(speedX, speedY);
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

        //System.out.println("BG:(" + bgX + "," + bgY + ") speed(x,y):(" + speedX + "," + speedY + ") bg X: " + bgCollideX + " bg Y: " + bgCollideY);

        if (!bgCollideX) {
            //sets collide true if center of screen hits right most bound of map
            //sets collide true if center of screen hits left most bound of map
            if (bgX + speedX <= -(2000 - centerX - playerMidX)){
                player.setIsCollideX(true);
                bg.setIsCollideX(true);
                base.setIsCollideX(true);

                enemy.setIsCollideX(true);

                bg.setSpeedX(0);
                base.setDx(0);

                enemy.setDx(0);
            }else if(bgX + speedX >= centerX + playerMidX) {
                player.setIsCollideX(true);
                bg.setIsCollideX(true);
                base.setIsCollideX(true);

                enemy.setIsCollideX(true);

                bg.setSpeedX(0);
                base.setDx(0);
                player.setDx(0);

                enemy.setDx(0);
            }
        }

        if(bgCollideX){//else collide is true
            //sets collide false if player within right most bound and left most bound of map
            if(bgX > 0 && speedX < 0){
                player.setIsCollideX(false);
                bg.setIsCollideX(false);
                base.setIsCollideX(false);

                enemy.setIsCollideX(false);
            }else if(bgX < 0 && speedX > 0){//player moving right
                player.setIsCollideX(false);
                bg.setIsCollideX(false);
                base.setIsCollideX(false);

                enemy.setIsCollideX(false);
            }else {
                //set background speed to 0 if collide true
                player.setDx(0);
                bg.setSpeedX(0);
                base.setDx(0);

                enemy.setDx(0);
            }

        }

        //check if background y collides with player
        if (!bgCollideY) {
            if (bgY + speedY <= -(2000 - centerY - playerMidY) || bgY + speedY >= centerY + playerMidY) {
                player.setIsCollideY(true);
                bg.setIsCollideY(true);
                base.setIsCollideY(true);

                enemy.setIsCollideY(true);

                player.setDy(0);
                bg.setSpeedY(0);
                base.setDy(0);

                enemy.setDy(0);
            }
        }

        if(bgCollideY) {//else collide is true
            //sets collide false if player within down most bound and up most bound of map
            if(bgY > 0 && speedY < 0) {
                player.setIsCollideY(false);
                bg.setIsCollideY(false);
                base.setIsCollideY(false);

                enemy.setIsCollideY(false);
            }else if(bgY < 0 && speedY > 0){
                player.setIsCollideY(false);
                bg.setIsCollideY(false);
                base.setIsCollideY(false);

                enemy.setIsCollideY(false);
            }else {
                //set background speed to 0 if collide true
                player.setDy(0);
                bg.setSpeedY(0);
                base.setDy(0);

                enemy.setDy(0);
            }
        }
    }

    //checks movement and adjust speed of background objects
    public void setBgMove(int speedX, int speedY) {
        if (isBgMove) {
            //System.out.println("speedX: " + speedX + " speedY: " + speedY);

            //base case if both speeds are 0
            if (speedX == 0 && speedY == 0) {
                bg.setSpeedX(0);
                bg.setSpeedY(0);
                base.setDx(0);
                base.setDy(0);
                //set player x y speeds
                player.setDx(0);
                player.setDy(0);
                //set enemy x y speeds
                enemy.setDx(0);
                enemy.setDy(0);

            } else {//else speedX or speedY doesn't equal 0
                //check for change in speed x
                if (speedX != 0) {
                     //sets the inputed speedX of background
                    bg.setSpeedX(speedX);
                    base.setDx(speedX);
                    player.setDx(-speedX);

                    enemy.setDx(speedX);
                } else {
                    bg.setSpeedX(0);
                    base.setDx(0);
                    player.setDx(0);

                    enemy.setDx(0);
                }

                //check for change in speed y
                if (speedY != 0) {
                    bg.setSpeedY(speedY);
                    base.setDy(speedY);
                    player.setDy(-speedY);

                    enemy.setDy(speedY);
                } else {
                    bg.setSpeedY(0);
                    base.setDy(0);
                    player.setDy(0);

                    enemy.setDy(0);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
