package gwint;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animations {
    //Hello there, a node appears
    public static void fadeOut(Node obj, long duration) {
        FadeTransition fadeOutTrans=new FadeTransition(Duration.millis(duration),obj);
        fadeOutTrans.setFromValue(1.0);
        fadeOutTrans.setToValue(0.0);
        fadeOutTrans.play();
    }

    //It must have been the wind, a node disappears
    public static void fadeIn(Node obj, long duration) {
        FadeTransition fadeOutTrans=new FadeTransition(Duration.millis(duration),obj);
        fadeOutTrans.setFromValue(0.0);
        fadeOutTrans.setToValue(1.0);
        fadeOutTrans.play();
    }

    //Hulk smash, a node changes its size
    public static void scaleTo(Node obj, double X, double Y, long duration) {
        ScaleTransition scaleTrans=new ScaleTransition(Duration.millis(duration), obj);
        scaleTrans.setToX(X);
        scaleTrans.setToY(Y);
        scaleTrans.play();
    }

    //You spin me right round baby right round, a node spins
    public static void rotateTo(Node obj, double angle, long duration) {
        RotateTransition rotateTrans=new RotateTransition(Duration.millis(duration), obj);
        rotateTrans.setAxis(Rotate.Z_AXIS);
        rotateTrans.setFromAngle(0);
        rotateTrans.setToAngle(angle);
        rotateTrans.setCycleCount(1);
        rotateTrans.play();
    }

    //Smooth opacity change
    public static void setOpacityTo(Node obj, double from, double to, long duration) {
        FadeTransition fadeOutTrans=new FadeTransition(Duration.millis(duration),obj);
        fadeOutTrans.setFromValue(from);
        fadeOutTrans.setToValue(to);
        fadeOutTrans.play();
    }
}
