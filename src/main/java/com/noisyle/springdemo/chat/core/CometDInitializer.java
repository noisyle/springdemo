package com.noisyle.springdemo.chat.core;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.cometd.annotation.ServerAnnotationProcessor;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.transport.JSONPTransport;
import org.cometd.server.transport.JSONTransport;
import org.cometd.websocket.server.WebSocketTransport;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
/*     */

@Component
public class CometDInitializer implements ServletContextAware {
	private ServletContext servletContext;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public BayeuxServer bayeuxServer() {
		BayeuxServerImpl bean = new BayeuxServerImpl();
		bean.setTransports(new WebSocketTransport(bean), new JSONTransport(bean), new JSONPTransport(bean));
		servletContext.setAttribute(BayeuxServer.ATTRIBUTE, bean);
		bean.setOption(ServletContext.class.getName(), servletContext);
		bean.setOption("ws.cometdURLMapping", "/cometd/*");
		return bean;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Component
	public static class Processor implements DestructionAwareBeanPostProcessor {
		@Inject
		private BayeuxServer bayeuxServer;
		private ServerAnnotationProcessor processor;

		@PostConstruct
		private void init() {
			this.processor = new ServerAnnotationProcessor(bayeuxServer);
		}

		@PreDestroy
		private void destroy() {
		}

		public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
			processor.processDependencies(bean);
			processor.processConfigurations(bean);
			processor.processCallbacks(bean);
			return bean;
		}

		public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
			return bean;
		}

		public void postProcessBeforeDestruction(Object bean, String name) throws BeansException {
			processor.deprocessCallbacks(bean);
		}
	}
}
