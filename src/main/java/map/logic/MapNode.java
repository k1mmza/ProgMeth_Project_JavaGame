package map.logic;

import enums.RoomType;
import map.Room;

import java.util.HashSet;
import java.util.Set;
import map.Room;
import map.room.RoomFactory;

/**
 * คลาส MapNode แทนโหนดหนึ่งจุดในแผนที่ของเกม
 * <p>
 * แต่ละโหนดจะมีข้อมูล:
 * - ตำแหน่งใน grid (floor, column)
 * - พิกัดสำหรับแสดงผล (x, y)
 * - ประเภทห้อง (RoomType)
 * - อ้างอิงไปยัง Room จริง
 * - สถานะ visited / accessible
 * - รายการโหนดถัดไป (nextNodes)
 * </p>
 *
 * <p>
 * โหนดจะถูกเชื่อมต่อแบบ Directed Graph
 * โดย nextNodes แสดงเส้นทางที่สามารถเดินต่อไปได้
 * </p>
 */
public class MapNode {
    private final int floor;
    private final int column;
    private double x;
    private double y;
    private RoomType type;
    private Room room;
    private boolean visited = false;
    private boolean accessible = false;

    private final Set<MapNode> nextNodes = new HashSet<>();

    /**
     * สร้างโหนดใหม่ในตำแหน่งที่กำหนด
     *
     * @param floor  ชั้นของแผนที่
     * @param column คอลัมน์ในชั้นนั้น
     * @param x      พิกัด X สำหรับแสดงผล
     * @param y      พิกัด Y สำหรับแสดงผล
     */
    public MapNode(int floor, int column, double x, double y) {
        this.floor = floor;
        this.column = column;
        this.x = x;
        this.y = y;
        this.type = null;
    }

    /**
     * เชื่อมต่อโหนดปัจจุบันไปยังโหนดถัดไป
     *
     * @param node โหนดปลายทาง
     */
    public void connect(MapNode node){
        nextNodes.add(node);
    }

    /**
     * ตรวจสอบว่าโหนดนี้ถูกใช้งานในเส้นทางหรือไม่
     * (มีการเชื่อมต่อ หรือเป็น Boss Room)
     *
     * @return true ถ้าโหนดถูกใช้งาน
     */
    public boolean isUsed() {
        return !nextNodes.isEmpty() || type == RoomType.BOSS;
    }

    /**
     * @return ชั้นของโหนด
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return คอลัมน์ของโหนด
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return พิกัด X สำหรับแสดงผล
     */
    public double getX() {
        return x;
    }

    /**
     * @return พิกัด Y สำหรับแสดงผล
     */
    public double getY() {
        return y;
    }

    /**
     * @return ประเภทห้องของโหนด
     */
    public RoomType getType() {
        return type;
    }

    /**
     * กำหนดประเภทห้องของโหนด
     *
     * @param type RoomType ใหม่
     */
    public void setType(RoomType type) {
        this.type = type;
    }

    /**
     * @return รายการโหนดถัดไปที่เชื่อมต่ออยู่
     */
    public Set<MapNode> getNextNodes() {
        return nextNodes;
    }

    /**
     * สร้าง Room ตาม RoomType ที่กำหนด
     * ใช้หลังจากตั้งค่า type แล้ว
     */
    public void createRoomFromType() {
        if (type != null) {
            this.room = new Room(type);
        }
    }

    /**
     * @return Room ที่ผูกกับโหนดนี้
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @return true ถ้าโหนดถูกเล่นแล้ว
     */
    public boolean isVisited() { return visited; }

    /**
     * กำหนดสถานะ visited
     *
     * @param visited ค่าใหม่ของสถานะ
     */
    public void setVisited(boolean visited) { this.visited = visited; }

    /**
     * @return true ถ้าโหนดสามารถเลือกได้
     */
    public boolean isAccessible() { return accessible; }

    /**
     * กำหนดสถานะ accessible
     *
     * @param accessible ค่าใหม่ของสถานะ
     */
    public void setAccessible(boolean accessible) { this.accessible = accessible; }
}
