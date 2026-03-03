package map.room;

import Entity.Player;
import Entity.enemy.Enemy;
import inventory.potion.Potion;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import map.Room;

import java.util.Iterator;
import java.util.List;
import javafx.animation.ParallelTransition;

public class EnemyRoomScene {

    public static Scene create(Room room, Player player, Runnable onComplete) {

        List<Enemy> enemies = room.getEnemies();
        final Enemy[] selectedEnemy = {null};

        // ===== ROOT (StackPane for Overlay) =====
        StackPane root = new StackPane();

        // ===== BACKGROUND IMAGE =====
        javafx.scene.image.Image bgImage =
                new javafx.scene.image.Image(
                        EnemyRoomScene.class.getResourceAsStream("/images/enemy_room.png")
                );

        javafx.scene.image.ImageView bgView = new javafx.scene.image.ImageView(bgImage);
        bgView.setPreserveRatio(false);

        // Make background auto resize with window
        bgView.fitWidthProperty().bind(root.widthProperty());
        bgView.fitHeightProperty().bind(root.heightProperty());

        // ===== MAIN LAYOUT =====
        BorderPane mainLayout = new BorderPane();
        mainLayout.setBackground(null); // remove dark background

        // Add background FIRST, then UI
        root.getChildren().addAll(bgView, mainLayout);

        HBox combatArea = new HBox(50);
        combatArea.setAlignment(Pos.CENTER);
        mainLayout.setCenter(combatArea);

        // ===== PLAYER PANEL =====
        VBox playerPanel = new VBox(20);
        playerPanel.setAlignment(Pos.CENTER);
        playerPanel.setPadding(new Insets(30));

        javafx.scene.image.ImageView playerImageView = new javafx.scene.image.ImageView();
        javafx.scene.image.Image pImage = getPlayerImage(player);
        if (pImage != null) {
            playerImageView.setImage(pImage);
            playerImageView.setFitHeight(200);
            playerImageView.setPreserveRatio(true);
        }

        Label playerName = new Label(player.getName());
        playerName.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        ProgressBar playerHpBar = new ProgressBar();
        playerHpBar.setPrefWidth(200);
        Label playerHpText = new Label();
        playerHpText.setTextFill(Color.WHITE);
        playerHpText.setStyle("-fx-font-size: 14px;");

        Label energyLabel = new Label();
        energyLabel.setTextFill(Color.WHITE);
        energyLabel.setStyle("-fx-font-size: 14px;");

        playerPanel.getChildren().addAll(playerImageView, playerName, playerHpBar, playerHpText, energyLabel);

        // ===== ENEMY PANEL =====
        HBox enemyPanel = new HBox(30);
        enemyPanel.setAlignment(Pos.CENTER);
        enemyPanel.setPadding(new Insets(30));

        combatArea.getChildren().addAll(playerPanel, enemyPanel);

        // ===== ACTION PANEL =====
        HBox actionPanel = new HBox(15);
        actionPanel.setAlignment(Pos.CENTER);
        actionPanel.setStyle("-fx-padding: 20; -fx-background-color: #333;");

        Button attackBtn = new Button("Attack");
        Button blockBtn = new Button("Block");
        Button focusBtn = new Button("Focus");
        Button skillBtn = new Button("Skill");
        Button itemBtn = new Button("Item");

        actionPanel.getChildren().addAll(
                attackBtn, blockBtn, focusBtn, skillBtn, itemBtn
        );

        mainLayout.setBottom(actionPanel);

        // ===== SKILL POPUP =====
        VBox skillPopup = new VBox(10);
        skillPopup.setAlignment(Pos.CENTER);
        skillPopup.setStyle("-fx-background-color: #222; -fx-padding: 15; -fx-border-color: #555;");
        skillPopup.setMaxSize(200, 200);
        skillPopup.setVisible(false);

        Button s1 = new Button();
        Button s2 = new Button();
        Button s3 = new Button();
        Button closeSkillBtn = new Button("Close");
        closeSkillBtn.setOnAction(e -> skillPopup.setVisible(false));

        skillPopup.getChildren().addAll(s1, s2, s3, closeSkillBtn);
        root.getChildren().add(skillPopup);

        // ===== ITEM POPUP =====
        VBox itemPopup = new VBox(10);
        itemPopup.setAlignment(Pos.CENTER);
        itemPopup.setStyle("-fx-background-color: #222; -fx-padding: 15; -fx-border-color: #555;");
        itemPopup.setMaxSize(200, 250);
        itemPopup.setVisible(false);

        Button closeItemBtn = new Button("Close");
        closeItemBtn.setOnAction(e -> itemPopup.setVisible(false));

        root.getChildren().add(itemPopup);

        // ===== INITIAL UI =====
        updateUI(player, enemies, selectedEnemy,
                playerHpBar, playerHpText, energyLabel, enemyPanel);

        // ===== SKILL TEXT =====
        s1.setText(player.getSkill1Name() + " (Cost: " + player.getSkill1Cost() + ")");
        s2.setText(player.getSkill2Name() + " (Cost: " + player.getSkill2Cost() + ")");
        s3.setText(player.getSkill3Name() + " (Cost: " + player.getSkill3Cost() + ")");

        // ===== BUTTON EVENTS =====
        skillBtn.setOnAction(e -> {
            itemPopup.setVisible(false);
            skillPopup.setVisible(!skillPopup.isVisible());
        });

        itemBtn.setOnAction(e -> {
            skillPopup.setVisible(false);
            updateItemPopup(player, itemPopup, selectedEnemy, enemies, room, onComplete,
                    playerHpBar, playerHpText, energyLabel, enemyPanel, actionPanel, root); // ส่ง actionPanel เข้าไปคุมแทน
            itemPopup.setVisible(!itemPopup.isVisible());
        });

        // ===== ATTACK =====
        attackBtn.setOnAction(e -> {
            final Enemy target = selectedEnemy[0];
            if (target == null) return;

            itemPopup.setVisible(false);
            skillPopup.setVisible(false);
            actionPanel.setDisable(true);

            playAttackAnimation(playerPanel, true, () -> {
                player.normalAttack(target);
                endPlayerTurn(player, enemies, room, onComplete,
                        selectedEnemy,
                        playerHpBar, playerHpText, energyLabel,
                        enemyPanel,
                        actionPanel,
                        root);
            });


        });

        // ===== BLOCK =====
        blockBtn.setOnAction(e -> {
            player.block();

            itemPopup.setVisible(false);
            skillPopup.setVisible(false);

            endPlayerTurn(player, enemies, room, onComplete,
                    selectedEnemy,
                    playerHpBar, playerHpText, energyLabel,
                    enemyPanel,
                    actionPanel,
                    root);
        });

        // ===== FOCUS =====
        focusBtn.setOnAction(e -> {
            player.focus();

            itemPopup.setVisible(false);
            skillPopup.setVisible(false);

            endPlayerTurn(player, enemies, room, onComplete,
                    selectedEnemy,
                    playerHpBar, playerHpText, energyLabel,
                    enemyPanel,
                    actionPanel,
                    root);
        });

        // ===== SKILLS =====
        s1.setOnAction(e -> {
            final Enemy target = selectedEnemy[0];
            if (target == null) return;
            if (player.getEnergy() < player.getSkill1Cost()) return;
            skillPopup.setVisible(false);
            actionPanel.setDisable(true);
            playAttackAnimation(playerPanel, true, () -> {
                if (player.skill1(target)) {
                    endPlayerTurn(player, enemies, room, onComplete,
                            selectedEnemy,
                            playerHpBar, playerHpText, energyLabel,
                            enemyPanel,
                            actionPanel,
                            root);
                } else {
                    actionPanel.setDisable(false);
                }
            });
        });

        s2.setOnAction(e -> {
            final Enemy target = selectedEnemy[0];
            if (target == null) return;
            if (player.getEnergy() < player.getSkill2Cost()) return;
            skillPopup.setVisible(false);
            actionPanel.setDisable(true);
            playAttackAnimation(playerPanel, true, () -> {
                if (player.skill2(target)) {
                    endPlayerTurn(player, enemies, room, onComplete,
                            selectedEnemy,
                            playerHpBar, playerHpText, energyLabel,
                            enemyPanel,
                            actionPanel,
                            root);
                } else {
                    actionPanel.setDisable(false);
                }
            });
        });

        s3.setOnAction(e -> {
            final Enemy target = selectedEnemy[0];
            if (target == null) return;
            if (player.getEnergy() < player.getSkill3Cost()) return;
            skillPopup.setVisible(false);
            actionPanel.setDisable(true);
            playAttackAnimation(playerPanel, true, () -> {
                if (player.skill3(target)) {
                    endPlayerTurn(player, enemies, room, onComplete,
                            selectedEnemy,
                            playerHpBar, playerHpText, energyLabel,
                            enemyPanel,
                            actionPanel,
                            root);
                } else {
                    actionPanel.setDisable(false);
                }
            });
        });

        return new Scene(root, 1024, 768);
    }

    // ================= Animation =================
    private static void playAttackAnimation(Node node, boolean isPlayer, Runnable onFinished) {
        TranslateTransition dash = new TranslateTransition(Duration.seconds(0.15), node);

        dash.setByX(isPlayer ? 60 : -60);

        dash.setAutoReverse(true);
        dash.setCycleCount(2);

        dash.setOnFinished(e -> {
            if (onFinished != null) onFinished.run();
        });

        dash.play();
    }

    // ================= TURN SYSTEM =================

    private static void endPlayerTurn(
            Player player, List<Enemy> enemies, Room room, Runnable onComplete,
            Enemy[] selectedEnemy, ProgressBar playerHpBar, Label playerHpText, Label energyLabel,
            HBox enemyPanel, HBox actionPanel, StackPane root
    ) {
        int goldEarned = removeDead(enemies, player);

        if (goldEarned > 0) {
            showGoldEffect(root, goldEarned);
        }

        updateUI(player, enemies, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel);

        if (enemies.isEmpty()) {
            room.setCleared(true);

            actionPanel.setDisable(false);

            actionPanel.getChildren().clear();
            Button victoryBtn = new Button("Victory! Leave");
            victoryBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");
            victoryBtn.setOnAction(e -> onComplete.run());
            actionPanel.getChildren().add(victoryBtn);
            return;
        }

        // ปิดปุ่มผู้เล่น ป้องกันการกดสแปมตอนศัตรูกำลังตี
        actionPanel.setDisable(true);

        // เริ่มให้ศัตรูตีทีละตัว
        processEnemyTurn(0, player, enemies, room, onComplete, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel, actionPanel, root);
    }

    private static void processEnemyTurn(
            int index, Player player, List<Enemy> enemies, Room room, Runnable onComplete,
            Enemy[] selectedEnemy, ProgressBar playerHpBar, Label playerHpText, Label energyLabel,
            HBox enemyPanel, HBox actionPanel, StackPane root
    ) {
        // ถ้าศัตรูตีจบครบทุกตัวแล้ว
        if (index >= enemies.size()) {
            int goldEarned = removeDead(enemies, player);

            if (goldEarned > 0) {
                showGoldEffect(root, goldEarned);
            }
            if (!player.isAlive()) {
                showGameOver(root, actionPanel);
                return;
            }

            // วนกลับมาเริ่มเทิร์นผู้เล่น
            player.startTurn();
            updateUI(player, enemies, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel);

            // เปิดปุ่มคืนให้ผู้เล่นกดได้
            actionPanel.setDisable(false);
            return;
        }

        Enemy currentEnemy = enemies.get(index);

        if (currentEnemy.isAlive() && player.isAlive()) {

            // สร้างดีเลย์
            PauseTransition waitBeforeAttack = new PauseTransition(Duration.seconds(0.5));
            waitBeforeAttack.setOnFinished(e -> {

                // ค้นหาการ์ดของศัตรูตัวนี้เพื่อทำอนิเมชัน
                if (index < enemyPanel.getChildren().size()) {
                    Node enemyCardNode = enemyPanel.getChildren().get(index);

                    // สั่งเล่นอนิเมชันศัตรูพุ่งไปตีผู้เล่น
                    playAttackAnimation(enemyCardNode, false, () -> {
                        // พอพุ่งเสร็จค่อยลดเลือดเรา
                        currentEnemy.performAction(player);
                        // ===== Screen Shake Trigger =====
                        String enemyName = currentEnemy.getClass().getSimpleName().toLowerCase();
                        if (enemyName.equals("irongladiator")) {
                            screenShake(root);
                        }
                        updateUI(player, enemies, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel);

                        if (!player.isAlive()) {
                            showGameOver(root, actionPanel);
                            return;
                        }

                        // เรียกตัวต่อไปออกมาตี
                        processEnemyTurn(index + 1, player, enemies, room, onComplete, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel, actionPanel, root);
                    });
                }
            });
            waitBeforeAttack.play();

        } else {
            // ข้ามไปตัวต่อไปทันที
            processEnemyTurn(index + 1, player, enemies, room, onComplete, selectedEnemy, playerHpBar, playerHpText, energyLabel, enemyPanel, actionPanel, root);
        }
    }

    private static void showGameOver(StackPane root, HBox actionPanel) {
        VBox loseBox = new VBox(20);
        loseBox.setAlignment(Pos.CENTER);
        loseBox.setStyle("-fx-background-color: rgba(0,0,0,0.9);");

        Label loseLabel = new Label("YOU LOSE");
        loseLabel.setStyle("-fx-text-fill: red; -fx-font-size: 50px; -fx-font-weight: bold;");

        Button exitBtn = new Button("Exit Game");
        exitBtn.setStyle("-fx-font-size: 20px;");
        exitBtn.setOnAction(ev -> System.exit(0));

        loseBox.getChildren().addAll(loseLabel, exitBtn);
        root.getChildren().add(loseBox);
        actionPanel.setDisable(true);
    }

    private static int removeDead(List<Enemy> enemies, Player player) {

        int totalGold = 0;

        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();

            if (!enemy.isAlive()) {

                int reward = enemy.getGoldReward();
                player.addGold(reward);

                totalGold += reward;

                iterator.remove();
            }
        }

        return totalGold;
    }

    // ================= ITEM PANEL =================

    private static void updateItemPopup(
            Player player,
            VBox itemPopup,
            Enemy[] selectedEnemy,
            List<Enemy> enemies,
            Room room,
            Runnable onComplete,
            ProgressBar playerHpBar,
            Label playerHpText,
            Label energyLabel,
            HBox enemyPanel,
            HBox actionPanel,
            StackPane root
    ) {
        itemPopup.getChildren().clear();

        List<Potion> potions = player.getInventory().getPotions();

        for (int i = 0; i < potions.size(); i++) {
            int index = i;
            Potion potion = potions.get(i);
            Button potionBtn = new Button(potion.getName());
            potionBtn.setPrefWidth(150);

            potionBtn.setOnAction(e -> {
                boolean used = potion.use(player);
                if (used) {
                    player.getInventory().getPotions().remove(index);
                    itemPopup.setVisible(false);
                    endPlayerTurn(player, enemies, room, onComplete, selectedEnemy, playerHpBar, playerHpText,energyLabel, enemyPanel, actionPanel, root);
                }
            });
            itemPopup.getChildren().add(potionBtn);
        }

        // เพิ่มปุ่มปิดกลับเข้าไปใหม่เสมอ
        Button closeItemBtn = new Button("Close");
        closeItemBtn.setPrefWidth(100);
        closeItemBtn.setOnAction(ev -> itemPopup.setVisible(false));
        itemPopup.getChildren().add(closeItemBtn);

        if (potions.isEmpty()) {
            itemPopup.getChildren().remove(0);
            Label empty = new Label("No Potions");
            empty.setStyle("-fx-text-fill: white;");
            itemPopup.getChildren().addAll(empty, closeItemBtn);
        }
    }

    // ================= UI UPDATE =================

    private static void screenShake(Node root) {

        TranslateTransition shake = new TranslateTransition(Duration.millis(40), root);
        shake.setByX(15);
        shake.setCycleCount(8);
        shake.setAutoReverse(true);
        shake.play();
    }

    private static void updateUI(
            Player player,
            List<Enemy> enemies,
            Enemy[] selectedEnemy,
            ProgressBar playerHpBar,
            Label playerHpText,
            Label energyLabel,
            HBox enemyPanel
    ) {
        // Ensure selection is valid after enemies are removed
        if (selectedEnemy[0] != null && !enemies.contains(selectedEnemy[0])) {
            selectedEnemy[0] = null;
        }
        if (selectedEnemy[0] == null && enemies.size() == 1) {
            selectedEnemy[0] = enemies.get(0);
        }

        playerHpBar.setProgress(
                (double) player.getHp() / player.getMaxHp()
        );
        playerHpText.setText("HP: " + player.getHp() + " / " + player.getMaxHp());

        energyLabel.setText(
                "Energy: " + player.getEnergy() +
                        " | Shield: " + player.getShield()
        );

        enemyPanel.getChildren().clear();

        for (Enemy e : enemies) {

            VBox enemyCard = new VBox(5);
            enemyCard.setAlignment(Pos.CENTER);

            boolean isSelected = (selectedEnemy[0] == e);

            enemyCard.setStyle(
                    "-fx-padding: 15;" +
                            "-fx-background-color: transparent;" +
                            (isSelected ? "-fx-border-color: yellow; -fx-border-width: 2; -fx-border-radius: 10;" : "")
            );

            javafx.scene.image.ImageView enemyImageView = new javafx.scene.image.ImageView();
            javafx.scene.image.Image eImage = getEnemyImage(e);
            if (eImage != null) {
                enemyImageView.setImage(eImage);
                enemyImageView.setPreserveRatio(true);

                String enemyName = e.getClass().getSimpleName().toLowerCase();

                if (enemyName.equals("irongladiator")) {
                    enemyImageView.setFitHeight(280); // bigger than other bosses
                }
                else if (enemyName.equals("goblinchief") ||
                        enemyName.equals("ratking") ||
                        enemyName.equals("slimetyrant")) {

                    enemyImageView.setFitHeight(240);
                }
                else {
                    enemyImageView.setFitHeight(150);
                }
            }

            Label name = new Label(e.getName());
            name.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            ProgressBar hpBar = new ProgressBar((double) e.getHp() / e.getMaxHp());
            hpBar.setPrefWidth(120);
            Label hpText = new Label("HP: " + e.getHp() + " / " + e.getMaxHp());
            hpText.setTextFill(Color.WHITE);

            Label shieldInfo = new Label(e.getShield() > 0 ? "Shield: " + e.getShield() : "");
            shieldInfo.setTextFill(Color.LIGHTBLUE);

            enemyCard.getChildren().addAll(enemyImageView, name, hpBar, hpText, shieldInfo);

            enemyCard.setOnMouseClicked(ev -> {
                selectedEnemy[0] = e;
                updateUI(player, enemies, selectedEnemy,
                        playerHpBar, playerHpText, energyLabel, enemyPanel);
            });

            enemyPanel.getChildren().add(enemyCard);
        }
    }

    // ================= IMAGE LOADERS =================

    private static javafx.scene.image.Image getPlayerImage(Player player) {
        String path = "/player/knight.png";
        String className = player.getClass().getSimpleName();

        if (className.equals("Mage")) {
            path = "/player/mage.png";
        } else if (className.equals("Rogue")) {
            path = "/player/rogue.png";
        }

        try {
            return new javafx.scene.image.Image(EnemyRoomScene.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.out.println("ไม่พบรูปผู้เล่น: " + path);
            return null;
        }
    }

    private static javafx.scene.image.Image getEnemyImage(Enemy enemy) {
        String name = enemy.getClass().getSimpleName().toLowerCase();
        String path = "/enemy/basic/" + name + ".png";

        if (name.equals("goblinchief") || name.equals("irongladiator") ||
                name.equals("ratking") || name.equals("slimetyrant")) {
            path = "/enemy/boss/" + name + ".png";
        }

        try {
            return new javafx.scene.image.Image(EnemyRoomScene.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.out.println("ไม่พบรูปศัตรู: " + path);
            return null;
        }
    }

    private static void showGoldEffect(StackPane root, int goldEarned) {

        Label goldLabel = new Label("+" + goldEarned + " Gold");
        goldLabel.setStyle(
                "-fx-text-fill: gold;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        StackPane.setAlignment(goldLabel, Pos.CENTER);
        root.getChildren().add(goldLabel);

        TranslateTransition rise =
                new TranslateTransition(Duration.seconds(1.2), goldLabel);
        rise.setByY(-80);

        FadeTransition fade =
                new FadeTransition(Duration.seconds(1.2), goldLabel);
        fade.setFromValue(1);
        fade.setToValue(0);

        ParallelTransition animation =
                new ParallelTransition(rise, fade);

        animation.setOnFinished(e ->
                root.getChildren().remove(goldLabel)
        );

        animation.play();
    }
}
