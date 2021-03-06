package com.k3ntako.HTTPServer.wrappers;

import java.io.IOException;
import java.net.Socket;

public interface ServerSocketWrapperInterface extends AutoCloseable {
  Socket accept() throws IOException;

  void close() throws IOException;

  int port();
}
