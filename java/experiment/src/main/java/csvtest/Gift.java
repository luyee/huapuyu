package csvtest;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 根据CSV文件导出礼品迁移SQL
 * 
 * @author Anders Zhu
 * 
 */
public class Gift extends Thread {
	private static Log log = LogFactory.getLog(Gift.class);

	private Map<Long, String> storageMap = new HashMap<Long, String>();

	@Override
	public void run() {
		long beginTime = new Date().getTime();
		try {
			readFile();
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
		long endTime = new Date().getTime();
		log.info("总运行时间：" + (endTime - beginTime));
	}

	/**
	 * 读取CSV文件，首先生成礼品表的SQL
	 * 
	 * @throws IOException
	 */
	public void readFile() throws IOException {
		String path = getClass().getResource("gift.csv").getPath();
		log.debug(path);
		CSVReader reader = new CSVReader(new FileReader(path));
		String[] row;
		List<String> sqlList = new ArrayList<String>();
		String sql = StringUtils.EMPTY;
		int count = 1;
		reader.readNext();
		// 获得礼品表最大编号，通过加100作为后续记录的编号
		Long max = getMaxIdOfGift() + 100;
		log.info("礼品表最大编号：" + max);
		while ((row = reader.readNext()) != null) {
			System.out.println(String.format("(%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4])));
			if (row.length > 5)
				storageMap.put(max, row[5]);
			if (count == 1)
				sql = String.format("INSERT INTO tb_gift (id,org_id,gift_name,gift_code,gift_type_id,is_delete,gift_price,gift_origin) VALUES (%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4]));
			else
				sql += String.format(",(%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4]));
			// 每2000条数据组成一条INSERT语句
			if (count % 2000 == 0) {
				count = 1;
				sqlList.add(sql + ";");
				log.debug(sql + ";");
				sql = StringUtils.EMPTY;
			}
			else {
				count++;
			}

			max++;
		}

		if (StringUtils.isNotEmpty(sql)) {
			sqlList.add(sql + ";");
			log.debug(sql + ";");
		}
		log.info(writeFile("gift", sqlList));
		// 生成礼品库存表的SQL
		genGiftStorageSql();
	}

	/**
	 * 从盘古库中获取特定表的最大编号
	 * 
	 * @param sql
	 * @return
	 */
	private Long getMaxFromMySql(String sql) {
		long max = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = MySqlUtils.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				max = rs.getLong(1);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		finally {
			if (rs != null)
				try {
					rs.close();
				}
				catch (SQLException e) {
					log.error(e.getMessage());
				}
		}
		return max;
	}

	/**
	 * 从盘古库中获取礼品表的最大编号
	 * 
	 * @return
	 */
	private Long getMaxIdOfGift() {
		return getMaxFromMySql("SELECT MAX(id) FROM tb_gift");
	}

	/**
	 * 根据礼品类型名称从盘古库礼品类型表中获取礼品编号
	 * 
	 * @param giftTypeName
	 * @return
	 */
	private Long getGiftTypeId(String giftTypeName) {
		Long id = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = MySqlUtils.getConnection().prepareStatement("SELECT id FROM tb_gift_type WHERE org_id = 2254 AND is_delete = 0 AND type_name = ?");
			ps.setString(1, giftTypeName);
			rs = ps.executeQuery();
			if (rs.next())
				id = rs.getLong(1);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		finally {
			if (rs != null)
				try {
					rs.close();
				}
				catch (SQLException e) {
					log.error(e.getMessage());
				}

			if (ps != null)
				try {
					ps.close();
				}
				catch (SQLException e) {
					log.error(e.getMessage());
				}
		}
		return id;
	}

	/**
	 * 获取数据来源编号（0：总部；1：分公司）
	 * 
	 * @param name
	 * @return
	 */
	private int getSrc(String name) {
		return name.equalsIgnoreCase("总部") ? 0 : 1;
	}

	/**
	 * 写SQL文件，每10条SQL Flush一次文件
	 * 
	 * @param fileName
	 * @param sqlList
	 * @return
	 * @throws IOException
	 */
	private String writeFile(String fileName, List<String> sqlList) throws IOException {
		long beginTime = new Date().getTime();
		File file = new File(String.format(PropUtils.readSqlFilePath(), fileName));
		if (file.exists())
			file.delete();
		file.createNewFile();
		OutputStream output = new FileOutputStream(file);
		BufferedOutputStream buffer = new BufferedOutputStream(output);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer, "UTF-8"));
		writer.write("set names utf8;");
		for (int i = 0; i < sqlList.size(); i++) {
			writer.newLine();
			writer.write(sqlList.get(i));
			if (i % 10 == 0)
				writer.flush();
		}
		writer.close();
		buffer.close();
		output.close();
		long endTime = new Date().getTime();
		log.info("写文件时间：" + (endTime - beginTime));
		return file.getAbsolutePath();
	}

	/**
	 * 生成礼品库存表的SQL
	 * 
	 * @throws IOException
	 */
	private void genGiftStorageSql() throws IOException {
		List<String> sqlList = new ArrayList<String>();
		String sql = StringUtils.EMPTY;
		int count = 1;
		for (Iterator<Long> iterator = storageMap.keySet().iterator(); iterator.hasNext();) {
			Long id = iterator.next();
			String current_storage = storageMap.get(id);
			if (count == 1)
				sql = String.format("INSERT INTO tb_gift_storage (parent_id,sup_unit_id,gift_id,org_id,current_storage,usable_amount,assigned_amount,cuid,cdate,uuid,udate,is_delete,ver) VALUES (%s,%s,%d,%s,%s,%s,%d,%d,NOW(),%s,%s,%d,%d)", null, "2254", id, "2254", current_storage, current_storage, 0, -1, null, null, 0, 0);
			else
				sql += String.format(",(%s,%s,%d,%s,%s,%s,%d,%d,NOW(),%s,%s,%d,%d)", null, "2254", id, "2254", current_storage, current_storage, 0, -1, null, null, 0, 0);

			if (count % 2000 == 0) {
				count = 1;
				sqlList.add(sql + ";");
				log.debug(sql + ";");
				sql = StringUtils.EMPTY;
			}
			else {
				count++;
			}
		}

		if (StringUtils.isNotEmpty(sql)) {
			sqlList.add(sql + ";");
			log.debug(sql + ";");
		}
		log.info(writeFile("giftStorage", sqlList));
	}
}
