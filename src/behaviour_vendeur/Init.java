package behaviour_vendeur;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import agents.VendeurAgent;
import behaviour_vendeur.WaitFirstBid;
@SuppressWarnings("serial")
public class Init extends Behaviour {
	
	private final VendeurAgent vendeurAgent;

	public Init(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}
	
	@Override
    public void action() {
    	vendeurAgent.doWait();
    }

	@Override
    public boolean done(){
        if (vendeurAgent.is_initStateEnd() == true){
        	vendeurAgent.set_payPreneur(0);
        	vendeurAgent.set_timerRest(vendeurAgent.get_timer());
        	vendeurAgent.addBehaviour(new WaitFirstBid(this.vendeurAgent));
            String messageVente = (vendeurAgent.get_nomAID() + "," + vendeurAgent.get_nomLot() + "," + vendeurAgent.get_currentPrice()
            + "," + vendeurAgent.get_statut());
            vendeurAgent.TransMsg("Marche", messageVente,ACLMessage.CFP );
        }
        return (vendeurAgent.is_initStateEnd());
    }
}
