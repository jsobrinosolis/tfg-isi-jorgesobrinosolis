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

public class BidderAgent extends Agent {

    private static String[] message;
    private static int maxPrice = 1500;

    protected void setup() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("bidder");
        sd.setName(getLocalName());
        register(sd);
        System.out.println("Agent "+ getLocalName()+ " started.");
        addBehaviour(new ReceiveInformBehaviour());
    }

    private static class ReceiveInformBehaviour extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                message = msg.getContent().split(","); //NOTA: para no implementar Serializable
                switch (msg.getPerformative()){
                    case ACLMessage.CFP:
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        if (!message[3].equals(String.valueOf(myAgent.getAID()))) {
                            if (Integer.parseInt(message[2]) <= maxPrice) {
                                System.out.println(myAgent.getLocalName() + " :" + message[0] + " " + message[1] + " at " + message[2]);
                                reply.setContent(String.valueOf(Integer.parseInt(message[2]) + 100));
                                myAgent.send(reply);
                            }
                        }
                        break;
                    case ACLMessage.ACCEPT_PROPOSAL:
                        // TODO Winning auction logic
                        System.out.println(myAgent.getLocalName() + ": You won the auction!");
                        break;
                    case ACLMessage.INFORM:
                        // TODO: Losing auction logic
                        System.out.println(myAgent.getLocalName() +": Auction ended, car was bought by another user.");
                        break;
                    default:
                        // TODO: Error in auction logic
                        System.out.println("Error in auction.");
                        break;
                }
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
    protected void takeDown(){
        System.out.println("Terminating...");
    }
}
