package com.app.main.view.levelEditor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**<p>
 * Vue de liste des niveaux qui affiche les fichiers JSON présents dans le
 * répertoire fourni. Les boutons sont créés dynamiquement et déclenchent
 * un callback lorsque l'utilisateur sélectionne un niveau.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public final class LevelListView extends ScrollPane {

    public static final LevelListView INSTANCE = new LevelListView();

    private final VBox container = new VBox(6);
    private Path levelsDir;
    private Consumer<Path> onLevelSelected;
    private static final int VISIBLE_LEVELS = 3;
    private static final double BUTTON_HEIGHT = 48.0;

    /**
     * Vue de liste des niveaux qui affiche les fichiers JSON présents dans le
     * répertoire fourni. Les boutons sont créés dynamiquement et déclenchent
     * un callback lorsque l'utilisateur sélectionne un niveau.
     */
    private LevelListView() {
        this(Paths.get(System.getProperty("user.dir")).resolve("editorlevels"));
    }

    /**
     * Crée une vue pointant sur le répertoire spécifié.
     *
     * @param levelsDir chemin du dossier contenant les fichiers de niveau
     */
    public LevelListView(Path levelsDir) {
        this.levelsDir = levelsDir;
        setFitToWidth(true);
        container.setPadding(new Insets(8));
        double totalHeight = container.getPadding().getTop()
                + container.getPadding().getBottom()
                + container.getSpacing() * (VISIBLE_LEVELS - 1)
                + VISIBLE_LEVELS * BUTTON_HEIGHT;
        setPrefHeight(totalHeight);
        setMaxHeight(totalHeight);
        setContent(container);
        refresh();
    }

    /**
     * Définit le répertoire à utiliser pour lister les niveaux et rafraîchit
     * l'affichage.
     *
     * @param dir chemin du dossier de niveaux (non null)
     */
    public void setLevelsDirectory(Path dir) {
        if (dir == null) throw new IllegalArgumentException("dir can't be null");
        this.levelsDir = dir;
        refresh();
    }

    /**
     * Définit le callback appelé lorsque l'utilisateur sélectionne un niveau.
     *
     * @param callback fonction recevant le Path du fichier sélectionné
     */
    public void setOnLevelSelected(Consumer<Path> callback) {
        this.onLevelSelected = callback;
    }

    /**
     * Rafraîchit la liste des boutons en lisant le dossier des niveaux.
     * L'opération est effectuée sur le thread JavaFX via Platform.runLater.
     */
    public void refresh() {
        Platform.runLater(() -> {
            container.getChildren().clear();
            if (levelsDir == null) return;
            if (!Files.exists(levelsDir) || !Files.isDirectory(levelsDir)) return;

            try (Stream<Path> stream = Files.list(levelsDir)) {
                stream.filter(p -> !Files.isDirectory(p))
                      .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".json"))
                      .sorted(Comparator.comparing(Path::getFileName))
                      .forEach(p -> {
                          String name = stripExtension(p.getFileName().toString());
                          Button btn = new Button(name);
                          btn.setMaxWidth(Double.MAX_VALUE);
                              btn.setPrefHeight(BUTTON_HEIGHT);
                              btn.setStyle("-fx-font-size:14px;");
                          btn.getStyleClass().add("level-item");
                          btn.setOnAction(e -> {
                              if (onLevelSelected != null) onLevelSelected.accept(p);
                          });
                          container.getChildren().add(btn);
                      });
            } catch (IOException e) {
                // silently ignore here; consuming code may provide logging if needed
            }
        });
    }

    /** Enlève l'extension d'un nom de fichier. */
    private String stripExtension(String s) {
        int i = s.lastIndexOf('.');
        return (i > 0) ? s.substring(0, i) : s;
    }
}