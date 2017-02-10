package com.anders.experiment.cache.openhtf;

import net.openhft.lang.collection.HugeArray;
import net.openhft.lang.collection.HugeCollections;
import net.openhft.lang.collection.HugeQueue;
import net.openhft.lang.model.constraints.MaxSize;

//-XX:MaxDirectMemorySize=4g
public class OpenHTFTest {
	public static void main(String[] args) throws InterruptedException {

		Thread.sleep(10000);
		
		// can create an array of any size (provided you have the memory) off heap.
		HugeArray<JavaBeanInterface> array = HugeCollections.newArray(JavaBeanInterface.class, 10 * 1000 *10* 1000L);
		
		Thread.sleep(60000);
		
		JavaBeanInterface dt = array.get(1111111111);

		// set data on dt
		array.recycle(dt); // recycle the reference (or discard it)

		// create a ring writeBuffer
		HugeQueue<JavaBeanInterface> queue = HugeCollections.newQueue(JavaBeanInterface.class, 10 * 1000 * 1000L);
		// give me a reference to an object to populate
		JavaBeanInterface dt2 = queue.offer();
		// set the values od dt2
		queue.recycle(dt2);

		JavaBeanInterface dt3 = queue.take();
		// get values
		queue.recycle(dt3);
	}
}

//class DataType {
//	private Long id;
//	private String name;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//}

 interface JavaBeanInterface {
    void busyLockRecord() throws InterruptedException;

    boolean tryLockRecord();

    void unlockRecord();

    void setFlag(boolean flag);

    boolean getFlag();

    void setByte(byte b);

    byte getByte();

    void setShort(short s);

    short getShort();

    void setChar(char ch);

    char getChar();

    void setInt(int i);

    int getVolatileInt();

    void setOrderedInt(int i);

    int getInt();

    void setFloat(float f);

    float getFloat();

    void setLong(long l);

    long getLong();

    long addAtomicLong(long toAdd);

    void setDouble(double d);

    double getDouble();

    double addAtomicDouble(double toAdd);

    void setString(@MaxSize(8) String s);

    String getString();

    StringBuilder getUsingString(StringBuilder b);
}
