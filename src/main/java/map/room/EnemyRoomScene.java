package map.room;

import Entity.Player;
import Entity.enemy.Enemy;
import inventory.potion.Potion;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.Room;

import java.util.Iterator;
import java.util.List;

public class EnemyRoomScene {

    public static Scene create(Room room, Player player, Runnable onComplete) {

        List<Enemy> enemies = room.getEnemies();
        final Enemy[] selectedEnemy = {null};

        // ===== ROOT (StackPane for Overlay) =====
        StackPane root = new StackPane();
        BorderPane mainLayout = new BorderPane();
        root.getChildren().add(mainLayout);

        mainLayout.setStyle("-fx-background-color: #1b1b1b;");

        // ===== PLAYER PANEL =====
        VBox playerPanel = new VBox(10);
        playerPanel.setAlignment(Pos.CENTER);

        Label playerName = new Label(player.getName());
        playerName.setTextFill(Color.WHITE);

        ProgressBar playerHpBar = new ProgressBar();
        playerHpBar.setPrefWidth(300);

        Label energyLabel = new Label();
        energyLabel.setTextFill(Color.WHITE);

        playerPanel.getChildren().addAll(playerName, playerHpBar, energyLabel);
        mainLayout.setTop(playerPanel);

        // ===== ENEMY PANEL =====
        HBox enemyPanel = new HBox(30);
        enemyPanel.setAlignment(Pos.CENTER);
        mainLayout.setCenter(enemyPanel);

        // ===== ACTION PANEL =====
        HBox actionPanel = new HBox(15);
        actionPanel.setAlignment(Pos.CENTER);

        Button attackBtn = new Button("Attack");
        Button blockBtn = new Button("Block");
        Button focusBtn = new Button("Focus");
        Button skillBtn = new Button("Skill");
        Button itemBtn = new Button("Item");

        actionPanel.getChildren().addAll(
                attackBtn, blockBtn, focusBtn, skillBtn, itemBtn
        );

        mainLayout.setBottom(actionPanel);

        // ===== SKILL PANEL =====
        VBox skillPanel = new VBox(10);
        skillPanel.setAlignment(Pos.CENTER);
        skillPanel.setStyle("-fx-background-color: #222; -fx-padding: 15;");
        skillPanel.setVisible(false);

        Button s1 = new Button();
        Button s2 = new Button();
        Button s3 = new Button();

        skillPanel.getChildren().addAll(s1, s2, s3);
        mainLayout.setRight(skillPanel);

        // ===== ITEM PANEL =====
        VBox itemPanel = new VBox(10);
        itemPanel.setAlignment(Pos.CENTER);
        itemPanel.setStyle("-fx-background-color: #222; -fx-padding: 15;");
        itemPanel.setVisible(false);

        mainLayout.setLeft(itemPanel);

        // ===== INITIAL UI =====
        updateUI(player, enemies, selectedEnemy,
                playerHpBar, energyLabel, enemyPanel);

        // ===== SKILL TEXT =====
        s1.setText(player.getSkill1Name() + " (Cost: " + player.getSkill1Cost() + ")");
        s2.setText(player.getSkill2Name() + " (Cost: " + player.getSkill2Cost() + ")");
        s3.setText(player.getSkill3Name() + " (Cost: " + player.getSkill3Cost() + ")");

        skillBtn.setOnAction(e ->
                skillPanel.setVisible(!skillPanel.isVisible())
        );

        itemBtn.setOnAction(e -> {
            updateItemPanel(player, itemPanel,
                    selectedEnemy,
                    enemies, room, onComplete,
                    playerHpBar, energyLabel,
                    enemyPanel,
                    attackBtn, blockBtn, focusBtn,
                    root);

            itemPanel.setVisible(!itemPanel.isVisible());
        });

        // ===== ATTACK =====
        attackBtn.setOnAction(e -> {
            if (selectedEnemy[0] == null) return;

            player.normalAttack(selectedEnemy[0]);

            endTurn(player, enemies, room, onComplete,
                    selectedEnemy,
                    playerHpBar, energyLabel,
                    enemyPanel,
                    attackBtn, blockBtn, focusBtn,
                    root);
        });

        // ===== BLOCK =====
        blockBtn.setOnAction(e -> {
            player.block();
            endTurn(player, enemies, room, onComplete,
                    selectedEnemy,
                    playerHpBar, energyLabel,
                    enemyPanel,
                    attackBtn, blockBtn, focusBtn,
                    root);
        });

        // ===== FOCUS =====
        focusBtn.setOnAction(e -> {
            player.focus();
            endTurn(player, enemies, room, onComplete,
                    selectedEnemy,
                    playerHpBar, energyLabel,
                    enemyPanel,
                    attackBtn, blockBtn, focusBtn,
                    root);
        });

        // ===== SKILLS =====
        s1.setOnAction(e -> {
            if (selectedEnemy[0] == null) return;
            if (player.skill1(selectedEnemy[0])) {
                endTurn(player, enemies, room, onComplete,
                        selectedEnemy,
                        playerHpBar, energyLabel,
                        enemyPanel,
                        attackBtn, blockBtn, focusBtn,
                        root);
            }
        });

        s2.setOnAction(e -> {
            if (selectedEnemy[0] == null) return;
            if (player.skill2(selectedEnemy[0])) {
                endTurn(player, enemies, room, onComplete,
                        selectedEnemy,
                        playerHpBar, energyLabel,
                        enemyPanel,
                        attackBtn, blockBtn, focusBtn,
                        root);
            }
        });

        s3.setOnAction(e -> {
            if (selectedEnemy[0] == null) return;
            if (player.skill3(selectedEnemy[0])) {
                endTurn(player, enemies, room, onComplete,
                        selectedEnemy,
                        playerHpBar, energyLabel,
                        enemyPanel,
                        attackBtn, blockBtn, focusBtn,
                        root);
            }
        });

        return new Scene(root, 1024, 768);
    }

    // ================= TURN SYSTEM =================

    private static void endTurn(
            Player player,
            List<Enemy> enemies,
            Room room,
            Runnable onComplete,
            Enemy[] selectedEnemy,
            ProgressBar playerHpBar,
            Label energyLabel,
            HBox enemyPanel,
            Button attackBtn,
            Button blockBtn,
            Button focusBtn,
            StackPane root
    ) {

        removeDead(enemies);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.performAction(player);
            }
        }

        removeDead(enemies);
        player.startTurn();

        updateUI(player, enemies, selectedEnemy,
                playerHpBar, energyLabel, enemyPanel);

        // ===== GAME OVER =====
        if (!player.isAlive()) {

            VBox loseBox = new VBox(20);
            loseBox.setAlignment(Pos.CENTER);
            loseBox.setStyle("-fx-background-color: rgba(0,0,0,0.9);");

            Label loseLabel = new Label("YOU LOSE");
            loseLabel.setStyle(
                    "-fx-text-fill: red;" +
                            "-fx-font-size: 50px;" +
                            "-fx-font-weight: bold;"
            );

            Button exitBtn = new Button("Exit Game");
            exitBtn.setStyle("-fx-font-size: 20px;");
            exitBtn.setOnAction(ev -> System.exit(0));

            loseBox.getChildren().addAll(loseLabel, exitBtn);
            root.getChildren().add(loseBox);

            attackBtn.setDisable(true);
            blockBtn.setDisable(true);
            focusBtn.setDisable(true);

            return;
        }

        // ===== VICTORY =====
        if (enemies.stream().noneMatch(Enemy::isAlive)) {
            room.setCleared(true);
            attackBtn.setText("Victory! Leave");
            attackBtn.setOnAction(ev -> onComplete.run());
        }
    }

    private static void removeDead(List<Enemy> enemies) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isAlive()) {
                iterator.remove();
            }
        }
    }

    // ================= ITEM PANEL =================

    private static void updateItemPanel(
            Player player,
            VBox itemPanel,
            Enemy[] selectedEnemy,
            List<Enemy> enemies,
            Room room,
            Runnable onComplete,
            ProgressBar playerHpBar,
            Label energyLabel,
            HBox enemyPanel,
            Button attackBtn,
            Button blockBtn,
            Button focusBtn,
            StackPane root
    ) {

        itemPanel.getChildren().clear();

        List<Potion> potions = player.getInventory().getPotions();

        for (int i = 0; i < potions.size(); i++) {

            int index = i;   // สำคัญมาก
            Potion potion = potions.get(i);

            Button potionBtn = new Button(potion.getName());

            potionBtn.setOnAction(e -> {

                boolean used = potion.use(player);

                if (used) {

                    player.getInventory().getPotions().remove(index);

                    // รีเฟรช panel ทันที
                    updateItemPanel(player, itemPanel,
                            selectedEnemy,
                            enemies, room, onComplete,
                            playerHpBar, energyLabel,
                            enemyPanel,
                            attackBtn, blockBtn, focusBtn,
                            root);

                    endTurn(player, enemies, room, onComplete,
                            selectedEnemy,
                            playerHpBar, energyLabel,
                            enemyPanel,
                            attackBtn, blockBtn, focusBtn,
                            root);
                }
            });

            itemPanel.getChildren().add(potionBtn);
        }

        if (potions.isEmpty()) {
            Label empty = new Label("No Potions");
            empty.setStyle("-fx-text-fill: white;");
            itemPanel.getChildren().add(empty);
        }
    }

    // ================= UI UPDATE =================

    private static void updateUI(
            Player player,
            List<Enemy> enemies,
            Enemy[] selectedEnemy,
            ProgressBar playerHpBar,
            Label energyLabel,
            HBox enemyPanel
    ) {

        playerHpBar.setProgress(
                (double) player.getHp() / player.getMaxHp()
        );

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
                    "-fx-background-color: #2a2a2a;" +
                            "-fx-padding: 10;" +
                            "-fx-border-color: " +
                            (isSelected ? "red;" : "white;")
            );

            Label name = new Label(e.getName());
            name.setTextFill(Color.WHITE);

            ProgressBar hpBar = new ProgressBar(
                    (double) e.getHp() / e.getMaxHp()
            );

            enemyCard.getChildren().addAll(name, hpBar);

            enemyCard.setOnMouseClicked(ev -> {
                selectedEnemy[0] = e;
                updateUI(player, enemies, selectedEnemy,
                        playerHpBar, energyLabel, enemyPanel);
            });

            enemyPanel.getChildren().add(enemyCard);
        }
    }
}