package com.app.main.controller.menu;

/**
 * Contrôleur de la page de sélection du mode de jeu.
 * <p>
 * Fournit des actions pour naviguer vers les différents écrans (solo,
 * multijoueur local, en ligne) et pour retourner au menu principal.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public class GamemodeController {
    
    /** Ouvre l'écran de sélection des niveaux pour le mode solo. */
    public void solo(){
        MenuSwitcher.switchScene("LevelMenu.fxml");
    }

    /** Retourne au menu principal. */
    public void goBack(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }

    /** Ouvre l'écran pour configurer une partie locale (multi-joueur). */
    public void local(){
         MenuSwitcher.switchScene("Multilocal.fxml");
    }

    /** Option pour le mode en ligne (non implémenté). */
     public void online(){
        //! Réseau
    }
}
