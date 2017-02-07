package gameBigPirate;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Map extends JPanel{
	
	int[][] tab;
	int mX;
	int mY;
	
	List<Chemin> listeCheminCase = new LinkedList<Chemin>();
	List<Point> listeCaseFinal = new LinkedList<Point>();
	
	Chemin cheminChoisi = null;
	
	private int resultatDe = 0;
	int nombreMoussaillon = 3;
	//JPanel
	JLabel jlX ;
	JLabel jlY ;

	public Pion tabPion[];
	public List<Tresor> listeTresor = new LinkedList<Tresor>();
	
	Image imagePion ;
	Image imageTerrain;
	Image imageContour;
	Image imageTresor;
	Image imageTresor3030;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////Constructeur///////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * <p>Constructeur, initialise le terrain (matrice d'entier "tab")</p>
	 * <p>@param g : graphics</p>
	 * @param tabPion : tableau de pion contenant l'ensemble des pions (moussaillons, pirate et fantome)</p>
	 */
	public Map(Graphics g, Pion tabPion[])
	{
		initTour();
		
		this.tabPion = tabPion;
		
		//Jaune = 0                     
		//OrangeCocotier = 1            
		//Grotte = 2                    
		//Barque = 3                    
		//Vert = 4
		//VertCachetteCocotier = 5      
		//bleu = 6	                    
		tab = new int[][]{
				  { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
				  { 6, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 6 },
				  { 6, 0, 5, 4, 4, 0, 4, 5, 4, 4, 0, 6 },
				  { 6, 0, 4, 0, 0, 0, 0, 0, 1, 0, 0, 6 },
				  { 6, 0, 0, 2, 4, 0, 4, 4, 5, 4, 0, 6 },
				  { 6, 1, 5, 4, 0, 0, 4, 4, 4, 4, 0, 6 },
				  { 6, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 6 },
				  { 6, 0, 4, 4, 0, 4, 0, 4, 4, 4, 0, 6 },
				  { 6, 0, 4, 5, 1, 0, 0, 0, 0, 0, 0, 6 },
				  { 6, 1, 5, 4, 4, 0, 4, 4, 4, 4, 0, 6 },
				  { 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
				  { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 3 }
				};
		
		
		//INIT MOUSE
		mX = 0;
		mY = 0;
		
		//Init JComponent
		jlX = new JLabel("0");
		jlY = new JLabel("0");
		jlX.setBounds(50,550,100,40);	//déplacement des coordonnées en x de la souris
		jlY.setBounds(100,550,100,40);	//déplacement des coordonnées en y de la souris

		this.add(jlX);
		this.add(jlY);
	
		//INIT IMAGE
		try{
			imagePion = ImageIO.read(new File("Assets/Pion.png"));
			imageTerrain = ImageIO.read(new File("Assets/backgroundTerrain.png"));
			imageContour = ImageIO.read(new File("Assets/Contour.png"));
			imageTresor = ImageIO.read(new File("Assets/Tresor.png"));
			imageTresor3030 = ImageIO.read(new File("Assets/Tresor3030.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for(int i=0; i<nombreMoussaillon; i++)
		{
			listeTresor.add(new Tresor());

		}

		this.setVisible(true);
		

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Methodes Principales/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * <p>Methode d'initialisation</p>
	 * <p>Appele apres chaque fin de tour, reinitialise la liste des chemins, la liste des cases finales, et le resultat du De (que l'on affiche)</p>
	 */
	public void initTour()
	{
		//Appele par la methode JoueurSuivant() dans Game.java
		this.listeCheminCase = new LinkedList<Chemin>();
		this.listeCaseFinal = new LinkedList<Point>();
		
		this.resultatDe = 0;
	}
	
	/**
	 * <p>Methode Draw, dessine le terrain (case), les pions, les chemins, les tresors (sur le terrain)</p>
	 * <p>Cette methode est appelee par this.repaint()</p>
	 */
	public void paintComponent(Graphics g)
	{
		//Tracer le terrain
		for(int i=0; i<tab.length; i++)
		{
			for(int j=0; j<tab[0].length; j++)
			{
			    g.drawImage(imageTerrain,i*50,j*50,(i+1)*50,(j+1)*50,tab[i][j]*50,0,tab[i][j]*50+50,50,null);
			}
		}
		//Tracer les tresors sur le terrain
		if(!this.listeTresor.isEmpty())
		{
			for(Tresor t: listeTresor)
			{
				g.drawImage(imageTresor, t.x*50,t.y*50, (t.x+1)*50, (t.y+1)*50,0,0,50, 50,null);
			}
		}
		//Tracer le contour des cases pour les cases finales
		if(!this.listeCaseFinal.isEmpty())
		{
			for(Point p: listeCaseFinal)
			{
				g.drawImage(imageContour, p.x*50, p.y*50, (p.x+1)*50, (p.y+1)*50, 0,0,50, 50,null);
			}
		}
		//Dessiner les pions (et les attributs tresors)
		for(int i=0; i<tabPion.length;i++)
		{
			if(tabPion[i]!=null)
			{
				g.drawImage(imagePion, tabPion[i].x*50, tabPion[i].y*50, (tabPion[i].x+1)*50, (tabPion[i].y+1)*50, i*50,0,(i+1)*50, 50,null);
				if(i<3) //Si c'est un moussaillon
				{
					if(((PionMoussaillon) tabPion[i]).hasATresor())
					{
						g.drawImage(imageTresor3030, tabPion[i].x*50+20, tabPion[i].y*50+20, (tabPion[i].x+1)*50, (tabPion[i].y+1)*50, 0,0,30, 30,null);
					}
				}

			}
			
		}
		//Tracer les chemins
		if(!this.listeCheminCase.isEmpty())
		{
			for(Chemin c: listeCheminCase)
			{
				for(Point p: c.chemin)
				{
					g.drawRect(p.x*50, p.y*50, 50, 50);
				}
			}

		}
	}
	
	/**
	 * <p>Methode principale de deplacement, en fonction de l'indice joueur passe en parametre</p>
	 * <p>Pour tous les pions sauf le pion fantome, on modifie les coordonnees x et y de ce pion a la case cliquee.</p>
	 * <p>Pour le pion fantome, on lui lance le De, puis on effectue les verifications. </p>
	 * <p>@param indiceJoueur : indice du joueur pour ce tour, pour ainsi recuperer le pion dans le tableau des pions</p>
	 */
	public void deplacer(int indiceJoueur)
	{
		switch(indiceJoueur)
		{
		case 0:
		case 1:
		case 2:
		case 3:
			tabPion[indiceJoueur].x = (int) (mX/50);
			tabPion[indiceJoueur].y = (int) (mY/50);
			this.repaint();
			break;
		case 4: 
			gererDeplacementFantome();
			break;
		}
	}
	
	/**
	 * <p>Methode qui gere le deplacement du fantome</p>
	 * <p>Verifie l'existence d'un moussaillon proche par la methode "VerifierMoussaillonAvecTresor()".</p>
	 * <p>Deplace le fantome en fonction du resultat (le fantome est oblige d'aller sur un moussaillon ayant un tresor, si cela est possible).</p>
	 */
	private void gererDeplacementFantome()
	{
		int directionMoussaillonProche = verifierMoussaillonAvecTresor(); //int = 0 => pas de moussaillon proche avec un tresor
		if(directionMoussaillonProche == 0)
		{
			deplacerAleatoirementFantome();
		}
		else //Si on a trouve un moussaillon proche avec un tresor, on a la direction vers ce moussaillon grace a la methode deplacerFantomeVersMoussaillon 
		{
			deplacerFantomeVersMoussaillon(directionMoussaillonProche);
		}
		this.repaint();
	}
	
	/**
	 * <p>Methode appele par la methode "gererDeplacementFantome()" si un moussaillon avec un tresor peut etre atteint par le fantome.</p>
	 * <p>En fonction de l'entier passe en parametre, on cree le chemin que le fantome va parcourir.</p>
	 * <p>Ensuite, on deplace le fantome jusqu'a la fin de ce chemin.</p>
	 * <p>Enfin, on verifie si des moussaillons, possedant chacun un tresor, sont sur le chemin en question (il y en a au moins 1).</p>
	 * <p>Pour chaque moussaillon ainsi trouve, on affecte leurs attributs "aTresor" a false, et on remet aleatoirement un tresor sur une case accessible par un moussaillon.</p>
	 * <p>@param directionMoussaillonProche : direction du moussaillon le plus proche possedant un tresor</p>
	 */
	private void deplacerFantomeVersMoussaillon(int directionMoussaillonProche) {
		int i=1 ;
		Chemin cFantome = new Chemin();
		cFantome.prolongerChemin(new Point(tabPion[4].x, tabPion[4].y)); //case initiale du fantome, si un moussaillon ayant un tresor se situe sur la meme case que le fantome, le moussaillon se fait voler le tresor
		switch(directionMoussaillonProche)
		{
		case 1:
			while((i<=resultatDe) && (tabPion[4].y>0)) // tant qu'on a effectue autant de deplacements que la valeur du De, ou bien qu'on est a la limite du terrain, on avance :
			{
				tabPion[4].y--;
				cFantome.prolongerChemin(new Point(tabPion[4].x, tabPion[4].y));
				i++ ;
			}
			break;
		case 2:
			while((i<=resultatDe) && (tabPion[4].y<11)) // tant qu'on a effectue autant de deplacements que la valeur du De, ou bien qu'on est a la limite du terrain, on avance :
			{
				tabPion[4].y++;
				cFantome.prolongerChemin(new Point(tabPion[4].x, tabPion[4].y));
				i++;
			}
			break;
		case 3:
			while((i<=resultatDe) && (tabPion[4].x>0)) // tant qu'on a effectue autant de deplacements que la valeur du De, ou bien qu'on est a la limite du terrain, on avance :
			{
				tabPion[4].x--;
				cFantome.prolongerChemin(new Point(tabPion[4].x, tabPion[4].y));
				i++;
			}
			break;
		case 4:
			while((i<=resultatDe) && (tabPion[4].x<11)) // tant qu'on a effectue autant de deplacements que la valeur du De, ou bien qu'on est a la limite du terrain, on avance :
			{
				tabPion[4].x++;
				cFantome.prolongerChemin(new Point(tabPion[4].x, tabPion[4].y));
				i++; 
			}
			break;
		}
		//Chemin cree, on verifie les moussaillons presents sur le chemin

		for(i=0;i<3;i++)
		{
			if(tabPion[i]!=null) // moussaillon vivant
			{
				if(cFantome.chemin.contains(new Point(tabPion[i].x, tabPion[i].y))) // sur le chemin du fantome, il y a le moussaillon i
				{
					System.out.println("Le moussaillon "+(i+1) + " a perdu son tresor");
					((PionMoussaillon) tabPion[i]).setATresor(false);
					placerAleatoirementTresor();
				}
			}
		}
	}
	
	/**
	 * <p>Cette methode est appele si aucun moussaillon n'a ete trouve, d'ou un deplacement aleatoire du fantome.</p>
	 * <p>On lance un De a quatre face qui indiquera une direction que le fantome va prendre.</p>
	 * <p>Tant que le fantome n'a pas pu se deplacer (par exemple, deplacement en dehors du terrain), on relance le De.</p>
	 * <p>Enfin, apres avoir trouve une direction dont le deplacement est possible, on deplace le pion fantome vers cette direction.</p>
	 */
	private void deplacerAleatoirementFantome()
	{

		int resultatDeDirection = (int)(Math.random()*4)+1; // choix de la direction
		boolean deplacementPossible = false;
		while(!deplacementPossible) // tant qu'on a pas un deplacement possible :
		{
			switch(resultatDeDirection)
			{
			case 1: //haut
				if(this.tabPion[4].y > this.resultatDe) // si on peut deplacer, on deplace
				{
					System.out.println("FantomeY :" + this.tabPion[4].y + "ResultatDe = " +this.resultatDe);
					tabPion[4].y -=resultatDe;
					deplacementPossible = true;
				}
				else // sinon, on relance le De pour le choix de la direction
				{
					 resultatDeDirection = (int)(Math.random()*4)+1; 
				}
				break;
			case 2: //bas
				if(this.tabPion[4].y < 12-this.resultatDe) // si on peut deplacer, on deplace
				{
					System.out.println("FantomeY :" + this.tabPion[4].y + "ResultatDe = " +this.resultatDe);
					tabPion[4].y +=resultatDe;
					deplacementPossible = true;
				}
				else // sinon, on relance le De pour le choix de la direction
				{
					 resultatDeDirection = (int)(Math.random()*4)+1;
				}
				break;
			case 3: //gauche
				if(this.tabPion[4].x > this.resultatDe) // si on peut deplacer, on deplace
				{
					System.out.println("FantomeX :" + this.tabPion[4].x + "ResultatDe = " +this.resultatDe);
					tabPion[4].x -=resultatDe;
					deplacementPossible = true;
				}
				else // sinon, on relance le De pour le choix de la direction
				{
					 resultatDeDirection = (int)(Math.random()*4)+1;
				}
				break;
			case 4: //droite
				if(this.tabPion[4].x < 12-this.resultatDe) // si on peut deplacer, on deplace
				{
					System.out.println("FantomeX :" + this.tabPion[4].x + "ResultatDe = " +this.resultatDe);
					tabPion[4].x +=resultatDe;
					deplacementPossible = true;
				}
				else // sinon, on relance le De pour le choix de la direction
				{
					 resultatDeDirection = (int)(Math.random()*4)+1;
				}
				break;
			}
		}
	}

	/**
	 * <p>Methode complementaire, elle met a jour les coordonnees de la souris et l'affiche en bas a gauche du terrain.</p>
	 * <p>@param me : mouse event, contenant les coordonnees de la souris.</p>
	 */
	public void miseAJourMouse(MouseEvent me)
	 {
		mX = (int) me.getPoint().getX();
		mY = (int) me.getPoint().getY();
		jlX.setText("x: "+Integer.toString(mX));
		jlY.setText("y: "+Integer.toString(mY));
	 }

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////Methodes Auxiliaires/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Gestion de la souris
	/**
	 * <p>Methode appelee par le fantome uniquement.</p>
	 * <p>Cette methode ajoute un objet tresor de coordonnees aleatoires (mais coherentes, c'est-a-dire accessible par les moussaillons) dans la liste listeTresor.</p>
	 */
	private void placerAleatoirementTresor() {
		int x = (int) (Math.random() * 11) +1;
		int y = (int) (Math.random() * 11) +1;
		while(this.tab[x][y]>3) //Tant que la case n'est pas accessible, on relance l'aleatoire
		{
			x = (int) (Math.random() * 11) +1;
			y = (int) (Math.random() * 11) +1;
		}
		System.out.println("Le tresor a ete place aleatoirement par le fantome en x:" + x + " y:" + y);
		this.listeTresor.add(new Tresor(x,y));
		
	}
	
	/**
	 * <p>Methode appelee par le fantome uniquement.</p>
	 * <p>Cette methode retourne la direction (1 pour le haut, 2 pour le bas, 3 pour la gauche, 4 pour la droite) du premier moussaillon proche ayant un tresor.</p>
	 * <p>Si aucun moussaillon est trouve, on renvoie 0.</p>
	 * <p>@return direction: direction d'un moussaillon ayant un tresor, 0 si inexistant.</p>
	 */
	
	// RAPPORT (pb de complexite, des ifs inutiles (4 ifs concurrents qui change chacun la direction dans un if direction=0 ?????))
	private int verifierMoussaillonAvecTresor() { //Appele par le fantome
		Point p = new Point(tabPion[4].x,tabPion[4].y);
		int direction = 0;
		for(int i=0; i<4;i++)
		{
			if(direction ==0)
			{
				if(p.y>i)
				{
					for(int j=0; j<3;j++)
					{
						if(tabPion[j] != null) //Si le moussaillon j est vivant (encore en jeu)
						{
							if(((PionMoussaillon) tabPion[j]).hasATresor() && tabPion[j].y == p.y-i && tabPion[j].x == p.x) // si ce moussaillon j a un tresor et qu'il est atteignable par le fantome, alors :
							{
								System.out.println("Pion moussaillon avec un tresor trouve en x :" + tabPion[j].x +" y :" + tabPion[j].y);
								direction = 1;
							}
						}
					}
				}
				if(p.y<12-i)
				{
					for(int j=0; j<3;j++)
					{
						if(tabPion[j] != null) //Si le moussaillon j est vivant
						{
							if(((PionMoussaillon) tabPion[j]).hasATresor() && tabPion[j].y == p.y+i && tabPion[j].x == p.x) // si ce moussaillon j a un tresor et qu'il est atteignable par le fantome, alors :
							{
								System.out.println("Pion moussaillon avec un tresor trouve en x :" + tabPion[j].x +" y :" + tabPion[j].y);
								direction = 2;
							}
						}
					}
				}
				if(p.x>i)
				{
					for(int j=0; j<3;j++)
					{
						if(tabPion[j] != null) //Si le moussaillon j est vivant
						{
							if(((PionMoussaillon) tabPion[j]).hasATresor() && tabPion[j].x == p.x-i && tabPion[j].y == p.y) // si ce moussaillon j a un tresor et qu'il est atteignable par le fantome, alors :
							{
								System.out.println("Pion moussaillon avec un tresor trouve en x :" + tabPion[j].x +" y :" + tabPion[j].y);
								direction = 3;
							}
						}
					}
				}
				if(p.x<12-i)
				{
					for(int j=0; j<3;j++)
					{
						if(tabPion[j] != null) //Si le moussaillon j est vivant
						{
							if(((PionMoussaillon) tabPion[j]).hasATresor() && tabPion[j].x == p.x+i && tabPion[j].y == p.y) // si ce moussaillon j a un tresor et qu'il est atteignable par le fantome, alors :
							{
								System.out.println("Pion moussaillon avec un tresor trouv en x :" + tabPion[j].x +" y :" + tabPion[j].y);
								direction = 4;
							}
						}
					}
				}
			}
		}
		if(direction == 0)
		{
			System.out.println("Pas de moussaillon avec un tresor a proximite !");
		}
		return direction;
	}
	
	/**
	 * <p>Methode appelee par tout le monde, apres chaque lance de De, afin de recuperer le(s) chemin(s) correspondant(s).</p>
	 * <p>Cette methode fait appel a une fonction auxiliaire "getCheminAux" qui construit recursivement la liste des chemins possibles (attribut listeCheminCase).</p>
	 * <p>Ensuite, en fonction du joueur (ou du fantome), on modifie cette liste.</p>
	 * <p>S'il s'agit d'un moussaillon, on supprime tout les chemins qui passe par le pirate (il ne peut pas traverser le pirate, sinon il meurt).</p>
	 * <p>S'il s'agit du pirate, on supprime tous les chemins dont la direction est l'inverse de la direction prise au tour precedent par le pirate (autrement dit, il ne peut pas faire demi-tour), puis pour tous les chemins contenant un tresor, on retrecis ces chemins jusqu'a ce tresor (il s'arrete lorsqu'il marche sur un tresor)</p> 
	 * <p>S'il s'agit du fantome, on ne fait rien (puisque le fantome peut aller "partout", hormis en dehors des limites du terrain)</p>
	 * <p>@param indiceJoueur</p>
	 */

	public void getChemin(int indiceJoueur)
	{
		Pion p = this.tabPion[indiceJoueur];
		getCheminAux(resultatDe, p.x, p.y, new Chemin());
		switch(indiceJoueur)
		{
		case 0:
		case 1:
		case 2: //Cas Moussaillon
			supprimerCheminContenantPirate();
			break;
		case 3: //Cas Pirate
			supprimerCheminDirection();
			raccourcirCheminContenantTresor();
			break;
		case 4: //Cas Fantome
			break;
		}
		modifierCaseFinales(indiceJoueur); 
		this.repaint();
	}
	
	/**
	 * <p>Cette methode est appelee par le pirate dans la methode "getChemin".</p>
	 * <p>Cette methode reduit tous les chemins de la liste "listeCheminCase" passant par une case tresor.</p>
	 */
	
	private void raccourcirCheminContenantTresor() {
		List<Chemin> listeCheminAux = new LinkedList<Chemin>();
		if(!(tabPion[3].x==4 && tabPion[3].y == 3)) //Exception de la case initiale du spawn des tresors (la grotte), sinon le pirate ne pourrait pas bouger.
		{
			for(Chemin c:this.listeCheminCase)
			{
				boolean PasDeTresor = true;
				Chemin cheminAux = new Chemin();
				for(Point p: c.chemin)
				{
					if(PasDeTresor) //On va s'arreter au premier tresor, si on en trouve un, on ne modifie plus
					{
						cheminAux.prolongerChemin(p);//on a pas encore trouve de tresor, on prolonge le chemin.
					}
					for(Tresor t: listeTresor)
					{
						if(t.x==p.x && t.y==p.y)
						{
							this.listeCaseFinal.add(p);
							System.out.println("Point ajoute :" +p.x + p.y);
							PasDeTresor = false;
						}
						
					}
				}
				System.out.println("Ajout du chemin");
				cheminAux.afficher();
				listeCheminAux.add(cheminAux);
			}

		}
		else
		{
			listeCheminAux = this.listeCheminCase;
		}
		this.listeCheminCase = listeCheminAux;
	}
	
	/**
	 * <p>Cette methode est appelee par le pirate dans la methode "getChemin".</p>
	 * <p>Cette methode supprime tous les chemins commencant par la case dans le dos du pirate (le pirate ne peut pas faire demi-tour).</p>
	 */
	//RAPPORT (les commentaires sur les directions, pas sur qu'elles soient bonnes, deja les commentaires avant etaient clairement faux, sujet a verif)
	private void supprimerCheminDirection() {
		List<Chemin> listeCheminAux = new LinkedList<Chemin>();
		//Cas direction, on supprime tous les chemins commencant vers cette direction
		switch(((PionPirate)tabPion[3]).getDirection())
		{
		case 0: //Si le pirate n'a pas de direction (direction initiale)
			listeCheminAux.addAll(this.listeCheminCase);
			break;
		case 1: //Si le pirate s'est dirige au tour precedent vers le haut
			for(Chemin c: this.listeCheminCase)
			{
				if(!(c.chemin.get(1).y-1==tabPion[3].y)) //Si le chemin ne part pas vers le bas
				{
					listeCheminAux.add(c);
				}
			}
			break;
		case 2: //Si le pirate s'est dirige au tour precedent vers le bas
			for(Chemin c: this.listeCheminCase)
			{
				if(!(c.chemin.get(1).y+1==tabPion[3].y)) //Si le chemin ne part pas vers le haut
				{
					System.out.println("cheminAjoute");
					listeCheminAux.add(c);
				}
			}
			break;
		case 3: //Si le pirate s'est dirige au tour precedent vers la droite
			for(Chemin c: this.listeCheminCase)
			{
				if(!(c.chemin.get(1).x-1==tabPion[3].x)) //Si le chemin ne part pas vers la gauche
				{
					listeCheminAux.add(c);
				}
			}
			break;
		case 4: //Si le pirate s'est dirige au tour precedent vers la gauche
			for(Chemin c: this.listeCheminCase)
			{
				if(!(c.chemin.get(1).x+1==tabPion[3].x)) //Si le chemin ne part pas vers la droite
				{
					listeCheminAux.add(c);
				}
			}
			break;
		}
		this.listeCheminCase = listeCheminAux;
	}
	
	
	/**
	 * <p>Cette methode est appelee par un moussaillon dans la methode "getChemin".</p>
	 * <p>Cette methode supprime tous les chemins contenant la case du pirate de la liste "listeCheminCase"</p>
	 */
	
	private void supprimerCheminContenantPirate() {
		List<Chemin> listeCheminAux = new LinkedList<Chemin>();
		Point pPirate = new Point(this.tabPion[3].x,this.tabPion[3].y);
		for(Chemin c:this.listeCheminCase)
		{
			if(!c.chemin.contains(pPirate))
			{
				listeCheminAux.add(c);
			}
		}
		this.listeCheminCase = listeCheminAux;
		
	}

	
	/**
	 * <p>Methode auxiliaire qui construit recursivement la liste des chemins possibles (attribut listeCheminCase).</p>
	 * <p>Cette methode verifie si la case de coordonnees (x,y) est marchable (accessible) par un moussaillon (case cocotier, case sentier, etc...)</p>
	 * <p>Si oui, on regarde si le parametre "resultatDe" est egal a 0.</p>
	 * <p>	Si c'est le cas, on ajoute la case (x,y) dans le chemin c, dans la listeCaseFinal, et on ajoute le chemin c dans la listeCheminCase </p>
	 * <p>		Si ce n'est pas le cas, on ajoute la case (x,y) dans le chemin c puis on s'appelle recursivement dans les 4 directions.</p>
	 * <p>Sinon, on ne fait rien. Le chemin c ainsi construite n'est pas un chemin valide, on ne l'ajoute pas dans la liste des chemins.</p>
	 * <p>@param resultatDe : resultat de DE</p>
	 * <p>@param x : coordonnee x d'une case </p>
	 * <p>@param y : coordonnee y d'une case</p>
	 * <p>@param c : Chemin a prolonger si necessaire</p>
	 */
	
	public void getCheminAux(int resultatDe, int x, int y, Chemin c)
	{
		if(verifierCaseMarchable(x,y,resultatDe)) //verifie si c'est marchable ou pas, le fait de passer le resultatDe permet de valider (retourner vrai) si le moussaillon commence sur une case cocotier.
		{
			if(resultatDe==0) //S'il s'agit de la derniere case a verifier, case marchable
			{
				if(!c.chemin.contains(new Point(x,y))) //Si il ne s'agit pas d'une case deja verifier (demi-tour...)
				{
					c.prolongerChemin(new Point(x,y)); //on ajoute la derniere case au chemin
					this.listeCheminCase.add(c);		//on ajoute le chemin c dans la liste des chemins (attribut de cette classe)
					this.listeCaseFinal.add(new Point(x,y)); //il s'agit de la derniere case d'un chemin, on l'ajoute a la liste des dernieres case (attribut de cette classe)
				}
			}			
			else{ //Si ce n'est pas la derniere case d'un chemin
				//On fait la meme chose dans les 4 directions
				//Les if suivant traite respectivement la case du haut, du bas, de gauche et de droite.
				//On verifie, avant appel recursif, si on ne depasse pas le terrain et si la case en question est traitee ou non. (si elle est deja traitee, on ne fait rien)
				c.prolongerChemin(new Point(x,y)); //on a verifier que la case est valide, on prolonge le chemin.
				if(y>1) //Pour ne pas depasser le terrain
				{

					System.out.println("1 resultatDe :" + resultatDe +"C : " ); //test de verification
					c.afficher(); //test de verification
					if(!( c.chemin.contains(new Point(x,y-1)))) //si la case (x,y-1) n'est pas encore trait\E9e
					{
						Chemin cheminAux = new Chemin();
						cheminAux.copierChemin(c);
						getCheminAux(resultatDe-1, x,y-1,cheminAux); //haut
					}
				}
		
				if(y<11)
				{
					System.out.println("2 resultatDe :" + resultatDe +"C : " );
					c.afficher();
					if(!(c.chemin.contains(new Point(x,y+1))))
					{
						Chemin cheminAux = new Chemin();
						cheminAux.copierChemin(c);
						getCheminAux(resultatDe-1, x,y+1,cheminAux); //bas
					}
				}
		
				if(x>1)
				{
					System.out.println("3 resultatDe :" + resultatDe +"C : " );
					c.afficher();
					if(!(c.chemin.contains(new Point(x-1,y))))
					{
						Chemin cheminAux = new Chemin();
						cheminAux.copierChemin(c);
						getCheminAux(resultatDe-1, x-1,y,cheminAux); //gauche
					}
				}
		
				if(x<11)
				{
					System.out.println("4 resultatDe :" + resultatDe +"C : " );
					c.afficher();
					if(!(c.chemin.contains(new Point(x+1,y))))
					{
						Chemin cheminAux = new Chemin();
						cheminAux.copierChemin(c);
						getCheminAux(resultatDe-1, x+1,y,cheminAux); //droite
					}
				}
			}
			
		}
	}
	
	/**
	 * <p>Methode qui retourne si la case de coordonnees (x,y) est marchable.</p>
	 * <p>@param x : abscisse d'une case</p>
	 * <p>@param y : ordonnee d'une case</p>
	 * <p>@param resultatDe</p>
	 * <p>@return un booleen qui est vrai si la case est marchable, faux sinon, en sachant qu'une case Cocotier est marchable au debut de la partie (cas ou le moussaillon est deja sur une case Cocotier)</p>
	 */
	private boolean verifierCaseMarchable(int x, int y, int resultatDe) {
		if(x<=11&&x>=1&&y<=11&&y>=1) // si ce n'est pas une case eau
			//Le pion moussaillon peut egalement marcher sur une case Cocotier au debut du tour de jeu s'il est deja sur un cocotier.
			return(tab[x][y] < 4 || (resultatDe == this.resultatDe && tab[x][y]==5)); // la premiere condition verifie si la case est marchable ; la seconde vérifie s'il est sur une case cocotier initialement 
		else
			return false;
	}

	
	/**
	 * <p>Fonction qui retourne si la case p est dans la liste des chemins "listeCheminCase".</p>
	 * <p>@param p</p>
	 * <p>@return boolean qui est vrai si la case p est dans la liste des chemins "listeCheminCase", faux sinon.</p>
	 */
	public boolean verifierCheminAvecPoint(Point p) {
		//Verifie si le point p appartient a au moins un chemin dans la liste des chemins
		//Affecte le chemin dans le cheminActuel
		boolean found = false;
		for(Chemin c: listeCheminCase)
		{
			if(!found && c.chemin.contains(p))
			{
				this.cheminChoisi = c;
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * <p>Fonction qui retourne si la case p est dans la liste des chemins l.</p>
	 * <p>@param p : case</p>
	 * <p> @param l : chemin</p>
	 * <p> @return vrai si la case p est dans la liste des chemins l, faux sinon.</p>
	 */
	public boolean verifierCheminAvecPoint(Point p,List<Point> l) {
		//Verifie si le point p appartient a au moins un chemin dans la liste des chemins
		//Affecte le chemin dans le cheminActuel
		return l.contains(p);
	}

	/**
	 * <p>Methode qui supprime les chemins de la liste "listeCheminCase" ne contenant pas la case p. </p>
	 * <p>Cette methode est appelee apres chaque deplacement d'un pion.</p>
	 * <p>@param p</p>
	 * <p>@param indiceJoueur</p>
	 */
	
	public void modifierCheminContenantPoint(Point p,int indiceJoueur) {
		List<Chemin> lc = new LinkedList<Chemin>();
		for(Chemin c : listeCheminCase)
		{
			if(c.chemin.contains(p))
			{
				lc.add(c);
			}
		}
		this.listeCheminCase = lc;
		modifierCaseFinales(indiceJoueur);
	}
	
	/**
	 * <p>Cette methode est appelee par la methode "modifierCheminContenantPoint".</p> 
	 * <p>Elle remplit de nouveau la liste "listeCaseFinal" par les derniers cases de chaque chemin de la liste "listeCheminCase" </p>
	 * <p>@param indiceJoueur</p>
	 */
	
	public void modifierCaseFinales(int indiceJoueur)
	{
		List<Point> lcf = new LinkedList<Point>();
		for(Point p: this.listeCaseFinal)
		{
			for(Chemin c: this.listeCheminCase)
			{
				if(c.chemin.contains(p))
				{
					lcf.add(p);
				}
			}
		}
		if(lcf.isEmpty())
		{
			Chemin c = new Chemin();
			c.prolongerChemin(new Point(tabPion[indiceJoueur].x,tabPion[indiceJoueur].y));
			listeCheminCase.add(c);
			lcf.add(new Point(tabPion[indiceJoueur].x,tabPion[indiceJoueur].y));
		}
		
		this.listeCaseFinal = lcf;
	}

	/**
	 * <p>Cette methode verifie si le pion en question est sur une des dernieres cases.</p>
	 * <p>@param indiceJoueur</p>
	 * <p>@return vrai si le pion est sur une des derniere case (case appartenant dans la listeCaseFinal)</p>
	 */
	public boolean verifierDerniereCase(int indiceJoueur) {
		Point p = new Point(this.tabPion[indiceJoueur].x,this.tabPion[indiceJoueur].y);
		return this.listeCaseFinal.contains(p);
	}

	/**
	 * <p>Fonction qui verifie, pour le chemin choisi (premier chemin de la liste "listeCheminCase") l'existence d'un ou plusieurs moussaillons</p>
	 * <p>@return liste des moussaillons present sur le chemin choisi</p>
	 */
	public List<Integer> verifierMoussaillonSurChemin() {
		//Cette methode verifie  l'existence de moussaillons dans le chemin currentChemin;
		Chemin currentChemin = this.listeCheminCase.get(0); 		//Chemin choisi
		List<Integer> listeMoussaillonATuer = new LinkedList<Integer>();
		for(Point p: currentChemin.chemin)
		{
			for(int i=0; i<3; i++)
			{
				if(this.tabPion[i]!=null)
				{
					if(this.tabPion[i].x == p.x && this.tabPion[i].y == p.y)
					{
						listeMoussaillonATuer.add(i);
					}
				}
			}
		}
		return listeMoussaillonATuer;
	}
	
	/**
	 * <p>Verifie pour une case "point" donnee, l'existence d'un ou plusieurs tresors.</p>
	 * <p>@param point</p>
	 * <p>@return la liste des tresor present sur cette case "point"</p>
	 */
	public List<Tresor> verifierTresor(Point point) {
		List<Tresor> lt = new LinkedList<Tresor>();
		for(Tresor t: this.listeTresor)
		{
			if(t.x == point.x && t.y == point.y)
			{
				lt.add(t);
			}
		}
		return lt;
	}
	
	/**
	 * <p>Verifie pour une case "point" donnee, l'existence d'un tresor.</p>
	 * <p>Cette methode est utile pour s'avoir si un moussaillon est passe par un tresor ou non.</p>
	 * <p>@param c</p>
	 * <p>@return le tresor present sur cette chemin c</p>
	 */
	public Tresor verifierTresor(Chemin c) {
		Tresor tAux = null;
		for(Point p: c.chemin)
		{
			for(Tresor t: this.listeTresor)
			{
				if(tAux == null && p.x == t.x && p.y == t.y){
					tAux = t;
				}
			}
		}
		return tAux;
		
	}
	
	/**
	 * <p>Fonction qui retourne le chemin qu'un pion a parcouru pour le moment.</p>
	 * <p>@param indiceJoueur</p>
	 * <p>@return</p>
	 */
	public Chemin getCheminPourLeMoment(int indiceJoueur) {
		Chemin cAux = new Chemin();
		boolean pionAtteint = false;
		for(Chemin c : listeCheminCase)
		{
			for(Point p: c.chemin)
			{
				cAux.prolongerChemin(p);
				if(!pionAtteint && p.x == tabPion[indiceJoueur].x && p.y == tabPion[indiceJoueur].y)
				{
					pionAtteint = true;
				}
			}
		}
		return cAux;
	}

	/**
	 * <p>Fonction qui retourne la case cocotier a cote du pion moussaillon pm.</p>
	 * <p>@param pm</p>
	 * <p>@return</p>
	 */
	public Point chercherCocotier(PionMoussaillon pm) {
		Point p = new Point(pm.x,pm.y); //On retourne sa position si il est deja sur une case cocotier.
		//On sait que pm contient les coordonnees d'un moussaillon, on ne deppasera pas le tableau par la suite
		if(this.tab[pm.x][pm.y-1]==5)
		{
			p.x= pm.x;
			p.y= pm.y-1;
		}
		else if(this.tab[pm.x][pm.y+1]==5)
		{
			p.x= pm.x;
			p.y= pm.y+1;
		}
		else if(this.tab[pm.x-1][pm.y]==5)
		{
			p.x= pm.x-1;
			p.y= pm.y;
		}
		else if(this.tab[pm.x+1][pm.y]==5)
		{
			p.x= pm.x+1;
			p.y= pm.y;
		}
		return p;
	}

	/**
	 * <p>Fonction qui retourne le chemin qui reste pour le pion en question.</p>
	 * <p>@param indiceJoueur</p>
	 * <p>@return retourne le chemin qui reste pour le pion en question.</p>
	 */
	public Chemin getCheminRestant(int indiceJoueur) {
		Chemin cheminRestant = new Chemin();
		boolean pionTrouve = false;
		
		for(Point p:this.cheminChoisi.chemin)
		{
			if(tabPion[indiceJoueur].x==p.x && tabPion[indiceJoueur].y==p.y)
			{
				pionTrouve = true;
			}
			if(pionTrouve)
			{
				cheminRestant.prolongerChemin(p);
			}
		}
		System.out.println("Chemin restant :");
		cheminRestant.afficher();
		return cheminRestant;
	}

	/**
	 * <p>Setter resultatDe</p>
	 * <p>@param res</p>
	 */
	public void setResultatDe(int res)
	{
		this.resultatDe = res;
	}
	
	/**
	 * <p>Getter resultatDe</p>
	 * <p>@return this.resultatDe</p>
	 */
	public int getResultatDe()
	{
		return resultatDe;
	}
	
	
}
