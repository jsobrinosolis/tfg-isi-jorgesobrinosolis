package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InformAgent extends Agent {
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    protected void setup() {
        System.out.println("Agent "+getLocalName()+" started.");
        addBehaviour(new InformBehaviour());
    }

    private static class InformBehaviour extends OneShotBehaviour {
        public void action() {
            ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
            inform.setContent("Hola!");
            inform.addReceiver(new AID("da0", AID.ISLOCALNAME));
            myAgent.send(inform);
        }
    }
    protected void takeDown(){
        System.out.println("Terminating...");
    }
}
