package behaviour_vendeur;

import agents.VendeurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class WaitSecondBid extends Behaviour {
	
	private final VendeurAgent vendeurAgent;

	public WaitSecondBid(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}

	@Override
	public void action() {
		vendeurAgent.ReceiveMsg(this.vendeurAgent);
        block(1000);
        vendeurAgent.set_timerRest(vendeurAgent.get_timerRest() - 1);
        vendeurAgent.get_Interface().setTempsRestant(String.valueOf(vendeurAgent.get_timerRest()));
        if (vendeurAgent.get_timerRest() <= 0){
        	vendeurAgent.set_bidStateEnd(true);
        	vendeurAgent.set_timerRest(vendeurAgent.get_timer());
        }
    }

	@Override
    public boolean done(){
        if (vendeurAgent.is_bidStateEnd() == true){
            vendeurAgent.set_bidStateEnd(false);
            vendeurAgent.get_Interface().setStatut("Lot emporte par: ");
            vendeurAgent.set_statut("Enchere terminee");
            vendeurAgent.get_Interface().setStatutLot(vendeurAgent.get_statut());
            String messageVente = (vendeurAgent.get_nomAID() + "," + vendeurAgent.get_nomLot() + "," + vendeurAgent.get_currentPrice()
            + "," + vendeurAgent.get_statut());
            vendeurAgent.TransMsg("Marche", messageVente,ACLMessage.CFP );
            vendeurAgent.addBehaviour(new Attribution(this.vendeurAgent));
            return true;
        }else if(vendeurAgent.get_payPreneur() >= 2){
        	vendeurAgent.addBehaviour(new WaitOtherBid(this.vendeurAgent));
            return true;
        }else
            return false;
    }
    
}
