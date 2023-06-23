package kr.co.company.minigame_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ActionGame actionGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionGame = new ActionGame(this);
        setContentView(actionGame);
    }

    @Override
    protected void onResume(){
        super.onResume();
        actionGame.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        actionGame.pause();
    }
}