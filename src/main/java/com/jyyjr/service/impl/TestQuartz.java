package com.jyyjr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestQuartz {

    private static final Logger logger = LoggerFactory.getLogger(TestQuartz.class);

    /**
     * 定时任务
     */
    public void doJob(){

        logger.info("---------定时任务开始-------------");

        logger.info("---------正在执行定时任务！-------------");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("---------定时任务结束-------------");

    }
    
    /**
     * 定时任务2
     */
    public void doJob2() {
    	logger.info("---------定时任务2开始-------------");
	}
}
