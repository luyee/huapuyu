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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

public class GiftDb extends Thread {
	private static Log log = LogFactory.getLog(GiftDb.class);

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

	public void readFile() throws IOException {
		String path = getClass().getResource("gift.csv").getPath();
		log.debug(path);
		CSVReader reader = new CSVReader(new FileReader(path));
		String[] row;
		List<String> sqlList = new ArrayList<String>();
		String sql = StringUtils.EMPTY;
		int count = 1;
		reader.readNext();
		Long max = getMaxIdOfGift() + 100;
		log.info("礼品表最大编号：" + max);
		createTempTable();
		while ((row = reader.readNext()) != null) {
			System.out.println(String.format("(%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4])));
			String current_storage = null;
			if (row.length > 5)
				current_storage = row[5];
			insertTempRecord(String.format("INSERT INTO temp (gift_id,current_storage) VALUES (%d,%s)", max, current_storage));
			if (count == 1) {
				sql = String.format("INSERT INTO tb_gift (id,org_id,gift_name,gift_code,gift_type_id,is_delete,gift_price,gift_origin) VALUES (%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4]));
			}
			else {
				sql += String.format(",(%d,%s,'%s','%s',%d,%d,%s,%d)", max, "2254", row[0], row[1], getGiftTypeId(row[2]), 0, row[3], getSrc(row[4]));
			}

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
		genGiftStorageSql();
	}

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

	private Long getMaxIdOfGift() {
		return getMaxFromMySql("SELECT MAX(id) FROM tb_gift");
	}

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

	private void createTempTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE temp");
		sb.append("(");
		sb.append("gift_id INT NOT NULL,");
		sb.append("current_storage DECIMAL DEFAULT NULL,");
		sb.append("PRIMARY KEY (gift_id)");
		sb.append(")");
		Statement st = null;
		try {
			log.debug(sb.toString());
			st = HSqlDbUtils.getConnection().createStatement();
			st.execute(sb.toString());
		}
		catch (SQLException e) {
			log.error(e.getMessage());
		}
		finally {
			if (st != null)
				try {
					st.close();
				}
				catch (SQLException e) {
					log.error(e.getMessage());
				}
		}
	}

	private void insertTempRecord(String sql) {
		Statement st = null;
		try {
			log.debug(sql);
			st = HSqlDbUtils.getConnection().createStatement();
			st.execute(sql);
		}
		catch (SQLException e) {
			log.error(e.getMessage());
		}
		finally {
			if (st != null)
				try {
					st.close();
				}
				catch (SQLException e) {
					log.error(e.getMessage());
				}
		}
	}

	private int getSrc(String name) {
		return name.equalsIgnoreCase("总部") ? 0 : 1;
	}

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

	private void genGiftStorageSql() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<String> sqlList = new ArrayList<String>();
			String sql = StringUtils.EMPTY;
			int count = 1;
			ps = HSqlDbUtils.getConnection().prepareStatement("SELECT gift_id,current_storage FROM temp WHERE current_storage IS NOT NULL");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (count == 1) {
					sql = String.format("INSERT INTO tb_gift_storage (parent_id,sup_unit_id,gift_id,org_id,current_storage,usable_amount,assigned_amount,cuid,cdate,uuid,udate,is_delete,ver) VALUES (%s,%s,%d,%s,%d,%d,%d,%d,NOW(),%s,%s,%d,%d)", null, "2254", rs.getLong(1), "2254", rs.getBigDecimal(2).longValue(), rs.getBigDecimal(2).longValue(), 0, -1, null, null, 0, 0);
				}
				else {
					sql += String.format(",(%s,%s,%d,%s,%d,%d,%d,%d,NOW(),%s,%s,%d,%d)", null, "2254", rs.getLong(1), "2254", rs.getBigDecimal(2).longValue(), rs.getBigDecimal(2).longValue(), 0, -1, null, null, 0, 0);
				}

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
	}
}
