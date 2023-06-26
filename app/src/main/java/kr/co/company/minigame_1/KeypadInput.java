package kr.co.company.minigame_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class KeypadInput {
    private String[][] keypad = {
            {"A", "B", "C", "D"},
            {"E", "F", "G", "H"},
            {"I", "J", "K", "L"},
            {"M", "N", "O", "P"},
            {"Q", "R", "S", "T"},
            {"U", "V", "W", "X"},
            {"Y", "Z", "Del", "Set"}
    };
    private String playerName = "";
    private Canvas canvas;
    private final int KEYPAD_CELL_WIDTH = 200;
    private final int KEYPAD_CELL_HEIGHT = 200;
    private int maskCellX = -1; // 마스크가 위치한 cell의 X 인덱스
    private int maskCellY = -1; // 마스크가 위치한 cell의 Y 인덱스
    private int screenWidth;
    private int screenHeight;

    public KeypadInput(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void draw(Canvas canvas) {
        int keypadWidth = keypad[0].length; // 키패드의 가로 개수
        int keypadHeight = keypad.length; // 키패드의 세로 개수
        int startX = (screenWidth - (keypadWidth * KEYPAD_CELL_WIDTH)) / 2; // 시작 X 좌표
        int startY = (screenHeight - (keypadHeight * KEYPAD_CELL_HEIGHT)) / 2; // 시작 Y 좌표

        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);

        // 키패드 그리기
        for (int i = 0; i < keypadHeight; i++) {
            for (int j = 0; j < keypadWidth; j++) {
                int cellX = startX + (j * KEYPAD_CELL_WIDTH);
                int cellY = startY + (i * KEYPAD_CELL_HEIGHT);
                String character = keypad[i][j];

                // 문자열의 중앙 좌표 계산
                float textWidth = paint.measureText(character);
                float textHeight = paint.descent() - paint.ascent();
                float centerX = cellX + (KEYPAD_CELL_WIDTH - textWidth) / 2;
                float centerY = cellY + (KEYPAD_CELL_HEIGHT - textHeight) / 2;

                // 문자열 그리기
                canvas.drawText(character, centerX, centerY, paint);

                // 마스크 그리기
                if (maskCellX == j && maskCellY == i) {
                    paint.setColor(Color.RED);
                    canvas.drawRect(cellX, cellY, cellX + KEYPAD_CELL_WIDTH, cellY + KEYPAD_CELL_HEIGHT, paint);
                }
            }
        }

        // 플레이어 이름 그리기
        String playerNameText = "Player Name: " + playerName;
        float playerNameX = startX;
        float playerNameY = startY - 50;

        // 문자열 그리기
        canvas.drawText(playerNameText, playerNameX, playerNameY, paint);
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public String getTouchedCharacter(float x, float y) {
        System.out.println("getTouchedCharacter 실행!");
        int startX = (screenWidth - (keypad[0].length * KEYPAD_CELL_WIDTH)) / 2;
        int startY = (screenHeight - (keypad.length * KEYPAD_CELL_HEIGHT)) / 2;

        int cellX = (int) (x - startX) / KEYPAD_CELL_WIDTH;
        int cellY = (int) (y - startY) / KEYPAD_CELL_HEIGHT;

        if (cellX >= 0 && cellX < keypad[0].length && cellY >= 0 && cellY < keypad.length) {
            maskCellX = cellX;
            maskCellY = cellY;
            int cellCenterX = startX + (cellX * KEYPAD_CELL_WIDTH) + KEYPAD_CELL_WIDTH / 2;
            int cellCenterY = startY + (cellY * KEYPAD_CELL_HEIGHT) + KEYPAD_CELL_HEIGHT / 2;
            System.out.println("터치한 좌표: x=" + x + ", y=" + y);
            System.out.println("Cell의 중심 좌표: cellCenterX=" + cellCenterX + ", cellCenterY=" + cellCenterY);
            System.out.println("화면의 크기" + screenWidth + ", " + screenHeight);
            return keypad[cellY][cellX];
        }

        return null;
    }

    public void addCharacter(String character) {
        System.out.println("addCharacter");
        playerName += character;
    }

    public void deleteCharacter() {
        if (playerName != null && !playerName.isEmpty()) {
            System.out.println("deleteCharacter");
            playerName = playerName.substring(0, playerName.length() - 1);
        }
    }

    public String setPlayerName() {
        System.out.println("setPlayerName");
        return playerName;
    }
}