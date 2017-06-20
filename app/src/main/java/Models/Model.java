package Models;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import Utils.ShaderUtils;

/**
 * Created by Administrator on 2017-06-20.
 */

public abstract class Model {

    protected Context mContext;
    protected int mProgram;

    public abstract void draw(float[] matrix);

    protected FloatBuffer convertToFloatBuffer(float[] buffer) {
        FloatBuffer fb = ByteBuffer.allocateDirect(buffer.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(buffer);
        fb.position(0);
        return fb;
    }

    protected int getProgram(String file1, String file2) {
        return ShaderUtils.createProgram(mContext, file1, file2);
    }

}
