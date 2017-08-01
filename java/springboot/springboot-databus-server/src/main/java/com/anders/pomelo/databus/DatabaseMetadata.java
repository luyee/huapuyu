package com.anders.pomelo.databus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.dao.bo.Columns;
import com.anders.pomelo.databus.dao.bo.KeyColumnUsage;
import com.anders.pomelo.databus.dao.bo.Schemata;
import com.anders.pomelo.databus.dao.bo.Tables;
import com.anders.pomelo.databus.dao.mapper.ColumnsMapper;
import com.anders.pomelo.databus.dao.mapper.DatabaseMapper;
import com.anders.pomelo.databus.dao.mapper.KeyColumnUsageMapper;
import com.anders.pomelo.databus.dao.mapper.SchemataMapper;
import com.anders.pomelo.databus.dao.mapper.TablesMapper;
import com.anders.pomelo.databus.model.Column;
import com.anders.pomelo.databus.model.Database;
import com.anders.pomelo.databus.model.Schema;
import com.anders.pomelo.databus.model.Table;
import com.anders.pomelo.databus.model.column.BigIntColumn;
import com.anders.pomelo.databus.model.column.BitColumn;
import com.anders.pomelo.databus.model.column.DateColumn;
import com.anders.pomelo.databus.model.column.DateTimeColumn;
import com.anders.pomelo.databus.model.column.DecimalColumn;
import com.anders.pomelo.databus.model.column.FloatColumn;
import com.anders.pomelo.databus.model.column.IntColumn;
import com.anders.pomelo.databus.model.column.StringColumn;
import com.anders.pomelo.databus.model.column.TimeColumn;
import com.anders.pomelo.databus.model.column.YearColumn;

// TODO Anders bigint, varchar, tinyint, timestamp, bit, int, decimal, double, char, date, time, datetime, smallint, bigint unsigned, text

@Component
public class DatabaseMetadata implements InitializingBean {

	private static Logger LOGGER = LoggerFactory.getLogger(DatabaseMetadata.class);

	@Autowired
	private BinlogProps binlogProps;
	@Autowired
	private ColumnsMapper columnsMapper;
	@Autowired
	private DatabaseMapper databaseMapper;
	@Autowired
	private KeyColumnUsageMapper keyColumnUsageMapper;
	@Autowired
	private SchemataMapper schemataMapper;
	@Autowired
	private TablesMapper tablesMapper;

	public final static Set<String> IGNORED_DATABASES = new HashSet<String>(Arrays.asList(new String[] { "performance_schema", "information_schema" }));

	private Set<String> includedDatabases;
	private Set<String> ignoredTables;

	public synchronized Schema generate() {
		if (CollectionUtils.isEmpty(includedDatabases)) {
			LOGGER.error("includeDatabases is empty");
			throw new RuntimeException("includeDatabases is empty");
		}

		List<Schemata> schemataList = schemataMapper.selectAll();
		if (CollectionUtils.isEmpty(schemataList)) {
			LOGGER.warn("schemataList is empty");
			throw new RuntimeException("schemataList is empty");
		}

		Map<String, Database> databaseMap = new HashMap<String, Database>();
		for (Schemata schemata : schemataList) {
			if (IGNORED_DATABASES.contains(schemata.getSchemaName())) {
				continue;
			}

			if (includedDatabases.contains(schemata.getSchemaName())) {
				databaseMap.put(schemata.getSchemaName(), new Database(schemata.getSchemaName(), schemata.getDefaultCharacterSetName()));
			}
		}

		if (MapUtils.isEmpty(databaseMap)) {
			LOGGER.warn("databaseList is empty");
			throw new RuntimeException("databaseList is empty");
		}

		for (Database database : databaseMap.values()) {
			genTables(database);
		}

		return new Schema(databaseMap);
	}

	public synchronized void genTables(Database database) {
		List<Tables> tablesList = tablesMapper.selectByTableSchema(database.getName());
		if (CollectionUtils.isEmpty(tablesList)) {
			LOGGER.warn("tablesList is empty");
			return;
		}

		Map<String, Table> tableMap = new HashMap<String, Table>();
		for (Tables tables : tablesList) {
			if (ignoredTables.contains(tables.getTableName())) {
				continue;
			}

			tableMap.put(tables.getTableName(), new Table(database.getName(), tables.getTableName(), tables.getCharacterSetName()));
		}
		database.setTables(tableMap);

		genColumns(database, tableMap);
		genPkColumns(database, tableMap);
	}

	public synchronized void genColumns(Database database, Map<String, Table> tableMap) {
		List<Columns> columnsList = columnsMapper.selectByTableSchema(database.getName());
		if (CollectionUtils.isEmpty(columnsList)) {
			LOGGER.warn("columnsList is empty");
			return;
		}

		for (Columns columns : columnsList) {
			switch (columns.getDataType()) {
			case "tinyint":
			case "smallint":
			case "mediumint":
			case "int":
				tableMap.get(columns.getTableName()).addColumn(new IntColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1, !columns.getColumnType().matches(".* unsigned$")));
				break;
			case "bigint":
				tableMap.get(columns.getTableName()).addColumn(new BigIntColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1, !columns.getColumnType().matches(".* unsigned$")));
				break;
			case "tinytext":
			case "text":
			case "mediumtext":
			case "longtext":
			case "varchar":
			case "char":
				tableMap.get(columns.getTableName()).addColumn(new StringColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1, columns.getCharacterSetName()));
				break;
			case "tinyblob":
			case "blob":
			case "mediumblob":
			case "longblob":
			case "binary":
			case "varbinary":
				// return new StringColumnDef(name, type, pos, "binary");
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			case "geometry":
			case "geometrycollection":
			case "linestring":
			case "multilinestring":
			case "multipoint":
			case "multipolygon":
			case "polygon":
			case "point":
				// return new GeometryColumnDef(name, type, pos);
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			case "float":
			case "double":
				tableMap.get(columns.getTableName()).addColumn(new FloatColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1, !columns.getColumnType().matches(".* unsigned$")));
				break;
			case "decimal":
				tableMap.get(columns.getTableName()).addColumn(new DecimalColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "date":
				tableMap.get(columns.getTableName()).addColumn(new DateColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "datetime":
			case "timestamp":
				tableMap.get(columns.getTableName()).addColumn(new DateTimeColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "time":
				tableMap.get(columns.getTableName()).addColumn(new TimeColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "year":
				tableMap.get(columns.getTableName()).addColumn(new YearColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "enum":
				// return new EnumColumnDef(name, type, pos, enumValues);
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			case "set":
				// return new SetColumnDef(name, type, pos, enumValues);
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			case "bit":
				tableMap.get(columns.getTableName()).addColumn(new BitColumn(columns.getColumnName(), columns.getColumnType(), columns.getOrdinalPosition() - 1));
				break;
			case "json":
				// return new JsonColumnDef(name, type, pos);
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			case "bool":
			case "boolean":
			case "numeric":
			case "real":
				// return new JsonColumnDef(name, type, pos);
				// TODO Anders 这两种类型maxwell没有实现
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			default:
				throw new RuntimeException("unsupported column type : " + columns.getDataType());
			}
		}
	}

	public synchronized void genPkColumns(Database database, Map<String, Table> tableMap) {
		List<KeyColumnUsage> keyColumnUsageList = keyColumnUsageMapper.selectByTableSchema(database.getName());
		if (CollectionUtils.isEmpty(keyColumnUsageList)) {
			LOGGER.warn("keyColumnUsageList is empty");
			return;
		}

		for (KeyColumnUsage keyColumnUsage : keyColumnUsageList) {
			Table table = tableMap.get(keyColumnUsage.getTableName());
			List<Column> columns = table.getColumns();
			table.addPkColumn(columns.get(keyColumnUsage.getOrdinalPosition().intValue() - 1), keyColumnUsage.getOrdinalPosition().intValue() - 1);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		includedDatabases = binlogProps.getIncludedDatabases();
		ignoredTables = binlogProps.getIgnoredTables();
	}
}
