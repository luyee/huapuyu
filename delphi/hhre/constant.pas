unit constant;

interface

const
  //����ֵ
  RETURN_FALSE = 0;

  //����
  CFG_FILE_NAME = 'config.ini';

  //���ݿ�
  DB_DRIVER_NAME = 'FIREBIRD';
  DB_FILE_NAME = 'system.dll';
  DB_USER_NAME = 'sysdba';
  DB_PASSWORD = 'masterkey';

  //��֤
  EMPTY_STR_PROMPT = '������Ϊ��ֵ��';
  REG_EX_PROMPT = '���������';
  TWO_CONTROLS_GREAT = '��%s����С��%s��';
  TWO_CONTROLS_SMALL = '��%s���ܴ���%s��';

  //�ֶ�ֵ
  BUILDING_NAME = '¥������';
  ADDRESS = '��ϸ��ַ';
  PRICE = '�ۼ�';
  BUILDING_AREA = '�������';
  USABLE_AREA = 'ʹ�����';
  BEDROOM = '��';
  LIVING_ROOM = '��';
  KITCHEN = '��';
  WASHROOM = '��';
  BALCONY = '��̨';
  TOTAL_FLOOR = '��¥��';
  FLOOR = '����¥��';

  //�������ʽ
  TWO_DECIMALS = '^\d{1,5}(.\d{1,2})?$';
  TWO_FIGURES = '^\d{1,2}$';
  THREE_FIGURES = '^\d{1,3}$';

  //��ʽ��
  FORMAT_TWO_DECIMALS = '0.00';
  FORMAT_TEN_THOUSAND = 10000;

  //�����ϵӳ��
  PRIMARY_KEY_NAME = 'ID';
  INSERT_TEMPLATE = 'insert into %s (%s) values (%s) returning ID;';
  INSERT_WITHOUT_ID_TEMPLATE = 'insert into %s (%s) values (%s);';
  UPDATE_TEMPLATE = 'update %s set %s where %s;';
  DELETE_TEMPLATE = 'delete from %s where %s;';
  SELECT_BY_ID_TEMPLATE = 'select * from %s where ID = %s;';

  //��ѯ
  HOUSE_QUERY = 'select ID,'+
  'NAME "¥������",'+
  'ADDRESS "��ַ",'+
  'PRICE "�۸���Ԫ/�ף�",'+
  'BUILDING_AREA "���������ƽ���ף�",'+
  'USABLE_AREA "ʹ�������ƽ���ף�",'+
  'round(UNIT_PRICE,2) "���ۣ�Ԫ/ƽ���ף�",'+
  'HOUSING_TYPE "����",'+
  'DIRECTION "����",'+
  'FLOOR "����¥��/��¥��",'+
  'DECORATION "װ�޳̶�",'+
  'CONSTRUCT_YEAR "�������"'+
  'from VI_SECOND_HAND_HOUSE;';

implementation

end.