package hz.hzopengles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Activitys.SimpleGraphicsActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButton_sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton_sg = (Button) findViewById(R.id.main_button_sg);
        mButton_sg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button_sg:
                jumpToActivity(SimpleGraphicsActivity.class);
                break;
        }
    }

    public void jumpToActivity(Class nextActivity) {
        startActivity(new Intent(MainActivity.this, nextActivity));
    }
}
