package agents;

import behaviour_vendeur.*;
import interfaces.VendeurGUI;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author J�r�mi Duarte
 * Impl�mentaiton de l'agent Vendeur du protocole Fishmarket
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
 * Etat 1 : attente premiere offre (WaitFirstBid)
 * Etat 2 : attente seconde offre (WaitSecondBid)
 * Etat 3 : attente autres offres (WaitOtherBid)
 * Etat 4 : attribution (Attribution)
 * Etat 5 : attente paiement (WaitPayement)
 * Etat 6 : livraison (Give)
 *
 */
@SuppressWarnings("serial")
public class VendeurAgent extends Agent {
	
	//=============DECLARATION VARIABLES============//

    private VendeurGUI _Interface;
    private Vector<Vector<String>> _donnee;
    
    //Liste de tout les booleens necessaire
    private boolean _initStateEnd = false;
    private boolean _endStateAnnonce = false;
    private boolean _bidStateEnd = false;
    private boolean _payReceiveEnd = false;
    
    //Integers necessaire
    private int _payPreneur = 0;
    private int _currentPrice;
    private int _timer = 20;
    private int _timerRest = 0;
    private int _incrementation;
    private int _decrementation;
    
    //Strings necessaire
    private String _nomAID;
    private String _nomLot;
    private String _statut;
    private String _winner;

    //===========GETTER AND SETTER============//
    
    public VendeurGUI get_Interface() {
		return _Interface;
	}
	public void set_Interface(VendeurGUI _Interface) {
		this._Interface = _Interface;
	}
	public Vector<Vector<String>> get_donnee() {
		return _donnee;
	}
	public void set_donnee(Vector<Vector<String>> _donnee) {
		this._donnee = _donnee;
	}
	public boolean is_initStateEnd() {
		return _initStateEnd;
	}
	public void set_initStateEnd(boolean _initStateEnd) {
		this._initStateEnd = _initStateEnd;
	}
	public boolean is_endStateAnnonce() {
		return _endStateAnnonce;
	}
	public void set_endStateAnnonce(boolean _endStateAnnonce) {
		this._endStateAnnonce = _endStateAnnonce;
	}
	public boolean is_bidStateEnd() {
		return _bidStateEnd;
	}
	public void set_bidStateEnd(boolean _bidStateEnd) {
		this._bidStateEnd = _bidStateEnd;
	}
	public boolean is_payReceiveEnd() {
		return _payReceiveEnd;
	}
	public void set_payReceiveEnd(boolean _payReceiveEnd) {
		this._payReceiveEnd = _payReceiveEnd;
	}
	public int get_payPreneur() {
		return _payPreneur;
	}
	public void set_payPreneur(int _payPreneur) {
		this._payPreneur = _payPreneur;
	}
	public int get_currentPrice() {
		return _currentPrice;
	}
	public void set_currentPrice(int _currentPrice) {
		this._currentPrice = _currentPrice;
	}
	public int get_timer() {
		return _timer;
	}
	public void set_timer(int _timer) {
		this._timer = _timer;
	}
	public int get_timerRest() {
		return _timerRest;
	}
	public void set_timerRest(int _timerRest) {
		this._timerRest = _timerRest;
	}
	public int get_incrementation() {
		return _incrementation;
	}
	public void set_incrementation(int _incrementation) {
		this._incrementation = _incrementation;
	}
	public int get_decrementation() {
		return _decrementation;
	}
	public void set_decrementation(int _decrementation) {
		this._decrementation = _decrementation;
	}
	public String get_nomAID() {
		return _nomAID;
	}
	public void set_nomAID(String _nomAID) {
		this._nomAID = _nomAID;
	}
	public String get_nomLot() {
		return _nomLot;
	}
	public void set_nomLot(String _nomLot) {
		this._nomLot = _nomLot;
	}
	public String get_statut() {
		return _statut;
	}
	public void set_statut(String _statut) {
		this._statut = _statut;
	}
	public String get_winner() {
		return _winner;
	}
	public void set_winner(String _winner) {
		this._winner = _winner;
	}
	
	//==========DEMARRAGE ET ARRET===========//

	@Override
	protected void setup() {
        _donnee = new Vector<>();
        _nomAID = getAID().getLocalName();
        System.out.println("Hello! Agent "+_nomAID+" is ready.");
        _Interface = new VendeurGUI(_nomAID, this);
        addBehaviour(new Init(this));
    }

	@Override
    protected void takeDown() {
        System.out.println("Agent "+getAID().getName()+" terminating.");
    }
    
    //=============METHODES=============//

    public void ReceiveMsg(VendeurAgent va){
        String msgR;
        ACLMessage msg = receive();
        if (msg != null && msg.getPerformative() == ACLMessage.PROPOSE) {
            msgR = msg.getContent();
            boolean exist = false;
            int positionExistant = 0;
            va._payPreneur++;
            for (int i = 0; i < va._donnee.size(); i++) {
                if (va._donnee.get(i).get(0).equals(msgR)) {
                    exist = true;
                    positionExistant = i;
                    break;
                }
            }
            if(exist){
            	va._donnee.set(positionExistant, new Vector<>(Arrays.asList(msgR, "A propose")));
            	va._Interface.updateTableAcheteur(positionExistant, va._donnee.get(positionExistant));
            }else{
            	va._donnee.add(new Vector<>(Arrays.asList(msgR,"A propose")));
            	va._Interface.addTableAcheteur(new Vector<>(Arrays.asList(msgR,"A propose")));
            }
        }else if (msg != null && msg.getPerformative() == ACLMessage.CONFIRM) {
        	va._payReceiveEnd = true;
        }
    }

    public void TransMsg(String destinataire, String messageStr, int performatif ){
        ACLMessage msg = new ACLMessage(performatif);
        msg.addReceiver(new AID(destinataire, AID.ISLOCALNAME));
        msg.setLanguage("FishMarket");
        msg.setOntology("FishSale-ontology");
        msg.setContent(messageStr);
        send(msg);
    }

}
