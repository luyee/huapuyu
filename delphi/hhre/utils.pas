unit utils;

interface

uses
  DBXFirebird, SqlExpr, Forms, Provider, SimpleDS, DBClient, SysUtils, constant;

type
  // 单例模式
  TUtils = class
  strict private
    class var instance: TUtils;
    constructor Create;
  public
    function GetConnection: TSQLConnection;
    function GetQuery(const conn: TSQLConnection = nil): TSQLQuery;
    function GetSimpleDataSet(const conn: TSQLConnection = nil): TSimpleDataSet;
    class function GetInstance: TUtils;
    class procedure Free(const query: TSQLQuery; const conn: TSQLConnection = nil); overload;
    class procedure Free(const ds: TSimpleDataSet; const conn: TSQLConnection = nil); overload;
  end;

implementation

constructor TUtils.Create;
begin
  inherited;
end;

class procedure TUtils.Free(const query: TSQLQuery; const conn: TSQLConnection);
begin
  query.Close;
  query.Free;

  if conn <> nil then
  begin
    if conn.Connected then
      conn.Close;
    conn.Free;
  end;
end;

class procedure TUtils.Free(const ds: TSimpleDataSet; const conn: TSQLConnection);
begin
  ds.Close;
  ds.Free;

  if conn <> nil then
  begin
    if conn.Connected then
      conn.Close;
    conn.Free;
  end;
end;

function TUtils.GetConnection: TSQLConnection;
var
  conn: TSQLConnection;
begin
  conn := TSQLConnection.Create(Application);
  conn.DriverName := DB_DRIVER_NAME;
  conn.Params.Values['User_Name'] := DB_USER_NAME;
  conn.Params.Values['Password'] := DB_PASSWORD;
  conn.Params.Values['Database'] := ExtractFilePath(Application.ExeName) + DB_FILE_NAME;
  // sqlCon.Params.Values['HostName'] := '127.0.0.1';
  conn.LoginPrompt := False;
  Result := conn;
end;

class function TUtils.GetInstance: TUtils;
begin
  { TODO -oAnders : 需要加锁 }
  if instance = nil then
  begin
    instance := TUtils.Create;
  end;

  Result := instance;
end;

function TUtils.GetQuery(const conn: TSQLConnection): TSQLQuery;
var
  query: TSQLQuery;
begin
  query := TSQLQuery.Create(Application);
  if conn <> nil then
    query.SQLConnection := conn
  else
    query.SQLConnection := GetConnection;

  Result := query;
end;

function TUtils.GetSimpleDataSet(const conn: TSQLConnection): TSimpleDataSet;
var
  sds: TSimpleDataSet;
begin
  sds := TSimpleDataSet.Create(Application);
  if conn <> nil then
    sds.Connection := conn
  else
    sds.Connection := GetConnection;

  Result := sds;
end;

end.
