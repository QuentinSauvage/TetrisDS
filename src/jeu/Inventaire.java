package jeu;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import piece.Piece;
import piece.PieceFactory;
import piece.PieceRandomFactory;

/**
 * @author Emile
 * 
 */
public class Inventaire {
	/**
	 * Piece en reverse
	 */
	private Piece hold;
	/**
	 * Pieces suivantes
	 */
	private Piece[] stock = new Piece[6];
	/**
	 * La Factory des pieces
	 */
	private PieceFactory factory;

	public Inventaire() {
		hold = null;
		factory = new PieceRandomFactory();
		for (int i = 0; i < stock.length; i++) {
			stock[i] = factory.create();
		}
	}

	/**
	 * @return La Piece stocker ou la Piece suivante si null
	 */
	public Piece getHold() {
		if (hold == null)
			return getNext();
		return hold;
	}

	public void setHold(Piece hold) {
		this.hold = hold;
	}

	/**
	 * Renvoi la premiere Piece de l'inventaire et ajoute une nouvelle Piece
	 * generer par la factory
	 * 
	 * @return La Piece suivante
	 */
	public Piece getNext() {
		Piece next = stock[0];
		for (int i = 0; i < stock.length - 1; i++) {
			stock[i] = stock[i + 1];
		}
		stock[stock.length - 1] = factory.create();
		return next;
	}

	/**
	 * @return Un GridPane qui contient les Pieces de l'inventaire
	 */
	public Node getStockNode() {
		GridPane gp = new GridPane();
		gp.add(stock[0].getNode(), 1, 0);
		gp.add(stock[1].getNode(), 2, 0);
		gp.add(stock[2].getNode(), 3, 0);
		gp.add(stock[3].getNode(), 3, 1);
		gp.add(stock[4].getNode(), 3, 2);
		gp.add(stock[5].getNode(), 3, 3);
		return gp;
	}

	/**
	 * @return Le Node de la Piece en reserve ou de la Piece vide
	 */
	public Node getHoldNode() {
		if (hold != null)
			return hold.getNode();
		return Piece.getNodeVide();
	}

}
