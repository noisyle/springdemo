package com.noisyle.crowbar.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaemonTask {

	private static Logger logger = LoggerFactory.getLogger(DaemonTask.class);
	
	public void run() {
		logger.debug("守护线程启动");

        logger.debug("守护线程结束");
	}
}
