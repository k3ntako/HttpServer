package com.k3ntako.HTTPServer;

import com.google.gson.Gson;
import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.fileSystemsIO.ReminderIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import com.k3ntako.HTTPServer.utilities.JsonConverter;
import com.k3ntako.HTTPServer.utilities.UUID;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReminderIOTest {

  @Test
  void getReminderByIds() throws IOException, HTTPError {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\":{\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    var reminder = reminderIO.getReminderByIds("reminder-list-1", "reminder-123");

    assertNotNull(reminder);
    assertEquals("reminder-123", reminder.get("id").getAsString());
    assertEquals("task content!!", reminder.get("task").getAsString());
    assertEquals("./mock/data/reminders/reminder-list-1.json", fileIO.getLastReadPath().toString());
  }

  @Test
  void getReminderByIdsThrowsErrorIfListIsNotFound() {
    var fileIO = new FileIOMock((String) null);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.getReminderByIds(
        "reminder-list-2",
        "reminder-123"
    ));

    assertEquals("Reminder list was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void getReminderByIdsThrowsErrorIfReminderIsNotFound() {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\":{\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.getReminderByIds(
        "reminder-list-1",
        "reminder-404"
    ));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void createNewList() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, uuid);

    var returnedReminderList = reminderIO.createNewList();

    var expectedPath = "./mock/data/reminders/" + uuid.getDefaultUUID() + ".json";
    assertEquals(expectedPath, fileIO.getLastWritePath().toString());

    var expectedWrite = "{" +
        "\"id\":\"" + uuid.getDefaultUUID() + "\"," +
        "\"items\":{}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals(uuid.getDefaultUUID(), returnedReminderList.get("id").getAsString());

    var items = returnedReminderList.getAsJsonObject("items");
    assertEquals(0, items.size());
  }

  @Test
  void addReminder() throws IOException, HTTPError {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, uuid);

    var reminder = reminderIO.addReminder("reminder-list-1", "Do this task!");

    var expectedPath = "./mock/data/reminders/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"" + uuid.getDefaultUUID() + "\":" +
        "{" +
        "\"id\":\"" + uuid.getDefaultUUID() + "\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals(uuid.getDefaultUUID(), reminder.get("id").getAsString());
    assertEquals("Do this task!", reminder.get("task").getAsString());
  }

  @Test
  void addReminderThrowsErrorIfListIsNotFound() {
    var fileIO = new FileIOMock((String) null);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.addReminder(
        "reminder-list-2",
        "do this"
    ));

    assertEquals("Reminder list was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void updateReminder() throws IOException, HTTPError {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, uuid);

    var reminder = reminderIO.updateReminder("reminder-list-1", "reminder-1", "Updated task!");

    var expectedPath = "./mock/data/reminders/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Updated task!\"" +
        "}" +
        "}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());

    assertEquals("reminder-1", reminder.get("id").getAsString());
    assertEquals("Updated task!", reminder.get("task").getAsString());
  }

  @Test
  void updateReminderThrowsErrorIfListIsNotFound() {
    var fileIO = new FileIOMock((String) null);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.updateReminder(
        "reminder-list-2",
        "reminder-404",
        "updateTask"
    ));

    assertEquals("Reminder list was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void updateReminderThrowsErrorIfReminderIsNotFound() {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\":{\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.updateReminder(
        "reminder-list-1",
        "reminder-404",
        "updated task"
    ));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void deleteReminder() throws IOException, HTTPError {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{" +
        "\"reminder-1\":" +
        "{" +
        "\"id\":\"reminder-1\"," +
        "\"task\":\"Do this task!\"" +
        "}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var uuid = new UUIDMock();
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, uuid);

    reminderIO.deleteReminder("reminder-list-1", "reminder-1");

    var expectedPath = "./mock/data/reminders/reminder-list-1.json";
    assertEquals(expectedPath, fileIO.getLastReadPath().toString());

    var expectedWrite = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\":{}" +
        "}";
    assertEquals(expectedWrite, fileIO.getLastWrite());
  }

  @Test
  void deleteReminderThrowsErrorIfListIsNotFound() {
    var fileIO = new FileIOMock((String) null);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.deleteReminder(
        "reminder-list-2",
        "reminder-404"
    ));

    assertEquals("Reminder list was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void deleteReminderThrowsErrorIfReminderIsNotFound() {
    var mockJson = "{" +
        "\"id\":\"reminder-list-1\"," +
        "\"items\": {" +
        "\"reminder-123\":{\"id\": \"reminder-123\", \"task\": \"task content!!\"}" +
        "}" +
        "}";

    var fileIO = new FileIOMock(mockJson);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");
    var jsonConverter = new JsonConverter(new Gson());
    var reminderIO = new ReminderIO(dataDirectoryIO, jsonConverter, new UUID());

    HTTPError exception = assertThrows(HTTPError.class, () -> reminderIO.deleteReminder(
        "reminder-list-1",
        "reminder-404"
    ));

    assertEquals("Reminder was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}
