unit mSecondHandHouse;

interface

uses
  orm;

type
  [TTableInfo('TB_SECOND_HAND_HOUSE', '二手房')]
  TSecondHandHouse = class(TModel)
  strict private
    AId: Integer;
    APrice: Double;
    ABuildingArea: Double;
    AUsableArea: Double;
    APropertyId: Integer;
    APropTypeId: Integer;
    APropStructId: Integer;
    AConstructTypeId: Integer;
    AVisitTimeId: Integer;
  public
    [TFieldInfo('ID', '编号')]
    property Id: Integer read AId write AId;
    [TFieldInfo('PRICE', '售价')]
    property Price: Double read APrice write APrice;
    [TFieldInfo('BUILDING_AREA', '建筑面积')]
    property BuildingArea: Double read ABuildingArea write ABuildingArea;
    [TFieldInfo('USABLE_AREA', '使用面积')]
    property UsableArea: Double read AUsableArea write AUsableArea;
    [TFieldInfo('PROPERTY_ID', '产权性质')]
    property PropertyId: Integer read APropertyId write APropertyId;
    [TFieldInfo('PROP_TYPE_ID', '住宅类别')]
    property PropTypeId: Integer read APropTypeId write APropTypeId;
    [TFieldInfo('PROP_STRUCT_ID', '房屋结构')]
    property PropStructId: Integer read APropStructId write APropStructId;
    [TFieldInfo('CONSTRUCT_TYPE_ID', '建筑类别')]
    property ConstructTypeId: Integer read AConstructTypeId write AConstructTypeId;
    [TFieldInfo('VISIT_TIME_ID', '看房时间')]
    property VisitTimeId: Integer read AVisitTimeId write AVisitTimeId;
  end;

implementation

end.
