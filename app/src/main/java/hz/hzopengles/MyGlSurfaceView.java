package hz.hzopengles;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Models.Model;

/**
 * Created by Administrator on 2017-06-20.
 */

public class MyGlSurfaceView extends GLSurfaceView {
    private static final String TAG = MyGlSurfaceView.class.getSimpleName();

    private boolean mIsCompared = false;
    private Model mModel;
    private float mCameraMatrix[], mProjectMatrix[], mMVPMatrix[];

    public MyGlSurfaceView(Context context) {
        this(context, null);
    }

    public MyGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        identityMatrix();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new MyRenderer());
    }

    private void identityMatrix() {
        mCameraMatrix = new float[16];
        mProjectMatrix = new float[16];
        mMVPMatrix = new float[16];

        Matrix.setIdentityM(mCameraMatrix, 0);
        Matrix.setIdentityM(mProjectMatrix, 0);
        Matrix.setIdentityM(mMVPMatrix, 0);
    }

    public void setModel(Model model) {
        this.mModel = model;
        if (mIsCompared) {
            mModel.setup();
        }
    }

    public void doOnEglContext(Runnable r) {
        queueEvent(r);
    }

    public class MyRenderer implements Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mIsCompared = true;
            GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            //启用深度测试,否则三维图形旋转会有透明
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            if (mModel != null) {
                mModel.setup();
            }
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float)height/width;
            Matrix.orthoM(mProjectMatrix, 0, -1, 1, -ratio, ratio, 1, 7);// 3和7代表远近视点与眼睛的距离，非坐标点
            Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);// 3代表眼睛的坐标点
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mCameraMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            if (mModel != null)
                mModel.draw(mMVPMatrix);
        }
    }
}
