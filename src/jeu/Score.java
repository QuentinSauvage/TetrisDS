package jeu;

public class Score {
	/**
	 * Le nombre de points total
	 */
	private int points;

	public int getPoints() {
		return points;
	}

	/**
	 * Permet d'augmenter le score en fonction du nombre de ligne remplis
	 * 
	 * @param nbLignes
	 *            Le nombre de lignes
	 */
	public void increaseScore(int nbLignes) {
		if (nbLignes == 1)
			points += 100;
		else if (nbLignes == 2)
			points += 300;
		else if (nbLignes == 3)
			points += 500;
		else if (nbLignes == 4)
			points += 800;
		// nb+height;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
