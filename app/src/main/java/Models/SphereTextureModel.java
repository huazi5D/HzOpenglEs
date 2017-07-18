package Models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import Utils.AssetsUtils;

/**
 * Created by Administrator on 2017-06-20.
 */

public class SphereTextureModel extends Model {

    private float[] coords;
    private float[] color;
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;
    private int mTextureHandle;
    private float[] mRotateMatrix = new float[16];

    public SphereTextureModel(Context context) {
        this.mContext = context;
        Matrix.setIdentityM(mRotateMatrix, 0);
        Matrix.rotateM(mRotateMatrix, 0, 1, 0, 1, 0);
    }

    @Override
    public void draw(float[] matrix) {
        Matrix.multiplyMM(matrix, 0, matrix, 0, mRotateMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, coords.length / 3);
    }

    @Override
    public void setup() {
        ArrayList<Float> datas = new ArrayList<>();
        ArrayList<Float> colorDatas = new ArrayList<>();
        for (int i = -90; i <= 90; i += 1) {
            for (int j = 0; j <= 360; j ++) {
                datas.add((float) (Math.cos(Math.toRadians(i + 1)) * Math.cos(Math.toRadians(j))));
                datas.add((float) (Math.sin(Math.toRadians(i + 1))));
                datas.add((float) (Math.cos(Math.toRadians(i + 1)) * Math.sin(Math.toRadians(j))));

                datas.add((float) (Math.cos(Math.toRadians(i)) * Math.cos(Math.toRadians(j))));
                datas.add((float) (Math.sin(Math.toRadians(i))));
                datas.add((float) (Math.cos(Math.toRadians(i)) * Math.sin(Math.toRadians(j))));

                colorDatas.add(1 - j / 360f);
                colorDatas.add((180f - (i + 1 + 90)) / 180f);
                colorDatas.add(1 - j / 360f);
                colorDatas.add((180f - (i + 90)) / 180f);
            }
        }

        coords = new float[datas.size()];
        for (int i = 0; i < datas.size(); i ++) {
            coords[i] = datas.get(i);
        }

        color = new float[colorDatas.size()];
        for (int i = 0; i < colorDatas.size(); i ++) {
            color[i] = colorDatas.get(i);
        }

        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        mProgram = getProgram("Shader/vertex_texture.glsl", "Shader/fragment_texture.glsl");
        GLES20.glUseProgram(mProgram);

        uColorHandle        = GLES20.glGetAttribLocation (mProgram, "a_Coordinate");
        aPositionHandle     = GLES20.glGetAttribLocation (mProgram, "a_Position");
        mMVPMatrixHandle    = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        mTextureHandle      = GLES20.glGetUniformLocation(mProgram, "a_Texture");

        GLES20.glVertexAttribPointer(uColorHandle   , 2, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glUniform1i(mTextureHandle, 0);

        createTexture(AssetsUtils.getBitmap(mContext, "png/dqy.png"));

        GLES20.glEnableVertexAttribArray(uColorHandle);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
    }
}
