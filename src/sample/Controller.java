package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller{
    //IHM
    public Label scoreGame;
    public Button startBouton;
    public GridPane gridPaneBoule;

    private ElementZ_Model EZJeu;
    private int selectedX = -1;
    private int selectedY = -1;
    //Tableau d'images
    private Image[] imageDiams = new Image[8];
    private Image[] imageDiamsOver = new Image[8];
    private Image[] imageDiamsSelected = new Image[8];

    //Static variables
    public static String CLASSIC_STATE = "diams_";
    public static String OVER_STATE="diams_o_";
    public static String SELECTED_STATE = "diams_s_";
    public static String IMAGE_ROOT = "/diams_img/";
    public static String EXTENSION_FILE = ".jpg";
    //Erreur message
    public static String CATCH_ERREUR = "Ce n'est pas une image. Veuillez cliquer sur un Diamant.\n";

    //--------------------------------------------------------------------------
    // Je viens dans cette méthode, charger mes images afin de puvoir les
    // utiliser plus tard.
    //--------------------------------------------------------------------------
    private void loadImageSimple(){
        for (int i=1; i<7; i++){
            imageDiams[i]= new Image(IMAGE_ROOT+CLASSIC_STATE+ i +EXTENSION_FILE);
        }
    }

    private void loadImageOver() {
        for (int i = 1; i < 7; i++) {
            imageDiamsOver[i] = new Image( IMAGE_ROOT+OVER_STATE+ i +EXTENSION_FILE);
        }
    }

    private void loadImageSelected(){
        for (int i=1; i<7; i++){
            imageDiamsSelected[i]= new Image(IMAGE_ROOT+SELECTED_STATE+ i +EXTENSION_FILE);
        }
    }

    //--------------------------------------------------------------------------
    // Cette méthode me permet quand à elle de venir affecter les boules en
    // fontion de la matrice de jeu.
    //--------------------------------------------------------------------------
    private void affectBalls (){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                int id = EZJeu.getXY(i, j);
                ImageView imageView = new ImageView(imageDiams[id]);
                imageView.setOnMouseEntered(event -> {
                    imageView.setImage(imageDiamsOver[id]);
                });
                imageView.setOnMouseExited(event -> {
                    imageView.setImage(imageDiams[id]);
                });
                gridPaneBoule.add(imageView, j, i);
            }
        }
    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode lancer le jeu
    //--------------------------------------------------------------------------
    @FXML
    private void jButtonStart() {
        loadImageSimple();
        loadImageOver();
        loadImageSelected();
        EZJeu = new ElementZ_Model();
        affectBalls();
        scoreGame.setText(String.valueOf(EZJeu.getScore()));
    }

    //--------------------------------------------------------------------------
    // Ici je viens avec cette méthode selectionner ma boule afin de réaliser
    // le déplacement et afficher le score
    //--------------------------------------------------------------------------
    @FXML
    private void gridPaneClick(MouseEvent e) {
        try {
            Node source = (Node) e.getTarget();
            Integer colIndex = GridPane.getColumnIndex(source);
            Integer rowIndex = GridPane.getRowIndex(source);
            if (selectedX == -1) {
                selectedX = colIndex.intValue();
                selectedY = rowIndex.intValue();
                gridPaneBoule.add(new ImageView(imageDiamsSelected[EZJeu.getXY(selectedY, selectedX)]), selectedX, selectedY);

            } else {
                System.out.println(EZJeu.toString());
                EZJeu.play(selectedY, selectedX, rowIndex.intValue(), colIndex.intValue());
                selectedX = -1;
                selectedY = -1;
                affectBalls();
                scoreGame.setText(String.valueOf(EZJeu.getScore()));
            }

        }catch (Exception err){
            System.out.printf(CATCH_ERREUR);
        }
    }
}
