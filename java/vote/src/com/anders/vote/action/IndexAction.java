package com.anders.vote.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

//Namespace			：指定命名空间。
//ParentPackage		：指定父包。
//Result			：提供了Action结果的映射。（一个结果的映射）
//Results			：Result注解列表
//ResultPath		：指定结果页面的基路径。
//Action			：指定Action的访问URL。
//Actions			：Action注解列表。
//ExceptionMapping	：指定异常映射。（映射一个声明异常）
//ExceptionMappings	：一级声明异常的数组。
//InterceptorRef	：拦截器引用。
//InterceptorRefs	：拦截器引用组。

@ParentPackage("vote-default")
@Namespace("/")
@Results({ @Result(name = "success", type = "freemarker", location = "/WEB-INF/index.ftl"), @Result(name = "error", location = "/error.jsp") })
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class IndexAction extends BaseAction {

	private static final long serialVersionUID = -3013656302209804787L;

	private String user;

	@Action(value = "index")
	public String index() {
		user = "index";
		return SUCCESS;
	}

	@Action(value = "test1", results = { @Result(name = "success", type = "freemarker", location = "/WEB-INF/test1.ftl") })
	public String index1() {
		user = "index";
		return SUCCESS;
	}

	@Action(value = "test2", results = { @Result(name = "success", type = "freemarker", location = "/WEB-INF/test2.ftl") })
	public String index2() {
		user = "index";
		return SUCCESS;
	}

	@Action(value = "add", results = { @Result(name = "success", type = "freemarker", location = "/WEB-INF/add.ftl") })
	public String add() {
		user = "add";
		return SUCCESS;
	}

	// getter and setter

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
