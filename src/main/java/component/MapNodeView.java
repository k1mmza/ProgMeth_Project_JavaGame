package component;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.MapLogic;
import logic.MapNode;
import logic.RoomType;
import util.Constants;

public class MapNodeView extends StackPane {
    private final MapNode node;
    private final ImageView imageView;

    public MapNodeView(MapNode node, MapPane mapPane) {
        this.node = node;

        double radius = (node.getType() == RoomType.BOSS) ? Constants.BOSS_RADIUS : Constants.NODE_RADIUS;
        double size = radius * 2;

        // บังคับขนาด StackPane ให้เป๊ะเท่ากับวงกลม (ป้องกันเส้นเบี้ยว)
        this.setMinSize(size, size);
        this.setMaxSize(size, size);

        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);

        Image icon = getImageByType(node.getType());
        if (icon != null) {
            imageView.setImage(icon);
        }

        getChildren().add(imageView);

        setOnMouseEntered(e -> {
            if (MapLogic.canSelect(node)) {
                imageView.setFitWidth(size + 6);
                imageView.setFitHeight(size + 6);
            }
        });

        setOnMouseExited(e -> {
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
        });

        setOnMouseClicked(event -> {
            if (MapLogic.canSelect(node)) {
                MapLogic.setCurrentNode(node);
                mapPane.refreshNodes();
            }
        });
    }

    public void updateVisuals() {
        if (MapLogic.getCurrentNode() == node) {
            DropShadow glow = new DropShadow(20, Color.ORANGE);
            imageView.setEffect(glow);
            this.setOpacity(1.0);
        } else if (MapLogic.canSelect(node)) {
            imageView.setEffect(null);
            this.setOpacity(1.0);
        } else {
            imageView.setEffect(null);
            this.setOpacity(0.35);
        }
    }

    private Image getImageByType(RoomType type) {
        if (type == null) return null;

        String path = "";
        switch (type) {
            case ENEMY: path = "/Enemy.png"; break;
            case ELITE: path = "/Elite.png"; break;
            case SHOP: path = "/Shop.png"; break;
            case REST: path = "/Rest.png"; break;
            case BOSS: path = "/Boss.png"; break;
            case TREASURE: path = "/Treasure.png"; break;
            case EVENT: path = "/Event.png"; break;
        }

        try {
            return new Image(getClass().getResourceAsStream(path), 60, 60, true, true);
        } catch (Exception e) {
            System.out.println("ไม่พบไฟล์รูปภาพ: " + path + " โปรดตรวจสอบชื่อและโฟลเดอร์ให้ถูกต้อง");
            return null;
        }
    }
}