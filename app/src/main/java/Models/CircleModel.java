package Models;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by Administrator on 2017-06-20.
 */

public class CircleModel extends Model {

    private float[] coords = new float[360 * 2];
    private float[] color= new float[360 * 4];
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;;

    public CircleModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, coords.length / 2);
    }

    @Override
    public void setup() {
        for (int i = 0; i < 360; i ++) {
            coords[i * 2 + 0] = (float) Math.cos(i);
            coords[i * 2 + 1] = (float) Math.sin(i);

            color[i * 4 + 0]  = 0.0f;
            color[i * 4 + 1]  = 1.0f;
            color[i * 4 + 2]  = 0.0f;
            color[i * 4 + 3]  = 1.0f;
        }
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
