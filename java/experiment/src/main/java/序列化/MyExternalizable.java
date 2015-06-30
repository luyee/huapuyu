package 序列化;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MyExternalizable implements Externalizable {
	public String userName;
	public String userPass;
	public int userAge;

	public MyExternalizable() {
	}

	public MyExternalizable(String userName, String userPass, int userAge) {
		this.userName = userName;
		this.userPass = userPass;
		this.userAge = userAge;
	}

	// 当序列化对象时,该方法自动调用
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("执行序列化方法");
		// 可以在序列化时写非自身的变量
		String name = "朱振玩一玩";
		out.writeObject(name);
		// 只序列化userName,userPass变量
		out.writeObject(userName);
		out.writeObject(userPass);
	}

	// 当反序列化对象时,该方法自动调用
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("执行反序列化方法");
		String name = (String) in.readObject();
		System.out.println(name);
		this.userName = (String) in.readObject();
		this.userPass = (String) in.readObject();
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
