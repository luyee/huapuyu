package com.anders.ethan.rpc.common;

import org.apache.commons.lang3.StringUtils;

public final class ZKUtils implements Constants {

	private ZKUtils() {
	}

	/**
	 * 组装providers ZK全路径，例如：/rpc/com.andes.ethan.service.UserService/providers
	 * 
	 * @param serviceName
	 *            服务名，例如： com.andes.ethan.service.UserService
	 */
	public static String assembleProvidersFullPath(String serviceName) {
		return String.format("%s/%s/providers", ZK_ROOT_PATH, serviceName);
	}

	/**
	 * 组装服务ZK全路径，例如：/rpc/com.andes.ethan.service.UserService
	 * 
	 * @param serviceName
	 *            服务名，例如： com.andes.ethan.service.UserService
	 */
	public static String assembleServiceFullPath(String serviceName) {
		return String.format("%s/%s", ZK_ROOT_PATH, serviceName);
	}

	/**
	 * 从providers ZK全路径中提取服务ZK路径，例如：/rpc/com.andes.ethan.service.UserService
	 * 
	 * @param providersFullPath
	 *            providers ZK全路径，例如：/rpc/com.andes.ethan.service.UserService/
	 *            providers
	 */
	public static String extractServicePath(String providersFullPath) {
		return StringUtils.left(providersFullPath,
				providersFullPath.lastIndexOf(ZK_PROVIDERS_PATH));
	}

	/**
	 * 从服务提供者ZK全路径中提取服务提供者名，例如：127.0.0.1:1122（后期需要修改）
	 * 
	 * @param providerFullPath
	 *            服务提供者ZK全路径，例如：/rpc/com.andes.ethan.service.UserService/
	 *            providers/127.0.0.1:1122
	 */
	public static String extractProviderName(String providerFullPath) {
		String temp = StringUtils.substring(providerFullPath,
				providerFullPath.lastIndexOf(ZK_PROVIDERS_PATH));
		return StringUtils.substring(temp, temp.lastIndexOf("/") + 1);
	}
}
