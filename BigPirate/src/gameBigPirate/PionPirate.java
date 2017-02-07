package gameBigPirate;

/**
 * <b> La classe PionPirate correspond au pion pirate gere par le joueur pirate</b>
 * <p> Le pion pirate est caracterise par une direction</p>
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class PionPirate extends Pion{

	private int direction;
	
	/**
	 * <p>Constructeur </p>
	 * <p>@param _x : l'abscisse </p>
	 * <p>@param _y : l'ordonnee</p>
	 * <p>@param _direction : la direction dans laquelle le moussaillon se dirige</p>
	 */
	public PionPirate(int _x, int _y, int _direction){
		super(_x, _y);
		direction = _direction;
	}
	
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
