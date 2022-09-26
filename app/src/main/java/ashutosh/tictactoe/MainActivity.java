package ashutosh.tictactoe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public void openOnePlayerActivity(View view){
        Intent intent = new Intent(getApplicationContext(), OnePlayerActivity.class);
        startActivity(intent);
    }

    public void openTwoPlayersActivity(View view){
        Intent intent = new Intent(getApplicationContext(), TwoPlayersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}