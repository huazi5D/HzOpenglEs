package hz.hzopengles;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import FBO.FboView;
import Utils.SDCardUtils;

public class FboActivity extends Activity implements FboView.FBOCallback{

    private FboView mFboView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbo);
        mFboView = (FboView) findViewById(R.id.fbo_view);
        mFboView.setCallback(this);
    }

    @Override
    public void onCall(final ByteBuffer data, final int width, final int height) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("wuwang","callback success");
                Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(data);
                saveBitmap(bitmap);
                data.clear();
            }
        }).start();
    }


    //图片保存
    public void saveBitmap(final Bitmap b){
        String path = SDCardUtils.getSDCardPath() + "aaa/";
        File folder=new File(path);
        if(!folder.exists()&&!folder.mkdirs()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FboActivity.this, "无法保存照片", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        long dataTake = System.currentTimeMillis();
        final String jpegName=path+ dataTake +".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FboActivity.this, "保存成功->"+jpegName, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
