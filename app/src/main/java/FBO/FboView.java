package FBO;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Utils.AssetsUtils;
import Utils.ShaderHandles;
import Utils.ShaderUtils;

/**
 * Created by Administrator on 2017-07-25.
 */

public class FboView extends GLSurfaceView {

    private Context mContext;
    private Bitmap mBitmap;

    public FboView(Context context) {
        this(context, null);
    }

    public FboView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(new FboRender());
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    class FboRender implements Renderer {

        private String vertex = "uniform mat4 u_Matrix;\n" +
                "attribute vec4 a_Position;\n" +
                "attribute vec2 a_Coordinate;\n" +
                "varying   vec2 v_Coordinate;\n\n" +
                "void main() {\n" +
                "   gl_Position = u_Matrix * a_Position;\n" +
                "   v_Coordinate = a_Coordinate;\n" +
                "}";

        private String fragment = "precision mediump float;\n" +
                "uniform sampler2D a_Texture;\n" +
                "varying vec2 v_Coordinate;\n\n" +
                "void main() {\n" +
                "   gl_FragColor = texture2D(a_Texture, v_Coordinate);\n" +
                "}";

        private int mProgram;

        private float[] mMVPMatrix = new float[16];
        private float[] mProjectionMatrix = new float[16];
        private float[] mModelViewMatrix = new float[16];

        private float[] mVertexCoordinate = {-1, 1, -1, -1, 1, 1, 1, -1};
        private float[] mTextureCoordinate = {0, 0, 0, 1, 1, 0, 1, 1};

        private FloatBuffer mVertexBuffer;
        private FloatBuffer mTextureBuffer;
        private ByteBuffer mBuffer;

        private int mTextureId;
        private int mFBOTextureId;

        private ShaderHandles mShaderHandles = new ShaderHandles();

        public FboRender() {
            Matrix.setIdentityM(mMVPMatrix, 0);
            Matrix.setIdentityM(mProjectionMatrix, 0);
            Matrix.setIdentityM(mModelViewMatrix, 0);

            mVertexBuffer = convertToFloatBuffer(mVertexCoordinate);
            mTextureBuffer = convertToFloatBuffer(mTextureCoordinate);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            mProgram = ShaderUtils.createProgram(vertex, fragment);
            GLES20.glUseProgram(mProgram);

            mShaderHandles.mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
            mShaderHandles.aPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
            mShaderHandles.uColorHandle = GLES20.glGetAttribLocation(mProgram, "a_Coordinate");
            mShaderHandles.mTextureHandle = GLES20.glGetUniformLocation(mProgram, "a_Texture");

            mBitmap = AssetsUtils.getBitmap(mContext, "png/dm.png");
            mTextureId = createTexture(mBitmap);

            GLES20.glUniformMatrix4fv(mShaderHandles.mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
            GLES20.glVertexAttribPointer(mShaderHandles.aPositionHandle, 2, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
            GLES20.glVertexAttribPointer(mShaderHandles.uColorHandle, 2, GLES20.GL_FLOAT, false, 0, mTextureBuffer);
            GLES20.glUniform1i(mShaderHandles.mTextureHandle, 0);

            GLES20.glEnableVertexAttribArray(mShaderHandles.uColorHandle);
            GLES20.glEnableVertexAttribArray(mShaderHandles.aPositionHandle);

            mFBOTextureId = createTextureWithFBO(mBitmap.getWidth(), mBitmap.getHeight());
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);

            float ratio = (float)height/width;
            Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -ratio, ratio, 1, 7);// 3和7代表远近视点与眼睛的距离，非坐标点
            Matrix.setLookAtM(mModelViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);// 3代表眼睛的坐标点
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mModelViewMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
//            GLES20.glUniform1i(mShaderHandles.mTextureHandle, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mVertexCoordinate.length / 2);

        }

        public int createTexture(Bitmap bitmap) {
            int[] texture=new int[1];
            if(bitmap!=null&&!bitmap.isRecycled()){
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glGenTextures(1,texture,0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
                return texture[0];
            }
            return 0;
        }

        private int mFrameBufferId;
        private int mRenderBufferId;

        public int createTextureWithFBO(int width, int height) {
            int[] texture=new int[1];
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glGenTextures(1,texture,0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

            int[] framebuffer = new int[1];
            GLES20.glGenFramebuffers(1, framebuffer, 0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffer[0]);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texture[0], 0);
            mFrameBufferId = framebuffer[0];

            int[] renderBuffer = new int[1];
            GLES20.glGenRenderbuffers(1, renderBuffer, 0);
            GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, renderBuffer[0]);
            GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height);
            GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER,renderBuffer[0]);
            mRenderBufferId = renderBuffer[0];

            int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
            if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
                throw new RuntimeException(this+": Failed to set up render buffer with status "+status+" and error "+GLES20.glGetError());
            }
            unbindFBO();

            mBuffer = ByteBuffer.allocate(mBitmap.getWidth() * mBitmap.getHeight() * 4);

            return texture[0];
        }


        public void bindFBO() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mFBOTextureId, 0);
            GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, mRenderBufferId);
        }

        public void unbindFBO() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        }

    }

    public FloatBuffer convertToFloatBuffer(float[] buffer) {
        FloatBuffer fb = ByteBuffer.allocateDirect(buffer.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(buffer);
        fb.position(0);
        return fb;
    }

    private FBOCallback mCallback;

    public void setCallback(FBOCallback callback){
        this.mCallback=callback;
    }

    public interface FBOCallback{
        void onCall(ByteBuffer data, int width, int height);
    }

}
