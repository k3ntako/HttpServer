package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.YamlIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import static org.junit.jupiter.api.Assertions.*;

class YamlIOTest {

  @Test
  void read() throws Exception {
    var mockYAML = "---" +
        "\nport: 5000";

    var fileIO = new FileIOMock(mockYAML);
    var yamlIO = new YamlIO(fileIO, new Yaml());

    var config = yamlIO.read("serverConfig.json");


    assertEquals(fileIO.getLastGetResourceFileName(), "serverConfig.json");
    assertEquals(5000, config.get("port"));
  }
}