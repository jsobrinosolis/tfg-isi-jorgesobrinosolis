package utils;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class JadeUtil {
/*
    private static volatile JadeUtil instance;
    private AgentContainer container;

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

    private void initializeJADEPlatform() {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        jade.wrapper.AgentContainer container = rt.createMainContainer(p);
    }

    public void addAgent(String agentName, Agent agent) {
        try {
            instance.initializeJADEPlatform();
            AgentController ac = container.createNewAgent("InformAgent", agent);
            ac.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }
 */
}
