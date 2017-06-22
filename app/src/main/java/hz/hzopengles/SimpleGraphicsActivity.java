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
        findViewById(R.id.circul  ).setOnClickListener(this);
        findViewById(R.id.cube    ).setOnClickListener(this);
        findViewById(R.id.cone    ).setOnClickListener(this);
        findViewById(R.id.cylinder).setOnClickListener(this);
        findViewById(R.id.sphere  ).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.triangle:
                nextActivity(ModelFactory.ModelType.TRIANGLE);
                break;
            case R.id.square:
                nextActivity(ModelFactory.ModelType.SQUARE);
                break;
            case R.id.circul:
                nextActivity(ModelFactory.ModelType.CIRCLE);
                break;
            case R.id.cube:
                nextActivity(ModelFactory.ModelType.CUBE);
                break;
            case R.id.cone:
                nextActivity(ModelFactory.ModelType.CONE);
                break;
            case R.id.cylinder:
                nextActivity(ModelFactory.ModelType.CYLINDER);
                break;
            case R.id.sphere:
                nextActivity(ModelFactory.ModelType.SPHERE);
                break;
        }
    }

    private void nextActivity(ModelFactory.ModelType modelType) {
        Intent intent = new Intent(SimpleGraphicsActivity.this, GraphicActivity.class);
        intent.putExtra("type", modelType);
        startActivity(intent);
    }
}
