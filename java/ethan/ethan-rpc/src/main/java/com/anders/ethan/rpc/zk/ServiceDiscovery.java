package com.anders.ethan.rpc.zk;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.Assert;

import com.anders.ethan.rpc.common.Constants;
import com.anders.ethan.rpc.common.Utils;

/**
 * 服务发现<br/>
 * <br/>
 * 名词解释<br/>
 * FullPath：完整的ZK路径，例如：/rpc/com.andes.ethan.service.UserService/providers <br/>
 * Path：相对的ZK路径，例如：/com.andes.ethan.service.UserService或者/providers <br/>
 * Name：名称，例如：com.andes.ethan.service.UserService或者providers <br/>
 * 
 * @author Anders
 * 
 */
public class ServiceDiscovery implements Constants {

	private CuratorFramework client;
	/**
	 * key : /rpc/com.andes.ethan.service.UserService, value :
	 * 127.0.0.1:1111（节点名后期需要修改）
	 */
	private Map<String, Set<String>> providersMap = new ConcurrentHashMap<String, Set<String>>();

	public ServiceDiscovery(String registryAddress) throws Exception {
		client = CuratorFrameworkFactory.builder()
				.connectString(registryAddress)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.sessionTimeoutMs(5000).build();

		client.start();

		String serviceName = null;
		// 获取/rpc节点下的子节点名列表，如：com.andes.ethan.service.UserService
		Iterator<String> it = client.getChildren().forPath(ZK_ROOT_PATH)
				.iterator();
		while (it.hasNext()) {
			serviceName = it.next();
			if (StringUtils.isBlank(serviceName)) {
				continue;
			}
			System.out.println(serviceName);

			Set<String> providersSet = new CopyOnWriteArraySet<String>();
			String providerName = null;

			// 获取/rpc/***.***Service/providers节点下的子节点名列表，如：127.0.0.1:1111（节点名后期需要修改）
			Iterator<String> providersIt = client.getChildren()
					.forPath(Utils.assembleProvidersFullPath(serviceName))
					.iterator();
			while (providersIt.hasNext()) {
				providerName = providersIt.next();
				if (StringUtils.isBlank(providerName)) {
					continue;
				}
				providersSet.add(providerName);
			}

			providersMap.put(Utils.assembleServiceFullPath(serviceName),
					providersSet);
			System.out.println(Utils.assembleServiceFullPath(serviceName));

			registryWatcher(Utils.assembleProvidersFullPath(serviceName));
		}
		System.out.println("**************");

		// TODO Anders 检查是否存在，判断路径不存在是否有问题
		// client.checkExists().creatingParentContainersIfNeeded().

		// registryWatcher(ROOT_PATH);

	}

	private void registryWatcher(String path) throws Exception {
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable()
				.addListener(new ClientPathChildrenCacheListener());
		// cache.close();
	}

	private class ClientPathChildrenCacheListener implements
			PathChildrenCacheListener {

		@Override
		public void childEvent(CuratorFramework client,
				PathChildrenCacheEvent event) throws Exception {

			if (event.getData() == null || event.getType() == null) {
				return;
			}

			String providerFullPath = null;
			String serviceName = null;
			String providerName = null;
			switch (event.getType()) {
			case INITIALIZED:
				providerFullPath = event.getData().getPath();
				if (StringUtils.isNotBlank(providerFullPath)) {
					serviceName = Utils.extractServicePath(providerFullPath);
					providerName = Utils.extractProviderName(providerFullPath);

					System.out.println("ini\t" + serviceName);

					Set<String> providersSet = providersMap.get(serviceName);
					if (!providersSet.contains(providerName)) {
						providersSet.add(providerName);
					}
				}
				break;
			case CHILD_ADDED:
				providerFullPath = event.getData().getPath();
				if (StringUtils.isNotBlank(providerFullPath)) {
					serviceName = Utils.extractServicePath(providerFullPath);
					providerName = Utils.extractProviderName(providerFullPath);

					System.out.println("add\t" + serviceName);

					Set<String> providersSet = providersMap.get(serviceName);
					if (!providersSet.contains(providerName)) {
						providersSet.add(providerName);
					}
				}
				break;
			case CHILD_UPDATED:
				throw new UnsupportedOperationException();
			case CHILD_REMOVED:
				providerFullPath = event.getData().getPath();
				if (StringUtils.isNotBlank(providerFullPath)) {
					serviceName = Utils.extractServicePath(providerFullPath);
					providerName = Utils.extractProviderName(providerFullPath);

					System.out.println("remove\t" + serviceName);

					Set<String> providersSet = providersMap.get(serviceName);
					if (providersSet.contains(providerName)) {
						providersSet.remove(providerName);
					}
				}
				break;
			default:
				break;
			}
			System.out.println(providersMap);
		}
	}

	// TODO Anders 实际上需要根据方法名，方法参数以及版本号查找对应的配置信息
	public String discover(String serviceName) {
		Assert.notEmpty(providersMap);

		Set<String> providersSet = providersMap.get(serviceName);
		Assert.notEmpty(providersSet);

		// TODO Anders 添加路由策略
		return providersSet.toArray(new String[providersSet.size()])[0];
	}

	public static void main(String[] args) throws Exception {
		new ServiceDiscovery("127.0.0.1:2181");
	}
}
