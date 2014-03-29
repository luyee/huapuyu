package com.anders.dp.结构模式.享元.composite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConcreteCompositeFlyweight extends Flyweight
{
	private HashMap<Character, Flyweight> flies = new HashMap<Character, Flyweight>(10);

	public void add(Character key, Flyweight fly)
	{
		flies.put(key, fly);
	}

	@Override
	public void operation(String state)
	{
		Flyweight fly;
		for (Iterator<Map.Entry<Character, Flyweight>> it = flies.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<Character, Flyweight> entry = it.next();
			fly = entry.getValue();
			fly.operation(state);
		}
	}
}
