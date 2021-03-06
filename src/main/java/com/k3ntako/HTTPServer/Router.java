package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.controllers.NotFound;
import com.k3ntako.HTTPServer.utilities.MimeTypes;
import com.k3ntako.HTTPServer.utilities.JsonConverter;

import java.io.IOException;

public class Router {
  final private RouteRegistry routeRegistry;

  public Router(RouteRegistry routeRegistry) {
    this.routeRegistry = routeRegistry;
  }

  public ResponseInterface routeRequest(RequestInterface request) throws IOException, HTTPError {
    var route = routeRegistry.getController(request);
    ControllerMethodInterface controllerMethod = (RequestInterface req, ResponseInterface res) -> new NotFound().handleNotFound(req, res);

    if (route != null) {
      controllerMethod = route.getControllerMethod();
      request.setRouteParams(route.getRouteParams());
    }

    var jsonConverter = new JsonConverter(new Gson());
    var response = new Response(jsonConverter, new MimeTypes());
    return controllerMethod.getResponse(request, response);
  }
}
