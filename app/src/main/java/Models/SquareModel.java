package Models;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by Administrator on 2017-06-20.
 */

public class SquareModel extends Model {

    private float[] coords = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f};
    private float[] color = { 1.0f, 0.0f, 0.0f, 1.0f,
                              0.0f, 1.0f, 0.0f, 1.0f,
                              0.0f, 0.0f, 1.0f, 1.0f,
                              0.0f, 0.0f, 0.0f, 1.0f };
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;;

    public SquareModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        /**
         * 参数1：有三种取值
         * 1.GL_TRIANGLES：每三个顶之间绘制三角形，之间不连接
         * 2.GL_TRIANGLE_FAN：以V0V1V2,V0V2V3,V0V3V4，……的形式绘制三角形
         * 3.GL_TRIANGLE_STRIP：顺序在每三个顶点之间均绘制三角形。这个方法可以保证从相同的方向上所有三角形均被绘制。以V0V1V2,V1V2V3,V2V3V4……的形式绘制三角形
         * 参数2：从数组缓存中的哪一位开始绘制，一般都定义为0
         * 参数3：顶点的数量
         * */
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, coords.length / 2);
    }

    @Override
    public void setup() {
        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        mProgram = getProgram("vertex.glsl", "fragment.glsl");
        GLES20.glUseProgram(mProgram);

        uColorHandle        = GLES20.glGetAttribLocation (mProgram, "a_Color");
        aPositionHandle     = GLES20.glGetAttribLocation (mProgram, "a_Position");
        mMVPMatrixHandle    = GLES20.glGetUniformLocation(mProgram, "u_Matrix");

        GLES20.glVertexAttribPointer(uColorHandle   , 4, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(uColorHandle);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
    }
}
