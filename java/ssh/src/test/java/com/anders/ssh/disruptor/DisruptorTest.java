package com.anders.ssh.disruptor;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.util.NamedThreadFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException, InterruptedException {
		EventFactory<LongEvent> eventFactory = new LongEventFactory();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

		// Disruptor<LongEvent> disruptor = new
		// Disruptor<LongEvent>(eventFactory,
		// ringBufferSize, executor, ProducerType.SINGLE,
		// new BlockingWaitStrategy());

		ConcurrentHashMap<Long, Disruptor<LongEvent>> map = new ConcurrentHashMap<Long, Disruptor<LongEvent>>();

		for (long i = 0; i < 20; i++) {
			Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
					eventFactory, 1024, new NamedThreadFactory("writer" + i),
					ProducerType.MULTI, new TimeoutBlockingWaitStrategy(5000,
							TimeUnit.MILLISECONDS));
			EventHandler<LongEvent> eventHandler = new LongEventHandler();
			disruptor.handleEventsWith(eventHandler);
			disruptor.start();
			map.put(new Long(i), disruptor);
		}

		for (long i = 0; i < 10000000; i++) {
//			Thread.sleep(100);
			System.out.println(i % 20);
			Disruptor<LongEvent> disruptor = map.get(new Long(i % 20));
			RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
			long sequence = ringBuffer.next();// 请求下一个事件序号；

			try {
				LongEvent event = ringBuffer.get(sequence);// 获取该序号对应的事件对象；
				long data = i;// 获取要通过事件传递的业务数据；
				event.set(data);
			} finally {
				ringBuffer.publish(sequence);// 发布事件；
			}
		}

		System.in.read();
	}

}