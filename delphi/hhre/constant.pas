unit constant;

interface

const
  //返回值
  RETURN_FALSE = 0;

  //配置
  CFG_FILE_NAME = 'config.ini';

  //数据库
  DB_DRIVER_NAME = 'FIREBIRD';
  DB_FILE_NAME = 'system.dll';
  DB_USER_NAME = 'sysdba';
  DB_PASSWORD = 'masterkey';

  //验证
  EMPTY_STR_PROMPT = '：不能为空值！';
  REG_EX_PROMPT = '：输入错误！';
  TWO_CONTROLS_GREAT = '：%s不能小于%s！';
  TWO_CONTROLS_SMALL = '：%s不能大于%s！';

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

  //正则表达式
  TWO_DECIMALS = '^\d{1,5}(.\d{1,2})?$';
  TWO_FIGURES = '^\d{1,2}$';
  THREE_FIGURES = '^\d{1,3}$';

  //格式化
  FORMAT_TWO_DECIMALS = '0.00';
  FORMAT_TEN_THOUSAND = 10000;

  //对象关系映射
  PRIMARY_KEY_NAME = 'ID';
  INSERT_TEMPLATE = 'insert into %s (%s) values (%s) returning ID;';
  INSERT_WITHOUT_ID_TEMPLATE = 'insert into %s (%s) values (%s);';
  UPDATE_TEMPLATE = 'update %s set %s where %s;';
  DELETE_TEMPLATE = 'delete from %s where %s;';

implementation

end.
