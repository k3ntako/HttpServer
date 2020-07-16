package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.RequestGeneratorInterface;
import com.k3ntako.HTTPServer.RequestInterface;
import com.k3ntako.HTTPServer.ServerIOInterface;

import java.util.HashMap;

public class RequestGeneratorMockThrowsError implements RequestGeneratorInterface {
  private String method;
  private String route;
  private String protocol;
  private HashMap<String, String> headers;
  private String body;
  private boolean handleRequestCalled = false;

  public RequestGeneratorMockThrowsError() {
    this.method = "GET";
    this.route = "/simple_get_with_body";
    this.protocol = "HTTP/1.1";
    this.headers = new HashMap<>();
    this.body = "";
  }

  public RequestInterface generateRequest(ServerIOInterface serverIO) throws HTTPError {
    throw new HTTPError(500, "This is a test error");
  }

  public boolean wasHandleRequestCalled() {
    return handleRequestCalled;
  }
}