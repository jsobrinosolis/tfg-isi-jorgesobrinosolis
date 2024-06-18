package services;

import agents.AuctioneerAgent;
import entities.User;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.HashMap;
import java.util.Map;

public class JadeService {

    private static JadeService instance;

    public static JadeService getInstance() {
        if (instance == null) {
            instance = new JadeService();
        }
        return instance;
    }

    public ContainerController startJade(){
        AuctioneerAgent auctioneerAgent = new AuctioneerAgent();
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController container = rt.createAgentContainer(p);
        try{
            AgentController ac = container.acceptNewAgent("Auctioneer", auctioneerAgent);
            ac.start();
        }catch (StaleProxyException e){
            e.printStackTrace();
        }
        return container;
    }
}
