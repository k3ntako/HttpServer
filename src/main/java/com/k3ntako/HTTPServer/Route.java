package com.k3ntako.HTTPServer;

import java.util.HashMap;

public class Route {
  final private String url;
  private ControllerMethodInterface controllerMethod;
  private HashMap<String, String> routeParams = new HashMap<>();

  public Route(String url) {
    this.url = url;
  }

  public String getRouteUrl() {
    return url;
  }

  public void setControllerMethod(ControllerMethodInterface controllerMethod) {
    this.controllerMethod = controllerMethod;
  }

  public ControllerMethodInterface getControllerMethod() {
    return this.controllerMethod;
  }

  public void setRouteParams(HashMap<String, String> routeParams) {
    this.routeParams = routeParams;
  }

  public HashMap<String, String> getRouteParams() {
    return this.routeParams;
  }
}
