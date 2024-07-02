package agents;

import entities.Car;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class AuctioneerAgent extends Agent {

    public ArrayList<Car> auctionLedger = new ArrayList<>();

    protected void setup() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("auctioneer");
        sd.setName(getLocalName());
        register(sd);
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new HandleCarRegistrationBehaviour());
        //addBehaviour(new ManageAuctionBehaviour(this, 5000));
    }

    private class HandleCarRegistrationBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchConversationId("register-car"));
            ACLMessage request = myAgent.receive(mt);
            if (request != null){
                String[] content = request.getContent().split(",");
                auctionLedger.add(new Car(content[0], content[1], Integer.parseInt(content[2].trim()), Integer.parseInt(content[3].trim())));
                ACLMessage reply = request.createReply();
                reply.setPerformative(ACLMessage.CONFIRM);
                reply.setConversationId("register-car");
                reply.setContent("Contenido reply: " + content[0] + " " + content[1] + " successfully registered for auction");
                myAgent.send(reply);
            } else {
                block();
            }
        }
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
}
