package cn.com.sjtu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

import com.util.Tools;

/**
 * 导出作用类
 * @author chixuan
 *
 */
public class ExportUtils {
	
	
	

	public File createSdFile(String fileName) throws IOException{
		File file = new File(Tools.getContactSavePath() + fileName);
		if(file.exists()) {file.delete();}
		file.createNewFile();
		return file;
	}
	
	public OutputStream getOutPutStream(String fileName) throws FileNotFoundException, IOException{
		OutputStream outputs = new FileOutputStream(fileName==null?createSdFile():createSdFile(fileName));
		return outputs;
	}
	public InputStream getInPutStream(File file) throws FileNotFoundException, IOException{
		InputStream inputs = new FileInputStream(file);
		return inputs;
	}
	public File createSdFile() throws IOException{
		String fileName = Tools.getTime()+".xml";
		return createSdFile(Tools.getContactSavePath()+fileName.replace(":", ""));
	}
	
	public File writeToFile(String fileName,InputStream inputStream) throws IOException{
		File file ;
		OutputStream outputs;
		
		if(null == fileName){
			file = this.createSdFile();
		}else{
			file = this.createSdFile(fileName);
		}
		byte[] byts = new byte[1*1024];
		outputs = new FileOutputStream(file);
		while (inputStream.read(byts) != -1) {
			outputs.write(byts);
		}
		return file;
	}
	
	public File writeToFile(InputStream inputStream) throws IOException{
		return writeToFile(null, inputStream);
	}

	
	
}
