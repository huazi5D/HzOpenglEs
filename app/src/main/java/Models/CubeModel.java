package Models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Administrator on 2017-06-20.
 */

public class CubeModel extends Model {

    private float[] coords = {-1.0f,  1.0f,  1.0f,
                              -1.0f, -1.0f,  1.0f,
                               1.0f,  1.0f,  1.0f,
                               1.0f, -1.0f,  1.0f,
                               1.0f,  1.0f, -1.0f,
                               1.0f, -1.0f, -1.0f,
                              -1.0f,  1.0f, -1.0f,
                              -1.0f, -1.0f, -1.0f};

    private short[] index= { 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 4, 2, 6, 0, 3, 5, 1, 7};
    private float[] color;
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorsBuffer;
    private ShortBuffer indexBuffer;
    private int uColorHandle;
    private int aPositionHandle;
    private int mMVPMatrixHandle;;

    public CubeModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void draw(float[] matrix) {
        Matrix.rotateM(matrix, 0, 1, 0, 1, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }

    @Override
    public void setup() {
        color = new float[index.length * 4];
        for (int i = 0; i < index.length; i ++) {
            color[i * 4 + 0] = 0.0f;
            color[i * 4 + 1] = 1.0f;
            color[i * 4 + 2] = 0.0f;
            color[i * 4 + 3] = 1.0f;
        }
        vertexBuffer = convertToFloatBuffer(coords);
        colorsBuffer = convertToFloatBuffer(color );
        indexBuffer  = convertToShortBuffer(index );
        mProgram = getProgram("Shader/vertex.glsl", "Shader/fragment.glsl");
        GLES20.glUseProgram(mProgram);

        uColorHandle        = GLES20.glGetAttribLocation (mProgram, "a_Color");
        aPositionHandle     = GLES20.glGetAttribLocation (mProgram, "a_Position");
        mMVPMatrixHandle    = GLES20.glGetUniformLocation(mProgram, "u_Matrix");

        GLES20.glVertexAttribPointer(uColorHandle   , 4, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(uColorHandle);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
    }
}
