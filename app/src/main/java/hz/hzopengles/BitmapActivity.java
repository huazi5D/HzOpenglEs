package hz.hzopengles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import Filter.BrightnessFilter;

public class BitmapActivity extends Activity implements SeekBar.OnSeekBarChangeListener,AdapterView.OnItemSelectedListener {

    private MyGlSurfaceView mGlSurfaceView;
    private BrightnessFilter mBrightnessFilter;
    private SeekBar mSeekBar;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mGlSurfaceView = (MyGlSurfaceView) findViewById(R.id.mapview);

        mGlSurfaceView.setModel(mBrightnessFilter = new BrightnessFilter(this));
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(this);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(this);
    }

    /*----------------------------------------------------------------------seekbar----------------------------------------------------------------------------*/

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

    /*----------------------------------------------------------------------seeekbar----------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------spinner----------------------------------------------------------------------------*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((String)mSpinner.getSelectedItem()) {
            case "亮度":

                break;

            case "普通混合":
                mBrightnessFilter.blendRegist();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*----------------------------------------------------------------------spinner----------------------------------------------------------------------------*/
}
