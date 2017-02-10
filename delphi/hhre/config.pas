unit config;

interface

uses
  IniFiles, SysUtils, Forms, constant;

type
  TConfig = class
  strict private
    constructor Create;
  public
    class function ReadConfigFile(const section, ident: string): string;
    class procedure WriteConfigFile(const section, ident, value: string);
    class function GetCustomizeProvince: string;
    class function GetCustomizeCity: string;
  end;

implementation

constructor TConfig.Create;
begin
  inherited;
end;

class function TConfig.GetCustomizeCity: string;
begin
  Result := ReadConfigFile('customize', 'city');
end;

class function TConfig.GetCustomizeProvince: string;
begin
  Result := ReadConfigFile('customize', 'province');
end;

class function TConfig.ReadConfigFile(const section, ident: string): string;
var
  iniFile: TIniFile;
begin
  iniFile := TIniFile.Create(ExtractFilePath(Application.ExeName) + CFG_FILE_NAME);
  Result := iniFile.ReadString(section, ident, EmptyStr);
  iniFile.Free;
end;

class procedure TConfig.WriteConfigFile(const section, ident, value: string);
var
  iniFile: TIniFile;
begin
  iniFile := TIniFile.Create(ExtractFilePath(Application.ExeName) + CFG_FILE_NAME);
  iniFile.WriteString(section, ident, value);
  iniFile.Free;
end;

end.
