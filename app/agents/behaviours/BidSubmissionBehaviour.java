package agents.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BidSubmissionBehaviour extends CyclicBehaviour {

    private static String[] message;
    private int userBudget;
    private MessageTemplate mt;

    public BidSubmissionBehaviour(int userBudget){
        this.userBudget = userBudget;
    }
    public void action() {
        mt = MessageTemplate.MatchConversationId("car-auction");
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            switch (msg.getPerformative()){
                case ACLMessage.CFP:
                    message = msg.getContent().split(","); //NOTA: para no implementar Serializable
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setConversationId("car-auction");
                    if (!message[3].equals(String.valueOf(myAgent.getAID()))) {
                        if (Integer.parseInt(message[2]) <= userBudget) {
                            System.out.println(myAgent.getLocalName() + " :" + message[0] + " " + message[1] + " at " + message[2]);
                            reply.setContent(String.valueOf(Integer.parseInt(message[2]) + 100));
                            myAgent.send(reply);
                        }
                    }
                    break;
                case ACLMessage.ACCEPT_PROPOSAL:
                    System.out.println(myAgent.getLocalName() + ": won the auction!");
                    break;
                case ACLMessage.INFORM:
                    System.out.println(myAgent.getLocalName() +": Auction ended, car was bought by another user.");
                    break;
                default:
                    System.out.println("Error in auction.");
                    break;
            }
        } else {
            block();
        }
    }
}
