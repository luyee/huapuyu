unit mFeature;

interface

uses
  orm;

type
  [TTableInfo('TB_FEATURE', '房源特色')]
  TFeature = class(TModel)
  strict private
    AHouseId: Integer;
    AFeatureId: Integer;
  public
    [TFieldInfo('HOUSE_ID', '房屋编号')]
    property HouseId: Integer read AHouseId write AHouseId;
    [TFieldInfo('FEATURE_ID', '房源特色编号')]
    property FeatureId: Integer read AFeatureId write AFeatureId;
  end;

implementation

end.
