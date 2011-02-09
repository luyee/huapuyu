unit shh;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls, Dialogs, ComCtrls,
  Grids, DBGrids, ExtCtrls, sql, DB, DBClient;

type
  TFrmShh = class(TForm)
    Panel1: TPanel;
    dbg: TDBGrid;
    btnNew: TButton;
    btnQuery: TButton;
    ds: TDataSource;
    cds: TClientDataSet;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure btnNewClick(Sender: TObject);
    procedure btnQueryClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

implementation

uses shhNew;

{$R *.dfm}

procedure TFrmShh.btnNewClick(Sender: TObject);
begin
  TFrmNew.Create(Application).ShowModal;
end;

procedure TFrmShh.btnQueryClick(Sender: TObject);
var
  col: TColumn;
begin
  cds.Data := TSql.ExecuteQuery('select * from tb_house');

  col := dbg.Columns.Add;
  col.FieldName := 'NAME';
  col.Title.Caption := 'ÄãºÃ';
  col.Title.Alignment := taCenter;
end;

procedure TFrmShh.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Action := caFree;
end;

end.
