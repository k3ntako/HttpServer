package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.RequestBodyParserMock;
import com.k3ntako.HTTPServer.mocks.SocketMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientSocketIOTest {

  @Test
  void readTextBody() throws IOException {
    var bodyStr = "Body line 1: abc\n" +
        "Body line 2: abc\n" +
        "Body line 3: abc\n" +
        "Body line 4: abc\n";
    var socket = new SocketMock(bodyStr);

    var requestBodyParser = new RequestBodyParserMock(bodyStr);
    var clientSocketIO = new ClientSocketIO(requestBodyParser);
    clientSocketIO.init(socket);
    var body = (String) clientSocketIO.parseBody("text/plain", bodyStr.length());

    assertEquals(bodyStr, body);
    assertEquals(bodyStr.length(), requestBodyParser.contentLength);
    assertEquals("text", requestBodyParser.contentTypeCategory);
  }

  @Test
  void readBinaryBody() throws IOException {
    var str = "This is text!";
    var bodyBinary = str.getBytes();
    var socket = new SocketMock(bodyBinary);

    var requestBodyParser = new RequestBodyParserMock(bodyBinary);
    var clientSocketIO = new ClientSocketIO(requestBodyParser);
    clientSocketIO.init(socket);
    var body = (byte[]) clientSocketIO.parseBody("image/png", bodyBinary.length);

    assertArrayEquals(bodyBinary, body);
    assertEquals(bodyBinary.length, requestBodyParser.contentLength);
    assertEquals("image", requestBodyParser.contentTypeCategory);
  }
}