package services;

import agents.BidderAgent;
import entities.Car;
import entities.User;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import utils.JadeUtil;

import java.util.HashMap;
import java.util.Map;

public class CarService {

    private static CarService instance;
    private Map<Integer, Car> cars = new HashMap<>();
    private Map<User, Car> carLedger = new HashMap<>();
    private ContainerController containerController = JadeUtil.getInstance().getContainerController();
    private Map<Integer, User> users = UserService.getInstance().getUsers();
    private Map<User, BidderAgent> agents = UserService.getInstance().getAgents();
    public static CarService getInstance() {
        if (instance == null) {
            instance = new CarService();
        }
        return instance;
    }

    public Car addCar(Car car) {
        int id = cars.size()+1;
        car.setId(id);
        cars.put(id, car);
        carLedger.put(UserService.getInstance().getUsers().get(car.getSellerID()), car);
        BidderAgent bidderAgent = UserService.getInstance().getAgents().get(UserService.getInstance().getUsers().get(car.getSellerID()));
        bidderAgent.addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Soy un agente creando un coche!");
            }
        });
        return car;
    }
}
