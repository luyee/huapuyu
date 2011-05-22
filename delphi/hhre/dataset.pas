unit dataset;

interface

uses
  Generics.Collections, constant, SysUtils;

type
  TDataSet = class
  strict private
    ds: TDictionary<Integer, TDictionary<string, string>>;
  public
    constructor Create;
    procedure SetValue(const rowNum: Integer; const fieldName, fieldValue: string);
    function getValue(const rowNum: Integer; const fieldName: string): string;
    function getSize: Integer;
  end;

implementation

constructor TDataSet.Create;
begin
  Self.ds := TDictionary<Integer, TDictionary<string, string>>.Create;
end;

function TDataSet.getSize: Integer;
begin
  if ds = nil then
    Result := RETURN_ZERO
  else
    Result := ds.Count;
end;

function TDataSet.getValue(const rowNum: Integer; const fieldName: string): string;
var
  row: TDictionary<string, string>;
  returnValue: string;
begin
  Result := EmptyStr;
  if Self.ds.TryGetValue(rowNum, row) then
    if row.TryGetValue(fieldName, returnValue) then
      Result := returnValue;
end;

procedure TDataSet.SetValue(const rowNum: Integer; const fieldName, fieldValue: string);
var
  row: TDictionary<string, string>;
begin
  if not Self.ds.TryGetValue(rowNum, row) then
  begin
    row := TDictionary<string, string>.Create;
    ds.Add(rowNum, row);
  end;

  row.Add(fieldName, fieldValue);
end;

end.
