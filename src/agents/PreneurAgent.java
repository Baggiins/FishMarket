package agents;

import behaviour_preneur.*;
import interfaces.PreneurGUI;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.core.behaviours.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author J�r�mi Duarte
 * Impl�mentaiton de l'agent Preneur du protocole Fishmarket
 *
 * SIGNIFICATION PERFORMATIF :
 *
 * ACLMessage.CFP = to_annoncce
 * ACLMessage.PROPOSE = to_bid
 * ACLMessage.ACCEPT_PROPOSAL = to_attribute
 * ACLMessage.AGREE = to_give
 * ACLMessage.CONFIRM  = to_pay
 * ACLMessage.SUBSCRIBE = abonnement acheteur/vendeur
 * ACLMessage.INFORM = rep_bid
 *
 * Liste des �tats :
 * Etat 1 : traitement annonces (TraitementAnnounce)
 * Etat 2 : attente r�ponse offre (WaitForOffer)
 * Etat 3 : attente attribution (WaitAttribution)
 * Etat 4 : paiement (Payment)
 * Etat 5 : attente livraison (WaitGive)
 * 
 *
 */
@SuppressWarnings("serial")
public class PreneurAgent extends Agent {
	
	//=============DECLARATION VARIABLES============//
	
	private PreneurGUI _Interface;
	private Vector<Vector<String>> _donnee;
	
	//Strings n�cessaire
    private String _nomAID;
    private String _typeEnchere;
    private String _vendeurWin;
	
	//Liste de tout les bool�ens n�cessaire
	private boolean _nonPropEnchereStateEnd = false;
    private boolean _abo = false;
    private boolean _attributionLot = false;
    private boolean _listeAboVide = true;
    private boolean _selecAnnonces = false;
    private boolean _initStateEnd = false;
    private boolean _majAnnonce = false;
    private boolean _selecMode = false;
    private boolean _propOk = false;
    private boolean _receiveToGiveStateEnd = false;
    
    //Integer n�cessaire
    private int _budget = 0;
    private int[] _annonces;
    
    //================GETTER AND SETTER==============//

    public PreneurGUI get_Interface() {
		return _Interface;
	}
	public void set_Interface(PreneurGUI _Interface) {
		this._Interface = _Interface;
	}
	public Vector<Vector<String>> get_donnee() {
		return _donnee;
	}
	public void set_donnee(Vector<Vector<String>> _donnee) {
		this._donnee = _donnee;
	}
	public String get_nomAID() {
		return _nomAID;
	}
	public void set_nomAID(String _nomAID) {
		this._nomAID = _nomAID;
	}
	public String get_typeEnchere() {
		return _typeEnchere;
	}
	public void set_typeEnchere(String _typeEnchere) {
		this._typeEnchere = _typeEnchere;
	}
	public String get_vendeurWin() {
		return _vendeurWin;
	}
	public void set_vendeurWin(String _vendeurWin) {
		this._vendeurWin = _vendeurWin;
	}
	public boolean is_nonPropEnchereStateEnd() {
		return _nonPropEnchereStateEnd;
	}
	public void set_nonPropEnchereStateEnd(boolean _nonPropEnchereStateEnd) {
		this._nonPropEnchereStateEnd = _nonPropEnchereStateEnd;
	}
	public boolean is_abo() {
		return _abo;
	}
	public void set_abo(boolean _abo) {
		this._abo = _abo;
	}
	public boolean is_attributionLot() {
		return _attributionLot;
	}
	public void set_attributionLot(boolean _attributionLot) {
		this._attributionLot = _attributionLot;
	}
	public boolean is_listeAboVide() {
		return _listeAboVide;
	}
	public void set_listeAboVide(boolean _listeAboVide) {
		this._listeAboVide = _listeAboVide;
	}
	public boolean is_selecAnnonces() {
		return _selecAnnonces;
	}
	public void set_selecAnnonces(boolean _selecAnnonces) {
		this._selecAnnonces = _selecAnnonces;
	}
	public boolean is_initStateEnd() {
		return _initStateEnd;
	}
	public void set_initStateEnd(boolean _initStateEnd) {
		this._initStateEnd = _initStateEnd;
	}
	public boolean is_majAnnonce() {
		return _majAnnonce;
	}
	public void set_majAnnonce(boolean _majAnnonce) {
		this._majAnnonce = _majAnnonce;
	}
	public boolean is_selecMode() {
		return _selecMode;
	}
	public void set_selecMode(boolean _selecMode) {
		this._selecMode = _selecMode;
	}
	public boolean is_propOk() {
		return _propOk;
	}
	public void set_propOk(boolean _propOk) {
		this._propOk = _propOk;
	}
	public boolean is_receiveToGiveStateEnd() {
		return _receiveToGiveStateEnd;
	}
	public void set_receiveToGiveStateEnd(boolean _receiveToGiveStateEnd) {
		this._receiveToGiveStateEnd = _receiveToGiveStateEnd;
	}
	public int get_budget() {
		return _budget;
	}
	public void set_budget(int _budget) {
		this._budget = _budget;
	}
	public int[] get_annonces() {
		return _annonces;
	}
	public void set_annonces(int[] _annonces) {
		this._annonces = _annonces;
	}
    
	
	//==========DEMARRAGE ET ARRET===========//
	
	@Override
	protected void setup() {
        _donnee = new Vector<>();
        _nomAID = getAID().getLocalName();
        System.out.println("Hello! Agent "+_nomAID+" is ready.");
        _Interface = new PreneurGUI(_nomAID, this);
        addBehaviour(new TraitementAnnounce(this));
    }


	@Override
	protected void takeDown() {
        System.out.println("Agent "+getAID().getName()+" terminating.");
    }
    
    //=============METHODES=============//
    
    
    public void transMsg(String destinataire, String messageStr, int performatif ){
        ACLMessage msg = new ACLMessage(performatif);
        msg.addReceiver(new AID(destinataire, AID.ISLOCALNAME));
        msg.setLanguage("FishMarket");
        msg.setOntology("FishSale-ontology");
        msg.setContent(messageStr);
        send(msg);
    }

    public void receiveMsg(PreneurAgent pa){
	    String messageRecu;
	    ACLMessage msg = receive();
	        if (msg != null && msg.getPerformative() == ACLMessage.PROPAGATE) {
	            messageRecu = msg.getContent();
	            boolean existe = false;
	            int positionExistant = 0;
	            String[] champsMsg = messageRecu.split(",");
	            for (int i = 0; i < pa._donnee.size(); i++) {
	                if (pa._donnee.get(i).get(0).equals(champsMsg[0])) {
	                    existe = true;
	                    positionExistant = i;
	                    break;
	                }
	            }
	            if(existe){
	            	pa._donnee.set(positionExistant, new Vector<>(Arrays.asList(champsMsg)));
	            	pa._Interface.updateTableVente(positionExistant, pa._donnee.get(positionExistant));
	                if (champsMsg[3].equals("Ouvert")){
	                	pa._majAnnonce = true;
	                }
	            }else if(pa._initStateEnd == false){
	            	pa._donnee.add(new Vector<>(Arrays.asList(champsMsg)));
	            	pa._Interface.addTableVente(new Vector<>(Arrays.asList(champsMsg)));
	            	pa._listeAboVide = false;
	            }
	        }else if (msg != null && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
	        	pa._vendeurWin = msg.getContent();
	        	pa._attributionLot = true;
	        }else if (msg != null && msg.getPerformative() == ACLMessage.AGREE) {
	        	pa._receiveToGiveStateEnd = true;
	        }
	        else{
	        	pa.doWait();
	        }
	    }
}
