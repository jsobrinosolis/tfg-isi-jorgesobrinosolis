package utils;

import agents.AuctioneerAgent;
import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JadeUtil {

    @Inject
    public ContainerController JadeUtil(){
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        ContainerController container = rt.createAgentContainer(p);
        return container;
    }


    /*private static volatile JadeUtil instance;
    private jade.wrapper.AgentContainer container;

    private JadeUtil() {
        initializeJADEPlatform();
    }

    public static JadeUtil getInstance() {
        if (instance == null) {
            synchronized (JadeUtil.class) {
                if (instance == null) {
                    instance = new JadeUtil();
                }
            }
        }
        return instance;
    }

    public AgentContainer initializeJADEPlatform() {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        container = rt.createMainContainer(p);
        return (AgentContainer) container;
    }*/

   /* public void addAgent(String agentName, Agent agent) {
        try {
            instance.initializeJADEPlatform();
            AgentController ac = container.createNewAgent("InformAgent", agent);
            ac.start();
            System.out.println("Check");
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }*/

}
