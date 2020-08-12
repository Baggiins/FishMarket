package behaviour_preneur;

import agents.PreneurAgent;
import jade.core.behaviours.Behaviour;
@SuppressWarnings("serial")
public class WaitGive extends Behaviour {
	
	private final PreneurAgent preneurAgent;
	
	public WaitGive(PreneurAgent agent) {
		super(agent);
		preneurAgent = agent;
	}
	
	@Override
    public void action() {
    	preneurAgent.receiveMsg(this.preneurAgent);
    }

    @Override
    public boolean done(){
        if (preneurAgent.is_receiveToGiveStateEnd() == true){
            for (int i = 0; i < preneurAgent.get_donnee().size(); i++) {
                if (preneurAgent.get_donnee().get(i).get(0).equals(preneurAgent.get_vendeurWin())) {
                	preneurAgent.get_donnee().get(i).set(3, "Enchere Win !!");
                }else {
                }
            }
            preneurAgent.get_Interface().setStatutLabel("Enchere Win !!");
            preneurAgent.get_Interface().setEnableBoutonEncherir(false);
            return true;
        }else {
            return false;
        }
    }
}
