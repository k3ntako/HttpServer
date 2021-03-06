package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {

  @Test
  void handleHTTPError() throws HTTPError {
    var httpError = new HTTPError(404, "File was not found");
    var errorHandler = new ErrorHandler();

    var response = errorHandler.handleError(httpError);
    var responseStr = response.createResponse();

    var expectedResponse = "HTTP/1.1 404 Not Found\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 18\r\n\r\n" +
        "File was not found";

    assertEquals(expectedResponse, new String(responseStr));
  }

  @Test
  void handleServerError() throws HTTPError {
    var error = new Exception("Something went wrong");
    var errorHandler = new ErrorHandler();

    var response = errorHandler.handleError(error);
    var responseStr = response.createResponse();

    var expectedResponse = "HTTP/1.1 500 Internal Server Error\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Length: 20\r\n\r\n" +
        "Something went wrong";

    assertEquals(expectedResponse, new String(responseStr));
  }
}