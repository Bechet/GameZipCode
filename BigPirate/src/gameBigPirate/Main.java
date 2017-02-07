package gameBigPirate;

/**
 * Voici le main du projet
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class Main {

	/**
	 * Le main qui lance le jeu
	 * @param args
	 */
	public static void main(String[] args)
	{
		int nbMoussaillon;
		boolean debutPartieOK=false;
		EcranDebutPartie fenetre = new EcranDebutPartie(debutPartieOK);
		
		while (!debutPartieOK){
			debutPartieOK=fenetre.isDebutPartieOK();
			try {	
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		nbMoussaillon=fenetre.getNbMoussaillon();
		Game s = new Game(nbMoussaillon);
		s.getTerrain().repaint();
		while(!s.finDeLaPartie)
		{
			try{
				//toutes les 10ms
				Thread.sleep(10);
			}catch(Exception e)
			{
				System.out.println("Exception détectée !");
			}
		}
		
		//On reaffiche l'interface graphique
		s.getTerrain().repaint();
		
		System.out.println();
		System.out.println("FIN DE LA PARTIE");
		System.out.println();
		
		if(s.listeGagnant.isEmpty())
		{
			System.out.println("LE PIRATE A GAGNE CETTE PARTIE");
			s.getTextField().setJL("LE PIRATE A GAGNE CETTE PARTIE");
		}
		else
		{
			for(int i: s.listeGagnant)
			{
				System.out.println("Le joueur "+ (i+1) + " a gagne !");
				s.getTextField().setJL("Le joueur "+ (i+1) + " a gagne !");
			}
		}

	}
}
