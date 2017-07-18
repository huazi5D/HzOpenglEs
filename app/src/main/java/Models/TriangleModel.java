package Models;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by Administrator on 2017-06-20.
 */

public class TriangleModel extends Model {

    private float[] coords = {0.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f};
    private float[] color= { 0.0f, 1f, 0f, 1.0f, 0.0f, 1f, 0f, 1.0f, 0.0f, 1f, 0f, 1.0f};
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;;

    public TriangleModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.length / 2);
    }

    @Override
    public void setup() {
        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        mProgram = getProgram("Shader/vertex.glsl", "Shader/fragment.glsl");
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
