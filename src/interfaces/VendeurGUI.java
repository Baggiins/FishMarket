package interfaces;

import javax.swing.*;

import agents.VendeurAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author J�r�mi Duarte
 *
 */
@SuppressWarnings("serial")
public class VendeurGUI extends JFrame implements ActionListener {
	
	//===============ELEMENTS PRINCIPAL DE LA FENETRE============//

    private VendeurAgent _agentVendeur;
    private JButton _boutonAdd;
    private JPanel _princPannel;
    private JLabel _timerRestant;
    private JLabel _lotName;
    private JLabel _price;
    private JLabel _lotAcheteur;
    private JLabel _statut;
    private JLabel _agentStatut;
    private JTextField _lotNameTF;
    private JTextField _priceTF;
    private JTextField _timerRestantTF;
    private JTextField _increTF;
    private JTextField _decrTF;
    private JTable _tableAcheteur;
    private Vector<Vector<String>> dataVector;

    public VendeurGUI(String pNameAgent, VendeurAgent originMonAgent) {
    	
    	//============INITIALISATION DE LA FENETRE============//
    	
    	this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        _agentVendeur = originMonAgent;
        this.setTitle("Agent Vendeur: " + pNameAgent);
        this.setSize(750, 300);
        _boutonAdd = new JButton("Mettre en vente");
        
        //=============COMPOSANTS DE LA FENETRE============//
        
        _timerRestant = new JLabel("");
        _agentStatut = new JLabel("En attente de la creation d un lot");
        _lotName = new JLabel();
        _price = new JLabel();
        _lotAcheteur = new JLabel();
        _statut = new JLabel();
        _lotNameTF = new JTextField("Daurade");
        _priceTF = new JTextField("1000");
        _timerRestantTF = new JTextField("20");
        _increTF = new JTextField("50");
        _decrTF = new JTextField("50");
        _boutonAdd.addActionListener(this);
        Vector<String> nomColumn = new Vector<>(Arrays.asList("Agent Acheteur", "Statut"));
        dataVector = new Vector<>();
        _tableAcheteur = new JTable(dataVector,nomColumn);
        JScrollPane propSP = new JScrollPane(_tableAcheteur);
        _princPannel = new JPanel();
        _princPannel.setLayout(new BoxLayout(_princPannel, BoxLayout.PAGE_AXIS));
        this.setContentPane(_princPannel);
        JPanel lineNameAgent = new JPanel();
        JPanel lineSaisie = new JPanel();
        JPanel mineMiseEnchere = new JPanel();
        JPanel gridLot = new JPanel();
        JPanel lineTableauPreneur = new JPanel();
        JPanel lineStatut = new JPanel();
        
        //==========CONFIG DES COMP DE LA FENETRE===========//
        
        lineNameAgent.add(_timerRestant);
        lineNameAgent.setLayout(new BoxLayout(lineNameAgent,BoxLayout.LINE_AXIS));
        lineSaisie.setLayout(new GridLayout(2,6));
        lineSaisie.add(new JLabel("Nom lot"));
        lineSaisie.add(new JLabel("Prix initial en euros"));
        lineSaisie.add(new JLabel("Temps en seconde"));
        lineSaisie.add(new JLabel("incrementation"));
        lineSaisie.add(new JLabel("decrementation"));
        lineSaisie.add(new JLabel(""));
        lineSaisie.add(_lotNameTF);
        lineSaisie.add(_priceTF);
        lineSaisie.add(_timerRestantTF);
        lineSaisie.add(_increTF);
        lineSaisie.add(_decrTF);
        lineSaisie.add(_boutonAdd);
        gridLot.setLayout(new GridLayout(2,4));
        gridLot.add(new JLabel("Nom lot"));
        gridLot.add(new JLabel("Prix actuel"));
        gridLot.add(new JLabel("Acheteur"));
        gridLot.add(new JLabel("Statut"));
        gridLot.add(_lotName);
        gridLot.add(_price);
        gridLot.add(_lotAcheteur);
        gridLot.add(_statut);
        lineTableauPreneur.setLayout(new BoxLayout(lineTableauPreneur,BoxLayout.LINE_AXIS));
        lineTableauPreneur.add(propSP);
        lineStatut.setLayout(new FlowLayout(FlowLayout.LEFT));
        lineStatut.add(_agentStatut);
        _princPannel.add(lineNameAgent);
        _princPannel.add(new JSeparator());
        _princPannel.add(lineSaisie);
        _princPannel.add(mineMiseEnchere);
        _princPannel.add(new JSeparator());
        _princPannel.add(gridLot);
        _princPannel.add(new JSeparator());
        _princPannel.add(lineTableauPreneur);
        _princPannel.add(new JSeparator());
        _princPannel.add(lineStatut);
        _princPannel.revalidate();
        _princPannel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	JOptionPane errorField = new JOptionPane();
    	
    	//==============TRAITEMENT DES EVENEMENTS============//
    	
        if(e.getSource() == _boutonAdd) {
            int prixLot;
            int tempsLot;
            int pasIncrLot;
            int pasDecrLot;
            try{prixLot = Integer.parseInt(_priceTF.getText());}
            catch(NumberFormatException nfe){prixLot = -1;}
            try{tempsLot = Integer.parseInt(_timerRestantTF.getText());}
            catch(NumberFormatException nfe){tempsLot = -1;}
            try{pasIncrLot = Integer.parseInt(_increTF.getText());}
            catch(NumberFormatException nfe){pasIncrLot = -1;}
            try{pasDecrLot = Integer.parseInt(_decrTF.getText());}
            catch(NumberFormatException nfe){pasDecrLot = -1;}
            if(_lotNameTF.getText().trim().length() == 0){
                errorField.showMessageDialog(null, "Erreur: pas de nom de lot", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else if(prixLot < 0){
                errorField.showMessageDialog(null, "Erreur: prix invalide, entrez un entier positif non null", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else if(tempsLot < 2){
                errorField.showMessageDialog(null, "Erreur: temps invalide, entrez un entier superieur a deux", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else if(pasIncrLot < 1){
                errorField.showMessageDialog(null, "Erreur: incrementation invalide, entrez un entier superieur a 1", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else if(pasDecrLot < 1){
                errorField.showMessageDialog(null, "Erreur: decrementation invalide, entrez un entier superieur a 1", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else{
                _agentStatut.setText("Enchere annoncee");
                _lotName.setText(_lotNameTF.getText());
                _price.setText(_priceTF.getText());
                _statut.setText("Ouvert");
                _agentVendeur.set_nomLot(_lotNameTF.getText());
                _agentVendeur.set_currentPrice(Integer.valueOf(_priceTF.getText()));
                _agentVendeur.set_statut(_statut.getText());
                _agentVendeur.set_timer(tempsLot);
                _agentVendeur.set_incrementation(pasIncrLot);
                _agentVendeur.set_decrementation(pasDecrLot);
                _agentVendeur.set_initStateEnd(true);
                _agentVendeur.doWake();
                _lotNameTF.setEnabled(false);
                _boutonAdd.setEnabled(false);
                _priceTF.setEnabled(false);
                _timerRestantTF.setEnabled(false);
                _increTF.setEnabled(false);
                _decrTF.setEnabled(false);
            }

        }
    }
    
    //=============METHODES==========//
    
    public void addTableAcheteur(Vector<String> originDataVector) {
        dataVector.add(originDataVector);
        refreshGUI();
    }
    public void updateTableAcheteur(int positionAnnonce,Vector<String> originDataVector) {
        dataVector.set(positionAnnonce, originDataVector);
        refreshGUI();
    }
    public void resetStatutAcheteur() {
        for (int i = 0; i < dataVector.size(); i++){
            Vector<String> data = dataVector.get(i);
            data.set(1,"N'as pas encore propose");
            dataVector.set(i, data);
        }
        refreshGUI();
    }
    private void refreshGUI() {
        _tableAcheteur.invalidate();
        _tableAcheteur.revalidate();
        _tableAcheteur.repaint();
        _princPannel.invalidate();
        _princPannel.revalidate();
        _princPannel.repaint();
    }
    public void setPrixActuelAffichage(int prix){
        _price.setText(String.valueOf(prix));
    }
    public void setStatutLot(String text){
        _statut.setText(text);
    }
    public void setStatut(String text){
        _agentStatut.setText(text);
    }
    public void setTempsRestant(String text){
        _timerRestant.setText(" Temps: " + text + " secondes");
    }
    public void finEnchere(String gagnant){
        _timerRestant.setText(" Enchere fini");
        _lotAcheteur.setText(gagnant);
    }
}