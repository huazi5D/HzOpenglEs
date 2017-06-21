package hz.hzopengles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Models.ModelFactory;

public class SimpleGraphicsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_graphics);
        findViewById(R.id.triangle).setOnClickListener(this);
        findViewById(R.id.square  ).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.triangle:
                nextActivity(GraphicActivity.class, ModelFactory.ModelType.TRIANGLE);
                break;
            case R.id.square:
                nextActivity(GraphicActivity.class, ModelFactory.ModelType.SQUARE);
                break;
        }
    }

    private void nextActivity(Class clasz, ModelFactory.ModelType modelType) {
        Intent intent = new Intent(SimpleGraphicsActivity.this, clasz);
        intent.putExtra("type", modelType);
        startActivity(intent);
    }
}
