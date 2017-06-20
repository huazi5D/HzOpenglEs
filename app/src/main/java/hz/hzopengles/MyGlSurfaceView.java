package hz.hzopengles;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Models.Model;
import Models.TriangleModel;

/**
 * Created by Administrator on 2017-06-20.
 */

public class MyGlSurfaceView extends GLSurfaceView {
    private static final String TAG = MyGlSurfaceView.class.getSimpleName();

    private Context mContext;
    private MyRenderer mRenderer;
    private Model mModel;
    private float mCameraMatrix[], mProjectMatrix[], mMVPMatrix[];

    public MyGlSurfaceView(Context context) {
        this(context, null);
    }

    public MyGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        identityMatrix();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(mRenderer = new MyRenderer());
    }

    private void identityMatrix() {
        mCameraMatrix = new float[16];
        mProjectMatrix = new float[16];
        mMVPMatrix = new float[16];

        Matrix.setIdentityM(mCameraMatrix, 0);
        Matrix.setIdentityM(mProjectMatrix, 0);
        Matrix.setIdentityM(mMVPMatrix, 0);
    }

    public class MyRenderer implements Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            mModel = new TriangleModel(mContext);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float)height/width;
            Matrix.frustumM(mProjectMatrix,0,-1,1,-ratio,ratio,3,7);// 3和7代表远近视点与眼睛的距离，非坐标点
            Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);// 3代表眼睛的坐标点
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mCameraMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            mModel.draw(mMVPMatrix);
        }
    }
}
