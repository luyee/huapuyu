package cn.com.sjtu;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.util.User;

import android.util.Xml;


/**
 * Xml相关操作类
 * @author chixuan
 *
 */
public class XmlTools {
	
	private XmlSerializer serializer ;
	
	private ExportUtils exportUtils = new ExportUtils();
	private String startTag;
	private String startChild;
	private String firstChild;
	private String secondChild;
	private String thirdChild;
	private String forthChild;
	private String fifthChild;
	
	public XmlTools() {
		serializer = Xml.newSerializer();
		
		startTag = "Contact";
		startChild = "User";
		firstChild = "name";
		secondChild = "mobileNumber";
		thirdChild = "createdDate";
		forthChild = "modifiedDate";
		fifthChild = "groupName";
	}
	
	public int writeXml(String fileName,List<User> listUser) throws IllegalArgumentException, IllegalStateException, IOException{
		OutputStream os = exportUtils.getOutPutStream(fileName);
		serializer.setOutput(os ,"UTF-8");
		serializer.startDocument("UTF-8",true);
		
		serializer.startTag("",startTag);
		for (User user : listUser) {
			serializer.startTag("",startChild);
			serializer.attribute("", "id", user.getId()+"");
				initXmlChild(firstChild, user.getName());
				initXmlChild(secondChild, user.getMobileNumber());
				initXmlChild(thirdChild, user.getCreatedDate());
				initXmlChild(forthChild, user.getModifiedDate());
				initXmlChild(fifthChild, user.getGroupName());
			serializer.endTag("", startChild);
		}
		serializer.endTag("", startTag);
		serializer.endDocument();
		os.flush();
		os.close();
		return 1;
	}
	
	public void initXmlChild(String tagName,String text) throws IllegalArgumentException, IllegalStateException, IOException{
		serializer.startTag("",tagName);
		serializer.text(text);
		serializer.endTag("",tagName);
	}
}
