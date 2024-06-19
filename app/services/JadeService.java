package services;

import agents.AuctioneerAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class JadeService {

    private static JadeService instance;
    private ContainerController containerController;

    public static JadeService getInstance() {
        if (instance == null) {
            instance = new JadeService();
        }
        return instance;
    }

    public void startJade(){
        AuctioneerAgent auctioneerAgent = new AuctioneerAgent();
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        containerController = rt.createAgentContainer(p);
        try{
            AgentController ac = containerController.acceptNewAgent("Auctioneer", auctioneerAgent);
            ac.start();
        }catch (StaleProxyException e){
            e.printStackTrace();
        }
    }

    public ContainerController getContainerController(){
        return containerController;
    }
}
