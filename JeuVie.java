import java.util.*;
import java.awt.*;

public class JeuVie {
  static final Random rand = new Random();

  enum Cell{ MORTE, VIVANTE }
  int taille;
  Cell[][] ecosysteme;
  int[][] voisines;

  /**
   * Renvoie vrai avec une probabilité p
   * @param p la probabilité de tirage vrai souhaitée
   * @return  le tirage vrai ou faux
   */
  public boolean tireProba(double p) {
      if(rand.nextDouble() < p) return true;
      return false ;
  }

  /**
   * Constructeur d'un jeu de la vie de taille n.
   * Initialisé avec n x n cellules dont p % sont vivantes
   * @param N la taille du coté de l'écosystème
   * @param P la probabilité d'une cellule d'être vivante
   */
  public JeuVie(final int N, final double P) {
      taille = N;
      ecosysteme = new Cell[taille][taille];
      voisines = new int[taille][taille];
      for (int x = 0; x < taille; x++) {
        for (int y = 0; y < taille; y++) {
          if (tireProba(P)) ecosysteme[x][y] = Cell.VIVANTE;
          else              ecosysteme[x][y] = Cell.MORTE;
        }
      }
  }

  /**
   * Initialise l'affichage
   */
  public void initFenetre() {
    StdDraw.setCanvasSize(750, 750);
    StdDraw.setScale(-0.5, taille-0.5);
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenRadius(1.2/taille);
    StdDraw.setPenColor(106, 204, 61);
    StdDraw.setFont(new Font("Arial", Font.PLAIN, 17));
  }

  /**
   * Affiche les cellules et leur nombre de voisines
   */
  public void affichCellules() {
    for (int x = 0; x < taille; x++) {
      for (int y = 0; y < taille; y++) {
        StdDraw.setPenColor(106, 204, 61);
        if (ecosysteme[x][y] == Cell.VIVANTE) {
          StdDraw.point(x, y);
          StdDraw.setPenColor(0, 0, 0);
        } else StdDraw.setPenColor(255, 255, 255);
        voisines[x][y] = nbVoisines(x, y);
        StdDraw.text(x, y, "" + voisines[x][y]);
      }
    }
  }

  /**
   * Compte le nombre de voisines d'une cellule
   * @param x indice de la ligne d'où ce situe la cellule
   * @param y indice de la colonne d'où ce situe la cellule
   * @return  le nombre de voisines vivantes d'une cellule
   */
  public int nbVoisines(int x, int y) {
    int nbVois = 0;
    for (int i = x-1; i < x+2; i++) {
      for (int j = y-1; j < y+2; j++) {
        if ((i != x || j != y) && ecosysteme[(i+taille)%taille][(j+taille)%taille] == Cell.VIVANTE) {
          nbVois++;
        }
      }
    }
    return nbVois;
  }

  /**
   * Calcule l’état de cellule pour la génération suivante
   */
  public void nouvelleGen() {
    for (int i = 0; i < taille; i++) {
      for (int j = 0; j < taille; j++) {
        if (voisines[i][j] == 3) ecosysteme[i][j] = Cell.VIVANTE;
        if ((voisines[i][j] < 2 || voisines[i][j] > 3) && ecosysteme[i][j] == Cell.VIVANTE) ecosysteme[i][j] = Cell.MORTE;
      }
    }
  }


  public static void main(String[] args) {
    final int N = Integer.parseInt(args[0]);
    final double P = Double.parseDouble(args[1]);;

    JeuVie jeu = new JeuVie(N, P);

    int nbGen = 0;

    jeu.initFenetre();
    while (true) {
      StdDraw.clear(StdDraw.DARK_GRAY);
      StdDraw.setPenColor(120, 120, 120);
      StdDraw.text(-0.1, N-0.7, "Gen: " + nbGen);
      jeu.affichCellules();
      jeu.nouvelleGen();
      nbGen++;
      StdDraw.show();
      StdDraw.pause(200);
    }
  }

}
