package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServer implements EchoServerInterface {
  private BufferedReader input;
  private PrintWriter output;
  private Socket clientSocket;
  private ServerSocketWrapperInterface serverSocketWrapper;
//  public EchoSocket(Socket clientSocket, BufferedReader bufferedReader, PrintWriter printWriter) {
//    input = bufferedReader;
//    output = printWriter;
//  }

  public void createAndListen(ServerSocketWrapperInterface serverSocket) throws IOException {
    serverSocketWrapper = serverSocket;
    clientSocket = serverSocketWrapper.accept();

    input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    output = new PrintWriter(clientSocket.getOutputStream(), true);
  }

  public String readLine() throws IOException {
    return input.readLine();
  }

  public void sendData(String data) {
    output.println(data);
  }

  public void close() throws IOException {
    output.close();
    input.close();
    clientSocket.close();
    serverSocketWrapper.close();
  }
}
