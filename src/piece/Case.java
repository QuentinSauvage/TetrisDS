package piece;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * @author Emile
 * 
 */
public class Case {
	/**
	 * @author Emile Les Etat de la Case
	 */
	public enum EtatCase {
		VIDE, PLEIN, OMBRE
	}

	/**
	 * L'etat courant de la Case
	 */
	private EtatCase etat;
	/**
	 * Couleur de la case
	 */
	private Color color;

	/**
	 * Constructeur des Cases vides.
	 */
	public Case() {
		this.etat = EtatCase.VIDE;
		this.color = Color.BLACK;
	}

	/**
	 * @param etat
	 *            L'etat de la Case
	 * @param color
	 *            La couleur de la case
	 */
	public Case(EtatCase etat, Color color) {
		this.etat = etat;
		this.color = color;
	}

	/**
	 * @return Une copie de la Case
	 */
	public Case copie() {
		return new Case(this.etat, this.color);
	}

	public Color getColor() {
		return color;
	}

	public EtatCase getEtat() {
		return etat;
	}

	/**
	 * Cree un Rectangle en fonction de l'etat et de la couleur
	 * 
	 * @return Le Rectangle cree
	 */
	public Node getNode() {
		Rectangle r = new Rectangle(25, 25, color);
		r.setStroke(Color.GRAY);
		r.setStrokeType(StrokeType.INSIDE);
		switch (etat) {
		case PLEIN:
			return r;
		case VIDE:
			return new Rectangle(25, 25, Color.BLACK);
		default:
			return new Rectangle(25, 25, color.darker().darker());
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setEtat(EtatCase etat) {
		this.etat = etat;
	}

}
