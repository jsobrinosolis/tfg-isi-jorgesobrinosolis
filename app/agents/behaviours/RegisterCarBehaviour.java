package agents.behaviours;

import agents.BidderAgent;
import entities.Car;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RegisterCarBehaviour extends OneShotBehaviour {
    private Car car;
    private MessageTemplate mt;
    public RegisterCarBehaviour(Car car){
        this.car = car;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setConversationId("register-car");
        msg.setContent(car.getBrand() + "," + car.getModel() + "," + car.getCurrentPrice() +"," + car.getSellerID());

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("auctioneer");
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            if (result.length > 0) {
                msg.addReceiver(result[0].getName());
                myAgent.send(msg);
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("register-car"),MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
            } else {
                System.out.println("Error: No auctioneer was found.");
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        ACLMessage reply = myAgent.blockingReceive();
        if (reply != null) {
            if (reply.getPerformative() == ACLMessage.CONFIRM) {
                System.out.println(myAgent.getLocalName() + reply.getContent());
            } else {
                System.out.println("Error: Failed to register car");
            }
        }
    }
}
