package dev.hornetshell.bigdemoangular.controllers;

import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import dev.hornetshell.bigdemoangular.services.SmartDataService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/** Controller for handling STOMP traffic. */
@Profile("stomp")
@Controller
public class SmartDataStompController {

  private final SmartDataService smartDataService;

  @Autowired
  public SmartDataStompController(SmartDataService smartDataService) {
    this.smartDataService = smartDataService;
  }

  @MessageMapping("/all")
  @SendTo("/topic/all")
  public SmartData getRecentData() throws Exception {
    // This is a busy wait loop and should be avoided, but in this case
    // This is our best option for not overloading the DB
    // which is running on my pc without implementing LISTEN/NOTIFY
    // since spring doesn't have hibernate hooks for it
    while (true) {
      final Optional<SmartData> smartDataOptional = smartDataService.getRecentSmartData();
      if (smartDataOptional.isPresent()) {
        return smartDataOptional.get();
      } else {
        Thread.sleep(1000L);
      }
    }
  }
}
