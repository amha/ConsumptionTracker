package consumptiontracker.amogus.com.consumptiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    // Time to display launcher
    private static int SPLASH_TIME_OUT = 2000;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent home_activity = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(home_activity);
                finish();
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
