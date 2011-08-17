package com.baidu.rigel.unique.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baidu.rigel.service.usercenter.bean.PosPath;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.service.UserMgr;
import com.baidu.rigel.unique.service.UcService;
import com.baidu.rigel.unique.utils.Utils;

@Service("ucService")
public class UcServiceImpl implements UcService {
	@Resource(name = "userMgr")
	private UserMgr userMgr;

	public Map<Long, Position> getPositionMapByUcidListAndRootTag(List<Long> ucidList, String rootTag) {
		Map<Long, Position> map = new HashMap<Long, Position>();
		List<PosPath> posPathList = userMgr.getPosPathByUser(ucidList, rootTag);

		if (CollectionUtils.isNotEmpty(posPathList)) {
			for (PosPath posPath : posPathList) {
				String idPath = posPath.getIdpath();
				if (Utils.isNotNull(idPath) && idPath.length() >= 10) {
					Long lastPosId = Long.valueOf(idPath.substring(idPath.length() - 10));
					Position pos = userMgr.getPositionById(lastPosId);
					if (Utils.isNotNull(pos)) {
						if (pos.getPostype().equals(Short.valueOf("3")))
							pos = userMgr.getPositionById(pos.getParentid());
						map.put(posPath.getUcid(), pos);
					}
				}
			}
		}

		return map;
	}

}
