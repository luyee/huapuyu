package com.anders.ssh.dao.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisArrayList<E> implements List<E>, Serializable {

	private static final long serialVersionUID = -2549916091733064036L;

	private RedisTemplate<String, E> redisTemplate;

	private String key;

	@Override
	public int size() {
		Long value = redisTemplate.opsForList().size(key);
		if (value == null) {
			return 0;
		}

		return value.intValue();
	}

	@Override
	public boolean isEmpty() {
		Long value = redisTemplate.opsForList().size(key);
		if (value == null) {
			return true;
		}

		return value.intValue() == 0;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public Iterator<E> iterator() {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			// TODO Anders 看是否要优化
			return CollectionUtils.EMPTY_COLLECTION.iterator();
		}

		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			// TODO Anders 看是否要优化
			return ArrayUtils.EMPTY_OBJECT_ARRAY;
		}
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			// TODO Anders 看是否要优化
			return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
		}
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return redisTemplate.opsForList().rightPush(key, e) > 0;
	}

	@Override
	public boolean remove(Object o) {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}

		if (o == null) {
			for (int i = 0; i < list.size(); i++)
				if (list.get(i) == null) {
					// TODO Anders 改进
					return redisTemplate.opsForList().remove(key, 1, o) > 0;
				}
		}
		else {
			for (int i = 0; i < list.size(); i++)
				if (o.equals(list.get(i)))
					return redisTemplate.opsForList().remove(key, 1, o) > 0;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return redisTemplate.opsForList().rightPushAll(key, (E[]) c.toArray()) > 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(int index, E element) {
		E oldValue = redisTemplate.opsForList().index(key, index);
		redisTemplate.opsForList().set(key, index, element);
		return oldValue;
	}

	@Override
	public void add(int index, E element) {
		// redisTemplate.opsForList().

	}

	@Override
	public E remove(int index) {
		// redisTemplate.opsForList().r
		return null;
	}

	@Override
	public int indexOf(Object o) {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			return -1;
		}

		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			return -1;
		}

		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		List<E> list = redisTemplate.opsForList().range(key, 0, -1);
		if (CollectionUtils.isEmpty(list)) {
			// TODO Anders 优化
			return new ArrayList<E>(0).listIterator();
		}

		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		List<E> list = redisTemplate.opsForList().range(key, index, -1);
		if (CollectionUtils.isEmpty(list)) {
			// TODO Anders 优化
			return new ArrayList<E>(0).listIterator();
		}

		return list.listIterator();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return redisTemplate.opsForList().range(key, fromIndex, toIndex);
	}

}
