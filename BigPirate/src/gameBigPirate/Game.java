package gameBigPirate;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Game implements ActionListener {
	private int nombreMoussaillon; 
	private int indiceJoueur;
	private int coefficientPerroquet;
	
	Pion tabPion[] = new Pion[5];
	List<Integer>listeGagnant = new LinkedList<Integer>();

	/**
	 * Sert a dessiner la map
	 */
	Graphics g ;
	private Map terrain; 
	private MapAuxiliaire mapDesPerso;
	private JPanelTextField textField;
	private JFrame jf ;

	boolean finDeLaPartie;
	private boolean aDejaLanceDe;
	private boolean aDejaUtilisePerroquet;
	
	public boolean bLancerDe;
	public boolean bCocotier;
	public boolean bPerroquet;
	public boolean bFinDuTour;
	public boolean bDeposerTresor;
	
	JButton jButtonLancerDe;
	JButton jButtonCocotier;
	JButton jButtonPerroquet;
	JButton jButtonFinDuTour;
	JButton jButtonDeposerTresor;
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Constructeur/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * <p> Constructeur du jeu, permet d'initialiser toutes les variables (attributs) </p>
	 * <p> @param nombreMoussaillon </p>
	 */
	public Game(int nombreMoussaillon)
	{
		//INIT Variables
		aDejaLanceDe = false;			//Personne n'a encore lance de De
		aDejaUtilisePerroquet = false;	//Personne n'a encore lance de De
		coefficientPerroquet = 1;		//Le resultat du De sera multiplie par ce coefficientPerroquet
		this.indiceJoueur = 0;			//On commence par le premier joueur
		this.finDeLaPartie = false;		//Indicateur (flag) de jeu (=true lorsque jeu termine), verifie dans la boucle while du main
		this.nombreMoussaillon = nombreMoussaillon;	//On affecte l'attribut nombreMoussaillon par celui passe en parametre (1,2,ou 3)
		
		// Initialisation des boutons, aucun bouton n'est appuye (tous a false)
		// Ces booleens se mettent a true lorsque le bouton correspondant est appuyé
		// Cela permet de bloquer une action lorsque un meme bouton est appuye deux fois par le meme joueur dans le meme tour
		this.bLancerDe = false;			
		this.bCocotier = false;
		this.bPerroquet = false;
		this.bFinDuTour = false;
		this.bDeposerTresor = false;
		
		//Mise en place des boutons
		initButton();	
		
		//Ajout des actionListener, l'interruption se fait par l'intermediaire de la methode actionPerformed(event e)
		jButtonLancerDe.addActionListener(this);
		jButtonCocotier.addActionListener(this);
		jButtonPerroquet.addActionListener(this);
		jButtonFinDuTour.addActionListener(this);
		jButtonDeposerTresor.addActionListener(this);
		
		//INIT ListePION, cette liste contiendra l'ensemble des Pions du jeu
		for(int i=0; i<nombreMoussaillon; i++)
		{
			tabPion[i]=new PionMoussaillon();
		}
		tabPion[3]=new PionPirate(4,3,0); // creation du pion pirate
		tabPion[4]=new PionFantome(10,1); // creation du pion fantome
		
		
		//Creation du terrain, ainsi que sa mise en place
		setTerrain(new Map(g,tabPion));
		getTerrain().setLayout(null); 
		getTerrain().setBounds(0,0,600,600); // placement manuel et taille de l'interface
		//On lui attibue un MouseListener. Le jeu se deroulera ainsi par l'intermediaire de la souris.
		getTerrain().addMouseListener(new MouseListener() {
		     @Override
		     public void mouseClicked(MouseEvent e) {
		    	 gererLesDeplacement(e);
		     }

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Creation de la MapAuxiliaire. Ce dernier est une interface graphique qui indiquera le nombre de joueurs presents, le nombre d'atouts restants, et comportera l'ensemble des boutons
		setMapDesPerso(new MapAuxiliaire(nombreMoussaillon , jButtonLancerDe, jButtonCocotier, jButtonPerroquet, jButtonFinDuTour, jButtonDeposerTresor));
		getMapDesPerso().setLayout(null);
		getMapDesPerso().setBounds(600,0,240,600); // placement manuel et taille de l'interface
		
		//Creation d'un champ de texte. On indiquera l'ensemble des evenements, l'historique des actions des joueurs, dans ce champ.
		this.setTextField(new JPanelTextField());
		
		//Creation du JFrame, qui contiendra les 3 JPanels cree precedemment.
		jf = new JFrame("BigPirate");
		jf.setLayout(null);		// placement manuel
		jf.setSize(850, 850);	//Taille du JFrame
		//Ajout des JPanels
		jf.add(getTerrain());		
		jf.add(getMapDesPerso());	
		jf.add(getTextField());		
		
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE); // Sinon, le jeu tournera derriere apres avoir ferme la fenetre
	    jf.setLocationRelativeTo(null);		// Centrer la fenetre
		jf.setVisible(true);		// On affiche le JFrame

	}

	/**
	 * Cette methode definie les boutons (Creation des boutons et mise en position sur l'interface)
	 */
	public void initButton()
	{
		
		jButtonLancerDe = new JButton("Lancer De");
		jButtonLancerDe.setBounds(0,350,200,50);
		
		jButtonCocotier = new JButton("Atout Cocotier");
		jButtonCocotier.setBounds(0,400,200,50);
		
		jButtonPerroquet = new JButton("Atout Perroquet");
		jButtonPerroquet.setBounds(0,450,200,50);
		
		jButtonFinDuTour = new JButton("Fin du tour");
		jButtonFinDuTour.setBounds(0,500,200,50);
		
		jButtonDeposerTresor = new JButton("Deposer Tresor");
		jButtonDeposerTresor.setBounds(0,550,200,50);
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Methodes Principales/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * <p> Methode principale qui indique le deroulement du jeu, ainsi que la gestion des boutons en fonction de l'indice "indiceJoueur". </p>
	 * <p> Cette methode fait appel a 5 autres methodes privees (gererLanderDe(), gererFinDeTour(), gererAtoutCocotier(), gererAtoutPerroquet() et deposerTresor()). </p>
	 * <p> Ces methodes seront appelees une fois les conditions d'utilisation verifiees </p>
	 */
	public void actionPerformed(ActionEvent e) { //Bouton action, interruption
		Object source = e.getSource(); // permet de savoir sur quel bouton on a appuye
		if(indiceJoueur < 3) // Les joueurs moussaillon sont designes par les indices 0,1 et 2
		{
			if(source == this.jButtonLancerDe)
			{
				if(!aDejaLanceDe) // si il n'avait pas deja lance le De
				{
					gererLancerDe();
				}
				else // il ne peut pas jeter le De deux fois lors du meme tour
				{
					this.getTextField().setJL("Vous avez deja lance le de !");
				}
				this.bLancerDe = false; // remise a false du booleen (flag)
			} 
			else if(source == jButtonCocotier)
			{
				if(((PionMoussaillon) this.getTerrain().tabPion[indiceJoueur]).getNbAtoutCocotier()>0) // si il lui reste au moins 1 atout cocotier
				{
					gererUtilisationAtoutCocotier(); 
				}
				else 
				{
					this.getTextField().setJL("Vous n'avez plus d'atout cocotier !");
				}
				this.bCocotier = false; // remise a false du booleen (flag)
				
			} else if(source == jButtonPerroquet)
			{
				if( !aDejaLanceDe  && !aDejaUtilisePerroquet) // si il n'a pas deja lance le De et qu'il n'a pas deja utilise un atout Perroquet
				{
					if(((PionMoussaillon) this.getTerrain().tabPion[indiceJoueur]).getNbAtoutPerroquet()>0) // si il lui reste au moins 1 atout Perroquet
					{
						gererAtoutPerroquet();
					}
					else
					{
						this.getTextField().setJL("Vous n'avez plus d'atout perroquet !");
					}
					
				}
				else
				{
					this.getTextField().setJL("Vous avez deja lance le de ou utilise un atout perroquet !");
				}
				this.bPerroquet = false; // remise a false du booleen (flag)
			}
	
			else if(source == jButtonDeposerTresor)
			{
				if(((PionMoussaillon)this.getTerrain().tabPion[indiceJoueur]).hasATresor()) // si le pion a un tresor sur lui
				{
					gererDeposerTresor();
				}
				else
				{
					this.getTextField().setJL("Vous ne pouvez pas deposer de tresor, car vous n'en avez pas");
				}
				this.bDeposerTresor = false;  // remise a false du booleen (flag)
			}
			else if(source == jButtonFinDuTour)
			{
				if(getTerrain().verifierDerniereCase(this.indiceJoueur)) //Si le pion est au bout du chemin (sur la case finale)
				{
					gererFinDuTour();
				}
				else // le pion est sur une case intermediaire du chemin
				{
					this.getTextField().setJL("Vous ne pouvez pas finir sur cette case, avancez jusqu'au bout du chemin.");
				}
				this.bFinDuTour = false; // remise a false du booleen (flag)
			}
			
		}
		else if(indiceJoueur == 3) //Joueur pirate
		{
			if(source == this.jButtonLancerDe)
			{
				if(!aDejaLanceDe) // si il n'avait pas deja lance le De
				{
					gererLancerDe();
				}
				else // il ne peut pas jeter le De deux fois lors du meme tour
				{
					this.getTextField().setJL("Vous avez deja lance le de");
				}
				this.bLancerDe = false; // remise a false du booleen (flag)
			}
		}
	}
	
	/**
	 * <p>Cette methode est appelee apres avoir clique sur le terrain, sans tenir en compte sur quelle case on a clique (il se peut que le joueur ait clique en dehors de la zone de deplacement)</p>
	 * <p>Cette methode gere le deplacement en fonction de l'indice du joueur (indiceJoueur), attribut de cette classe.</p>
	 * <p>On verifie que le joueur ait clique sur une case dont le deplacement est possible.</p>
	 * <p>S'il a clique sur une case impossible à atteindre, il ne se passe rien, on attends qu'il clique sur une case valide.</p>
	 * <p>Sinon, on verifie ensuite s'il s'agit d'une case finale.</p>
	 * <p>S'il s'agit d'une case intermediaire (case non finale), on le deplace sur la case cliquee,et on attends qu'il choisisse une case finale. </p>
	 * <p>Sinon (case finale cliquee), et qu'il s'agit du joueur pirate, on le deplace et on passe au joueur suivant.</p>
	 * <p>Si les conditions sont vérifiées et qu'il s'agit d'un moussaillon, on le deplace seulement puisque le moussaillon peut a la fois deposer un tresor ou utiliser un atout cocotier (dans la limite du possible)</p>
	 * <p>Le deplacement du fantome n'est pas gere ici.</p>
	 * <p>@param e : evenement de la souris.</p>
	 */
	private void gererLesDeplacement(MouseEvent e){
   	 //Mise a jour de la souris pour recuperer les coordonnees
   	 getTerrain().miseAJourMouse(e);
   	 
   	 Point p = new Point((int)(getTerrain().mX/50),(int)(getTerrain().mY/50)); //On recupere les coordonnees de la case cliquee dans la variable p
   	 if(getTerrain().verifierCheminAvecPoint(p)) //Si la case clique est dans la zone de deplacement
   	 {
   		 getTerrain().modifierCheminContenantPoint(p,this.indiceJoueur); //modifie la listeCheminCase
   		 
    	 if(indiceJoueur>2) // Cas autre que Moussaillon
    	 {
    		 if(indiceJoueur ==3) //Cas Pirate
    		 {
    			 getTerrain().deplacer(indiceJoueur); // Deplacement du pirate
    			 if(getTerrain().verifierCheminAvecPoint(p,getTerrain().listeCaseFinal)) //le pirate a choisi son chemin
    			 {
    				 //Le pion Pirate a fini de choisir un chemin, on modifie donc sa direction
    				 getTerrain().deplacer(indiceJoueur);
    				 modifierDirectionPirate();
    				 
    				 //On verifie ensuite s'il y avait des moussaillons sur son chemin
    				 List<Integer> ListeMoussaillonATuer = getTerrain().verifierMoussaillonSurChemin();
    				 if(ListeMoussaillonATuer != null) //Si il y avait au moins un moussaillon sur son chemin
    				 {
    					 tuerLesMoussaillons(ListeMoussaillonATuer); //Modifie les moussaillon du terrain.tabPion en null
    					 verifierFinPartie(); //On verifie si les moussaillons sont tous tue (null) ou non
    				 }
    				 // si ce moussaillon avait un tresor sur lui lorsqu'il s'est fait tue, on remet le tresor sur la grotte
    				 List<Tresor> ListeTresorARemettre = terrain.verifierTresor(new Point(terrain.tabPion[3].x,terrain.tabPion[3].y));
    				 if(ListeTresorARemettre != null)
    				 {
    					 remettreTresor(ListeTresorARemettre);
    				 }
        	   		 
    	    		 joueurSuivant();
    	    		 getTerrain().initTour(); //init listCase, resultatDe = 0;
    			 }
    		 }
    	 }
		 else //Cas des moussaillons
		 {
			 //On verifie si on est pas passe par au moins un tresor;
			 if(!((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).hasATresor()) //si le moussaillon n'a pas encore de tresor sur lui
			 {
    			 Chemin cAux = getTerrain().getCheminRestant(this.indiceJoueur);
				 Tresor tAux = getTerrain().verifierTresor(cAux); //Retourne le premier tresor rencontre sur le chemin, null si non existant
				 if(tAux != null)
				 {
					 getTerrain().listeTresor.remove(tAux);
					 ((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).setATresor(true);
					 System.out.println("Le joueurMoussaillon " + indiceJoueur + " recupere le tresor");
				 }
			 }
	   		 getTerrain().deplacer(indiceJoueur);
		 }

	 }

   	}
	
	/**
	 * Cette methode peut etre divisee en plusieurs parties, étant :
	 * <p> - Initialisation des variables (booleans, coefficientPerroquet, etc ...) </p>
	 * <p> - Incrémentation de indiceJoueur jusqu'a avoir un joueur "vivant" (c'est-a-dire encore dans la partie)</p>
	 * <p> - Verification si la partie est fini ou non (si oui, on met le flag (boolean) finDeLaPartie a true).</p>
	 */
	private void joueurSuivant()
	{
		// Initialisation des variables
		this.aDejaLanceDe = false;
		this.coefficientPerroquet = 1;
		this.aDejaUtilisePerroquet = false;
		this.mapDesPerso.miseAJourResultatDe(0);
		//Donne la main au joueur n'ayant pas encore perdu
		System.out.println("JoueurSuivant");
		this.indiceJoueur = (this.indiceJoueur+1) % 5;
		this.afficherCoordonneesPion();
		while(getTerrain().tabPion[indiceJoueur]==null) // tant qu'on a pas trouve de joueur encore dans la partie
		{
			while(getTerrain().tabPion[indiceJoueur]==null) // tant qu'on a pas trouve de joueur encore dans la partie
			{
				this.indiceJoueur = (this.indiceJoueur+1) % 5;
				this.mapDesPerso.joueurSuivant();
				System.out.println("indiceJoueur :" + indiceJoueur);
			}
		}
		getTerrain().initTour();
		if(getTerrain().tabPion[0]==null && getTerrain().tabPion[1]==null && getTerrain().tabPion[2]==null) // tous les moussaillons sont morts
		{
			this.finDeLaPartie=true;
		}
		if(indiceJoueur == 4) //Si c'est au tour du fantome
		{
			gererFantome();
		}
		//this.mapDesPerso.miseAJourJoueur(indiceJoueur);
		switch (indiceJoueur)
		{
		case 0:
		case 1:
		case 2:
			this.getTextField().setJL("A toi de jouer ! Joueur moussaillon " + (indiceJoueur+1) +".");
			break;
		case 3:
			this.getTextField().setJL("A toi de jouer ! Joueur pirate !"+".");
			break;
		case 4:
			this.getTextField().setJL("Tour du fantome.");
			break;
		}
		this.mapDesPerso.joueurSuivant();
		this.mapDesPerso.repaint();
	}
	
	/**
	 * Verifie si tous les moussaillons sont morts : si oui, fin de la partie, victoire du pirate
	 */
	private void verifierFinPartie() {
		boolean resteUnVivant = false;
		int i=0 ;
		while ((i<this.nombreMoussaillon)&&(resteUnVivant==false)) {
			if(getTerrain().tabPion[i]!=null)
			{
				resteUnVivant = true;
			}
			i++;
		}
		
		if(!resteUnVivant) //Cas ou le pirate a gagne (plus aucun moussaillon vivant)
		{
			this.finDeLaPartie = true;
		}
		
	}

	/**
	 * <p>Cette methode verifie si le moussaillon (caracterise par indiceJoueur) est dans la barque avec un tresor. </p>
	 * <p>Si oui, on ajoute l'indice dans une liste de victoire et on l'enleve du tableau des Pions.</p>
	 */
	private void verifierVictoireMoussaillon() {
		
		PionMoussaillon pm = (PionMoussaillon)getTerrain().tabPion[indiceJoueur];
		if(getTerrain().tab[pm.x][pm.y] == 3) // s'il est sur la barque
		{
			if(pm.hasATresor()) // s'il a un tresor
			{
				getTerrain().tabPion[indiceJoueur]=null; // On le sort de la partie, il a gagné
				this.listeGagnant.add(indiceJoueur);
				System.out.println("Le joueur " + indiceJoueur + " a gagne !");
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Methodes privees/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * <p>Cette methode modifie l'attribut "direction" (int) de la classe PionPirate</p>
	 * <p>1 => haut</p>
	 * <p>2 => bas</p>
	 * <p>3 => gauche</p>
	 * <p>4 => droite</p>
	 */
	private void modifierDirectionPirate() {
		//On calcule la direction
		int direction = 0;
		Chemin cheminChoisit = getTerrain().listeCheminCase.get(0);
		cheminChoisit.afficher();
		
		Point pDernier 		= cheminChoisit.chemin.get(cheminChoisit.chemin.size()-1);
		Point pAvantDernier = cheminChoisit.chemin.get(cheminChoisit.chemin.size()-2);
		
		System.out.println("pDernier.x :" + pDernier.x);
		System.out.println("pDernier.y :" + pDernier.y);
		System.out.println("pAvantDernier.x :" + pAvantDernier.x);
		System.out.println("pAvantDernier.y :" + pAvantDernier.y);
		
		if(pDernier.y == pAvantDernier.y-1) //Si le pirate s'est deplace vers le haut
		{
			direction = 1;
		}
		else if(pDernier.y == pAvantDernier.y+1)//Si le pirate s'est deplace vers le bas
		{
			direction = 2;
		}
		else if(pDernier.x == pAvantDernier.x-1)//Si le pirate s'est deplace vers la gauche
		{
			direction = 3;
		}
		else if(pDernier.x == pAvantDernier.x+1)//Si le pirate s'est deplace vers la droite
		{
			direction = 4;
		}
		//On modifie la direction
		((PionPirate)getTerrain().tabPion[3]).setDirection(direction);
		System.out.println("Direction modifier en :" + direction);
	}

	/**
	 * <p>Cette methode permet de "tuer" les moussaillons, autrement dit d'enlever (de remplacer par null) les moussaillons de la liste terrain.tabPion </p>
	 * <p>Cette methode est appele apres chaque fin de deplacement du pirate (ce n'est qu'au tour de pirate qu'un moussaillon peut mourir).</p>
	 * <p>@param listeIndiceMoussaillon: liste des moussaillons a enlever du tableau terrain.tabPion</p>
	 */
	private void tuerLesMoussaillons(List<Integer> listeIndiceMoussaillon) {
		//Suppression des moussaillons de la liste terrain.listPion
		for(int i:listeIndiceMoussaillon)
		{
			System.out.println("Le moussaillon " + (i+1) + " a ete tue");
			this.textField.setJL("Le moussaillon " + (i+1) + " a ete tue");
			getTerrain().tabPion[i]=null;
		}
	}
	
	/**
	 * <p>Cette methode est appele par l'intermediaire du bouton "Deposer le tresor".</p>
	 * <p>On fait de sorte que, le moussaillon depose a la case d'avant (case derriere lui). Ainsi, le tresor ne peut pas etre depose si le moussaillon n'a pas encore lance le De ou s'il n'a pas commence son parcours.</p>
	 * <p>Cette methode verifie d'abord que le moussaillon a lance le De (Si la liste listeCheminCase est vide, le moussaillon n'a pas encore lance le De)</p>
	 * <p>Si ce n'est pas le cas, il ne peut pas deposer le tresor.</p>
	 * <p>Ensuite, apres avoir recupere la position de la case d'avant, on depose le tresor sur cette case (on ajoute le tresor ayant ses coordonnees egaux a ceux de la case precedente dans la liste terrain.listeTresor)</p>
	 * <p>On n'oublie pas de modifier l'attribue PionMoussaillon.aTresor en false.</p>
	 * <p>Cette methode est appele seulement si le moussaillon possede un tresor, ainsi la verification ne se fait pas ici.</p>
	 */
	private void gererDeposerTresor() {
		if(!getTerrain().listeCheminCase.isEmpty())
			{
			//il se peut qu'il n'a pas fini son chemin, on prend le premier chemin de la liste.
			Chemin cheminPrisParMoussaillon = getTerrain().listeCheminCase.get(0); 
			
			//On cherche la case juste avant
			Point pAuxMoussaillon = new Point(getTerrain().tabPion[indiceJoueur].x,getTerrain().tabPion[indiceJoueur].y);
			int indexPointMoussaillon = cheminPrisParMoussaillon.chemin.indexOf(pAuxMoussaillon);
			System.out.println("indexPointMoussaillon :" + indexPointMoussaillon);
			
			if(indexPointMoussaillon>0) //Si le moussaillon a bouge, il peut deposer le tresor et on le depose a la case juste avant 
			{
				//On a deja verifie que le moussaillon possedait un tresor, on l'enleve
				((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).setATresor(false);
				Point caseJusteAvant = cheminPrisParMoussaillon.chemin.get(indexPointMoussaillon-1);
				getTerrain().listeTresor.add(new Tresor(caseJusteAvant.x,caseJusteAvant.y));
			}
		}
		this.getTerrain().repaint(); // remise a jour du terrain
		this.getTextField().setJL("Le moussaillon " + (indiceJoueur + 1) + "depose son tresor");
	}
	
	/**
	 * <p>Cette methode est appelee lorsqu'un joueur, possedant au moins un atout cocotier, a appuye sur le bouton "Utiliser Atout Cocotier".</p>
	 * <p>On verifie si la position du moussaillon en question est coherente ou non (sur une case "Orange Cocotier")</p>
	 * <p>Si valide, on cherche la case cocotier correspondante et on deplace le pion moussaillon sur cette case.</p>
	 * <p>Une fois au dessus d'un cocotier, le joueur moussaillon a fini son tour, on passe au joueur suivant.</p>
	 */
	private void gererUtilisationAtoutCocotier() {
		//On a deja verifie qu'il s'agit d'un moussaillon, pas besoin de faire la verification ici
		PionMoussaillon pm = (PionMoussaillon)getTerrain().tabPion[indiceJoueur];
		if(getTerrain().tab[pm.x][pm.y]==1 || getTerrain().tab[pm.x][pm.y]==5 ) //Si le moussaillon est sur une case OrangeCocotier ou sur un cocotier
		{
			((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).setNbAtoutPerroquet(pm.getNbAtoutPerroquet()-1); //nb-1
			this.mapDesPerso.utiliserAtoutCocotier(indiceJoueur); //nb -1 pour l'interface graphique
			Point pCocotier = getTerrain().chercherCocotier(pm); //pCocotier = case Cocotier
			((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).x = pCocotier.x;
			((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).y = pCocotier.y;
			this.getTextField().setJL("Le joueur moussaillon " + (indiceJoueur+1) + " utilise un atout cocotier");
			joueurSuivant();
		}
		else
		{
			this.getTextField().setJL("Vous ne pouvez pas utiliser l'atout cocotier ici");
		}
	}

	/**
	 * <p>Cette methode gere le deplacement du fantome.</p>
	 * <p>On verifie si au moins un moussaillon possedant un tresor est dans son champ de mouvement. </p>
	 * <p>Si oui, il va sur le moussaillon et deplace le tresor aleatoirement sur la map, en calculant la direction.</p>
	 */
	private void gererFantome() {
		if(this.getTerrain().getResultatDe()==0) // si le fantome n'a pas encore lance le De (aucun deplacement enregistre)
		{
			int resultatDe = (int)((Math.random()*3)+1) ;
			System.out.println("Le Fantome lance le de :" + resultatDe);
			this.getTerrain().setResultatDe(resultatDe);
			this.getTerrain().deplacer(indiceJoueur);
		}
		joueurSuivant();
		getTerrain().initTour(); //init listCase, resultatDe = 0;
		afficherCoordonneesPion();
	}

	/**
	 * <p>Cette methode est appele par le pirate. Il s'agit de remettre le tresor dans la grotte lorsqu'il passe sur un tresor.</p>
	 * <p>Elle supprime de la liste terrain.listeTresor les tresors qui ont ete passe en parametre dans la liste des tresors.</p>
	 * <p>Elle ajoute dans cette liste terrain.listeTresor un tresor dont les coordonnees correspondent a la case grotte.</p>
	 * <p>@param listeTresorARemettre : liste des tresors a supprimer du terrain et a remettre dans la grotte.</p>
	 */
	private void remettreTresor(List<Tresor> listeTresorARemettre) {
		for(Tresor t: listeTresorARemettre)
		{
			getTerrain().listeTresor.remove(t); //On le supprime
			getTerrain().listeTresor.add(new Tresor()); //On le remet a la position initiale, autrement dit la grotte
		}
	}

	/**
	 * <p>Cette methode genere un entier aleatoire (dont le seuil depend de l'indice "indiceJoueur").</p>
	 * <p>Elle met egalement l'attribut booleen "aDejaLanceDe" a 'true' pour empecher le joueur de relancer le De.</p>
	 * <p>Enfin, elle fait appel a la methode getChemin(int indiceJoueur) qui determinera les chemins (liste de liste de case) que le pion peut prendre.</p>
	 * <p>Le deplacement du pirate n'est pas gere ici.</p>
	 */
	private void gererLancerDe()
	{
		if(!this.finDeLaPartie){
			int resultatDe = 0;
			aDejaLanceDe = true;
			if(this.indiceJoueur < 3) // Cas Moussaillon
			{
				resultatDe = (int)((Math.random()*3)+1);
				this.getTextField().setJL("Le joueur moussaillon " + (indiceJoueur+1) + " lance le de : " + resultatDe);
			}
			else //Cas Pirate 
			{
				resultatDe = (int)((Math.random()*6)+1);
				this.getTextField().setJL("Le joueur pirate lance le de : " + resultatDe);
			}
			resultatDe = resultatDe * this.coefficientPerroquet;
			this.getTerrain().setResultatDe(resultatDe);
			this.getTerrain().getChemin(indiceJoueur);
			this.mapDesPerso.miseAJourResultatDe(resultatDe);
		}
	}
	
 	/**
	 * <p>Cette methode est appele une fois que l'on a verifier qu'il s'agit du tour d'un moussaillon, et que ce dernier possede au moins un atout perroquet</p>
	 * <p>Cette methode affecte l'attribut "coefficientPerroquet" a deux. Ce coefficient sera multiplie par le resultat du lance de De et reinitialise a 1 apres la fin du tour.</p>
	 */
	private void gererAtoutPerroquet()
	{
		PionMoussaillon pm = (PionMoussaillon) getTerrain().tabPion[indiceJoueur];
		if(pm.getNbAtoutPerroquet()>0) //Si il reste des atouts perroquet...
		{
			this.aDejaUtilisePerroquet = true;
			System.out.println("Nombre d'Atout est de :" + pm.getNbAtoutPerroquet());
			((PionMoussaillon) getTerrain().tabPion[indiceJoueur]).setNbAtoutPerroquet(pm.getNbAtoutPerroquet()-1);
			System.out.println("Nombre d'Atout est de :" + ((PionMoussaillon)getTerrain().tabPion[indiceJoueur]).getNbAtoutPerroquet());
			this.coefficientPerroquet = 2;
			this.mapDesPerso.utiliserAtoutPerroquet(indiceJoueur);
			this.getTextField().setJL("Le joueur moussaillon " + (indiceJoueur+1) + " utilise un atout perroquet");
		}
		else
		{
			this.getTextField().setJL("Vous n'avez plus d'atout perroquet !");
		}
	}
	
	/**
	 * <p>Cette methode ne peut etre appele que par un moussaillon ayant fini son chemin.</p>
	 * <p>Cette methode verifie si le moussaillon a gagne ou pas, puis donne la main au joueur suivant.</p>
	 * <p>Enfin, elle initialise tout attribut necessaire (par exemple, le "coefficientPerroquet").</p>
	 */
	private void gererFinDuTour()
	{
		 System.out.println("Bouton FinDuTour appuye");
		 this.getTextField().setJL("Fin du joueur moussaillon " + (indiceJoueur+1));
		 verifierVictoireMoussaillon();
		 joueurSuivant();
		 getTerrain().initTour(); //init listCase, resultatDe = 0;
		 this.getTerrain().repaint();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Autres methodes/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Methode complementaire, utilisee lors de certains tests. 
	 */
	private void afficherCoordonneesPion()
	{
		for(int i=0;i<3;i++)
		{
			if(getTerrain().tabPion[i] != null)
			{
				System.out.println("PionMoussaillon " + (i+1) +" "+ getTerrain().tabPion[i].x + " " + getTerrain().tabPion[i].y);
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Methodes GetSets/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public Map getTerrain() {
		return terrain;
	}

	public void setTerrain(Map terrain) {
		this.terrain = terrain;
	}


	public MapAuxiliaire getMapDesPerso() {
		return mapDesPerso;
	}

	public void setMapDesPerso(MapAuxiliaire mapDesPerso) {
		this.mapDesPerso = mapDesPerso;
	}

	public JPanelTextField getTextField() {
		return textField;
	}
	
	public void setTextField(JPanelTextField textField) {
		this.textField = textField;
	}

}
