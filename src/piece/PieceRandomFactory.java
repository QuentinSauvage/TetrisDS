package piece;

import java.util.Random;

public class PieceRandomFactory implements PieceFactory {

	/**
	 * Permet de generer une Piece de maniere al�atoire
	 * @return La Piece generer
	 */
	@Override
	public Piece create() {
		String lettres = "IJLOSTZ";
		Random rand = new Random();
		Piece p = null;
		try {
			p = (Piece) Class.forName("piece.Piece" + lettres.charAt(rand.nextInt(lettres.length()))).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return p;
	}
}
