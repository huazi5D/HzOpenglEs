package hz.hzopengles;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import Models.BitmapModel;

public class BitmapActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    private MyGlSurfaceView mGlSurfaceView;
    private RadioGroup mRadioGroup;
    private BitmapModel mBitmapModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mGlSurfaceView = (MyGlSurfaceView) findViewById(R.id.mapview);
        mRadioGroup = (RadioGroup) findViewById(R.id.audio_group);

        mGlSurfaceView.setModel(mBitmapModel = new BitmapModel(this));
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioButton0:
                mBitmapModel.setBitmapType(0);
                break;
            case R.id.radioButton1:
                mBitmapModel.setBitmapType(1);
                break;
            case R.id.radioButton2:
                break;
        }
    }
}
