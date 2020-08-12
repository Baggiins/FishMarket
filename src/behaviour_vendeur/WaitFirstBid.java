package behaviour_vendeur;

import agents.VendeurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class WaitFirstBid extends Behaviour{
	
	private final VendeurAgent vendeurAgent;

	public WaitFirstBid(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}
	
	@Override
	public void action() {
		vendeurAgent.ReceiveMsg(this.vendeurAgent);
        block(1000);
        vendeurAgent.set_timerRest(vendeurAgent.get_timerRest() - 1);;
        vendeurAgent.get_Interface().setTempsRestant(String.valueOf(vendeurAgent.get_timerRest()));
        if (vendeurAgent.get_timerRest() <= 0){
        	vendeurAgent.set_timerRest(vendeurAgent.get_timer());
        	vendeurAgent.set_endStateAnnonce(true);
        	vendeurAgent.get_Interface().setTempsRestant(String.valueOf(vendeurAgent.get_timerRest()));
        }
    }

	@Override
    public boolean done(){
        if (vendeurAgent.is_endStateAnnonce() == true && vendeurAgent.get_payPreneur() == 0){
            vendeurAgent.set_currentPrice(vendeurAgent.get_currentPrice() - vendeurAgent.get_decrementation());
            vendeurAgent.get_Interface().setPrixActuelAffichage(vendeurAgent.get_currentPrice());
            vendeurAgent.get_Interface().resetStatutAcheteur();
            vendeurAgent.set_endStateAnnonce(false);
            vendeurAgent.get_Interface().setStatut("Prix decremente, lot re-annonce");
            String messageVente = (vendeurAgent.get_nomAID() + "," + vendeurAgent.get_nomLot() + "," + vendeurAgent.get_currentPrice()
            + "," + vendeurAgent.get_statut());
            vendeurAgent.TransMsg("Marche", messageVente,ACLMessage.CFP );
            vendeurAgent.set_payPreneur(0);
            vendeurAgent.addBehaviour(new WaitFirstBid(this.vendeurAgent));
            return true;
        }else if(vendeurAgent.get_payPreneur() == 1){
        	vendeurAgent.addBehaviour(new WaitSecondBid(this.vendeurAgent));
            return true;
        }else
        return false;
    }
}
