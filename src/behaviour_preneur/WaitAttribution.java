package behaviour_preneur;

import agents.PreneurAgent;
import jade.core.behaviours.Behaviour;
@SuppressWarnings("serial")
public class WaitAttribution extends Behaviour {
	
	private final PreneurAgent preneurAgent;
	
	public WaitAttribution(PreneurAgent agent) {
		super(agent);
		preneurAgent = agent;
	}
	
	@Override
    public void action() {
    	preneurAgent.receiveMsg(this.preneurAgent);
    }

	@Override
    public boolean done(){
        if(preneurAgent.is_attributionLot() == true) {
        	preneurAgent.addBehaviour(new Payment(preneurAgent));
            return true;
        }else if(preneurAgent.is_majAnnonce() == true) {
        	preneurAgent.set_majAnnonce(false);
        	preneurAgent.get_Interface().setEnableBoutonEncherir(true);
        	preneurAgent.addBehaviour(new WaitForOffer(this.preneurAgent));
            return true;
        }else {
        	return false;
        }
    }
}