unit sqlScript;

interface

uses
  SysUtils;

type
  TSqlScript = class
  strict private
    constructor Create;
  public
    //******************************
    //           cfg_data          *
    //******************************
    //省、自治区、直辖市：0
    class function GetProvince: string;
    //城市              ：1
    class function GetCity(const parent_id: string): string;
    //区、县、市        ：2
    class function GetDistrict(const parent_id: string): string;
    //******************************
    //           cfg_data          *
    //******************************
    //产权性质：1
    class function GetPropety: string;
    //朝向    ：2
    class function GetDirection: string;
    //物业类型：3
    class function GetPropMgtType: string;
    //住宅类别：4
    class function GetPropType: string;
    //房屋结构：5
    class function GetConstructType: string;
    //建筑类别：6
    class function GetPropStruct: string;
    //装修程度：7
    class function GetDecoration: string;
    //看房时间：8
    class function GetVisitTime: string;
    //配套设施：9
    class function GetFacility: string;
    //房源特色：10
    class function GetFeature: string;
    //建筑年代：11
    class function GetConstructYear: string;
  end;

implementation

constructor TSqlScript.Create;
begin
  inherited;
end;

//******************************
//           cfg_area          *
//******************************
class function TSqlScript.GetProvince: string;
begin
  Result := 'SELECT id, name FROM cfg_area WHERE type = 0';
end;

class function TSqlScript.GetCity(const parent_id: string): string;
begin
  Result := Format('SELECT id, name FROM cfg_area WHERE type = 1 and parent_id = %s', [parent_id]);
end;

class function TSqlScript.GetDistrict(const parent_id: string): string;
begin
  Result := Format('SELECT id, name FROM cfg_area WHERE type = 2 and parent_id = %s', [parent_id]);
end;

//******************************
//           cfg_data          *
//******************************
class function TSqlScript.GetPropety: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 1';
end;

class function TSqlScript.GetDirection: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 2';
end;

class function TSqlScript.GetPropMgtType: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 3';
end;

class function TSqlScript.GetPropType: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 4';
end;

class function TSqlScript.GetConstructType: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 5';
end;

class function TSqlScript.GetPropStruct: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 6';
end;

class function TSqlScript.GetDecoration: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 7';
end;

class function TSqlScript.GetVisitTime: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 8';
end;

class function TSqlScript.GetFacility: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 9';
end;

class function TSqlScript.GetFeature: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 10';
end;

class function TSqlScript.GetConstructYear: string;
begin
  Result := 'SELECT id, name FROM cfg_data WHERE type = 11';
end;

end.
