package services;

import agents.AuctioneerAgent;
import agents.BidderAgent;
import agents.behaviours.ManageAuctionBehaviour;
import agents.behaviours.RegisterCarBehaviour;
import entities.Car;
import entities.User;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import utils.JadeUtil;

import java.util.HashMap;
import java.util.Map;

public class CarService {

    private static CarService instance;
    private Map<Integer, Car> cars = new HashMap<>();
    private Map<User, Car> carLedger = new HashMap<>();
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
        bidderAgent.addBehaviour(new RegisterCarBehaviour(car));
        return car;
    }

    public Car auctionCar(int id){
        cars.get(id).setStatus("AUCTION");
        AuctioneerAgent auctioneerAgent = JadeUtil.getAuctioneer();
        auctioneerAgent.addBehaviour(new ManageAuctionBehaviour(auctioneerAgent, 5000, cars.get(id)));
        return cars.get(id);
    }

    public Car getCar(int id) {return cars.get(id);}
}
