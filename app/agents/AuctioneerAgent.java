package agents;

import entities.Car;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AuctioneerAgent extends Agent {

    protected void setup() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("auctioneer");
        sd.setName(getLocalName());
        register(sd);
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new ManageAuctionBehaviour(this, 5000));
    }

    private static class ManageAuctionBehaviour extends TickerBehaviour {

        Car car = new Car("VW", "Golf", 1000);
        private AID standingBidBuyer;
        private int standingBidPrice;
        private long lastBidReceived = 0;
        private final long TIMEOUT = 10000;

        public ManageAuctionBehaviour(Agent a, long period) {
            super(a, period);
            CallForBids(car);
        }

        public void onTick() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
            ACLMessage bid = myAgent.receive(mt);
            if (bid != null) {
                int proposedPrice = Integer.parseInt(bid.getContent());
                if (standingBidBuyer == null || proposedPrice >= standingBidPrice) {
                    lastBidReceived = System.currentTimeMillis();
                    standingBidPrice = proposedPrice;
                    car.setCurrentPrice(standingBidPrice);
                    standingBidBuyer = bid.getSender();
                    car.setStandingBidBuyer(standingBidBuyer);
                    bid = myAgent.receive(mt);
                    while (bid != null) {
                        if (standingBidPrice < Integer.parseInt(bid.getContent())) {
                            lastBidReceived = System.currentTimeMillis();
                            standingBidPrice = Integer.parseInt(bid.getContent());
                            car.setCurrentPrice(standingBidPrice);
                            standingBidBuyer = bid.getSender();
                            car.setStandingBidBuyer(standingBidBuyer);
                        }
                        bid = myAgent.receive(mt);
                    }
                    CallForBids(car);
                }
            } else if (System.currentTimeMillis() - lastBidReceived > TIMEOUT){
                System.out.println("Auction ended, " + standingBidBuyer + " won.");
                ACLMessage acceptBid = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                acceptBid.addReceiver(standingBidBuyer);
                acceptBid.setContent(car.getBrand() + "," + car.getModel() + "," + car.getCurrentPrice());
                myAgent.send(acceptBid);
                stop();
            } else {
                CallForBids(car);
            }
        }

        private void CallForBids(Car car){
            System.out.println("Current winner: " + car.getStandingBidBuyer());
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            // TODO implement Reply parameters
            cfp.setReplyWith(myAgent.getLocalName() + "cfp" + System.currentTimeMillis());
            cfp.setContent(car.getBrand() + "," + car.getModel() + "," + car.getCurrentPrice() + "," + car.getStandingBidBuyer());

            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("bidder");
            template.addServices(sd);

            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                for (DFAgentDescription dfd : result) {
                    cfp.addReceiver(dfd.getName());
                }
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
            myAgent.send(cfp);
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
