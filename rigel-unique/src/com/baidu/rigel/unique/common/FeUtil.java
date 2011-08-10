/**
 * 
 */
package com.baidu.rigel.unique.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.baidu.rigel.service.usercenter.bean.Position;

/**
 * @author YanBing
 * 
 */
public class FeUtil {

	/**
	 * 转换为前端树状接口格式，如有不明请参考fe接口文档
	 * 
	 * ps: If it works properly, it is written by yanbing, otherwise it is written by zhaobing
	 * 
	 * @param posid
	 *            根节点id
	 * @param pl
	 *            岗位信息列表
	 * @param exType
	 *            需要过滤的postype
	 * @return
	 */
	public static List<Map<String, Object>> trans2FeList(Long posid, List<Position> pl, Short exType) {

		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		if (posid == null || pl == null)
			return ret;

		Collections.sort(pl, new PosComparator());

		Position root = null;
		Map<Long, List<Position>> pid2pos = new HashMap<Long, List<Position>>();
		for (Position pos : pl) {
			if (pos == null)
				continue;

			if (exType != null && exType.equals(pos.getPostype()))
				continue;

			if (posid.equals(pos.getPosid())) {
				root = pos;
				continue;
			}

			Long pid = pos.getParentid();
			List<Position> clist = pid2pos.get(pid);

			if (clist == null) {
				clist = new ArrayList<Position>();
				pid2pos.put(pid, clist);
			}
			clist.add(pos);
		}

		if (root == null)
			return ret;

		Stack<Position> stack = new Stack<Position>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Position pos = stack.pop();

			Map<String, Object> mp = new HashMap<String, Object>();

			mp.put("value", pos.getPosid());
			mp.put("parent_id", pos.getParentid());
			mp.put("text", pos.getPosname());

			ret.add(mp);

			List<Position> clist = pid2pos.get(pos.getPosid());
			if (clist != null) {
				for (Position cpos : clist) {
					stack.push(cpos);
				}
			}
		}

		ret.get(0).put("parent_id", false);

		return ret;
	}

	private static class PosComparator implements Comparator<Position> {

		public int compare(Position o1, Position o2) {

			int id1 = o1.getPosid() == null ? 0 : o1.getPosid().intValue();
			int id2 = o2.getPosid() == null ? 0 : o2.getPosid().intValue();

			return id2 - id1;
		}

	}
}
