package piece;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * @author Emile
 *
 */
public abstract class Piece {
	/**
	 * Index de la liste correspondant a la forme courrante
	 */
	protected int direction;
	/**
	 * Liste des formes de la Piece
	 */
	protected List<Case[][]> shapeList;
	/**
	 * Couleur de la Piece
	 */
	protected Color color;

	public Piece() {
		direction = 0;
		setShapeList(new LinkedList<>());
	}

	public Color getColor() {
		return color;
	}

	public int getDirection() {
		return direction;
	}

	/**
	 * @return La forme courante de la Piece
	 */
	public Case[][] getShape() {
		return shapeList.get(direction);
	}

	public List<Case[][]> getShapeList() {
		return shapeList;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setShapeList(List<Case[][]> shapeList) {
		this.shapeList = shapeList;
	}

	/**
	 * @return La forme suivante de la Piece
	 */
	public Case[][] nextShape() {
		return shapeList.get((direction + 1) % shapeList.size());
	}

	/**
	 * Modifie la forme de la Piece
	 */
	public void rotate() {
		direction = (direction + 1) % shapeList.size();
	}

	/**
	 * @return Un GridPane qui contient les Cases de la Piece
	 */
	public Node getNode() {
		Case[][] shape = getShape();
		GridPane gp = new GridPane();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				gp.add(shape[i][j].getNode(), j, i);
			}
		}
		gp.setBorder(new Border(new BorderStroke(Color.WHITE, 
		BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		return gp;
	}

	/**
	 * @return Un GridPane qui contient des Cases vides
	 */
	public static Node getNodeVide() {
		GridPane gp = new GridPane();
		Case vide = new Case();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				gp.add(vide.getNode(), j, i);
			}
		}
		gp.setBorder(new Border(new BorderStroke(Color.WHITE, 
		BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		return gp;
	}
}