package com.anders.ssh.webservice.cxf.rs;

import java.util.ArrayList;
import java.util.List;

public class InfoServiceImpl implements InfoService {
	@Override
	public void deleteInfo(String id) {
		System.out.println("delete:" + id);
	}

	@Override
	public Info getInfo(String id) {
		System.out.println("get:" + id);
		return new Info("1", "zhuzhen", "hello zhuzhen");
	}

	@Override
	public List<Info> getInfos() {
		System.out.println("get all");
		Info info1 = new Info("1", "zhuzhen", "hello zhuzhen");
		Info info2 = new Info("2", "guolili", "hello guolili");
		List<Info> infosList = new ArrayList<Info>();
		infosList.add(info1);
		infosList.add(info2);
		return infosList;
	}

	@Override
	public void saveOrUpdateInfo(Info info) {
		System.out.println("post");
		System.out.println(info.toString());
	}
}
