package com.k3ntako.HTTPServer;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteRegistry {
  private final ArrayList<String> HTTP_METHODS = new ArrayList<>(Arrays.asList(
      "GET", "HEAD", "POST", "PUT", "DELETE",
      "CONNECT", "OPTIONS", "TRACE", "PATCH"
  ));
  final private ArrayList<RegisteredRoute> routes = new ArrayList<>();

  public void registerRoute(String method, String url, ControllerMethodInterface controller) throws Exception {
    method = method.toUpperCase();

    if (!HTTP_METHODS.contains(method)) {
      throw new Exception("Unknown method: " + method);
    }

    var registeredRoute = new RegisteredRoute(method, url, controller);
    routes.add(registeredRoute);
  }

  public Route getController(RequestInterface request) {
    var routeMatcher = new RouteMatcher();
    return routeMatcher.matchRoute(routes, request);
  }
}
