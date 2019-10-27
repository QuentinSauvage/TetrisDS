package jeu;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import piece.Case;
import piece.Case.EtatCase;
import piece.Piece;

public abstract class Plateau {
	/**
	 * L'inventaire du plateau
	 */
	protected Inventaire inventaire;
	/**
	 * Le score du Plateau
	 */
	protected Score score;
	/**
	 * Le nombre de lignes du joueur
	 */
	protected int nbLignes;
	/**
	 * La grille de case
	 */
	protected Case[][] grille;
	/**
	 * La piece courante
	 */
	protected Piece courante;
	/**
	 * Position x de la piece courante
	 */
	protected int xCourante;
	/**
	 * Positiion y de la piece courante
	 */
	protected int yCourante;
	/**
	 * Plateau de l'adversaire (Mode Push)
	 */
	protected Plateau adversaire;

	/**
	 * Constructeur mode Standard
	 */
	public Plateau() {
		adversaire = null;
		inventaire = new Inventaire();
		score = new Score();
		courante = inventaire.getNext();
		xCourante = 3;
		yCourante = 0;
		grille = new Case[14][20];
		for (int i = 2; i < 12; i++) {
			for (int j = 0; j < 20; j++) {
				grille[i][j] = new Case();
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 20; j++) {
				grille[i][j] = new Case(EtatCase.PLEIN, Color.BLACK);
				grille[i + 12][j] = new Case(EtatCase.PLEIN, Color.BLACK);
			}
		}
	}

	/**
	 * Consructeur Mode Push
	 * 
	 * @param adversaire
	 *            Plateau de l'adversaire
	 */
	public Plateau(Plateau adversaire) {
		this();
		this.adversaire = adversaire;
	}

	/**
	 * Test si la piece courante entre en collision avec les Case de la grille
	 * par le bas
	 * 
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionBas() {
		if (yCourante == 16)
			return true;
		Case[][] tmp = courante.getShape();
		for (int j = 3; j >= 0; j--) {
			for (int i = 0; i < 4; i++) {
				if ((tmp[j][i].getEtat() == EtatCase.PLEIN)
						&& (grille[xCourante + i][yCourante + j + 1].getEtat() == EtatCase.PLEIN)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Test si l'ombre de la piece courante entre en collision avec les Case de
	 * la grille par le bas
	 * 
	 * @param yOmbre
	 *            Position y de l'ombre
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionBasOmbre(int yOmbre) {
		if (yOmbre == 16)
			return true;
		Case[][] tmp = courante.getShape();
		for (int j = 3; j >= 0; j--) {
			for (int i = 0; i < 4; i++) {
				if ((tmp[j][i].getEtat() == EtatCase.PLEIN)
						&& (grille[xCourante + i][yOmbre + j + 1].getEtat() == EtatCase.PLEIN)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Test si la piece courante entre en collision avec les Case de la grille
	 * par la droite
	 * 
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionDroite() {
		if (xCourante == 9)
			return true;
		Case[][] shape = courante.getShape();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((shape[j][i].getEtat() == EtatCase.PLEIN)
						&& grille[xCourante + i + 1][yCourante + j].getEtat() == EtatCase.PLEIN) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Test si l'echange de Piece est possible
	 * 
	 * @param hold
	 *            La Piece a tester
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionEchange(Piece hold) {
		Case[][] shape = hold.getShape();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (shape[j][i].getEtat() == EtatCase.PLEIN
						&& grille[xCourante + i][yCourante + j].getEtat() == EtatCase.PLEIN) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Test si la courante entre en collision avec les Case de la grille par la
	 * gauche
	 * 
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionGauche() {
		if (xCourante == 0)
			return true;
		Case[][] shape = courante.getShape();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((shape[j][i].getEtat() == EtatCase.PLEIN)
						&& grille[xCourante + i - 1][yCourante + j].getEtat() == EtatCase.PLEIN) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Test si la rotation et possible
	 * 
	 * @return Vrai en cas de collision, Faux sinon
	 */
	private boolean collisionRotation() {
		Case[][] shape = courante.nextShape();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (shape[j][i].getEtat() == EtatCase.PLEIN
						&& grille[xCourante + i][yCourante + j].getEtat() == EtatCase.PLEIN) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @return Vrai si la partie et perdu, Faux sinon
	 */
	public boolean defaite() {
		return yCourante == 0 && collisionBas();
	}

	/**
	 * Deplace la Piece courante vers la droite
	 */
	public void deplacementDroite() {
		if (!collisionDroite())
			xCourante++;
	}

	/**
	 * Deplace la Piece courante vers la gauche
	 */
	public void deplacementGauche() {
		if (!collisionGauche())
			xCourante--;
	}

	/**
	 * Fait descendre la Piece d'une ligne
	 * 
	 * @return Vrai si la Piece a ete poser, Faux sinon
	 */
	public boolean descendre() {
		int nbLignes = 0;
		if (collisionBas()) {
			poserPiece();
			for (int i = 0; i < 4; i++) {
				if (testerLigne(i + yCourante)) {
					nbLignes++;
					this.nbLignes++;
					retirerLigne(i + yCourante);
				} else if (nbLignes > 0) {
					score.increaseScore(nbLignes);
					nbLignes = 0;
				}
			}
			if (nbLignes > 0) {
				score.increaseScore(nbLignes);
			}
			if (nbLignes > 1 && adversaire != null) {
				decalage();
			}
			xCourante = 3;
			yCourante = 0;
			courante = inventaire.getNext();
			return true;
		}
		yCourante++;
		return false;
	}

	/**
	 * Permet de decaler toutes les lignes vers le bas et d'inserer la derniere
	 * chez l'adversaire
	 */
	private void decalage() {
		Case[] dernier = new Case[14];
		for (int i = 0; i < 14; i++) {
			dernier[i] = grille[i][19];
		}
		for (int i = 19; i > 0; i--) {
			for (int j = 0; j < 14; j++) {
				grille[j][i] = grille[j][i - 1];
			}
		}
		adversaire.decalage(dernier);
	}

	/**
	 * Permet d'inserer une ligne en bas de la grille
	 * 
	 * @param ligne
	 *            La ligne a inserer
	 */
	protected void decalage(Case[] ligne) {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 14; j++) {
				grille[j][i] = grille[j][i + 1];
			}
		}
		for (int i = 0; i < 14; i++) {
			grille[i][19] = ligne[13 - i];
		}
	}

	/**
	 * Fait descendre la Piece courante tant qu'il n'y a pas eu de collision
	 */
	public void descendreTout() {
		while (!descendre())
			;
	}

	/**
	 * Permet d'echanger la Piece courante avec celle de l'inventaire
	 */
	public void echange() {
		Piece tmp = inventaire.getHold();
		if (!collisionEchange(tmp)) {
			inventaire.setHold(courante);
			courante = tmp;
		}
	}

	public Piece getCourante() {
		return courante;
	}

	public Case[][] getGrille() {
		return grille;
	}

	public Inventaire getInventaire() {
		return inventaire;
	}

	/**
	 * @return Le Node correspondant a la grille
	 */
	public abstract Node getNode();

	public Score getScore() {
		return score;
	}
	
	public int getNbLignes() {
		return nbLignes;
	}

	public Plateau getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(Plateau adversaire) {
		this.adversaire = adversaire;
	}

	/**
	 * Permet de poser la Piece courante dans la grille
	 */
	private void poserPiece() {
		Case[][] tmp = courante.getShape();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tmp[j][i].getEtat() == EtatCase.PLEIN) {
					grille[i + xCourante][j + yCourante].setEtat(EtatCase.PLEIN);
					grille[i + xCourante][j + yCourante].setColor(courante.getColor());
				}
			}
		}
	}

	/**
	 * @return la Position y de l'ombre
	 */
	protected int refreshOmbre() {
		int yOmbre = yCourante;
		while (!collisionBasOmbre(yOmbre)) {
			yOmbre++;
		}
		return yOmbre;
	}

	/**
	 * Permet de retirer une ligne et faire les decalages necesaires
	 * 
	 * @param ligne
	 */
	private void retirerLigne(int ligne) {
		for (int i = ligne; i > 0; i--) {
			for (int j = 0; j < 14; j++) {
				grille[j][i] = grille[j][i - 1];
			}
		}
	}

	/**
	 * Permet de faire roter la Piece courante
	 */
	public void rotation() {
		if (!collisionRotation())
			courante.rotate();
	}

	public void setCourante(Piece courante) {
		this.courante = courante;
	}

	public void setGrille(Case[][] grille) {
		this.grille = grille;
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	/**
	 * Permet de tester si la ligne est pleine
	 * 
	 * @param ligne
	 *            La ligne a tester
	 * @return Vrai si la ligne est pleine, Faux sinon
	 */
	private boolean testerLigne(int ligne) {
		for (int i = 0; i < 14; i++) {
			if (grille[i][ligne].getEtat() == EtatCase.VIDE)
				return false;
		}
		return true;
	}

}
