package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InformAgent extends Agent {

    private static int aux;
    protected void setup() {
        System.out.println("Agent "+getLocalName()+" started.");
        addBehaviour(new ReceiveInformBehaviour());
    }

    private static int getAux(int number){
        return aux + number;
    }

    private static class InformBehaviour extends OneShotBehaviour {
        public void action() {
            ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
            inform.setContent("Hola!");
            inform.addReceiver(new AID("dummy1", AID.ISLOCALNAME));
            myAgent.send(inform);
        }
    }
    private static class ReceiveInformBehaviour extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                aux = getAux(Integer.parseInt(msg.getContent()));
                System.out.println("Received INFORM message: " + msg.getContent() + "\n New value: " + aux);
            } else {
                block();
            }
        }
    }

    protected void takeDown(){
        System.out.println("Terminating agent" + getLocalName());
    }
}
