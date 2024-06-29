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

public class AuctionController {

    public Result startAuction(int id) {
        if (UserService.getInstance().getUser(id) == null) {
            return notFound(ApplicationUtil.createResponse("User with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(UserService.getInstance().getUser(id));
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }
}
