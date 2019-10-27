package piece;

@FunctionalInterface
public interface PieceFactory {

	public abstract Piece create();
}
