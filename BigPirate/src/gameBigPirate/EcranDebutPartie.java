package gameBigPirate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.GridLayout; 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>La classe EcranDebutPartie permet de generer un ecran avant de debuter la partie </p>
 * <p>Il est alors demande le nombre de joueur moussaillon durant la partie a venir. </p>
 * @author Bechet, Di-Fant et Le Bellour
 *
 */
public class EcranDebutPartie extends JFrame {

	private int nbMoussaillon ;
	private boolean debutPartieOK;
	private JTextField textfield ;
	private JButton boutonCommencerPartie;
    


	/**
	 * <p> Constructeur </p>
	 * <p> @param _debutPartieOK : booleen qui passe a vrai lorsque l'utilisateur clique sur le bouton "commencer la partie" </p>
	 */
	public EcranDebutPartie(boolean _debutPartieOK) {
		
		this.debutPartieOK=_debutPartieOK;
		boutonCommencerPartie = new JButton("Commencer la partie") ;
		textfield = new JTextField() ;
		
		this.setTitle("Debut Partie");
	    
	    this.setSize(300, 400);
	    this.setLocationRelativeTo(null);
	    
	    this.setLayout(new GridLayout(3, 1));
	    this.getContentPane().add(new JLabel("Combien de joueurs ? 1, 2 ou 3"));
	    this.getContentPane().add(textfield);
	    this.getContentPane().add(boutonCommencerPartie);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);

		boutonCommencerPartie.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent event){
	    		nbMoussaillon=Integer.parseInt(textfield.getText());
	    		debutPartieOK=true;
	    	}
	    }) ;
	}
	
	public boolean isDebutPartieOK() {
		return debutPartieOK;
	}
	
	public int getNbMoussaillon() {
		return nbMoussaillon;
	}
	
}
