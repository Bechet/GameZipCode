package gameBigPirate;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;


/**
 * <b>Chemin est la classe representant la notion de chemin.</b>
 * <p>La classe Chemin contient un attribut chemin qui lui correspond à une liste de cases.</p>
 * 
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class Chemin {
	//Variable
	public LinkedList<Point> chemin;
	
	/**
	 * Constructeur
	 */
	public Chemin()
	{
		this.chemin = new LinkedList<Point>();
	}
	
	/**
	 * <p> Cette methode permet de prolonger un chemin d'un point (d'une case) passe en parametre. </p>
	 * <p> @param p : Point a ajoute dans le chemin </p>
	 */
	public void prolongerChemin(Point p)
	{
		this.chemin.add(p);
	}

	/**
	 * Cette methode affiche l'ensemble des points de l'attribut "chemin" de la classe Chemin sur la console
	 */
	public void afficher()
	{
		for(Point p: this.chemin)
		{
			System.out.println(p.x + "   " +p.y);
		}
	}
	
	/**
	 * <p> Cette methode effectue un "deep copy" d'un chemin </p>
	 * <p> @param c : chemin a copier </p>
	 */
	public void copierChemin(Chemin c) 
	{
		for(Point p: c.chemin)
		{
			this.chemin.add(p);
		}
	}
	
}
