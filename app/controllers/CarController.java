package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entities.Car;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.CarService;
import services.UserService;
import utils.ApplicationUtil;

import static play.mvc.Results.*;
import static play.mvc.Results.ok;

public class CarController {
    public Result register(Http.Request request) {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        Car car = CarService.getInstance().addCar(Json.fromJson(json, Car.class));
        JsonNode jsonObject = Json.toJson(car);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result auction(int id) {
        if (CarService.getInstance().getCar(id) == null) {
            return notFound(ApplicationUtil.createResponse("Car with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(CarService.getInstance().auctionCar(id));
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result retrieve(int id) {
        if (CarService.getInstance().getCar(id) == null) {
            return notFound(ApplicationUtil.createResponse("Car with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(CarService.getInstance().getCar(id));
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }
}
