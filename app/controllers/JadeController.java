package controllers;

import jade.wrapper.ContainerController;
import play.mvc.Result;
import services.JadeService;
import utils.ApplicationUtil;

import static play.mvc.Results.ok;

public class JadeController {
    public Result start() {
        JadeService.getInstance().startJade();
        return ok(ApplicationUtil.createResponse("Platform started", true));
    }
}
