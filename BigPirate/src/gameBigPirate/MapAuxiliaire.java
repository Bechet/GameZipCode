package gameBigPirate;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *  <b> Cette classe correspond a l'interface graphique a droite de l'ecran de jeu. </b>
 *  <p>Elle permet de visualiser quel joueur est actuellement en train de jouer et leur resultat du lance de DE.</p>
 *  <p>Elle permet egalement de visualiser combien d'atouts sont encore disponibles pour chaque moussaillon.</p>
 *  
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class MapAuxiliaire extends JPanel{

	int nombreMoussaillon;
	
	int[] nombreAtoutPerroquet ;
	int[] nombreAtoutCocotier ;
	
	JButton jButtonLancerDe;
	JButton jButtonCocotier;
	JButton jButtonPerroquet;
	JButton jButtonFinDuTour;
	JButton jButtonDeposerTresor;
	
	JLabel jLabelResultatDe;
	
	Color c = new Color(255,255,255);
	

	Image imagePion ;
	Image imagePerroquet;
	Image imageCocotier;
	Image imageBackground;
	Image imageContour;
	private int indiceJoueur = 0;
	/**
	 * <p>Constructeur de la classe MapAuxiliaire</p>
	 * <p>@param nombreMoussaillon</p>
	 * <p>@param jButtonLancerDe</p>
	 * <p>@param jButtonCocotier</p>
	 * <p>@param jButtonPerroquet</p>
	 * <p>@param jButtonFinDuTour</p>
	 * <p>@param jButtonDeposerTresor</p>
	 */
	public MapAuxiliaire(int nombreMoussaillon, JButton jButtonLancerDe, JButton jButtonCocotier,	JButton jButtonPerroquet, JButton jButtonFinDuTour,	JButton jButtonDeposerTresor)
	{
		this.nombreMoussaillon = nombreMoussaillon;
		try{
			imageBackground = ImageIO.read(new File("Assets/CarreBlanc.png"));
			imagePion = ImageIO.read(new File("Assets/Pion.png"));
			imagePerroquet = ImageIO.read(new File("Assets/Perroquet.png"));
			imageCocotier = ImageIO.read(new File("Assets/Coco3030.png"));
			imageContour = ImageIO.read(new File("Assets/Contour.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.nombreAtoutCocotier = new int[nombreMoussaillon];
		this.nombreAtoutPerroquet = new int[nombreMoussaillon];
		
		switch(nombreMoussaillon)
		{
		case 1:
			for(int i=0;i<nombreMoussaillon;i++)
			{
				this.nombreAtoutPerroquet[i]=3;
				this.nombreAtoutCocotier[i] = 5;
			}
			break;
		case 2:
			for(int i=0;i<nombreMoussaillon;i++)
			{
				this.nombreAtoutPerroquet[i]=2;
				this.nombreAtoutCocotier[i] = 4;
			}
			break;
		case 3:
			for(int i=0;i<nombreMoussaillon;i++)
			{
				this.nombreAtoutPerroquet[i]=1;
				this.nombreAtoutCocotier[i] = 3;
			}
			break;
		}
		
		this.jButtonLancerDe = jButtonLancerDe;
		this.jButtonCocotier=jButtonCocotier;
		this.jButtonPerroquet=jButtonPerroquet;
		this.jButtonFinDuTour=jButtonFinDuTour;
		this.jButtonDeposerTresor=jButtonDeposerTresor;
		
		this.jLabelResultatDe = new JLabel("Resultat de : 0");
		this.jLabelResultatDe.setBounds(20, 310, 240, 50);
		this.add(jLabelResultatDe);
		
		this.add(jButtonLancerDe);
		this.add(jButtonCocotier);
		this.add(jButtonPerroquet);
		this.add(jButtonFinDuTour);
		this.add(jButtonDeposerTresor);

		this.setVisible(true);
	}
	/**
	 * <p>Methode draw qui permet de dessiner les images presentent sur cette map auxiliaire.</p>
	 * <p>Cette méthode sera appelé seulement par la methode repaint().</p>
	 */
	public void paintComponent(Graphics g)
	{
		g.drawImage(imageBackground, 0, 0, 200,600,0,0,30,30,null);
		for(int i=0; i<nombreMoussaillon;i++)
		{
			g.drawImage(imagePion, 20, 30+(80*i), 70, 80+(80*i), (50*i), 0, 50+(50*i), 50, null);
			for(int j=0;j<this.nombreAtoutPerroquet[i];j++)
			{
				g.drawImage(imagePerroquet, 80+(30*j), 35+ (80*i), 80+20 +(30*j)  , 35+20+ (80*i), 0, 0, 30, 30, null);
			}
			for(int j=0;j<this.nombreAtoutCocotier[i];j++)
			{
				g.drawImage(imageCocotier, 80+(30*j), 65+ (80*i), 80+20 +(30*j)  , 65+20+ (80*i), 0, 0, 30, 30, null);
			}
		}
		g.drawImage(imagePion, 20, 30+(80*3), 70, 80+(80*3), (50*3), 0, 50+(50*3), 50, null);
		
		g.drawImage(imageContour, 20, 30 + (80 * this.indiceJoueur), 70, 80+(80*this.indiceJoueur), 0,0,50,50,null);
	}
	/**
	 * <p>Methode qui permet de comptabiliser le nombre d'atout cocotier.</p>
	 * <p>@param indiceJoueur : l'indice du joueur dont on veut afficher son nombre d'atout cocotier.</p>
	 * <p>@return resteAtoutCocotier : un booleen qui est à vrai si le moussaillon possede au moins un atout cocotier, et a faux sinon.</p>
	 */
	public boolean utiliserAtoutCocotier(int indiceJoueur)
	{
		boolean resteAtoutCocotier = true;
		if(this.nombreAtoutCocotier[indiceJoueur] <=0)
		{
			resteAtoutCocotier = false;
			System.out.println("Attention, le joueur n'a plus d'atout Cocotier !");
		}
		else
		{
			this.nombreAtoutCocotier[indiceJoueur] = this.nombreAtoutCocotier[indiceJoueur]-1;
			System.out.println("Le nombre d'atout Cocotier est maintenant de :" + this.nombreAtoutCocotier[indiceJoueur]);
			this.repaint();
		}

		return resteAtoutCocotier;
	}
	
	/**
	 * <p>Methode qui permet de comptabiliser le nombre d'atout perroquet.</p>
	 * <p>@param indiceJoueur : l'indice du joueur dont on veut afficher son nombre d'atout perroquet.</p>
	 * <p>@return resteAtoutPerroquet : un booleen qui est à vrai si le moussaillon possede au moins un atout perroquet, et à faux sinon. </p> 
	 */
	public boolean utiliserAtoutPerroquet(int indiceJoueur)
	{
		boolean resteAtoutPerroquet = true;
		if(this.nombreAtoutCocotier[indiceJoueur] <=0)
		{
			resteAtoutPerroquet = false;
			System.out.println("Attention, le joueur n'a plus d'atout Perroquet !");
		}
		else
		{
			this.nombreAtoutPerroquet[indiceJoueur] = this.nombreAtoutPerroquet[indiceJoueur] -1;
			System.out.println("Le nombre d'atout Cocotier est maintenant de :" + this.nombreAtoutPerroquet[indiceJoueur]);
			this.repaint();
		}

		return resteAtoutPerroquet;
	}
	

	/**
	 * <p>Methode afin d'afficher dans la zone de texte (en bas de l'écran de jeu) le resultat du lance de DE</p>
	 * <p>@param resultatDe : le nouveau resultat a afficher</p>
	 */
	public void miseAJourResultatDe(int resultatDe)
	{
		this.jLabelResultatDe.setText("Resultat de : " + resultatDe);
	}
	

	public void joueurSuivant()
	{
		this.indiceJoueur=(indiceJoueur+1)%5;
		this.repaint() ;
	}
	

}
