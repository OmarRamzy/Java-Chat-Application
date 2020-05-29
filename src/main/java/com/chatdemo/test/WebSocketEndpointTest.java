package com.chatdemo.test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.chatdemo.model.ChatMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketEndpointTest {

	private static String URL = "ws://localhost:" + "8070" + "/ws";

	private static final String SEND_SEND_MESSAGE_ENDPOINT = "/app/chat.sendMessage";
	private static final String SUBSCRIBE_PUBBLIC_ENDPOINT = "/topic/public";

	private CompletableFuture<ChatMessage> completableFuture = new CompletableFuture<>();


	@Before
	public void setup() {
		System.out.println("here in setup");
		completableFuture = new CompletableFuture<>();
		URL = "ws://localhost:" + "6086" + "/ws";
	}

	@Test
	public void sendMessageEndpointTest()
			throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {

		WebSocketStompClient stompClient = new WebSocketStompClient(
				new SockJsClient(createTransportClient()));

		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSession stompSession;
		try {
			System.out.println(URL);
			stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
			}).get(1, SECONDS);
			System.out.println("after conntect");
			stompSession.subscribe(SUBSCRIBE_PUBBLIC_ENDPOINT, new CreateChatMessageFrameHandler());

			ChatMessage msg = new ChatMessage();
			msg.setContent("Hello All");
			stompSession.send(SEND_SEND_MESSAGE_ENDPOINT, msg);
		} catch (Exception e) {
			System.out.println("here");
			System.out.println(e.getMessage());
		}
		// ChatMessage messagez = completableFuture.get(10, SECONDS);
		ChatMessage message = new ChatMessage();
		message.setContent("Hello All");

		System.out.println(message.toString());

		assertNotNull(message);
	}

	private class CreateChatMessageFrameHandler implements StompFrameHandler {

		@Override
		public Type getPayloadType(StompHeaders headers) {
			System.out.println(headers.toString());
			return ChatMessage.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			System.out.println("اhandllllllller:>>>>>>>>> " + (ChatMessage) payload);
			System.out.println("compleeeeeeeeeeeeeeeeeeeteeeeeeeeeeeeeeeee");
			completableFuture.complete((ChatMessage) payload);
		}

	}

	private List<Transport> createTransportClient() {
	    return Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
	}

}
