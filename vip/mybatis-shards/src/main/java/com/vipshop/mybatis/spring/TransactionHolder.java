package com.vipshop.mybatis.spring;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.transaction.TransactionStatus;

/**
 * 事务信息、数据源、事务状态Holder
 * 
 * @author Anders
 * 
 */
public class TransactionHolder {
	private static ThreadLocal<Map<DataSource, LinkedList<TransactionInfoWrap>>> DS2TREE_HOLDER = new ThreadLocal<Map<DataSource, LinkedList<TransactionInfoWrap>>>();
	private static ThreadLocal<DataSource> DS_HOLDER = new ThreadLocal<DataSource>();
	private static ThreadLocal<TransactionInfoWrap> INFO_HOLDER = new ThreadLocal<TransactionInfoWrap>();
	private static ThreadLocal<Map<TransactionStatus, DataSource>> STATUS2DS_HOLDER = new ThreadLocal<Map<TransactionStatus, DataSource>>();

	static void addStatus2DataSource(TransactionStatus status, DataSource dataSource) {
		Map<TransactionStatus, DataSource> map = STATUS2DS_HOLDER.get();
		if (map == null) {
			map = new HashMap<TransactionStatus, DataSource>();
			STATUS2DS_HOLDER.set(map);
		}

		map.put(status, dataSource);
	}

	static DataSource removeStatus2DataSource(TransactionStatus status) {
		Map<TransactionStatus, DataSource> map = STATUS2DS_HOLDER.get();
		if (map != null) {
			DataSource ds = map.remove(status);
			if (map.isEmpty()) {
				STATUS2DS_HOLDER.remove();
			}
			return ds;
		}
		return null;
	}

	static void setDataSource(DataSource dataSource) {
		DS_HOLDER.set(dataSource);
	}

	public static DataSource getDataSource() {
		return DS_HOLDER.get();
	}

	static void addTxInfo2Tree(DataSource dataSource, TransactionInfoWrap transactionInfoWrap) {
		Map<DataSource, LinkedList<TransactionInfoWrap>> map = DS2TREE_HOLDER.get();
		if (map == null) {
			map = new LinkedHashMap<DataSource, LinkedList<TransactionInfoWrap>>();
			DS2TREE_HOLDER.set(map);
		}

		LinkedList<TransactionInfoWrap> txTree = map.get(dataSource);
		if (txTree == null) {
			txTree = new LinkedList<TransactionInfoWrap>();
			map.put(dataSource, txTree);
		}

		txTree.add(transactionInfoWrap);
	}

	static Map<DataSource, LinkedList<TransactionInfoWrap>> getDataSource2TxTree() {
		return DS2TREE_HOLDER.get();
	}

	static void setTransactionInfoWrap(TransactionInfoWrap transactionInfoWrap) {
		INFO_HOLDER.set(transactionInfoWrap);
	}

	static TransactionInfoWrap getTransactionInfoWrap() {
		return INFO_HOLDER.get();
	}

	static void clearAll() {
		DS2TREE_HOLDER.remove();
		DS_HOLDER.remove();
		INFO_HOLDER.remove();
		STATUS2DS_HOLDER.remove();
	}
}
