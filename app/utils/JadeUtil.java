package utils;

import agents.AuctioneerAgent;
import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JadeUtil {

    private static JadeUtil instance = null;
    private static ContainerController containerController1;

    public static JadeUtil getInstance() {
        if (instance == null) {
            instance = new JadeUtil();
        }
        return instance;
    }

    @Inject
    public JadeUtil(){
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        containerController1 = rt.createAgentContainer(p);
        AuctioneerAgent auctioneerAgent = new AuctioneerAgent();
        try{
            AgentController ac = containerController1.acceptNewAgent("Auctioneer", auctioneerAgent);
            ac.start();
        }catch (StaleProxyException e){
            e.printStackTrace();
        }
        instance = this;
    }

    public static ContainerController getContainerController(){
        return containerController1;
    }

}
