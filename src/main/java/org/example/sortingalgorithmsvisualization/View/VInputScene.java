package org.example.sortingalgorithmsvisualization.View;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class VInputScene extends Scene {
    public VInputScene(Parent parent) {
        super(parent);
    }

    public VInputScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
    }

    public VInputScene(Parent parent, Paint paint) {
        super(parent, paint);
    }

    public VInputScene(Parent parent, double v, double v1, boolean b, SceneAntialiasing sceneAntialiasing) {
        super(parent, v, v1, b, sceneAntialiasing);
    }

    public VInputScene(Parent parent, double v, double v1, boolean b) {
        super(parent, v, v1, b);
    }

    public VInputScene(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1, paint);
    }
}
