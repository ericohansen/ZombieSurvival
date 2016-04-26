package biz.hardcoregaming.zombiesurvival;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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

    //Game information
    public int waveNumber = 1;

    //Map Objects
    public int numEnemyOnMap = 5;

    //public List<Enemy> EnemyCollection;
    private Enemy[] enemyList = new Enemy[waveNumber*10];

    //Projectile information
    public Projectile bullet = new Projectile();

    //Player information
    public Player player;

    //Base information
    private Base base;


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
        player.draw(canvas);
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Score: " + player.getScore(), 10, 25, paint);
        canvas.drawText("Health: " + player.getHealth(),10,55,paint);
        canvas.drawText("Wave #: " + waveNumber,10,85,paint);

        for(Enemy enemy : enemyList)
            if(enemy.isAlive)
                enemy.draw(canvas);

        if(bullet != null) {
            if (bullet.isActive()) bullet.draw(canvas);
            bullet.setActive(false);
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //creates the player and background objects
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playersquare), 200, 200, 3);
        //bg2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.treebackground), -1500, -1000);

        for(int i = 0; i < waveNumber*10; i++){
            //creates enemy object with a different sprite
            if(i % 3 == 0) {
                enemyList[i] = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.zombiesprite1), 200, 200, 4, 100, 5);
            }else if(i % 3 == 1){
                enemyList[i] = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.zombiesprite2), 64, 64, 8, 100, 5);
            }else{
                enemyList[i] = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.zombiesprite3), 200, 200, 4, 100, 5);
            }
        }


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
            if (!bullet.isActive()) {
                bullet = new Projectile(50, 150, 20, player.getAngle(), BitmapFactory.decodeResource(getResources(), R.drawable.gunfire));
            }
        }else{
            bullet = null;
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

        //System.out.println("E X: " + enemy.getX() + " Y:" + enemy.getY() + " P X: " + (player.getX() + bg.getBgX()) + " Y:" + (player.getY() + bg.getBgY()));
        for(Enemy enemy : enemyList)
            if(enemy.isAlive) {
                enemy.setAngle(getAngle(enemy.getX(), enemy.getY(), player.getX() + bg.getBgX(), player.getY() + bg.getBgY()));
                enemy.update();
            }

        if (bg.getSpeedX() != 0 || bg.getSpeedY() != 0)
            player.update();//stops the player sprite frames from transitioning while player not moving
        if(bullet != null) {
            isCollide();
            bullet.update();
        }
        enemyPlayerCollide();
    }

    public void isCollide(){
        if(bullet.isActive())
            for(Enemy enemy : enemyList)
                if(enemy.isAlive) {
                    Rect eRect = new Rect(enemy.x, enemy.y, enemy.x + enemy.width, enemy.y + enemy.height);
                    Rect bRect = bullet.getRotRect();//new Rect(bullet.getX(), bullet.getY(), bullet.getX() + bullet.getWidth(), bullet.getY() + bullet.getHeight()*3);
                    Rect pRect = new Rect(player.x,player.y, player.x+player.width,player.y+player.height);
                    //System.out.println("Bullet: " + bRect + " Enemy: " + eRect);
                    if (bRect.intersect(eRect)) {
                        System.out.println("Collide");
                        //System.out.println("Health: " + enemy.getHealth() + " " + bullet.getDamage());
                        bullet.setActive(false);
                        enemy.setHealth(bullet.getDamage());
                    }

                }
    }

    public void enemyPlayerCollide(){
        Rect pRect = new Rect(player.x, player.y, player.x + player.width, player.y + player.height);
        for(Enemy enemy : enemyList) {
            if (enemy.isAlive) {
                Rect eRect = new Rect(enemy.x, enemy.y, enemy.x + enemy.width, enemy.y + enemy.height);

                if (pRect.intersect(eRect)) {
                    player.changeHealth(-10);

                }
            }
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

                //enemy.setIsCollideX(true);

                bg.setSpeedX(0);
                base.setDx(0);

                //enemy.setDx(0);
            }else if(bgX + speedX >= centerX + playerMidX) {
                player.setIsCollideX(true);
                bg.setIsCollideX(true);
                base.setIsCollideX(true);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideX(true);

                bg.setSpeedX(0);
                base.setDx(0);
                player.setDx(0);

                for(Enemy enemy : enemyList)
                    enemy.setDx(0);
            }
        }

        if(bgCollideX){//else collide is true
            //sets collide false if player within right most bound and left most bound of map
            if(bgX > 0 && speedX < 0){
                player.setIsCollideX(false);
                bg.setIsCollideX(false);
                base.setIsCollideX(false);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideX(false);

            }else if(bgX < 0 && speedX > 0){//player moving right
                player.setIsCollideX(false);
                bg.setIsCollideX(false);
                base.setIsCollideX(false);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideX(false);
            }else {
                //set background speed to 0 if collide true
                player.setDx(0);
                bg.setSpeedX(0);
                base.setDx(0);

                for(Enemy enemy : enemyList)
                    enemy.setDx(0);
            }

        }

        //check if background y collides with player
        if (!bgCollideY) {
            if (bgY + speedY <= -(2000 - centerY - playerMidY) || bgY + speedY >= centerY + playerMidY) {
                player.setIsCollideY(true);
                bg.setIsCollideY(true);
                base.setIsCollideY(true);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideY(true);

                player.setDy(0);
                bg.setSpeedY(0);
                base.setDy(0);

                for(Enemy enemy : enemyList)
                    enemy.setDy(0);
            }
        }

        if(bgCollideY) {//else collide is true
            //sets collide false if player within down most bound and up most bound of map
            if(bgY > 0 && speedY < 0) {
                player.setIsCollideY(false);
                bg.setIsCollideY(false);
                base.setIsCollideY(false);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideY(false);
            }else if(bgY < 0 && speedY > 0){
                player.setIsCollideY(false);
                bg.setIsCollideY(false);
                base.setIsCollideY(false);

                for(Enemy enemy : enemyList)
                    enemy.setIsCollideY(false);
            }else {
                //set background speed to 0 if collide true
                player.setDy(0);
                bg.setSpeedY(0);
                base.setDy(0);

                for(Enemy enemy : enemyList)
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
                for(Enemy enemy : enemyList) {
                    enemy.setDx(0);
                    enemy.setDy(0);
                }

            } else {//else speedX or speedY doesn't equal 0
                //check for change in speed x
                if (speedX != 0) {
                     //sets the inputed speedX of background
                    bg.setSpeedX(speedX);
                    base.setDx(speedX);
                    player.setDx(-speedX);

                    for(Enemy enemy : enemyList)
                        enemy.setDx(speedX);
                } else {
                    bg.setSpeedX(0);
                    base.setDx(0);
                    player.setDx(0);

                    for(Enemy enemy : enemyList)
                        enemy.setDx(0);
                }

                //check for change in speed y
                if (speedY != 0) {
                    bg.setSpeedY(speedY);
                    base.setDy(speedY);
                    player.setDy(-speedY);

                    for(Enemy enemy : enemyList)
                        enemy.setDy(speedY);
                } else {
                    bg.setSpeedY(0);
                    base.setDy(0);
                    player.setDy(0);

                    for(Enemy enemy : enemyList)
                        enemy.setDy(0);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
