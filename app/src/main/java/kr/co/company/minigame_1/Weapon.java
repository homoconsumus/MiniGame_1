package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Weapon {
    private Player player;
    private int distanceFromPlayer = 200;
    private double angle;
    private Rect weaponRect;
    private int sizeX = 50;
    private int sizeY = 50;
    public int positionX;
    public int positionY;
    private Paint paint;
    public int power;

    public Weapon(Player player) {
        this.player = player;
        this.angle = 0.0;
        this.weaponRect = new Rect();
        this.paint = new Paint();
        this.power = 10;
        positionX = (weaponRect.left + weaponRect.right)/2;
        positionY = (weaponRect.top + weaponRect.bottom)/2;

        // 무기의 초기 위치 설정
        double x = Math.cos(angle) * distanceFromPlayer;
        double y = Math.sin(angle) * distanceFromPlayer;
        double weaponX = player.positionX + x;
        double weaponY = player.positionY + y;
        setPosition(weaponX, weaponY);
    }

    public void updatePlayerPosition(int playerX, int playerY) {
        this.player.positionX = playerX;
        this.player.positionY = playerY;
    }

    public void update() {
        angle += 0.5; // 무기가 주위를 도는 속도를 조정할 수 있습니다.
        double x = Math.cos(angle) * distanceFromPlayer;
        double y = Math.sin(angle) * distanceFromPlayer;

        // 플레이어의 위치에 무기를 추가합니다.
        double weaponX = player.positionX + x;
        double weaponY = player.positionY + y;

        // 무기의 위치를 업데이트합니다.
        setPosition(weaponX, weaponY);
    }

    private void setPosition(double x, double y) {
        // 무기의 위치를 설정하는 코드
        weaponRect.left = (int) Math.round(x - sizeX / 2);
        weaponRect.right = (int) Math.round(x + sizeX / 2);
        weaponRect.top = (int) Math.round(y - sizeY / 2);
        weaponRect.bottom = (int) Math.round(y + sizeY / 2);
        positionX = (weaponRect.left + weaponRect.right)/2;
        positionY = (weaponRect.top + weaponRect.bottom)/2;
    }

    public boolean isCollidingWith(Enemy enemy) {
        return Rect.intersects(weaponRect, enemy.getRect());
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.RED);
        int centerX = weaponRect.centerX();
        int centerY = weaponRect.centerY();
        System.out.println("Weapon center: (" + centerX + ", " + centerY + ")");
        canvas.drawCircle(weaponRect.centerX(), weaponRect.centerY(), weaponRect.width() / 2, paint);
    }
}