package com.k3ntako.HTTPServer.mocks;

import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.HTTPError;
import com.k3ntako.HTTPServer.ResponseInterface;

import java.util.HashMap;

public class ResponseMock implements ResponseInterface {
  public boolean createResponseCalled = false;
  public String getSetBodyArg;
  public JsonElement getSetJsonBodyArg;
  public byte[] getSetBinaryBodyArg;
  public byte[] getSetBodyBodyArg;
  public String getSetBodyContentTypeArg;
  public int getSetStatusArg;
  public String getSetRedirectUrl;
  public int getSetRedirectStatus;
  public HashMap<String, String> headers = new HashMap<>();

  @Override
  public byte[] createResponse() {
    createResponseCalled = true;
    return null;
  }

  @Override
  public void setBody(String body) {
    getSetBodyArg = body;
  }

  @Override
  public void setBody(JsonElement body) {
    getSetJsonBodyArg = body;
  }

  @Override
  public void setBody(byte[] body) {
    getSetBinaryBodyArg = body;
  }

  @Override
  public void setBody(byte[] body, String contentType) {
    getSetBodyBodyArg = body;
    getSetBodyContentTypeArg = contentType;
  }

  @Override
  public void setStatus(int status) {
    getSetStatusArg = status;
  }

  @Override
  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  @Override
  public void setRedirect(String url, int status) {
    getSetRedirectUrl = url;
    getSetRedirectStatus = status;
  }

  public Boolean isBodyNull() {
    return getSetBodyArg == null &&
        getSetJsonBodyArg == null &&
        getSetBinaryBodyArg == null;
  }
}
