package com.anders.dp.结构模式.享元.simple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FlyweightFactory
{
	private HashMap<Character, Flyweight> flies = new HashMap<Character, Flyweight>();

	// private Flyweight lnkFlyweight;

	public FlyweightFactory()
	{
	}

	public Flyweight factory(Character state)
	{
		if (flies.containsKey(state))
		{
			return flies.get(state);
		}
		else
		{
			Flyweight fly = new ConcreteFlyweight(state);
			flies.put(state, fly);
			return fly;
		}
	}

	public void checkFlyweight()
	{
		// Flyweight fly;
		int i = 0;
		for (Iterator<Map.Entry<Character, Flyweight>> it = flies.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<Character, Flyweight> entry = it.next();
			System.out.println("Item" + ++i + " : " + entry.getKey());
		}
	}
}
