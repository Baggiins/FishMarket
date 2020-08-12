package behaviour_preneur;
import java.util.Vector;

import agents.PreneurAgent;
import behaviour_preneur.*;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class TraitementAnnounce extends Behaviour {
	
	private final PreneurAgent preneurAgent;

	public TraitementAnnounce(PreneurAgent agent) {
		super(agent);
		preneurAgent = agent;
	}

	@Override
	public void action() {
        if(preneurAgent.is_abo() == false){
        	preneurAgent.set_abo(true);
        	preneurAgent.transMsg("Marche",preneurAgent.get_nomAID(),ACLMessage.SUBSCRIBE);
        }
        preneurAgent.receiveMsg(this.preneurAgent);
    }

	@Override
    public boolean done(){
        if (preneurAgent.is_listeAboVide()==false && preneurAgent.is_selecMode()==true && preneurAgent.is_selecAnnonces()==true){
            preneurAgent.get_Interface().setStatutLabel("Abonnement OK");
            for (int i = 0; i< preneurAgent.get_annonces().length; i++ ){
                Vector<String> data = preneurAgent.get_donnee().get(preneurAgent.get_annonces()[i]);
                if(data.get(3).equals("Ouvert")){
                    data.set(3,"Propose");
                    preneurAgent.get_donnee().set(preneurAgent.get_annonces()[i], data);
                    preneurAgent.transMsg(data.get(0),preneurAgent.get_nomAID(), ACLMessage.PROPOSE);
                }
            }
            int positionVecteur = 0;
            int tailleVecteurOriginale = preneurAgent.get_donnee().size();
            for (int i = 0; i< tailleVecteurOriginale; i++ ){
                Vector<String> data = preneurAgent.get_donnee().get(positionVecteur);
                if (!data.get(3).equals("Propose")){
                	preneurAgent.get_donnee().remove(positionVecteur);
                }else{
                    positionVecteur++;
                }
            }
            preneurAgent.get_Interface().setEnableBoutonEncherir(false);
            preneurAgent.get_Interface().deselectionner();
            preneurAgent.get_Interface().resetTableEnchere(preneurAgent.get_donnee());
            if(preneurAgent.get_donnee().size() == 0){
            	preneurAgent.get_Interface().setStatutLabel("Plus d'encheres disponibles");
            	preneurAgent.doDelete();
            }
            preneurAgent.addBehaviour(new WaitForOffer(this.preneurAgent));
            preneurAgent.set_initStateEnd(true);
            return true;
        }
        return (preneurAgent.is_initStateEnd());
    }
}
