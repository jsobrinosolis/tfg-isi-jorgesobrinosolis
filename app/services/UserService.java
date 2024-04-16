package services;

import agents.InformAgent;
import entities.User;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.tools.DummyAgent.DummyAgent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
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

        InformAgent informAgent = new InformAgent();
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        AgentContainer container = rt.createMainContainer(p);
        try{
            AgentController ac = container.acceptNewAgent(user.getUsername(), informAgent);
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
