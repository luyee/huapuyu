package 字符串;

public class Main {

	public static void main(String[] args) {
		// 线程安全
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("this ");
		stringBuffer.append("is ");
		stringBuffer.append("stringbuffer");
		System.out.println(stringBuffer.toString());

		// 非线程安全，但是速度比StringBuffer稍快
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("this ");
		stringBuilder.append("is ");
		stringBuilder.append("stringBuilder");
		System.out.println(stringBuilder.toString());
	}
}
