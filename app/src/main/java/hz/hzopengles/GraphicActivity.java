package hz.hzopengles;

import android.app.Activity;
import android.os.Bundle;

import Models.ModelFactory;

public class GraphicActivity extends Activity {

    private MyGlSurfaceView mGlSurfaceView;
    private ModelFactory.ModelType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        type = (ModelFactory.ModelType) getIntent().getExtras().get("type");
        mGlSurfaceView = (MyGlSurfaceView) findViewById(R.id.my_glsurfaceview);
        mGlSurfaceView.setModel(ModelFactory.getModel(this, type));
    }

}
