package gameBigPirate;

/**
 * <b> La classe PionMoussaillon correspond a un pion moussaillon gere par un joueur moussaillon</b>
 * <p> Les pions moussaillons sont caracterises par un booleen ATresor qui est vrai s'ils possedent un tresor et faux sinon.</p>
 * <p> Ils possedent egalement un nombre d'atouts cocotiers et d'atouts perroquets
 * 
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class PionMoussaillon extends Pion{
	private int nbAtoutCocotier;
	private int nbAtoutPerroquet;
	private boolean aTresor;
	
	/**
	 * Constructeur sans parametre. Par defaut le pion moussaillon sera positionne sur la case ou ils doivent demarrer
	 */
	public PionMoussaillon()
	{
		this.setX(10);
		this.setY(10);
		this.aTresor = false; 
		this.setNbAtoutCocotier(3);
		this.setNbAtoutPerroquet(3);
	}
	
	/**
	 * <p>Constructeur avec en parametre la case ou positionner le pion, de base le moussaillon n'a pas de tresor</p>
	 * <p>@param _x : l'abscisse </p>
	 * <p>@param _y : l'ordonnee</p>
	 */
	public PionMoussaillon(int _x, int _y){
		super(_x, _y);
		aTresor = false;
	}
	
	
	public boolean hasATresor() {
		return aTresor;
	}
	public void setATresor(boolean aTresor) {
		this.aTresor = aTresor;
	}
	public int getNbAtoutCocotier() {
		return nbAtoutCocotier;
	}
	public void setNbAtoutCocotier(int nbAtoutCocotier) {
		this.nbAtoutCocotier = nbAtoutCocotier;
	}
	public int getNbAtoutPerroquet() {
		return nbAtoutPerroquet;
	}
	public void setNbAtoutPerroquet(int nbAtoutPerroquet) {
		this.nbAtoutPerroquet = nbAtoutPerroquet;
	}
	
}
