package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy {
    private Rect rect;
    private int health;
    private int maxHealth;
    private int speed;
    public int power;
    private Paint paint;
    private boolean invincible;
    private long invincibleEndTime;
    private int invincibleTime;
    private long lastCollisionTime;

    public Enemy(int x, int y, int health, int power) {
        paint = new Paint();
        rect = new Rect(x, y, x + 200, y + 200);
        this.health = health;
        this.maxHealth = health;
        this.power = power;
        speed = 10;
        invincible = false;
        invincibleTime = 1000; // 1초 동안 무적 상태 유지
        lastCollisionTime = 0;
    }

    public void setInvincible(boolean invincible){
        this.invincible = invincible;
        if (invincible) {
            invincibleEndTime = System.currentTimeMillis() + invincibleTime;
        }
    }

    public boolean isInvincible() {
        return invincible && System.currentTimeMillis() < invincibleEndTime;
    }

    public void handleCollision(Player player){
        if(System.currentTimeMillis() - lastCollisionTime > invincibleTime){
            setInvincible(true);
            lastCollisionTime = System.currentTimeMillis();

            // 플레이어의 중심으로부터 반대 방향으로 200의 거리만큼 이동
            int playerX = player.getRect().centerX();
            int playerY = player.getRect().centerY();
            int enemyX = rect.centerX();
            int enemyY = rect.centerY();
            double angle = Math.atan2(enemyY - playerY, enemyX - playerX);
            int dx = (int) (player.pushBackDistance * Math.cos(angle));
            int dy = (int) (player.pushBackDistance * Math.sin(angle));
            rect.offset(dx, dy);
        }
    }

    public Rect getRect() {
        return rect;
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
        return maxHealth;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void moveTowardsPlayer(Player player) {
        int playerX = player.getRect().centerX();
        int playerY = player.getRect().centerY();

        if (!isInvincible()) {
            if (rect.centerX() > playerX) {
                rect.left -= speed;
                rect.right -= speed;
            } else {
                rect.left += speed;
                rect.right += speed;
            }

            if (rect.centerY() > playerY) {
                rect.top -= speed;
                rect.bottom -= speed;
            } else {
                rect.top += speed;
                rect.bottom += speed;
            }
        }
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        if (isInvincible()) {
            paint.setAlpha(128); // 무적 상태일 때 투명도 적용
        } else {
            paint.setAlpha(255);
        }
        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
    }
}