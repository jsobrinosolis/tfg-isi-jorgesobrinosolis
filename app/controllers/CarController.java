package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entities.Car;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.CarService;
import utils.ApplicationUtil;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.created;

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
}
