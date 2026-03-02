package component;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * คลาสเส้นเชื่อมระหว่างโหนดบนแผนที่
 * <p>
 * ใช้สำหรับแสดงเส้นเชื่อมระหว่างห้อง (MapNode) แต่ละชั้นของแผนที่
 * โดยกำหนดรูปแบบเส้นให้เป็นเส้นประ สีเทา เพื่อให้มองเห็นความเชื่อมโยง
 * ระหว่างโหนดอย่างชัดเจน
 * </p>
 */
public class ConnectionLine extends Line {

    /**
     * สร้างเส้นเชื่อมระหว่างจุดเริ่มต้นและจุดปลาย
     * <p>
     * เส้นที่สร้างจะมีลักษณะเป็นเส้นประ (dashed line)
     * สีเทา และมีความหนา 2 พิกเซล
     * </p>
     *
     * @param startX พิกัดแกน X ของจุดเริ่มต้น
     * @param startY พิกัดแกน Y ของจุดเริ่มต้น
     * @param endX   พิกัดแกน X ของจุดปลาย
     * @param endY   พิกัดแกน Y ของจุดปลาย
     */
    public ConnectionLine(double startX, double startY, double endX, double endY){
        super(startX, startY, endX, endY);
        setStroke(Color.GRAY);
        setStrokeWidth(2);
        getStrokeDashArray().addAll(6d, 6d);
    }
}