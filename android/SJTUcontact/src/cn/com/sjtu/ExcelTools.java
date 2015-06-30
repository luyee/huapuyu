package cn.com.sjtu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.util.Constants;
import com.util.Tools;
import com.util.User;

public class ExcelTools {

	public int writeExcel(String fileName, List<User> listUser, int codeStyle)
			throws BiffException, IOException, WriteException {
		// OutputStream os = exportUtils.getOutPutStream(fileName);
		// Writer wr = new PrintWriter(os);
		// wr.write(Constants.CNAME
		// +","+Constants.CMODULE+","+Constants.CMOBILENUMBER+","+Constants.CHOMENUM+","+Constants.CGROUPNAME+","+Constants.CGROUPID+","+Constants.CEMAIL+","+Constants.CPOSTNUM+","+Constants.CADDRESS);
		// wr.write("\n");
		// for (User user : listUser) {
		// wr.write(user.getName()
		// +","+user.getModule()+","+user.getMobileNumber()+","+user.getHomenum()+","+user.getGroupName()+","+user.getGroupnum()+","+user.getEmail()+","+user.getPostnum()+","+user.getAddress());
		// wr.write("\n");
		// wr.flush();
		// }
		// wr.close();
		// os.close();
		// return 1;
		ExportUtils exportUtils = new ExportUtils();
		WritableWorkbook wwb = Workbook.createWorkbook(exportUtils
				.createSdFile(fileName == null ? Tools.getNullFileName()
						+ ".xls" : fileName));
		WritableSheet ws = wwb.createSheet(Constants.APPNAME, 0);
		ws.addCell(new Label(0, 0, Constants.CNAME));
		ws.addCell(new Label(1, 0, Constants.CMOBILENUMBER));
		ws.addCell(new Label(2, 0, Constants.CGROUPNAME));
		ws.addCell(new Label(3, 0, Constants.CGROUPID));
		ws.addCell(new Label(4, 0, Constants.CEMAIL));
		ws.addCell(new Label(5, 0, Constants.CHOMENUM));
		ws.addCell(new Label(6, 0, Constants.CADDRESS));
		ws.addCell(new Label(7, 0, Constants.CPOSTNUM));
		ws.addCell(new Label(8, 0, Constants.CMODULE));
		ws.addCell(new Label(9, 0, Constants.CJOB));
		ws.addCell(new Label(10, 0, Constants.CJOBNUM));
		ws.addCell(new Label(11, 0, codeStyle + ""));

		for (int i = 1; i < listUser.size() + 1; i++) {
			User user = listUser.get(i - 1);
			ws.addCell(new Label(0, i, user.getName()));
			ws.addCell(new Label(1, i, user.getMobileNumber()));
			ws.addCell(new Label(2, i, user.getGroupName()));
			ws.addCell(new Label(3, i, user.getGroupnum() + ""));
			ws.addCell(new Label(4, i, user.getEmail()));
			ws.addCell(new Label(5, i, user.getHomenum()));
			ws.addCell(new Label(6, i, user.getAddress()));
			ws.addCell(new Label(7, i, user.getPostnum()));
			ws.addCell(new Label(8, i, user.getModule()));
			ws.addCell(new Label(9, i, user.getJob()));
			ws.addCell(new Label(10, i, user.getJobnum()));
		}
		wwb.write();
		wwb.close();
		return 1;
	}

	public List<User> readExcel(String fileName) throws BiffException,
			IOException {
		File file = new File(fileName);
		List<User> returnValue = new ArrayList<User>();
		if (!file.isDirectory()) {
			// BufferedReader br = new BufferedReader(new FileReader(file));
			// br.readLine();
			// String line = null;
			// while ((line = br.readLine())!=null) {
			// User user = new User();
			// String[] fields = line.split(",");
			// user.setName(fields[0]);
			// user.setModule(fields[1]);
			// user.setMobileNumber(fields[2]);
			// user.setHomenum(fields[3]);
			// user.setGroupnum(Integer.parseInt(fields[5]==null?"1":fields[5]));
			// user.setEmail(fields[6]);
			// user.setPostnum(fields[7]);
			// user.setAddress(fields[8]);
			// returnValue.add(user);
			// }
			//			
			// br.close();
			Workbook book = Workbook.getWorkbook(file);
			book.getNumberOfSheets();
			Sheet sheet = book.getSheet(0);
			int Rows = sheet.getRows();
			codeStyle = Integer.parseInt(sheet.getCell(11, 0).getContents());
			for (int j = 1; j < Rows; ++j) {
				User user = new User();
				user.setName(deCode(sheet.getCell(0, j).getContents()));
				user.setMobileNumber(deCode(sheet.getCell(1, j).getContents()));
				user.setGroupName(deCode(sheet.getCell(2, j).getContents()));
				user.setGroupnum(Integer.parseInt(sheet.getCell(3, j)
						.getContents().equals("")
						|| sheet.getCell(3, j).getContents() == null ? "1"
						: sheet.getCell(3, j).getContents()));
				user.setEmail(deCode(sheet.getCell(4, j).getContents()));
				user.setHomenum(deCode(sheet.getCell(5, j).getContents()));
				user.setAddress(deCode(sheet.getCell(6, j).getContents()));
				user.setPostnum(deCode(sheet.getCell(7, j).getContents()));
				user.setModule(deCode(sheet.getCell(8, j).getContents()));
				user.setJob(deCode(sheet.getCell(9, j).getContents()));
				user.setJobnum(deCode(sheet.getCell(9, j).getContents()));
				returnValue.add(user);
			}
			book.close();
		}
		return returnValue;
	}

	int codeStyle = -1;

	private String deCode(String message) {
		if (-1 == codeStyle)
			return message;
		return Tools.deCode(message, codeStyle);
	}
}
