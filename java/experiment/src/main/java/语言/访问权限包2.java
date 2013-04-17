package 语言;

//将本代码移动到“语言”包以外，会报错，找不到类“访问权限包1”
class 访问权限包2 extends 访问权限包1 {

	@Override
	void print() {
		System.out.println("访问权限包2");
	}

}
