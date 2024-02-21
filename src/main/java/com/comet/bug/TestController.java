package com.comet.bug;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class TestController {
  @GetMapping(path = "hello")
  public String hello() {
    return "hello from spring";
  }
  @GetMapping
  public ResponseEntity<byte[]> index() {
    LinkedMultiValueMap<String, String> multivalueMap = new LinkedMultiValueMap<>();
    multivalueMap.add("Connection", "close");
    multivalueMap.add("Transfer-Encoding", "chunked");
    multivalueMap.add("Content-Type", "application/json");
    String testStr = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce cursus lacus ut dui rutrum, in dolor.\n";

    try (InputStream inputStream = new ByteArrayInputStream(testStr.getBytes())) {
      return new ResponseEntity<>(inputStream.readAllBytes(), multivalueMap, HttpStatusCode.valueOf(200));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(HttpStatusCode.valueOf(500));
  }
}
