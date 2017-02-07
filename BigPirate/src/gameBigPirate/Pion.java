package gameBigPirate;

/**
 * <b>Pion est la classe abstraite qui definie les differents pions de la partie</b>
 * <p>Les pions sont caracterises par leurs coordonnees (x et y) dans la map</p>
 *  
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public abstract class Pion {

	int x;
	int y;
	
	/**
	 * <p> Constructeur sans parametre </p>.
	 * <p> Par defaut le pion sera positionne sur la case ou demarre les moussaillons </p>
	 */
	public Pion()
	{
		setX(10);
		setY(10);

	}
	/**
	 * <p> Constructeur avec en parametre la case où positionner le pion </p>
	 * <p> @param x : l'abscisse </p>
	 * <p> @param y : l'ordonnee </p>
	 */
	public Pion(int x, int y)
	{
		this.setX(x);
		this.setY(y);

	}
	
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	
	/**
	 * <p> Methode qui permet de modifier les coordonnees du pion </p>
	 * <p> @param x : son nouvelle abscisse </p>
	 * <p> @param y : son nouvelle ordonnee </p>
	 */
	public void deplacer(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	
}
