package interfaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author J�r�mi Duarte
 * Interface de l'agent March�
 */
@SuppressWarnings("serial")
public class MarcheGUI extends JFrame{
	
	//==========ELEMENT PRINCIPAL DE LA FENETRE=======//
	
	private JPanel _princPannel;
    private JTable _tableAcheteur;
    private Vector<Vector<String>> _donnees;

    /**
     * Constructeur
     * @param pNameAgent (String) le nom de l'agent
     * 
     */
    
    public MarcheGUI(String pNameAgent) {
    	
    	//===========INITIALISATION DE LA FENETRE==============//
    	
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle(pNameAgent);
        this.setSize(600, 300);

        //=============LISTE DES COMPOSANTS DE LA FENETRE==========//
        

        Vector<String> colonne = new Vector<>(Arrays.asList("Vendeur", "Lot","Prix actuel","statut"));
        _donnees = new Vector<>();
        _tableAcheteur = new JTable(_donnees,colonne);
        JScrollPane ventesScrollPane = new JScrollPane(_tableAcheteur);
        _princPannel = new JPanel();
        _princPannel.setLayout(new BoxLayout(_princPannel, BoxLayout.PAGE_AXIS));
        this.setContentPane(_princPannel);
        JPanel statutAgent = new JPanel();
        JPanel titreWindow = new JPanel();
        JPanel ventes = new JPanel();
        
        //==========CONFIG DES COMP DE LA FENETRE============//
        
        statutAgent.setLayout(new FlowLayout(FlowLayout.LEFT));
        ventes.setLayout(new BoxLayout(ventes,BoxLayout.LINE_AXIS));
        ventes.add(ventesScrollPane);
        titreWindow.setLayout(new BoxLayout(titreWindow,BoxLayout.LINE_AXIS));
        _princPannel.add(titreWindow);
        _princPannel.add(new JSeparator());
        _princPannel.add(ventes);
        _princPannel.add(new JSeparator());
        _princPannel.add(statutAgent);
        _princPannel.revalidate();
        _princPannel.repaint();
    	}
    
    //============METHODES=========//

    public void majTable(int positionAnnonce,Vector<String> originDataVector) {
    	_donnees.set(positionAnnonce, originDataVector);
        updateInter();
    }
	public void ajoutNewTable(Vector<String> originDataVector) {
		_donnees.add(originDataVector);
		updateInter();
	}
    private void updateInter() {
        _tableAcheteur.invalidate();
        _tableAcheteur.revalidate();
        _tableAcheteur.repaint();
        _princPannel.invalidate();
        _princPannel.revalidate();
        _princPannel.repaint();
    }
}