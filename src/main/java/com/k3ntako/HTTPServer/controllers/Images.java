package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.*;

import java.io.IOException;

public class Images {
  final private DataDirectoryIO dataDirectoryIO;
  final private UUIDInterface uuid;

  public Images(DataDirectoryIO dataDirectoryIO, UUIDInterface uuid) {
    this.dataDirectoryIO = dataDirectoryIO;
    this.uuid = uuid;
  }

  public ResponseInterface post(RequestInterface request, ResponseInterface response) throws IOException {
    var fileBytes = (byte[]) request.getBody();

    var uuid = this.uuid.generate();
    dataDirectoryIO.write("images/" + uuid + ".png", fileBytes);

    var responseJson = new JsonObject();
    responseJson.addProperty("id", uuid);

    response.setJsonBody(responseJson);

    return response;
  }
}