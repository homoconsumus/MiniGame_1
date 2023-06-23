package kr.co.company.minigame_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ActionGame extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Joystick joystick;
    private Player player;
    private List<Enemy> enemies;
    private Paint playerHealthBarPaint;
    private List<Paint> enemyHealthBarPaints;
    private int screenWidth;
    private int screenHeight;
    private int joystick_position_x = 0;
    private int joystick_position_y = 0;

    public ActionGame(Context context) {
        super(context);
        getHolder().addCallback(this);
        player = new Player();
        enemies = new ArrayList<>();

        playerHealthBarPaint = new Paint();
        playerHealthBarPaint.setColor(Color.GREEN);
        joystick = new Joystick(0,0,0,0);

        enemyHealthBarPaints = new ArrayList<>();
        Paint enemyHealthBarPaint1 = new Paint();
        enemyHealthBarPaint1.setColor(Color.parseColor("#F0F8FF"));
        enemyHealthBarPaints.add(enemyHealthBarPaint1);

        Paint enemyHealthBarPaint2 = new Paint();
        enemyHealthBarPaint2.setColor(Color.parseColor("#F0F8FF"));
        enemyHealthBarPaints.add(enemyHealthBarPaint2);

        Paint enemyHealthBarPaint3 = new Paint();
        enemyHealthBarPaint3.setColor(Color.parseColor("#F0F8FF"));
        enemyHealthBarPaints.add(enemyHealthBarPaint3);

        Paint enemyHealthBarPaint4 = new Paint();
        enemyHealthBarPaint4.setColor(Color.parseColor("#F0F8FF"));
        enemyHealthBarPaints.add(enemyHealthBarPaint4);

        Paint enemyHealthBarPaint5 = new Paint();
        enemyHealthBarPaint5.setColor(Color.parseColor("#F0F8FF"));
        enemyHealthBarPaints.add(enemyHealthBarPaint5);
        // 화면 크기
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(),this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 조이스틱 컨트롤
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        if(action == MotionEvent.ACTION_DOWN){
            joystick = new Joystick((int) x - 100, (int) y - 100, (int) x + 200, (int) y + 200);
            joystick_position_x = (int) x;
            joystick_position_y = (int) y;
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (joystick.contains((int) x, (int) y)) {
                    // 조이스틱을 움직이는 경우
                    player.setPosition(player.positionX - (joystick_position_x - (int) x)/10, player.positionY - (joystick_position_y - (int) y)/10);
                    System.out.println(player.positionX + " " + player.positionY);
                    System.out.println("화면 넓이: " + screenWidth + " " + screenHeight);
                    joystick.setPosition((int) x - 100, (int) y - 100, (int) x + 200, (int) y + 200);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 손을 뗀 경우
                joystick.setPosition(0, 0, 0, 0); // Joystick 위치 초기화
                break;
        }
        return true;
    }

    public void update() {
        // 적 생성
        if(enemies.size()<5){
            Random random = new Random();
            int enemyX = screenWidth;
            int enemyY = random.nextInt(screenHeight);
            int enemyHealth = 15;
            Enemy enemy = new Enemy(enemyX, enemyY, enemyHealth);
            enemies.add(enemy);
        }

        // 플레이어와 적 충돌 체크
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            if (enemy.getHealth() <= 0) {
                iterator.remove();
            } else if (player.isCollidingWith(enemy)) {
                if (!enemy.isInvincible()) { // 적이 무적 상태가 아닌 경우에만 충돌 처리
                    // 플레이어와 적 충돌 시 체력 감소
                    player.decreaseHealth(5);
                    enemy.decreaseHealth(10);
                    // 충돌한 적은 잠시 무적시간
                    enemy.handleCollision(player);
                }
            } else if (enemy.isCollidingWithPlayerCorner(player)) {
//                // 플레이어 꼭지점과 적 충돌 시 적 체력 감소
//                enemy.decreaseHealth(10);
            }

            // 적 이동
            enemy.moveTowardsPlayer(player);
        }

        // 플레이어 체력 감소 시 게임 오버 처리
        if (player.getHealth() <= 0) {
            // 게임 오버 처리
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        // 플레이어 그리기
        player.draw(canvas);

        // 적 그리기
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

        // Joystick 그리기
        joystick.draw(canvas);

        // 체력바 그리기
        drawPlayerHealthBar(canvas);
        drawEnemyHealthBars(canvas);
    }

    private void drawPlayerHealthBar(Canvas canvas) {
        int healthBarWidth = (int) convertDpToPixel(100, getContext());
        int healthBarHeight = (int) convertDpToPixel(10, getContext());
        int healthBarX = 50;
        int healthBarY = screenHeight - 100;

        int playerHealth = player.getHealth();
        int maxHealth = player.getMaxHealth();
        int filledWidth = (int) ((float) playerHealth / maxHealth * healthBarWidth);

        Paint emptyPaint = new Paint();
        emptyPaint.setColor(Color.RED);
        canvas.drawRect(healthBarX, healthBarY, healthBarX + healthBarWidth, healthBarY + healthBarHeight, emptyPaint);

        Paint filledPaint = new Paint();
        filledPaint.setColor(Color.GREEN);
        canvas.drawRect(healthBarX, healthBarY, healthBarX + filledWidth, healthBarY + healthBarHeight, filledPaint);
    }

    private void drawEnemyHealthBars(Canvas canvas) {
        int healthBarWidth = (int) convertDpToPixel(60, getContext());
        int healthBarHeight = (int) convertDpToPixel(10, getContext());
        int healthBarX = 50;
        int healthBarY = 50;

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            int enemyHealthBarWidth = healthBarWidth;
            int enemyHealthBarHeight = healthBarHeight;

            int enemyHealth = enemy.getHealth();
            int enemyMaxHealth = enemy.getMaxHealth();
            int enemyFilledWidth = (int) ((float) enemyHealth / enemyMaxHealth * enemyHealthBarWidth);

            Paint emptyPaint = new Paint();
            emptyPaint.setColor(Color.RED);
            canvas.drawRect(healthBarX, healthBarY, healthBarX + enemyHealthBarWidth, healthBarY + enemyHealthBarHeight, emptyPaint);

            Paint filledPaint = new Paint();
            filledPaint.setColor(Color.GREEN);
            canvas.drawRect(healthBarX, healthBarY, healthBarX + enemyFilledWidth, healthBarY + enemyHealthBarHeight, filledPaint);

            healthBarX += enemyHealthBarWidth + 20;
        }
    }

    private float convertDpToPixel(float dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / 160f);
    }

    public void resume() {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    public void pause() {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
