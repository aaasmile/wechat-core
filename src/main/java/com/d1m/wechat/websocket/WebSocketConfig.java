package com.d1m.wechat.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @desc：Websocket请求处理回调方法
 */
//@Deprecated
@Configuration
//@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHander(), "/websocket").addInterceptors(new Handshake());
		registry.addHandler(webSocketHander(), "/socketjs/websocket").addInterceptors(new Handshake()).withSockJS();
	}

	@Bean
	public WebSocketHandler webSocketHander() {
		return new WebSocketEndPoint();
	}
}
