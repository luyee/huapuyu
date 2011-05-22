unit constant;

interface

const
  //返回值
  RETURN_ZERO = 0;

  //配置
  CFG_FILE_NAME = 'config.ini';

  //数据库
  DB_DRIVER_NAME = 'FIREBIRD';
  DB_FILE_NAME = 'system.dll';
  DB_USER_NAME = 'sysdba';
  DB_PASSWORD = 'masterkey';

  //错误信息
  ERROR_EMPTY_STR = '：不能为空值！';
  ERROR_REG_EXPR = '：输入错误！';
  ERROR_TWO_VALUE_GREAT = '：%s不能小于%s！';
  ERROR_TWO_VALUE_SMALL = '：%s不能大于%s！';

  //字段值
  BUILDING_NAME = '楼盘名称';
  ADDRESS = '详细地址';
  PRICE = '售价';
  BUILDING_AREA = '建筑面积';
  USABLE_AREA = '使用面积';
  BEDROOM = '室';
  LIVING_ROOM = '厅';
  KITCHEN = '厨';
  WASHROOM = '卫';
  BALCONY = '阳台';
  TOTAL_FLOOR = '总楼层';
  FLOOR = '所在楼层';
  GO = '跳转';

  //正则表达式
  REG_TWO_DECIMALS = '^\d{1,5}(.\d{1,2})?$';
  REG_TWO_FIGURES = '^\d{1,2}$';
  REG_THREE_FIGURES = '^\d{1,3}$';
  REG_FIGURES = '^\d$';

  //格式化
  FORMAT_TWO_DECIMALS = '0.00';
  FORMAT_TEN_THOUSAND = 10000;

  //对象关系映射
  PRIMARY_KEY_NAME = 'id';

  COUNT_PER_PAGE = 2;
  FIRST_ROW_NUM = 0;
  FIRST_PAGE_NUM = 1;

  //SQL语句模板
  SQL_INSERT_RETURN_PK_TEMPLATE = 'INSERT INTO %s (%s) VALUES (%s) RETURNING id;';
  SQL_INSERT_TEMPLATE = 'INSERT INTO %s (%s) VALUES (%s);';
  SQL_UPDATE_TEMPLATE = 'UPDATE %s SET %s WHERE %s;';
  SQL_DELETE_TEMPLATE = 'DELETE FROM %s WHERE %s;';
  SQL_SELECT_BY_ID_TEMPLATE = 'SELECT * FROM %s WHERE id = %s;';

  //SQL语句
  SQL_SHH_QUERY = 'SELECT FIRST 2 SKIP %d id "编号",'+
  'name "楼盘名称",'+
  'address "地址",'+
  'price "价格（万元/套）",'+
  'building_area "建筑面积（平方米）",'+
  'usable_area "使用面积（平方米）",'+
  'ROUND(unit_price,2) "单价（元/平方米）",'+
  'housing_type "户型",'+
  'direction "朝向",'+
  'floor "所在楼层 / 总楼层",'+
  'decoration "装修程度",'+
  'construct_year "建筑年代"'+
  'FROM vi_second_hand_house ORDER BY id;';
  SQL_SHH_COUNT = 'SELECT COUNT(id) c FROM vi_second_hand_house';

  //按钮内容
  BTN_OK = '确定';

  PAGE_TITLE = '共%d条记录 共%d页 当前显示第%d页';

  PAGE_NUM = '第%d页';

implementation

end.
