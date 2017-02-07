package gameBigPirate;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
/**
 * <b> La classe JPanelTextField est l'interface graphique correspond a la zone de texte en bas de l'ecran de jeu </b>
 * <p> Elle permet de decrire en direct la partie et d'informer les joueurs sur l'etat de cette derniere </p>
 * <p> Par exemple elle avertie les joueurs lorsqu'ils doivent jouer ou lorsqu'ils ont perdu la partie ! </p>  
 * 
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class JPanelTextField extends JPanel{
	private JTextArea jt;
	private JScrollPane js;
	
	/**
	 * Constructeur
	 */
	public JPanelTextField()
	{
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0,600,834, 220);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.jt=new JTextArea();
		this.jt.setBounds(5, 5, 740, 200);
		this.jt.setOpaque(true);
		this.jt.setVisible(true);
		this.jt.setEditable(false);
		this.jt.setLineWrap(true);
		
		//Pour un scrolling automatique
		DefaultCaret caret = (DefaultCaret)jt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Creation du scroll
		this.js = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(js);
		this.js.setBounds(5, 5, 825, 220);
		
		//Indication du debut de la partie.
		setJL("Debut de la partie.");
		setJL("C'est au joueur Moussaillon 1 de jouer.");
	}
	
	/**
	 * <p>Cette methode prend en parametre un String (ensemble de caracteres) a ajouter dans la zone (champ) de texte.</p>
	 * <p>@param s : le string a ajouter dans la zone de texte</p>
	 */
	public void setJL(String s)
	{
		this.jt.append("  " +s + "\n");
	}
}
