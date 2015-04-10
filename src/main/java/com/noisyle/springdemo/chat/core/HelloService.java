package com.noisyle.springdemo.chat.core;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;

@Named
@Singleton
@Service("helloService")
public class HelloService {
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;

	@PostConstruct
	public void init() {
	}

	@Listener("/service/hello")
	public void processHello(ServerSession remote, ServerMessage message) {
		Map<String, Object> input = message.getDataAsMap();
		String name = (String) input.get("name");

		Map<String, Object> output = new HashMap<>();
		output.put("greeting", "你好，" + name);
		remote.deliver(serverSession, "/hello", output);
	}
}
