package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
  final private Path path = Paths.get("./file.txt");
  final private Path pathInFolder = Paths.get("./data/test/file.txt");

  @Test
  void write() throws IOException {
    final var str = "This is text\nthis is a new line!";

    final var fileIO = new FileIO();
    fileIO.write(path, str);

    final var fileContent = Files.readString(path);

    assertEquals(str, fileContent);
  }

  @Test
  void read() throws IOException {
    final var str = "This is a different text\nthis is a new line!\r\n And more!";
    Files.write(path, str.getBytes());

    final var fileIO = new FileIO();
    final var fileContent = fileIO.read(path);

    assertEquals(str, fileContent);
  }

  @Test
  void writeToFileInFolder() throws IOException {
    final var str = "This is text\nthis is a new line!";

    final var fileIO = new FileIO();
    fileIO.write(pathInFolder, str);

    final var fileContent = Files.readString(pathInFolder);

    assertEquals(str, fileContent);
  }


  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(path);

    if (Files.exists(pathInFolder.getParent())) {
      Files.walk(pathInFolder.getParent())
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }
}