package agents;

import behaviour_marche.MarcheBehaviour;
import interfaces.MarcheGUI;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author J�r�mi Duarte
 * Impl�mentaiton de l'agent March� du protocole Fishmarket
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
 */
@SuppressWarnings("serial")
public class MarcheAgent extends Agent {
	
	//===============DECLARATION VARIABLES=============//

	private Vector<Vector<String>> _list_offres;//liste des annonces
    private Vector<String> _list_preneurs_abonnes;//liste des abo
    private MarcheGUI _Interface;//interface de l'agent
    
    //================GUETTER AND SETTER=============//

    public Vector<Vector<String>> get_list_offres() {
		return _list_offres;
	}
	public void set_list_offres(Vector<Vector<String>> annonceListe) {
		this._list_offres = annonceListe;
	}
	public Vector<String> get_list_preneurs_abonnes() {
		return _list_preneurs_abonnes;
	}
	public void set_list_preneurs_abonnes(Vector<String> abonnes) {
		this._list_preneurs_abonnes = abonnes;
	}
	public MarcheGUI get_Interface() {
		return _Interface;
	}
	public void set_Interface(MarcheGUI _Interface) {
		this._Interface = _Interface;
	}
	
	//==========DEMARRAGE ET ARRET===========//
	
	@Override
	protected void setup() {
        String nomAgent = getAID().getLocalName();
        System.out.println("Hello! Agent "+nomAgent+" is ready.");
        _Interface = new MarcheGUI(nomAgent);//interface de l'agent
        _list_offres = new Vector<>();//liste des annonces
        _list_preneurs_abonnes = new Vector<>();//liste des abo
        addBehaviour(new MarcheBehaviour(this));
    }

	@Override
    protected void takeDown() {
        System.out.println("Agent "+getAID().getName()+" terminating.");
    }
    
  //==========METHODE===========//
    
    /**
     * m�thode envoiMessage (void)
     * Permet d'envoyer un message
     * @param receiver (String) le destinataire
     * @param messageVentes (String) le message � envoy�
     */
    public void envoiMessage(String receiver, String messageVentes){
        ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
        message.addReceiver(new AID(receiver, AID.ISLOCALNAME));
        message.setLanguage("FishMarket");
        message.setOntology("FishSale-ontology");
        message.setContent(messageVentes);
        send(message);
    }
}