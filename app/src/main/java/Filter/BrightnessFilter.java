package Filter;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import Models.Model;
import Utils.AssetsUtils;
import Utils.ShaderHandles;

/**
 * Created by Administrator on 2017-07-18.
 */

public class BrightnessFilter extends Model{

    private float[] coords = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f};
    private float[] color = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private ShaderHandles mShaderHandles = new ShaderHandles();

    public BrightnessFilter(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUniformMatrix4fv(mShaderHandles.mMVPMatrixHandle, 1, false, matrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, coords.length / 2);
    }

    @Override
    public void setup() {
        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        mProgram = getProgram("FilterShader/vertex_texture.glsl", "FilterShader/fragment_texture.glsl");
        GLES20.glUseProgram(mProgram);

        mShaderHandles.uColorHandle        = GLES20.glGetAttribLocation (mProgram, "a_Coordinate");
        mShaderHandles.aPositionHandle     = GLES20.glGetAttribLocation (mProgram, "a_Position");
        mShaderHandles.mMVPMatrixHandle    = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        mShaderHandles.mTextureHandle      = GLES20.glGetUniformLocation(mProgram, "a_Texture");
        mShaderHandles.mBrightnessHandle   = GLES20.glGetUniformLocation(mProgram, "percent");
        mShaderHandles.mFilterTypeHandle   = GLES20.glGetUniformLocation(mProgram, "type");

        GLES20.glVertexAttribPointer(mShaderHandles.uColorHandle   , 2, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(mShaderHandles.aPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        createTexture(AssetsUtils.getBitmap(mContext, "png/dm.png"));
        GLES20.glUniform1i(mShaderHandles.mTextureHandle, 0);//GL_TEXTURE0
        GLES20.glUniform1f(mShaderHandles.mBrightnessHandle, 1);

        GLES20.glEnableVertexAttribArray(mShaderHandles.uColorHandle);
        GLES20.glEnableVertexAttribArray(mShaderHandles.aPositionHandle);

        // 默认亮度不变
        setPercent(0);
    }

    public void setType(int type) {
        GLES20.glUniform1i(mShaderHandles.mFilterTypeHandle, type);
    }

    public void setPercent(float percent) {
        GLES20.glUniform1f(mShaderHandles.mBrightnessHandle, percent);
    }

    public void blendRegist() {
        mShaderHandles.mTexture2Handle     = GLES20.glGetUniformLocation(mProgram, "a_Texture1");
        GLES20.glUniform1i(mShaderHandles.mTexture2Handle, 1);//GL_TEXTURE1
        createTexture(AssetsUtils.getBitmap(mContext, "png/text.png"), GLES20.GL_TEXTURE1);
    }

}
