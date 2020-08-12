package behaviour_marche;

import java.util.Arrays;
import java.util.Vector;

import agents.MarcheAgent;
import agents.PreneurAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
@SuppressWarnings("serial")
public class MarcheBehaviour extends CyclicBehaviour{
	
	private MarcheAgent marcheAgent;
	
	public MarcheBehaviour(MarcheAgent agent) {
		super(agent);
		marcheAgent = agent;
	}
	
	@Override
	public void action() {
    	String messageRecu;
        ACLMessage msg = marcheAgent.receive();
        if (msg != null && msg.getPerformative() == ACLMessage.CFP) {
            messageRecu = msg.getContent();
            String[] champsMsg = messageRecu.split(",");
            boolean annonceExist = false;
            int positionExistant = 0;
            for (int i = 0; i < marcheAgent.get_list_offres().size(); i++) {
                if (marcheAgent.get_list_offres().get(i).get(0).equals(champsMsg[0])) {
                	annonceExist = true;
                    positionExistant = i;
                    break;
                }
            }
            if(annonceExist){
            	marcheAgent.get_list_offres().set(positionExistant, new Vector<>(Arrays.asList(champsMsg)));
            	marcheAgent.get_Interface().majTable(positionExistant, marcheAgent.get_list_offres().get(positionExistant));
            }else{
            	marcheAgent.get_list_offres().add(new Vector<>(Arrays.asList(champsMsg)));
            	marcheAgent.get_Interface().ajoutNewTable(new Vector<>(Arrays.asList(champsMsg)));
            }
            for(int j = 0; j < marcheAgent.get_list_preneurs_abonnes().size(); j++){
            	marcheAgent.envoiMessage(marcheAgent.get_list_preneurs_abonnes().get(j),messageRecu);
            }
        }else if (msg != null && msg.getPerformative() == ACLMessage.SUBSCRIBE){
            messageRecu = msg.getContent();
            boolean subExist = false;
            for(int i = 0; i < marcheAgent.get_list_preneurs_abonnes().size(); i++){
                if (marcheAgent.get_list_preneurs_abonnes().get(i).equals(messageRecu)) {
                	subExist = true;
                    break;
                }
            }
            if(!subExist) {
            	marcheAgent.get_list_preneurs_abonnes().add(messageRecu);
                if (marcheAgent.get_list_offres().size() != 0){
                    String messageVentes= "";
                    boolean premiereLigne = true;
                    for(int j = 0; j < marcheAgent.get_list_offres().size(); j++){
                        if (premiereLigne){
                        	premiereLigne = false;
                        	}
                        else{
                        	messageVentes += ",";
                        	}
                        messageVentes += (marcheAgent.get_list_offres().get(j).get(0) +
                        		"," + marcheAgent.get_list_offres().get(j).get(1) + ","+
                        		marcheAgent.get_list_offres().get(j).get(2) + ","+ marcheAgent.get_list_offres().get(j).get(3));
                    }
                    marcheAgent.envoiMessage(messageRecu,messageVentes);
                }
            }
        }else{
        	marcheAgent.doWait();
        }
    }
}
