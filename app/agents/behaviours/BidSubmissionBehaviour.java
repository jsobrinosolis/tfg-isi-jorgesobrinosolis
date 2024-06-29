package agents.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BidSubmissionBehaviour extends CyclicBehaviour {

    private static String[] message;
    private int maxPrice;
    private MessageTemplate mt;

    public BidSubmissionBehaviour(int maxPrice){
        this.maxPrice = maxPrice;
    }
    public void action() {
        mt = MessageTemplate.MatchConversationId("car-auction");
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
