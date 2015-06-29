package com.anders.experiment.network;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSH {
	public static String sshExecute(String host, String user, String pwd, String command) {
		// String osName = System.getProperty("os.name");
		// ps -ef|grep tomcat|grep -v grep|awk '{print $2}'
		StringBuffer sb = new StringBuffer();
		try {
			JSch jsch = new JSch();
			// if (osName.toUpperCase().indexOf("WINDOWS") > -1) {
			// jsch.setKnownHosts("c:\\known_hosts");
			// } else {
			// jsch.setKnownHosts("/root/.ssh/known_hosts");
			// }
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			// config.put("compression.s2c", "zlib,none");
			// config.put("compression.c2s", "zlib,none");
			// config.put("PreferredAuthentications","password");
			Session session = jsch.getSession(user, host, 22);
			session.setPassword(pwd);
			session.setConfig(config);
			session.connect();

			Channel channel = session.openChannel("shell");
			// command="sh "+command+" > healthcheck.out.txt"+"\n";
			command = command + "> chi.log" + "\n";
			byte[] bytes = command.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			// command="/root/"+command+" >/root/healthcheck.out";
			// command="ls >11.out";
			// ((ChannelExec) channel).setCommand(command);
			((ChannelShell) channel).setInputStream(bais);
			InputStream in = channel.getInputStream();
			BufferedReader inb = new BufferedReader(new InputStreamReader(in));
			channel.connect();
			String nextChar;
			// while (true) {
			// while ((nextChar = inb.readLine()) != null) {
			// sb.append(nextChar);
			// }
			// System.out.print(sb);
			// if (channel.isClosed()) {
			// System.out.println("exit-status: " + channel.getExitStatus());
			// break;
			// }
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {
			}
			// }
			channel.disconnect();
			session.disconnect();
			System.out.println("hhhhhhhhh");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String sshExecute_new(String host, String user, String pwd, String command) {
		// String osName = System.getProperty("os.name");
		// ps -ef|grep tomcat|grep -v grep|awk '{print $2}'
		StringBuffer sb = new StringBuffer();
		try {
			JSch jsch = new JSch();
			// if (osName.toUpperCase().indexOf("WINDOWS") > -1) {
			// jsch.setKnownHosts("c:\\known_hosts");
			// } else {
			// jsch.setKnownHosts("/root/.ssh/known_hosts");
			// }
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			// config.put("compression.s2c", "zlib,none");
			// config.put("compression.c2s", "zlib,none");
			// config.put("PreferredAuthentications","password");
			Session session = jsch.getSession(user, host, 22);
			session.setPassword(pwd);
			session.setConfig(config);
			session.connect();

			Channel channel = session.openChannel("shell");
			command = "sh " + command + " > healthchecknew.out" + "\n";
			byte[] bytes = command.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			// command="/root/"+command+" >/root/healthcheck.out";
			// command="ls >11.out";
			// ((ChannelExec) channel).setCommand(command);
			((ChannelShell) channel).setInputStream(bais);
			InputStream in = channel.getInputStream();
			BufferedReader inb = new BufferedReader(new InputStreamReader(in));
			channel.connect();
			String nextChar;
			// while (true) {
			// while ((nextChar = inb.readLine()) != null) {
			// sb.append(nextChar);
			// }
			// System.out.print(sb);
			// if (channel.isClosed()) {
			// System.out.println("exit-status: " + channel.getExitStatus());
			// break;
			// }
			try {
				Thread.sleep(90 * 1000);
			} catch (Exception ee) {
			}
			// }
			channel.disconnect();
			session.disconnect();
			System.out.println("dfdfgdaa");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sshExecute("anders1", "anders", "123", "ls");
		// sshExecute("10.230.8.134","root","ascp01","jilin_MonitR331.sh");
		// sshExecute("10.230.8.150","root","ascp01","MonitR331");*
		// sshExecute("10.230.213.38","root","ascp01","MonitR331");*
		// sshExecute("10.161.221.248","root","ascp01","MonitR331");*
		// sshExecute_new("10.230.132.13","root","ascp01","ngHLRstatusCheck.sh");
		// sshExecute("10.230.228.41","root","ascp01","MonitR331");*
		// sshExecute("10.230.197.38","root","ascp01","MonitR331");*
		// sshExecute("10.230.164.6","root","ascp01","MonitR331");
		// sshExecute("10.230.181.36","root","ascp01","MonitR331");
		// sshExecute("10.230.245.40","root","ascp01","MonitR331");
	}
}
