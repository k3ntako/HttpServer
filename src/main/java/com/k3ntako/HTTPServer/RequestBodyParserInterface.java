package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public interface RequestBodyParserInterface {
  byte[] parseBody(
      BufferedReader bufferedReader,
      Socket clientSocket,
      String contentTypeCategory,
      int contentLength
  ) throws IOException;
}
