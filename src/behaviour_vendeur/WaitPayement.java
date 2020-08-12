package behaviour_vendeur;

import agents.VendeurAgent;
import jade.core.behaviours.Behaviour;
@SuppressWarnings("serial")
public class WaitPayement extends Behaviour{
	
	private final VendeurAgent vendeurAgent;

	public WaitPayement(VendeurAgent agent) {
		super(agent);
		vendeurAgent = agent;
	}

	@Override
	public void action() {
		vendeurAgent.ReceiveMsg(this.vendeurAgent);
    }

	@Override
    public boolean done(){
        if (vendeurAgent.is_payReceiveEnd() == true){
            vendeurAgent.addBehaviour(new Give(this.vendeurAgent));
            return true;
        }else
        return false;
    }
    
}
