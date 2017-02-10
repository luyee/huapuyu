package 转换图片格式;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class Main {
	private final static int TARGET_WIDTH = 200;
	private final static int TARGET_HEIGHT = 220;

	public static void main(String[] args) {
		try {
			Class<Main> clazz = Main.class;
			// 获取JPG文件绝对路径
			String jpgFilePath = clazz.getResource("image.png").getPath();
			System.out.println(jpgFilePath);
			// 获取JPG文件绝对目录
			String fileDir = jpgFilePath.substring(0, jpgFilePath.lastIndexOf("/"));
			System.out.println(fileDir);

			File jpgFile = new File(jpgFilePath);
			BufferedImage jpgImage = ImageIO.read(jpgFile);
			int targetWidth = TARGET_WIDTH;
			int targetHeight = TARGET_HEIGHT;

			// 计算PNG文件和JPG文件像素比，取高和宽中较小的像素比来计算PNG文件的像素
			double scaleWidth = (double) 200 / jpgImage.getWidth();
			double scaleHeight = (double) 200 / jpgImage.getHeight();
			if (scaleWidth > scaleHeight) {
				scaleWidth = scaleHeight;
				targetWidth = (int) (scaleWidth * jpgImage.getWidth());
			}
			else {
				scaleHeight = scaleWidth;
				targetHeight = (int) (scaleHeight * jpgImage.getHeight());
			}
			BufferedImage pngImage = new BufferedImage(targetWidth, targetHeight, jpgImage.getType());

			Graphics2D graphics = pngImage.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.drawRenderedImage(jpgImage, AffineTransform.getScaleInstance(scaleWidth, scaleHeight));
			graphics.dispose();

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// ImageIO.write(pngImage, "jpeg", byteArrayOutputStream);
			// File pngFile = new File(fileDir + "/image.jpeg");
			ImageIO.write(pngImage, "jpg", byteArrayOutputStream);
			File pngFile = new File(fileDir + "/image.jpg");
			// 如果PNG文件已经存在，则删除
			if (pngFile.exists())
				pngFile.delete();
			// 创建PNG文件
			FileUtils.writeByteArrayToFile(pngFile, byteArrayOutputStream.toByteArray());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("finish translate");
		}

		// 1. File file = new File("d:/temp/1.bmp");
		// 2. Image img = ImageIO.read(file);
		// 3. BufferedImage tag = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// 4. tag.getGraphics().drawImage(img.getScaledInstance(img.getWidth(null), img.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
		// 5. FileOutputStream out = new FileOutputStream("d:/temp/bmp2jpg.jpg");
		// 6. // JPEGImageEncoder可适用于其他图片类型的转换
		// 7. JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// 8. encoder.encode(tag);
		// 9. out.close();
	}
}
