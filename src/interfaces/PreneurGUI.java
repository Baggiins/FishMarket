package interfaces;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import agents.PreneurAgent;

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
public class PreneurGUI extends JFrame implements ActionListener{
	
	//===========ELEMENT PRINCIPAL DE LA FENETRE==========//
	
	private PreneurAgent _agentPreneur;
	private boolean _enchereFiniWin;
	private boolean _enchereFiniLoose;
    private JButton _boutonMan;
    private JButton _boutonAuto;
    private JButton _boutonStartEnchere;
    private JButton _boutonPropose;
    private JPanel _princPannel;
    private JLabel _statut;
    private JTextField _budget;
    private JTable _tableEnchere;
    private Vector<Vector<String>> _donnee;
    
    //=========GETTER AND SETTER==========//

    public boolean is_enchereFiniWin() {
		return _enchereFiniWin;
	}

	public JTable get_tableEnchere() {
		return _tableEnchere;
	}

	public void set_tableEnchere(JTable _tableEnchere) {
		this._tableEnchere = _tableEnchere;
	}

	public void set_enchereFiniWin(boolean _enchereFiniWin) {
		this._enchereFiniWin = _enchereFiniWin;
	}

	public boolean is_enchereFiniLoose() {
		return _enchereFiniLoose;
	}

	public void set_enchereFiniLoose(boolean _enchereFiniLoose) {
		this._enchereFiniLoose = _enchereFiniLoose;
	}

	public PreneurGUI(String pNameAgent, PreneurAgent OriginMonAgent) {
    	
    	//===========INITIALISATION DE LA FENETRE=============//
    	
    	this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        _agentPreneur = OriginMonAgent;
        this.setTitle("Agent Preneur: " + pNameAgent);
        int hauteurPx;
        hauteurPx = 350;
        this.setSize(750, 300);
        _enchereFiniWin = false;
        _enchereFiniLoose = false;

        //=============COMPOSANTS DE LA FENETRE============//
        
        _boutonMan = new JButton("Demarrer en manuel");
        _boutonAuto = new JButton("Demarrer en automatique");
        _boutonPropose = new JButton("Proposer");
        _boutonStartEnchere = new JButton("Rejoindre l'enchere");
        _statut = new JLabel("Veuillez choisir un mode de fonctionnement");
        _budget = new JTextField(10);
        _budget.setText("2000");
        Vector<String> columnNames = new Vector<>(Arrays.asList("Agent Vendeur", "Lot", "Prix", "Statut"));
        _donnee = new Vector<>();
        CellObjectRenderer objRender = new CellObjectRenderer(); 
        _tableEnchere = new JTable(_donnee, columnNames);
        _tableEnchere.setDefaultRenderer(java.lang.Object.class,objRender);
        _tableEnchere.getColumnModel().getColumn(0).setMinWidth(100);
        _tableEnchere.getColumnModel().getColumn(1).setMinWidth(100);
        _tableEnchere.getColumnModel().getColumn(2).setMinWidth(70);
        _tableEnchere.getColumnModel().getColumn(3).setMinWidth(150);
        JScrollPane enchereScrollPane = new JScrollPane(_tableEnchere);
        _boutonMan.addActionListener(this);
        _boutonAuto.addActionListener(this);
        _boutonStartEnchere.addActionListener(this);
        _boutonPropose.addActionListener(this);
        _princPannel = new JPanel();
        _princPannel.setLayout(new BoxLayout(_princPannel, BoxLayout.PAGE_AXIS));
        this.setContentPane(_princPannel);
        JPanel lineNameAgent = new JPanel();
        JPanel lineChoice = new JPanel();
        JPanel lineMode = new JPanel();
        JPanel lineEnchereMode = new JPanel();
        JPanel lineTableEnchere = new JPanel();
        JPanel lineProp = new JPanel();
        JPanel lineStatut = new JPanel();
        JPanel lineActReq = new JPanel();
        
        //==========CONFIG DES COMP DE LA FENETRE===========//
        
        lineNameAgent.setLayout(new BoxLayout(lineNameAgent,BoxLayout.LINE_AXIS));
        lineChoice.setLayout(new GridLayout(1,4));
        lineChoice.add(_boutonMan);
        lineChoice.add(new JLabel(" Budget (somme limite) : "));
        lineChoice.add(_budget);
        lineChoice.add(_boutonAuto);
        lineTableEnchere.setLayout(new BoxLayout(lineTableEnchere,BoxLayout.LINE_AXIS));
        lineTableEnchere.add(enchereScrollPane);
        lineProp.setLayout(new FlowLayout(FlowLayout.LEFT));
        lineProp.add(_boutonStartEnchere);
        _boutonStartEnchere.setEnabled(false);
        lineProp.add(_boutonPropose);
        _boutonPropose.setEnabled(false);
        lineStatut.setLayout(new FlowLayout(FlowLayout.LEFT));
        lineStatut.add(_statut);
        lineActReq.setLayout(new FlowLayout(FlowLayout.LEFT));
        _princPannel.add(lineNameAgent);
        _princPannel.add(new JSeparator());
        _princPannel.add(lineChoice);
        _princPannel.add(lineMode);
        _princPannel.add(lineEnchereMode);
        _princPannel.add(lineTableEnchere);
        _princPannel.add(lineProp);
        _princPannel.add(new JSeparator());
        _princPannel.add(lineStatut);
        _princPannel.add(lineActReq);
        _princPannel.revalidate();
        _princPannel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int budgetInt;
        int[]selectedRowsTableEnchere = _tableEnchere.getSelectedRows();
        JOptionPane errorMode = new JOptionPane();
        
        //==============TRAITEMENT DES EVENEMENTS============//

        if(e.getSource() == _boutonMan ) {
            _boutonMan.setEnabled(false);
            _boutonAuto.setEnabled(false);
            _budget.setEnabled(false);
            _boutonStartEnchere.setEnabled(true);
            _statut.setText("Pret a rejoindre une enchere.");
            _agentPreneur.set_typeEnchere("MANUEL");
            _agentPreneur.set_selecMode(true);
        }

        if(e.getSource() == _boutonAuto){
            try{budgetInt = Integer.parseInt(_budget.getText());}
            catch(NumberFormatException nfe){budgetInt = -1;}

            if (budgetInt < 1){
                errorMode.showMessageDialog(null, "Erreur, veuillez entrer un entier positif non nul.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }else{
                _agentPreneur.set_budget(budgetInt);
                _boutonMan.setEnabled(false);
                _boutonAuto.setEnabled(false);
                _budget.setEnabled(false);
                _boutonStartEnchere.setEnabled(true);
                _statut.setText("Pret a rejoindre une enchere.");
                _agentPreneur.set_typeEnchere("AUTO");
                _agentPreneur.set_selecMode(true);
                _agentPreneur.doWake();
            }
        }

        if (e.getSource() == _boutonStartEnchere){
            if(selectedRowsTableEnchere.length > 0){
                _agentPreneur.set_annonces(selectedRowsTableEnchere);
                _boutonPropose.setEnabled(false);
                _boutonStartEnchere.setEnabled(false);
                _agentPreneur.set_selecAnnonces(true);
                _agentPreneur.doWake();
            }else{
                _boutonPropose.setEnabled(false);
            }
        }

        if(e.getSource() == _boutonPropose) {
            if(selectedRowsTableEnchere.length == 1){
                _statut.setText("offre encherie.");
            }else if(selectedRowsTableEnchere.length > 1){
                _statut.setText("offres encheries.");
            }
            _statut.setText("offre encherie.");
            _agentPreneur.set_propOk(true);
            _agentPreneur.doWake();
        }
    }

    //=============METHODES==========//
    
    public void addTableVente(Vector<String> originDataVector) {
        _donnee.add(originDataVector);
        refreshGUI();
    }
    public void updateTableVente(int positionAnnonce,Vector<String> originDataVector) {
        _donnee.set(positionAnnonce, originDataVector);
        refreshGUI();
    }
    public void resetTableEnchere( Vector<Vector<String>> originDataVector) {
        _donnee.removeAllElements();
        for (int i = 0; i < originDataVector.size(); i++){
            _donnee.add(originDataVector.get(i));
        }
        refreshGUI();
    }
    public void refreshGUI() {
        _tableEnchere.invalidate();
        _tableEnchere.revalidate();
        _tableEnchere.repaint();
        _princPannel.invalidate();
        _princPannel.revalidate();
        _princPannel.repaint();
    }
    public void setStatutLabel(String text){
        _statut.setText(text);
    }
    public int[] getSelection(){
        return _tableEnchere.getSelectedRows();
    }
    public void setEnableBoutonEncherir(boolean isActive){
        _boutonPropose.setEnabled(isActive);
    }
    public void deselectionner(){
        _tableEnchere.clearSelection();
    }
	
	public class CellObjectRenderer extends JLabel implements TableCellRenderer{
		public CellObjectRenderer() 
		{
		  super();
		  setOpaque(true);
		}
		  /**
		   * m�thode personnalisant les couleurs de la cellule.
		   * @param table
		   * @param value
		   * @param isSelected
		   * @param hasFocus
		   * @param row
		   * @param column
		   * @return le component � afficher
		   */
		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			Object cellule = table.getValueAt(row,3);
			String ValCell = (String)cellule;
			setText((String) value);
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
			   if(isSelected) {
				   setBackground(Color.BLUE);
				   setForeground(Color.WHITE);
			   }
			if(ValCell == "Enchere Win !!")
			{
		        setBackground(Color.GREEN);
		        setForeground(Color.BLACK);
		    }else
			if(ValCell == "Enchere terminee") {
		    	setBackground(Color.RED);
		        setForeground(Color.BLACK);
		    }
			return this;
			}
		}
}
