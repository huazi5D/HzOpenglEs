package Models;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by Administrator on 2017-06-20.
 */

public class TriangleModel extends Model {

    private float coords[] = {0.0f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f};
    private float[] color= { 0.0f, 1f, 0f, 1.0f};
    private FloatBuffer vertexBuffer;
    private int uColorLocation;
    private int aPositionLocation;
    private int mMVPMatrixHandle;;

    public TriangleModel(Context context) {
        this.mContext = context;
        vertexBuffer = convertToFloatBuffer(coords);
        mProgram = getProgram("vertex.glsl", "fragment.glsl");

        uColorLocation = GLES20.glGetUniformLocation(mProgram, "u_Color");
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, "a_Position");
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);

        GLES20.glUniform4fv(uColorLocation, 1, color, 0);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, coords.length);
    }
}
