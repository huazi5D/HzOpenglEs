package Models;

import android.content.Context;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ModelFactory {

    public enum ModelType {
        TRIANGLE, SQUARE, CIRCLE, CUBE, CONE, CYLINDER, SPHERE;
    }

    public static Model getModel(Context context, ModelType modelType) {
        Model model = null;
        switch (modelType) {
            case TRIANGLE:
                model = new TriangleModel(context);
                break;
            case SQUARE:
                model = new SquareModel(context);
                break;
            case CIRCLE:
                model = new CircleModel(context);
                break;
            case CUBE:
                model = new CubeModel(context);
                break;
            case CONE:
                model = new ConeModel(context);
                break;
            case CYLINDER:
                model = new CylinderModel(context);
                break;
            case SPHERE:
                model = new SphereModel(context);
                break;
        }

        return model;
    }
}
