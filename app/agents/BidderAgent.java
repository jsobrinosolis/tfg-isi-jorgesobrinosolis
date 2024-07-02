package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class BidderAgent extends Agent {

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
