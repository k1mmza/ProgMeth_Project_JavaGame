package component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConnectionLine extends Line {

    public ConnectionLine(double startX, double startY, double endX, double endY){
        super(startX,startY,endX,endY);
        setStroke(Color.GRAY);
        setStrokeWidth(2);
        getStrokeDashArray().addAll(6d, 6d);
    }
}
