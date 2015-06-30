package 加密;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URL加密解密 {
	public static void main(String[] args) {
		String name = "zhu zhen";
		try {
			name = URLEncoder.encode(name, "UTF-8");
			System.out.println(name);
			name = URLDecoder.decode(name, "UTF-8");
			System.out.println(name);
		}
		catch (Exception e) {
		}

	}

}
