package behaviour_preneur;

import java.util.Vector;

import agents.PreneurAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class WaitForOffer extends Behaviour {
	
	private final PreneurAgent preneurAgent;
	
	public WaitForOffer(PreneurAgent agent) {
		super(agent);
		preneurAgent = agent;
	}
	
	@Override
    public void action() {
        if (preneurAgent.get_typeEnchere().equals("AUTO")){
        	preneurAgent.get_Interface().setEnableBoutonEncherir(false);
            for (int i = 0; i < preneurAgent.get_donnee().size(); i++){
                Vector<String> data = preneurAgent.get_donnee().get(i);
                if (Integer.valueOf(data.get(2)) <= preneurAgent.get_budget() && (data.get(3).equals("Ouvert"))){
                    String nomVendeur = data.get(0);
                    data.set(3,"Propose");
                    preneurAgent.get_donnee().set(i,data);
                    preneurAgent.get_Interface().updateTableVente(i,data);
                    preneurAgent.transMsg(nomVendeur,preneurAgent.get_nomAID(), ACLMessage.PROPOSE);
                    preneurAgent.set_nonPropEnchereStateEnd(true);
                }
            }
        }else if(preneurAgent.get_typeEnchere().equals("MANUEL")){
            if (preneurAgent.is_propOk() == true){
            	preneurAgent.set_propOk(false);
                int[] selectionPropositions = preneurAgent.get_Interface().getSelection();
                for (int j = 0; j < selectionPropositions.length; j++){
                    Vector<String> data = preneurAgent.get_donnee().get(selectionPropositions[j]);
                    if (data.get(3).equals("Ouvert")){
                        String nomVendeur = data.get(0);
                        data.set(3,"Propose");
                        preneurAgent.get_donnee().set(selectionPropositions[j],data);
                        preneurAgent.get_Interface().updateTableVente(selectionPropositions[j],data);
                        preneurAgent.transMsg(nomVendeur,preneurAgent.get_nomAID(), ACLMessage.PROPOSE);
                        preneurAgent.set_nonPropEnchereStateEnd(true);
                        preneurAgent.get_Interface().setEnableBoutonEncherir(false);
                        preneurAgent.get_Interface().deselectionner();
                    }
                }
            }
        }
        preneurAgent.receiveMsg(this.preneurAgent);
    }

	@Override
    public boolean done(){
        if(preneurAgent.is_attributionLot() == true) {
        	preneurAgent.addBehaviour(new Payment(this.preneurAgent));
            return true;
        }else if(preneurAgent.is_majAnnonce() == true){
        	preneurAgent.set_majAnnonce(false);
        	preneurAgent.set_nonPropEnchereStateEnd(false);
        	preneurAgent.get_Interface().setEnableBoutonEncherir(true);
        	preneurAgent.addBehaviour(new WaitForOffer(this.preneurAgent));
            return true;
        }else if (preneurAgent.is_nonPropEnchereStateEnd() == true){
        	preneurAgent.set_nonPropEnchereStateEnd(false);
        	preneurAgent.addBehaviour(new WaitAttribution(this.preneurAgent));
            return true;
        }else
        return false;
    }
}
