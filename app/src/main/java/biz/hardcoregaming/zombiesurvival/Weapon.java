package biz.hardcoregaming.zombiesurvival;

import android.graphics.Bitmap;

/**
 * Created by mriegger on 2/17/2016.
 */
public class Weapon {
    private Bitmap image;
    private String name;
    private String weaponType;

    private int cost;
    private int level;
    private int magazineSize;
    private double reloadTime;
    private int damageDistance;
    private int damage;

    public Weapon(Bitmap image, String name, String weaponType, int cost) {
        this.image = image;
        this.name = name;
        this.weaponType = weaponType;
        this.cost = cost;

        level = 1;
        magazineSize = 20;
        reloadTime = 2;
        damageDistance = GamePanel.screenWidth;
        damage = 100;
    }

    public Weapon(Bitmap image, String name, String weaponType, int cost, int level, int magazineSize, double reloadTime, int damageDistance, int damage) {
        this.image = image;
        this.name = name;
        this.weaponType = weaponType;
        this.cost = cost;

        this.level = level;
        this.magazineSize = magazineSize;
        this.reloadTime = reloadTime;
        this.damageDistance = damageDistance;
        this.damage = damage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public void setMagazineSize(int magazineSize) {
        this.magazineSize = magazineSize;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(double reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getDamageDistance() {
        return damageDistance;
    }

    public void setDamageDistance(int damageDistance) {
        this.damageDistance = damageDistance;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
