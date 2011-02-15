package cn.com.sjtu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.util.Constants;
import com.util.User;

public class CsvTools {
	private ExportUtils exportUtils = new ExportUtils();
	
	public int writeCsv(String fileName,List<User> listUser) throws FileNotFoundException, IOException{
		OutputStream os = exportUtils.getOutPutStream(fileName);
		
		Writer wr = new PrintWriter(os);
		wr.write(Constants.CNAME +","+Constants.CMODIFIEDDATE+","+Constants.CMOBILENUMBER+","+Constants.CHOMENUM+","+Constants.CGROUPNAME+","+Constants.CGROUPID+","+Constants.CEMAIL+","+Constants.CCREATEDATE+","+Constants.CADDRESS);
		wr.write("\n");
		for (User user : listUser) {
			wr.write(user.getName() +","+user.getModifiedDate()+","+user.getMobileNumber()+","+user.getHomenum()+","+user.getGroupName()+","+user.getGroupnum()+","+user.getEmail()+","+user.getCreatedDate()+","+user.getAddress());
			wr.write("\n");
			wr.flush();
		}
		wr.close();
		os.close();
		return 1;
	}
	
	public List<User> readCsv(String fileName) throws IOException{
		File file = new File(fileName);
		List<User> returnValue = new ArrayList<User>();
		if(!file.isDirectory()){
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine();
			String line = null;
			while ((line = br.readLine())!=null) {
				User user = new User();
				String[] fields = line.split(",");
				user.setName(fields[0]);
				user.setModifiedDate(fields[1]);
				user.setMobileNumber(fields[2]);
				user.setHomenum(fields[3]);
				user.setGroupnum(Integer.parseInt(fields[5]==null?"1":fields[5]));
				user.setEmail(fields[6]);
				user.setCreatedDate(fields[7]);
				user.setAddress(fields[8]);
				returnValue.add(user);
			}
			
			br.close();
		}
		return returnValue;
	}
	
}
