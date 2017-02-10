unit mFacility;

interface

uses
  orm;

type
  [TTableInfo('TB_FACILITY', '配套设施')]
  TFacility = class(TModel)
  strict private
    AHouseId: Integer;
    AFacilityId: Integer;
  public
    [TFieldInfo('HOUSE_ID', '房屋编号')]
    property HouseId: Integer read AHouseId write AHouseId;
    [TFieldInfo('FACILITY_ID', '配套设施编号')]
    property FacilityId: Integer read AFacilityId write AFacilityId;
  end;

implementation

end.
