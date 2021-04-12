package dev.hornetshell.bigdemoangular.controllers;

import static java.util.concurrent.TimeUnit.SECONDS;

import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import dev.hornetshell.bigdemoangular.services.SmartDataService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("stomp")
@EnableAutoConfiguration(
    exclude = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
    })
class SmartDataStompControllerTest {

  @MockBean private SmartDataService smartDataService;

  private WebSocketStompClient stompClient;

  private StompSession stompSession;

  private CompletableFuture<SmartData> smartDataCompletableFuture;

  @BeforeEach
  void setup() throws ExecutionException, InterruptedException, TimeoutException {
    smartDataCompletableFuture = new CompletableFuture<>();
    stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    stompSession =
        stompClient
            .connect("ws://localhost:8080/websocket", new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);
  }

  @AfterEach
  void tearDown() {
    stompSession.disconnect();
    stompClient.stop();
  }

  @Test
  void verifyCanGetEvent() throws ExecutionException, InterruptedException {
    Mockito.when(smartDataService.getRecentSmartData()).thenReturn(Optional.of(getSmartData()));
    stompSession.subscribe(
        "/topic/all",
        new StompFrameHandler() {
          @Override
          public Type getPayloadType(StompHeaders stompHeaders) {
            return SmartData.class;
          }

          @Override
          public void handleFrame(StompHeaders stompHeaders, Object o) {
            smartDataCompletableFuture.complete((SmartData) o);
          }
        });

    stompSession.send("/app/all", null);

    final SmartData result = smartDataCompletableFuture.get();
    Assertions.assertEquals(getSmartData(), result);
  }

  private List<Transport> createTransportClient() {
    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    return transports;
  }

  private SmartData getSmartData() {
    final SmartData smartData = new SmartData();
    smartData.setSerialNumber("testserial");
    smartData.setModel("testmodel");
    smartData.setCapacityBytes(100L);

    return smartData;
  }
}
