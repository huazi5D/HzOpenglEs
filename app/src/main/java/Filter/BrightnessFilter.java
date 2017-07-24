package Filter;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import Models.Model;
import Utils.AssetsUtils;

/**
 * Created by Administrator on 2017-07-18.
 */

public class BrightnessFilter extends Model{

    private float[] coords = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f};
    private float[] color = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;
    private int mTextureHandle;
    private int mTexture2Handle;
    private int mBitmapTypeHandle;
    private float mPercent = 0;

    public BrightnessFilter(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);
        GLES20.glUniform1f(mBitmapTypeHandle, mPercent);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, coords.length / 2);
    }

    @Override
    public void setup() {
        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        mProgram = getProgram("FilterShader/vertex_texture.glsl", "FilterShader/fragment_texture.glsl");
        GLES20.glUseProgram(mProgram);

        uColorHandle        = GLES20.glGetAttribLocation (mProgram, "a_Coordinate");
        aPositionHandle     = GLES20.glGetAttribLocation (mProgram, "a_Position");
        mMVPMatrixHandle    = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        mTextureHandle      = GLES20.glGetUniformLocation(mProgram, "a_Texture");
        mBitmapTypeHandle   = GLES20.glGetUniformLocation(mProgram, "percent");

        GLES20.glVertexAttribPointer(uColorHandle   , 2, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glUniform1i(mTextureHandle, 0);//GL_TEXTURE0
        GLES20.glUniform1f(mBitmapTypeHandle, 1);

        createTexture(AssetsUtils.getBitmap(mContext, "png/maomi.png"));

        GLES20.glEnableVertexAttribArray(uColorHandle);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
    }

    public void setPercent(float percent) {
        this.mPercent = percent;
    }

    public void blendRegist() {
        mTexture2Handle     = GLES20.glGetUniformLocation(mProgram, "a_Texture");
        GLES20.glUniform1i(mTextureHandle, 1);//GL_TEXTURE1
        createTexture(AssetsUtils.getBitmap(mContext, "png/maomi.png"));

    }

}
