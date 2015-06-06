package com.noisyle.springdemo.tanks.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

@Named
@Singleton
@Service("tanksServer")
public class TanksServer {
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	
	private static List<Map<String, Object>> clientList = new LinkedList<Map<String, Object>>();
	private static Map<String, Map<String, Object>> clientMap = new HashMap<String, Map<String, Object>>();
	
	@PostConstruct
	public void init() {
	}

	@Listener("/service/tanks")
	public void processTanks(ServerSession remote, ServerMessage message) {
		Map<String, Object> input = message.getDataAsMap();
		String type = (String) input.get("type");
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("id", remote.getId());
		output.put("type", type);
		
		ServerChannel tanksChannel = bayeux.getChannel("/tanks");
		if("new".equals(type)){
			output.put("x", input.get("x"));
			output.put("y", input.get("y"));
			if(tanksChannel!=null){
				tanksChannel.publish(remote, output);
			}
			
			remote.deliver(serverSession, "/tanks/echo", clientList);
			clientList.add(output);
			clientMap.put(remote.getId(), output);
		}else if("move".equals(type)){
			output.put("x", input.get("x"));
			output.put("y", input.get("y"));
			output.put("rotation", input.get("rotation"));
			output.put("turret_rotation", input.get("turret_rotation"));
			if(tanksChannel!=null){
				tanksChannel.publish(remote, output);
			}
			Map<String, Object> client = clientMap.get(remote.getId());
			client.put("x", output.get("x"));
			client.put("y", output.get("y"));
			System.out.println("player "+remote.getId()+" move to "+input.get("x")+":"+input.get("y"));
		}else if("remove".equals(type)){
			for(Iterator<Map<String, Object>> i = clientList.iterator();i.hasNext();){
				Map<String, Object> client = i.next();
				if(client.get("id").equals(remote.getId())){
					i.remove();
				}
			}
			if(tanksChannel!=null){
				tanksChannel.publish(remote, output);
			}
		}

	}
	
}
