package cn.com.sjtu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.util.Constants;
import com.util.Tools;
import com.util.User;

public class XmlTools {

	private XmlSerializer serializer;

	private ExportUtils exportUtils = new ExportUtils();
	private String startTag;
	private String startChild;

	private String firstChild;
	private String secondChild;
	private String thirdChild;
	private String forthChild;
	private String fifthChild;
	private String sixthChild;
	private String seventhChild;
	private String eighthChild;
	private String ninthChild;
	private String tenthChild;

	public XmlTools() {
		this.serializer = Xml.newSerializer();

		this.startTag = Constants.CONTACT;
		this.startChild = Constants.USER;
		this.firstChild = Constants.NAME;
		this.secondChild = Constants.MOBILENUMBER;
		this.thirdChild = Constants.POSTNUM;
		this.forthChild = Constants.MODULE;
		this.fifthChild = Constants.GROUPNAME;
		this.sixthChild = Constants.EMAIL;
		this.seventhChild = Constants.HOMENUM;
		this.eighthChild = Constants.ADDRESS;
		this.ninthChild = Constants.JOB;
		this.tenthChild = Constants.JOBNUM;
	}

	public int writeXml(String fileName, List<User> listUser, boolean code,
			int codeStyle) throws IllegalArgumentException,
			IllegalStateException, IOException {
		OutputStream os = exportUtils.getOutPutStream(fileName);
		serializer.setOutput(os, "UTF-8");
		serializer.startDocument("UTF-8", true);

		serializer.startTag("", startTag);
		serializer.attribute("", Constants.CODE, code + "");
		serializer.attribute("", Constants.CODESTYLE, codeStyle + "");
		for (User user : listUser) {
			serializer.startTag("", startChild);
			serializer.attribute("", Constants.ID, user.getId() + "");
			serializer
					.attribute("", Constants.GROUPID, user.getGroupnum() + "");
			initXmlChild(firstChild, user.getName());
			initXmlChild(secondChild, user.getMobileNumber());
			initXmlChild(thirdChild, user.getPostnum());
			initXmlChild(forthChild, user.getModule());
			initXmlChild(fifthChild, user.getGroupName());
			initXmlChild(sixthChild, user.getEmail());
			initXmlChild(seventhChild, user.getHomenum());
			initXmlChild(eighthChild, user.getAddress());
			initXmlChild(ninthChild, user.getJob());
			initXmlChild(tenthChild, user.getJobnum());
			serializer.endTag("", startChild);
		}
		serializer.endTag("", startTag);
		serializer.endDocument();
		os.flush();
		os.close();
		return 1;
	}

	public void initXmlChild(String tagName, String text)
			throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", tagName);
		serializer.text(text == null ? "" : text);
		serializer.endTag("", tagName);
	}

	public List<User> readXml(String fileName2) throws FileNotFoundException,
			IOException, ParserConfigurationException, SAXException {
		List<User> returnValue = new ArrayList<User>();
		File file = new File(fileName2);
		if (!file.isDirectory()) {
			InputStream in = exportUtils.getInPutStream(file);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser(); // ´´½¨½âÎöÆ÷
			saxParser.parse(in, new MyXmlHandler(returnValue));
			in.close();
		}
		return returnValue;
	}

	private class MyXmlHandler extends DefaultHandler {
		private List<User> returnValue;
		private String tagName;
		private String groupId;
		private User user = new User();
		private boolean isCode;
		private int codeStyle;

		public MyXmlHandler(List<User> returnValue) {
			this.returnValue = returnValue;
		}

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attrs) throws SAXException {
			tagName = localName;
			if (tagName.equals(Constants.CONTACT)) {
				isCode = Boolean.parseBoolean(attrs.getValue(Constants.CODE));
				codeStyle = Integer.parseInt(attrs
						.getValue(Constants.CODESTYLE));
			}
			if (tagName.equals(Constants.USER)) {
				groupId = attrs.getValue(Constants.GROUPID);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (localName.equals(Constants.USER)) {
				this.returnValue.add(user);
				user = new User();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (tagName.equals(Constants.NAME)) {
				user.setName(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.MOBILENUMBER)) {
				user.setMobileNumber(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.POSTNUM)) {
				user.setPostnum(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.MODULE)) {
				user.setModule(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.EMAIL)) {
				user.setEmail(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.HOMENUM)) {
				user.setHomenum(changeCharToString(ch, start, length));
			} else if (tagName.equals(Constants.ADDRESS)) {
				user.setAddress(changeCharToString(ch, start, length));
			} else {
				user.setGroupnum(Integer.parseInt(groupId == null
						|| groupId.equals("") ? Constants.ALLGROUP : groupId));
			}
		}

		private String changeCharToString(char[] ch, int start, int length) {
			if (isCode) {
				return Tools.deCode(new String(ch, start, length), codeStyle);
			} else {
				return new String(ch, start, length);
			}
		}
	}
}
