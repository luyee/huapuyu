package templateMethod;

public class MainClass
{
	public static void main(String[] args)
	{
		PlayPES p1 = new PlayChina();
		PlayPES p2 = new PlayJapan();
		p1.play();
		p2.play();

		// HttpServlet类中的service方法就是模板方法，子类只需要继承HttpServlet的doGet, doPost等方法
	}
}
