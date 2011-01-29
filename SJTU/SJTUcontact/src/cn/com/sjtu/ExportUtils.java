package cn.com.sjtu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.util.Tools;

import android.os.Environment;

/**
 * 导出作用类
 * @author chixuan
 *
 */
public class ExportUtils {
	
	private String sdcardPath;
	

	public File createSdFile(String fileName) throws IOException{
		File file = new File(this.getSdcardPath() + fileName);
		if(file.exists()) {file.delete();}
		file.createNewFile();
		return file;
	}
	
	public OutputStream getOutPutStream(String fileName) throws FileNotFoundException, IOException{
		OutputStream outputs = new FileOutputStream(createSdFile(fileName));
		return outputs;
	}
	
	public File createSdFile() throws IOException{
		String fileName = Tools.getTime()+".xml";
		return createSdFile(this.getSdcardPath()+fileName.replace(":", ""));
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

	public String getSdcardPath() {
		if(null == this.sdcardPath){
			this.sdcardPath = Environment.getExternalStorageDirectory() + "/";
		}
		return sdcardPath;
	}

	public void setSdcardPath(String sdcardPath) {
		this.sdcardPath = sdcardPath;
	}
}
