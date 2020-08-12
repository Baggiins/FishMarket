package behaviour_preneur;

import agents.PreneurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class Payment extends Behaviour {
	
	private final PreneurAgent preneurAgent;
	
	public Payment(PreneurAgent agent) {
		super(agent);
		preneurAgent = agent;
	}
	
	@Override
    public void action() {
        for (int i = 0; i < preneurAgent.get_donnee().size(); i++){
            if (preneurAgent.get_donnee().get(i).get(3).equals("Enchere Win !!")){
            	preneurAgent.set_vendeurWin(preneurAgent.get_donnee().get(i).get(0));
            }
        }
        preneurAgent.transMsg(preneurAgent.get_vendeurWin(),"To_Pay",ACLMessage.CONFIRM);
    }

	@Override
    public boolean done(){
    	preneurAgent.addBehaviour(new WaitGive(this.preneurAgent));
        return true;
    }
}
