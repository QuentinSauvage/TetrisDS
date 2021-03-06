package jeu;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import piece.Case;
import piece.Case.EtatCase;

public class PlateauSolo extends Plateau {
	public PlateauSolo() {
		super();
	}

	public PlateauSolo(Plateau adversaire) {
		super(adversaire);
	}

	/**
	 * @return Le Node correspondant a la grille de maniere inversee
	 */
	@Override
	public Node getNode() {
		GridPane gp = new GridPane();
		
		Case[][] shape = courante.getShape();
		int yOmbre = refreshOmbre();
		for (int i = 2; i < 12; i++) {
			for (int j = 0; j < 20; j++) {
				if (i >= xCourante && i < xCourante + 4 && j >= yCourante && j < yCourante + 4
						&& shape[j - yCourante][i - xCourante].getEtat() == EtatCase.PLEIN)
					gp.add(shape[j - yCourante][i - xCourante].getNode(), i, j);
				else if (i >= xCourante && i < xCourante + 4 && j >= yOmbre && j < yOmbre + 4
						&& shape[j - yOmbre][i - xCourante].getEtat() == EtatCase.PLEIN)
					gp.add(new Case(EtatCase.OMBRE, courante.getColor()).getNode(), i, j);
				else
					gp.add(grille[i][j].getNode(), i, j);
			}
		}
		return gp;
	}
}
