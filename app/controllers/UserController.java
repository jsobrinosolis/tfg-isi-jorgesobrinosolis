package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.UserService;
import utils.ApplicationUtil;

import java.util.Set;

import static play.mvc.Results.*;

public class UserController {

    public Result create(Http.Request request) {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting JSON data", false));
        }
        User user = UserService.getInstance().addUser(Json.fromJson(json, User.class));
        JsonNode jsonObject = Json.toJson(user);
        return created(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result update(Http.Request request) {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest(ApplicationUtil.createResponse("Expecting Json data", false));
        }
        User user = UserService.getInstance().updateUser(Json.fromJson(json, User.class));
        if (user == null) {
            return notFound(ApplicationUtil.createResponse("User not found", false));
        }

        JsonNode jsonObject = Json.toJson(user);
        return ok(ApplicationUtil.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        if (UserService.getInstance().getUser(id) == null) {
            return notFound(ApplicationUtil.createResponse("User with id:" + id + " not found", false));
        }
        JsonNode jsonObjects = Json.toJson(UserService.getInstance().getUser(id));
        return ok(ApplicationUtil.createResponse(jsonObjects, true));
    }

    public Result listUsers() {
        Set<User> result = UserService.getInstance().getAllUsers();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(ApplicationUtil.createResponse(jsonData, true));

    }

    public Result delete(int id) {
        if (!UserService.getInstance().deleteUser(id)) {
            return notFound(ApplicationUtil.createResponse("User with id:" + id + " not found", false));
        }
        return ok(ApplicationUtil.createResponse("User with id:" + id + " deleted", true));

    }
}
