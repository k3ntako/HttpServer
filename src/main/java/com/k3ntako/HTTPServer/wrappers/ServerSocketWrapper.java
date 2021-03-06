package com.k3ntako.HTTPServer.wrappers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
  final private ServerSocket serverSocket;

  public ServerSocketWrapper(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }

  public Socket accept() throws IOException {
    return serverSocket.accept();
  }

  public void close() throws IOException {
    serverSocket.close();
  }

  public int port() {
    return serverSocket.getLocalPort();
  }
}
