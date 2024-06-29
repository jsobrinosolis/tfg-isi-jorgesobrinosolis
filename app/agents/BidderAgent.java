package agents;

import entities.Car;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.Map;

public class BidderAgent extends Agent {
    private static int maxPrice = 1500;

    protected void setup() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("bidder");
        sd.setName(getLocalName());
        register(sd);
        System.out.println("Agent "+ getLocalName()+ " started.");
    }

    private void register(ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    protected void takeDown(){
        System.out.println("Terminating agent " + getLocalName());
    }
}
