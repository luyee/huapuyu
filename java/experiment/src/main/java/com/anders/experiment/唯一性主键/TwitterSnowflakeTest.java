package com.anders.experiment.唯一性主键;

/**
 * 在分布式系统中，需要生成全局UID的场合还是比较多的，twitter的snowflake解决了这种需求，实现也还是很简单的，除去配置信息，核心代码就是毫秒级时间41位+机器ID
 * 10位+毫秒内序列12位。
 * 
 * 该项目地址为：https://github.com/twitter/snowflake是用Scala实现的。
 * 
 * python版详见开源项目https://github.com/erans/pysnowflake。
 * 
 * 核心代码为其IdWorker这个类实现，其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用：
 * 
 * 0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---0000000000 00
 * 在上面的字符串中，第一位为未使用
 * （实际上也可作为long的符号位），接下来的41位为毫秒级时间，然后5位datacenter标识位，5位机器ID（并不算标识符，实际是为线程标识），然后12位该毫秒内的当前毫秒内的计数
 * ，加起来刚好64位，为一个Long型。
 * 
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），并且效率较高，经测试，snowflake每秒能够产生26万ID左右，
 * 完全满足需要。
 * 
 * java中-1的二进制为32（int）或64（long）个1
 * 
 * @author Anders
 * 
 */
public class TwitterSnowflakeTest {
	private final long workerId;
	private final long datacenterId;
	private long sequence = 0L;

	// *********************************1395932495355
	private final static long twepoch = 1288834974657L;
	private final static long workerIdBits = 5L;
	private final static long datacenterIdBits = 5L;
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);// 31
	private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);// 31
	private final static long sequenceBits = 12L;
	private final static long workerIdShift = sequenceBits;// 12
	private final static long datacenterIdShift = sequenceBits + workerIdBits;// 17
	private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;// 22
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	// ********-1******** : 111111111111111111111111111111111111111111-11111-11111-111111111111
	// timestampLeftShift : 111111111111111111111111111111111111111111-00000-00000-000000000000
	// -datacenterIdShift : 111111111111111111111111111111111111111111-11111-00000-000000000000
	// ------workerIdBits : 111111111111111111111111111111111111111111-11111-11111-000000000000

	// -1 << sequenceBits : 111111111111111111111111111111111111111111-11111-11111-000000000000
	// ------sequenceMask : 000000000000000000000000000000000000000000-00000-00000-111111111111

	private long lastTimestamp = -1L;

	public TwitterSnowflakeTest(final long workerId, final long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}

		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}

		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		if (timestamp == lastTimestamp) {
			sequence = (sequence + 1) & sequenceMask;
			// 最后12位如果都为1了，说明计数器满了，则等到下一秒
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		else {
			sequence = 0;
		}

		lastTimestamp = timestamp;
		long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {

		System.out.println(Long.MAX_VALUE);
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis() - 1288834974657L);

		TwitterSnowflakeTest idWorker = new TwitterSnowflakeTest(31, 31);
		System.out.println(idWorker.nextId());
		System.out.println(idWorker.nextId());
		System.out.println(idWorker.nextId());

		// 9223372036854775807
		// 1395939577953
		// 107104603296 : 1100011101111111011101000110010100000
		// 449229266027016192 : 1100011101111111011101000110010100000 11111 11111 000000000000
		// 449229266027016193 : 1100011101111111011101000110010100000 11111 11111 000000000001
		// 449229266027016194 : 1100011101111111011101000110010100000 11111 11111 000000000002

	}

}
