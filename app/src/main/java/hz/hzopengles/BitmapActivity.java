package hz.hzopengles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import Filter.BrightnessFilter;

public class BitmapActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private MyGlSurfaceView mGlSurfaceView;
    private BrightnessFilter mBrightnessFilter;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mGlSurfaceView = (MyGlSurfaceView) findViewById(R.id.mapview);

        mGlSurfaceView.setModel(mBrightnessFilter = new BrightnessFilter(this));
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mBrightnessFilter.setPercent((float) progress / 50 - 1f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
