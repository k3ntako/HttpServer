package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws Exception {
    var serverIO = new ServerServerIO();
    var requestHandler = new RequestHandler();
    var serverSocket = new ServerSocketWrapper(5000);

    var routeRegistrar = new RouteRegistrar(new RouteRegistry(), new FileIO());
    var routeRegistry = routeRegistrar.registerRoutes();
    var router = new Router(routeRegistry);

    var app = new Server(serverIO, requestHandler, serverSocket, router);

    while (true) {
      app.run();
    }
  }
}
