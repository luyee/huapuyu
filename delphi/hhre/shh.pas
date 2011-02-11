unit shh;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls, Dialogs, ComCtrls,
  Grids, DBGrids, ExtCtrls, sql, DB, DBClient, mHouse, SysUtils, constant, shhNew, shhEdit;

type
  TFrmShh = class(TForm)
    pnlControl: TPanel;
    dbg: TDBGrid;
    btnNew: TButton;
    btnQuery: TButton;
    ds: TDataSource;
    cds: TClientDataSet;
    btnAdvQry: TButton;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure btnNewClick(Sender: TObject);
    procedure btnQueryClick(Sender: TObject);
    procedure dbgCellClick(Column: TColumn);
  private
    procedure Query;
    procedure AdvQuery;
  public
    { Public declarations }
  end;

implementation

{$R *.dfm}

procedure TFrmShh.AdvQuery;
begin

end;

procedure TFrmShh.btnNewClick(Sender: TObject);
begin
  TFrmNew.Create(Application).ShowModal;
end;

procedure TFrmShh.btnQueryClick(Sender: TObject);
begin
  Query;
end;

procedure TFrmShh.dbgCellClick(Column: TColumn);
var
  fieldList: TFieldList;
  frmEdit: TFrmEdit;
begin
  frmEdit := TFrmEdit.Create(Application);
  frmEdit.SetId(cds.FieldByName('ID').AsInteger);
  frmEdit.ShowModal;
end;

procedure TFrmShh.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Action := caFree;
end;

procedure TFrmShh.Query;
var
  col: TColumn;
  field: TField;
begin
  cds.Data := TSql.ExecuteQuery(HOUSE_QUERY);

  for field in cds.Fields do
  begin
    if not SameStr(field.FieldName, PRIMARY_KEY_NAME) then
    begin
      col := dbg.Columns.Add;
      col.FieldName := field.FieldName;
      col.Title.Alignment := taCenter;
      col.Width := (dbg.Width - 30) div cds.FieldCount;
      col.Alignment := taCenter;
    end;
  end;
end;

end.
