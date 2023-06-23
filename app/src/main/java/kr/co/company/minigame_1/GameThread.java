package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private ActionGame actionGame;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, ActionGame actionGame) {
        this.surfaceHolder = surfaceHolder;
        this.actionGame = actionGame;
        this.running = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if(canvas != null){
                        actionGame.update();
                        actionGame.draw(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
