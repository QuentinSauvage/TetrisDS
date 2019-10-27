package piece;

import javafx.scene.paint.Color;
import piece.Case.EtatCase;

public class PieceO extends Piece {

	public PieceO() {
		super();
		this.color = Color.YELLOW;
		Case vide = new Case();
		Case plein = new Case(EtatCase.PLEIN, color);
		Case[][] shape = { { vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), plein.copie(), plein.copie(), vide.copie() },
				{ vide.copie(), plein.copie(), plein.copie(), vide.copie() } };
		shapeList.add(shape);
	}

	@Override
	public void rotate() {
		return;
	}
}
