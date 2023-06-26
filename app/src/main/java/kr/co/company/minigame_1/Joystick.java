package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Joystick {
    private Rect bounds;
    private Paint paint;

    public Joystick(int left, int top, int right, int bottom) {
        bounds = new Rect(left, top, right, bottom);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void draw(Canvas canvas) {
        paint.setAlpha(0);
        canvas.drawRect(bounds, paint);
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public void setPosition(int left, int top, int right, int bottom) {
        bounds.left = left;
        bounds.top = top;
        bounds.right = right;
        bounds.bottom = bottom;
    }
}