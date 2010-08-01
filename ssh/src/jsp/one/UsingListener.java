package jsp.one;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UsingListener implements ServletContextListener
{

	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent scte)
	{
		timer.cancel();
		scte.getServletContext().log(new Date() + "��ʱ�����计时器被销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent scte)
	{
		// ��ʱ������
		timer = new Timer(true);
		scte.getServletContext().log(new Date() + "计时器已经启动。。。。。������������");
		timer.schedule(new MyTask(), 0, 1000);// 每1秒执行定时任务ִ�ж�ʱ����
		scte.getServletContext().log(new Date() + "��ʱ���计时器已经执行一次。。。。。");
	}

}

class MyTask extends TimerTask
{

	private static boolean isRunning = false;

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		if (!isRunning)
		{
			isRunning = true;
			for (int i = 0; i < 10; i++)
			{
				System.out.println("正在执行" + i + "����任务");
				try
				{
					Thread.sleep(80);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			isRunning = true;
			System.out.println("所有任务执行完退出");
		}
		else
		{
			System.out.println("�����˳�任务退出");
		}
	}

}
