package services;

import agents.BidderAgent;
import entities.Car;
import entities.User;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import utils.JadeUtil;

import java.util.HashMap;
import java.util.Map;

public class AuctionService {

    private static AuctionService instance;

    public static AuctionService getInstance() {
        if (instance == null) {
            instance = new AuctionService();
        }
        return instance;
    }

}
