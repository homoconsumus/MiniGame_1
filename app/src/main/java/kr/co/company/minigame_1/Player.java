package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    private Rect rect;
    private int health;
    private Paint paint;
    private int sizeX = 200;
    private int sizeY = 200;
    public int positionX;
    public int positionY;
    public int pushBackDistance;
    public int power;

    public Player() {
        paint = new Paint();
        rect = new Rect(100 - sizeX/2, 100 - sizeY/2, 100 + sizeX/2, 100 + sizeY/2);
        positionX = (rect.left + rect.right)/2;
        positionY = (rect.top + rect.bottom)/2;
        health = 150;
        pushBackDistance = 100;
        power = 14;
    }

    public Rect getRect(){
        return rect;
    }

    public void setPosition(int x, int y) {
        if(x<0){
            x = 0;
        }
        if(x>1080){
            x = 1080;
        }
        if(y<0){
            y = 0;
        }
        if(y>2054){
            y = 2054;
        }
        rect.left = x - sizeX / 2;
        rect.right = x + sizeX / 2;
        rect.top = y - sizeY / 2;
        rect.bottom = y + sizeY / 2;
        positionX = (rect.left + rect.right)/2;
        positionY = (rect.top + rect.bottom)/2;
    }

    public void decreaseHealth(int amount) {
        if(health == 0){
            health = 0;
        }
        else {
            health -= amount;
        }
    }

    public int getHealth() {
        return health;
    }
    public int getMaxHealth(){
        return 150;
    }

    public boolean isCollidingWith(Enemy enemy) {
        return Rect.intersects(rect, enemy.getRect());
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(rect, paint);
    }
}