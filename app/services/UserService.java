package services;

import agents.AuctioneerAgent;
import agents.BidderAgent;
import entities.User;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserService {
    private static UserService instance;
    private Map<Integer, User> users = new HashMap<>();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User addUser(User user) {
        int id = users.size()+1;
        user.setId(id);
        users.put(id, user);

        AuctioneerAgent auctioneerAgent = new AuctioneerAgent();
        BidderAgent bidderAgent = new BidderAgent();
        BidderAgent bidderAgent2 = new BidderAgent();
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        //p.setParameter(Profile.GUI, "true");
        ContainerController container = rt.createAgentContainer(p);
        try{
            AgentController ac = container.acceptNewAgent("Prueba", auctioneerAgent);
            ac.start();
            AgentController ac1 = container.acceptNewAgent("Usuario", bidderAgent);
            ac1.start();
            AgentController ac2 = container.acceptNewAgent("Usuario2", bidderAgent2);
            ac2.start();
        }catch (StaleProxyException e){
            e.printStackTrace();
        }

        return user;
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(users.values());
    }

    public User updateUser(User user) {
        int id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            return user;
        }
        return null;
    }

    public boolean deleteUser(int id) {
        return users.remove(id) != null;
    }
}
