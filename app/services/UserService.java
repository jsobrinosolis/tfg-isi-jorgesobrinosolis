package services;

import agents.BidderAgent;
import agents.behaviours.BidSubmissionBehaviour;
import entities.User;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import utils.JadeUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserService {
    private static UserService instance;
    private static Map<Integer, User> users = new HashMap<>();
    private static Map<User, BidderAgent> agents = new HashMap<>();
    private ContainerController containerController = JadeUtil.getContainerController();

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
        BidderAgent bidderAgent = new BidderAgent();
        agents.put(user, bidderAgent);
        try {
            AgentController ac = containerController.acceptNewAgent(user.getUsername(), bidderAgent);
            ac.start();
            bidderAgent.addBehaviour(new BidSubmissionBehaviour(user.getBudget()));
        } catch (StaleProxyException e){
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

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<User, BidderAgent> getAgents() {
        return agents;
    }
}
