package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
  private ServerSocket serverSocket;
  public ServerSocketWrapper(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }

  public Socket accept() throws IOException {
    return serverSocket.accept();
  }
}
