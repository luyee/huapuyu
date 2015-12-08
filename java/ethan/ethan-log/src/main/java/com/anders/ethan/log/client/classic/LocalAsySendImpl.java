/**
 *
 */
package com.anders.ethan.log.client.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.log.client.common.JsonUtils;
import com.anders.ethan.log.client.common.LogSpan;
import com.anders.ethan.log.common.Span;

public class LocalAsySendImpl {

    private static Logger logger = LoggerFactory.getLogger(LocalAsySendImpl.class);

    private static Logger traceLogger = LoggerFactory.getLogger("mercury-trace-logger");

    private static Logger logLogger = LoggerFactory.getLogger("mercury-log-logger");

    private static Logger metricLogger = LoggerFactory.getLogger("mercury-metric-logger");

    private static final int LOG_MAX_SPLIT_NUMBER = 5;

    private static final int TRACE_MAX_SPLIT_NUMBER = 10;

    private static final int METRIC_MAX_SPLIT_NUMBER = 10;

    private ArrayBlockingQueue<Span> queue;

    private ArrayBlockingQueue<LogSpan> logQueue;

//    private ArrayBlockingQueue<MetricSpan> metricQueue;

    private ScheduledExecutorService executors = null;

    private List<Span> spansCache;

    private List<LogSpan> logsCache;

//    private List<MetricSpan> metricsCache;

    private volatile boolean isReady = false;

    private Long flushSize;

    private Long waitTime;

    private TransferTask task;

    private TransferTask4Log task4Log;

//    private TransferTask4Mertric task4Mertric;


    public LocalAsySendImpl() {
        this.flushSize = 1024L;
        this.waitTime = 60000L;
        this.queue = new ArrayBlockingQueue<Span>(2048);
        this.spansCache = new ArrayList<Span>();

        this.logQueue = new ArrayBlockingQueue<LogSpan>(2048);
        this.logsCache = new ArrayList<LogSpan>();

//        this.metricQueue = new ArrayBlockingQueue<MetricSpan>(1);
//        this.metricsCache = new ArrayList<MetricSpan>();

        this.executors = Executors.newSingleThreadScheduledExecutor();
        this.task = new TransferTask();
        this.task4Log = new TransferTask4Log();
//        this.task4Mertric = new TransferTask4Mertric();
    }

    private class TransferTask4Log extends Thread{
        TransferTask4Log() {
            this.setName("TransferTask4Log-agent-Thread");
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    if (!isReady()) {
                        isReady = true;
                    } else {
                        while (!task.isInterrupted()) {
                            LogSpan logSpan = logQueue.take();
                            logsCache.add(logSpan);
                            logQueue.drainTo(logsCache);
                            this.conSendLogs(logsCache);
                            logsCache.clear();
                        }
                    }

                } catch (Throwable e) {
                    logger.error(e.getMessage());
                }
            }
        }
        private void conSendLogs(List<LogSpan> logsCache) {
            logLogger.info(JsonUtils.toJson(logsCache));
//            writeLog(logsCache, LOG_MAX_SPLIT_NUMBER, logLogger);
        }

    }

//    private void writeLog1(List logsCache, int sendLimit, Logger logger) {
//        if (logsCache.size() <= sendLimit){
//            logger.info(JsonUtils.toJson(logsCache));
//        }else {
//            //Span list太大发送到kafka会报错
//            int sendCount = logsCache.size()/sendLimit + 1;
//            for (int i=0;i<sendCount;i++){
//                int fromIndex = i * sendLimit;
//                int endIndex = (i+1) * sendLimit >= logsCache.size()?logsCache.size():(i+1) * sendLimit;
//                logger.info(JsonUtils.toJson(logsCache.subList(fromIndex, endIndex)));
//            }
//        }
//    }

//    private class TransferTask4Mertric extends Thread{
//        TransferTask4Mertric() {
//            this.setName("TransferTask4Mertric-agent-Thread");
//        }
//
//        @Override
//        public void run() {
//            for (;;) {
//                try {
//                    if (!isReady()) {
//                        isReady = true;
//                    } else {
//                        while (!task.isInterrupted()) {
//                            MetricSpan firstMetric = metricQueue.take();
//                            metricsCache.add(firstMetric);
//                            metricQueue.drainTo(metricsCache);
//                            this.conSends(metricsCache);
//                            metricsCache.clear();
//                        }
//                    }
//
//                } catch (Throwable e) {
//                    logger.error(e.getMessage());
//                }
//            }
//        }
//        private void conSends(List<MetricSpan> spansCache) {
//            metricLogger.info(JsonUtils.toJson(spansCache));
////            writeLog(spansCache, METRIC_MAX_SPLIT_NUMBER, metricLogger);
//        }
//    }

    private class TransferTask extends Thread {
        TransferTask() {
            this.setName("TransferTask-client-Thread");
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    if (!isReady()) {
                        isReady = true;
                    } else {
                        while (!task.isInterrupted()) {
                            Span first = queue.take();
                            spansCache.add(first);
                            queue.drainTo(spansCache);
                            this.conSends(spansCache);
                            spansCache.clear();
                        }
                    }
                } catch (Throwable e) {
                    logger.info("TransferTask",e);
                }
            }
        }
        private void conSends(List<Span> spansCache) {
            traceLogger.info(JsonUtils.toJson(spansCache));
//            writeLog(spansCache, TRACE_MAX_SPLIT_NUMBER, traceLogger);
        }

    }


    public boolean isReady() {
        return isReady;
    }


    public void start() {

        if (!task.isAlive()) {
            //task.setDaemon(true);
            task.setName("write trace log-" + task.getName());
            task.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    cancel();
                }
            });
        }
        if (!task4Log.isAlive()) {
            //task4Log.setDaemon(true);
            task4Log.setName("write log-" + task4Log.getName());
            task4Log.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    task4Log.interrupt();
                }
            });
        }
//        if (!task4Mertric.isAlive()) {
//            //task4Mertric.setDaemon(true);
//            task4Mertric.setName("write metric log-" + task4Mertric.getName());
//            task4Mertric.start();
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                public void run() {
//                    task4Mertric.interrupt();
//                }
//            });
//        }
    }

    public void cancel() {
        task.interrupt();
    }


    public void asySend(Span span) {
        try {
            queue.add(span);
            //logger.info("asy send span:"+span);
        } catch (Exception e) {
            logger.info("span : ignore ..");
        }
    }


    public void asySends(List<Span> spans) {
        try {
            queue.addAll(spans);
        } catch (Exception e) {
            logger.info("span list : ignore ..");
        }

    }
    public void asySendLogSpan(LogSpan logSpan) {
        try {
            logQueue.add(logSpan);
            //logger.info("asy send logspan:"+logSpan);
        } catch (Exception e) {
            logger.info("logSpan : ignore ..",e);
        }
    }

    public void asySendLogSpans(List<LogSpan> logSpans) {
        try {
            logQueue.addAll(logSpans);
        } catch (Exception e) {
            logger.info("logSpan list : ignore ..",e);
        }

    }

//    public void asySendMetricSpan(MetricSpan metricSpan) {
//        try {
//            metricQueue.add(metricSpan);
//        } catch (Exception e) {
//            logger.info("metricSpan : ignore ..",e);
//        }
//    }


}
