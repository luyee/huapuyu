package com.anders.ssh.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.LoadEvent;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.def.DefaultLoadEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class MyListener extends DefaultLoadEventListener implements PostUpdateEventListener {

	private static final long serialVersionUID = 5288247666127315440L;

	@Override
	protected Object doLoad(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadType options) {
		System.out.println("MyListener doLoad");
		return super.doLoad(event, persister, keyToLoad, options);
	}

	@Override
	protected Object load(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadType options) {
		System.out.println("MyListener load");
		return super.load(event, persister, keyToLoad, options);
	}

	@Override
	protected Object loadFromDatasource(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadType options) {
		System.out.println("MyListener loadFromDatasource");
		return super.loadFromDatasource(event, persister, keyToLoad, options);
	}

	@Override
	protected Object loadFromSecondLevelCache(LoadEvent arg0, EntityPersister arg1, LoadType arg2) {
		System.out.println("MyListener loadFromSecondLevelCache");
		return super.loadFromSecondLevelCache(arg0, arg1, arg2);
	}

	@Override
	protected Object loadFromSessionCache(LoadEvent arg0, EntityKey arg1, LoadType arg2) throws HibernateException {
		System.out.println("MyListener loadFromSessionCache");
		return super.loadFromSessionCache(arg0, arg1, arg2);
	}

	@Override
	protected Object lockAndLoad(LoadEvent arg0, EntityPersister arg1, EntityKey arg2, LoadType arg3, SessionImplementor arg4) {
		System.out.println("MyListener lockAndLoad");
		return super.lockAndLoad(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void onLoad(LoadEvent arg0, LoadType arg1) throws HibernateException {
		System.out.println("MyListener onLoad");
		super.onLoad(arg0, arg1);
	}

	@Override
	protected Object proxyOrLoad(LoadEvent arg0, EntityPersister arg1, EntityKey arg2, LoadType arg3) {
		System.out.println("MyListener proxyOrLoad");
		return super.proxyOrLoad(arg0, arg1, arg2, arg3);
	}

	@Override
	public void onPostUpdate(PostUpdateEvent arg0) {
		System.out.println("MyListener onPostUpdate");
	}

}
