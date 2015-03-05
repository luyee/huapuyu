package com.anders.experiment.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class Tester {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// GroovyShell shell = new GroovyShell();

		ClassLoader cl = null;
		GroovyClassLoader gcl = null;
		Class<?> groovyClass = null;
		cl = Tester.class.getClassLoader();
		gcl = new GroovyClassLoader(cl);
		groovyClass = gcl.parseClass(getGroovyRuleEngine());
		GroovyObject ruleEngine = (GroovyObject) groovyClass.newInstance();
		System.out.println(ruleEngine.invokeMethod("eval", "18"));
	}

	public static String getGroovyRuleEngine() {
		StringBuilder sb = new StringBuilder();
		// sb.append("package com.vip.venus.jdbc.rule;");
		// sb.append("import com.vip.venus.jdbc.rule.RuleEngine;");
		sb.append("class GroovyRuleEngine");
		sb.append("{");
		// sb.append("@Override\r\n");
		sb.append("public Object eval(Object key)");
		sb.append("{");
		sb.append("return (key as String).length().intValue();");
		sb.append("}");
		sb.append("}");

		return sb.toString();
	}

}
