unit shh;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls, Dialogs, ComCtrls, common,
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
begin
  cds.Data := TSql.ExecuteQuery(HOUSE_QUERY);
  TCommon.AutoCfgDBGridSize(cds, dbg);
end;

end.
