package behaviour_vendeur;

import java.util.Arrays;
import java.util.Vector;

import agents.VendeurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class Attribution extends Behaviour {
	
	private final VendeurAgent vendeurAgent;
	
	public Attribution(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}
    
	@Override
	public void action() {

        for (int i = 0; i < vendeurAgent.get_donnee().size(); i++){
            if (vendeurAgent.get_donnee().get(i).get(1).equals("A propose")){
            	vendeurAgent.set_winner(vendeurAgent.get_donnee().get(i).get(0));
            }
        }
        vendeurAgent.TransMsg(vendeurAgent.get_winner(), vendeurAgent.get_nomAID(),ACLMessage.ACCEPT_PROPOSAL );
        vendeurAgent.get_Interface().setStatut("Paiement recu. Enchere termine !");
        vendeurAgent.get_Interface().finEnchere(vendeurAgent.get_winner());
        for (int j = 0; j < vendeurAgent.get_donnee().size(); j++) {
            if (vendeurAgent.get_donnee().get(j).get(0).equals(vendeurAgent.get_winner())) {
            	vendeurAgent.get_donnee().set(j, new Vector<>(Arrays.asList(vendeurAgent.get_winner(), "A emporte l'enchere!")));
            	vendeurAgent.get_Interface().updateTableAcheteur(j, vendeurAgent.get_donnee().get(j));
            }else{
            	vendeurAgent.get_donnee().set(j, new Vector<>(Arrays.asList(vendeurAgent.get_donnee().get(j).get(0), "A perdu l'enchere")));
            	vendeurAgent.get_Interface().updateTableAcheteur(j, vendeurAgent.get_donnee().get(j));
            }
        }
    }

	@Override
    public boolean done(){
        vendeurAgent.addBehaviour(new WaitPayement(this.vendeurAgent));
        return true;
    }
}
