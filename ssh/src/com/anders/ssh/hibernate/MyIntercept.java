package com.anders.ssh.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

public class MyIntercept extends EmptyInterceptor {

	private static final long serialVersionUID = 5582251051637357096L;

	@Override
	public void afterTransactionBegin(Transaction tx) {
		System.out.println("afterTransactionBegin");
		super.afterTransactionBegin(tx);
	}

	@Override
	public void afterTransactionCompletion(Transaction tx) {
		System.out.println("afterTransactionCompletion");
		super.afterTransactionCompletion(tx);
	}

	@Override
	public void beforeTransactionCompletion(Transaction tx) {
		System.out.println("beforeTransactionCompletion");
		super.beforeTransactionCompletion(tx);
	}

	@Override
	public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		System.out.println("findDirty");
		return super.findDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public Object getEntity(String entityName, Serializable id) {
		System.out.println("getEntity");
		return super.getEntity(entityName, id);
	}

	@Override
	public String getEntityName(Object object) {
		System.out.println("getEntityName");
		return super.getEntityName(object);
	}

	@Override
	public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {
		System.out.println("instantiate");
		return super.instantiate(entityName, entityMode, id);
	}

	@Override
	public Boolean isTransient(Object entity) {
		System.out.println("isTransient");
		return super.isTransient(entity);
	}

	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionRecreate");
		super.onCollectionRecreate(collection, key);
	}

	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionRemove");
		super.onCollectionRemove(collection, key);
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionUpdate");
		super.onCollectionUpdate(collection, key);
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		System.out.println("onDelete");
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		System.out.println("onFlushDirty");
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		System.out.println("onLoad");
		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@Override
	public String onPrepareStatement(String sql) {
		System.out.println("onPrepareStatement");
		return super.onPrepareStatement(sql);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		System.out.println("onSave");
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public void postFlush(Iterator entities) {
		System.out.println("postFlush");
		super.postFlush(entities);
	}

	@Override
	public void preFlush(Iterator entities) {
		System.out.println("preFlush");
		super.preFlush(entities);
	}

}
