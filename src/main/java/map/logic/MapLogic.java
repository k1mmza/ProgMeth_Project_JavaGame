package map.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * คลาส MapLogic ทำหน้าที่ควบคุมตรรกะการเลือกและปลดล็อกโหนดในแผนที่
 * <p>
 * ระบบจะจัดการโหนดปัจจุบัน (current node),
 * ตรวจสอบว่าโหนดสามารถเลือกได้หรือไม่
 * และควบคุมการปลดล็อกเส้นทางหลังจากผู้เล่นเล่นจบในโหนดหนึ่ง
 * </p>
 *
 * <p>
 * หลักการทำงาน:
 * - เมื่อผู้เล่นเลือกโหนดหนึ่ง จะถูกตั้งเป็น currentNode
 * - เมื่อเล่นจบ จะเรียก completeCurrentNode()
 * - ระบบจะ:
 *   1. ทำให้โหนดนั้นเป็น visited
 *   2. ล็อกโหนดอื่นในชั้นเดียวกัน
 *   3. ปลดล็อกเฉพาะโหนดถัดไป (nextNodes)
 * </p>
 */
public class MapLogic {
    private static MapNode currentNode;
    private static List<MapNode> allNodes = new ArrayList<>();

    /**
     * กำหนดโหนดปัจจุบันที่ผู้เล่นเลือก
     *
     * @param node โหนดที่เลือก
     */
    public static void setCurrentNode(MapNode node) {
        currentNode = node;
    }

    /**
     * คืนค่าโหนดปัจจุบัน
     *
     * @return current MapNode
     */
    public static MapNode getCurrentNode() {
        return currentNode;
    }

    /**
     * ตรวจสอบว่าโหนดสามารถเลือกได้หรือไม่
     *
     * เงื่อนไข:
     * - ต้อง accessible
     * - ต้องยังไม่ถูก visited
     *
     * @param node โหนดที่ต้องการตรวจสอบ
     * @return true ถ้าเลือกได้
     */
    public static boolean canSelect(MapNode node) {
        return node.isAccessible() && !node.isVisited();
    }

    /**
     * ทำงานหลังจากผู้เล่นเล่นโหนดปัจจุบันเสร็จ
     *
     * ขั้นตอน:
     * 1. ทำให้ currentNode เป็น visited
     * 2. ล็อกโหนดอื่นทั้งหมดใน floor เดียวกัน
     * 3. เปิดให้เลือกเฉพาะ nextNodes
     */
    public static void completeCurrentNode() {

        if (currentNode == null) return;

        // 1️⃣ ทำให้ node ปัจจุบันเป็น visited
        currentNode.setVisited(true);
        currentNode.setAccessible(false);

        int currentFloor = currentNode.getFloor();

        // 2️⃣ ล็อก node อื่นใน floor เดียวกันทั้งหมด
        for (MapNode node : allNodes) {
            if (node.getFloor() == currentFloor && node != currentNode) {
                node.setAccessible(false);
            }
        }

        // 3️⃣ เปิดเฉพาะ nextNodes
        for (MapNode next : currentNode.getNextNodes()) {
            if (!next.isVisited()) {
                next.setAccessible(true);
            }
        }
    }

    /**
     * รีเซ็ตสถานะ MapLogic
     * ใช้ตอนเริ่มเกมใหม่หรือสร้างแผนที่ใหม่
     */
    public static void reset() {
        currentNode = null;
    }

    /**
     * กำหนดรายการโหนดทั้งหมดของแผนที่
     * ใช้สำหรับควบคุมการล็อก/ปลดล็อกโหนด
     *
     * @param nodes รายการโหนดทั้งหมด
     */
    public static void setAllNodes(List<MapNode> nodes) {
        allNodes.clear();
        allNodes.addAll(nodes);
    }
}
