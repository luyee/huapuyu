package 语言;

// 将本代码移动到“语言”包以外，会报错，找不到类“访问权限包1”和“访问权限包2”
public class 测试访问权限包 {
	public static void main(String[] args) {
		new 访问权限包1().print();
		new 访问权限包2().print();
	}
}
