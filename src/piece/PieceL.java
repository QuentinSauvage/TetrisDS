package piece;

import javafx.scene.paint.Color;
import piece.Case.EtatCase;

public class PieceL extends Piece {

	public PieceL() {
		super();
		this.color = Color.ORANGE;
		Case vide = new Case();
		Case plein = new Case(EtatCase.PLEIN, color);
		Case[][] shape = { { vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), plein.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), plein.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), plein.copie(), plein.copie() } };
		shapeList.add(shape);
		shape = new Case[][] { { vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), plein.copie(), plein.copie(), plein.copie() },
				{ vide.copie(), plein.copie(), vide.copie(), vide.copie() } };
		shapeList.add(shape);
		shape = new Case[][] { { vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), plein.copie(), plein.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), plein.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), plein.copie(), vide.copie() } };
		shapeList.add(shape);
		shape = new Case[][] { { vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), vide.copie(), vide.copie() },
				{ vide.copie(), vide.copie(), vide.copie(), plein.copie() },
				{ vide.copie(), plein.copie(), plein.copie(), plein.copie() } };
		shapeList.add(shape);
	}
}
