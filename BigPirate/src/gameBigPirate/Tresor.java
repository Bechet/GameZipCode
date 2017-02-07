package gameBigPirate;

/**
 * <b> La classe Tresor correspond au tresor present sur la map</b>
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class Tresor extends Pion{

	/**
	 * <p>Constructeur avec en parametre la case ou positionner le tresor</p>
	 * <p>@param x : l'abscisse </p>
	 * <p>@param y : l'ordonnee</p>
	 */
	public Tresor(int x, int y) {
		super(x, y);
	}
	
	/**
	 * <p>Constructeur sans parametre. Par defaut le tresor sera dans la case grotte</p>
	 */
	public Tresor()
	{
		this.setX(4);
		this.setY(3);
	}


}
