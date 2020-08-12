package behaviour_vendeur;

import agents.VendeurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class Give extends Behaviour{
	
	private final VendeurAgent vendeurAgent;

	public Give(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}
	
	@Override
	public void action() {
		vendeurAgent.TransMsg(vendeurAgent.get_winner(), vendeurAgent.get_nomAID(),ACLMessage.AGREE );
    }

	@Override
    public boolean done(){
        return true;
    }
    
}
