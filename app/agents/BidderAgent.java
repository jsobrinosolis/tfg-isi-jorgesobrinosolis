package agents;

import entities.Car;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
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
        //addBehaviour(new ReceiveInformBehaviour());
    }

    /*private static class RegisterCar extends SimpleBehaviour {

        private Car car;
        public RegisterCar(Agent a, Car car){
            super(a);
            this.car = car;
        }

        @Override
        public void action() {
            //TODO: find auctioneers behaviour
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(car.getBrand() + "," + car.getModel() + "," + car.getCurrentPrice());
        }

        @Override
        public boolean done() {
            return false;
        }
    }*/

    private static class ReceiveInformBehaviour extends CyclicBehaviour {

        private MessageTemplate mt;
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchConversationId("car-auction"); // Use Conversation ID or Auction ID
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
