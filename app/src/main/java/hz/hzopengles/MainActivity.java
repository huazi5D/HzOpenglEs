package hz.hzopengles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButton_sg;
    private Button mButton_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton_sg = (Button) findViewById(R.id.main_button_sg);
        mButton_bitmap = (Button) findViewById(R.id.main_button_bitmap);

        mButton_sg.setOnClickListener(this);
        mButton_bitmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button_sg:
                jumpToActivity(SimpleGraphicsActivity.class);
                break;
            case R.id.main_button_bitmap:
                jumpToActivity(BitmapActivity.class);
                break;
        }
    }

    public void jumpToActivity(Class nextActivity) {
        startActivity(new Intent(MainActivity.this, nextActivity));
    }
}
