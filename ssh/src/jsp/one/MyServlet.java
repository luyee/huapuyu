package jsp.one;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自己写的一个servlet注意配置文件的访问方式
 * 
 * @author Administrator
 * 
 */
public class MyServlet extends HttpServlet
{

	public String xx;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		System.out.println("1111111111111111111111" + xx);
		if (null == xx)
		{
			xx = "xxxx";
		}
		else
		{

		}
		System.out.println("2222222222222222222222" + xx);

		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");

		PrintStream out = new PrintStream(response.getOutputStream());
		out.println("<html>");
		out.println("<head>");
		out.println("<title>这是一个servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("�������你的名字是" + name);
		out.println("</body>");
		out.println("</html>");

	}

}
