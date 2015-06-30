unit shh;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls, Dialogs, ComCtrls, common,
  Grids, DBGrids, ExtCtrls, sql, DB, DBClient, mHouse, SysUtils, constant, shhNew, shhEdit, page, validator;

type
  TFrmShh = class(TForm)
    pnlControl: TPanel;
    dbg: TDBGrid;
    btnNew: TButton;
    btnQuery: TButton;
    ds: TDataSource;
    cds: TClientDataSet;
    btnAdvQry: TButton;
    btnGo: TButton;
    edtGo: TEdit;
    btnLast: TButton;
    btnNext: TButton;
    btnPrior: TButton;
    btnFirst: TButton;
    sb: TStatusBar;
    cmbGo: TComboBox;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure btnNewClick(Sender: TObject);
    procedure btnQueryClick(Sender: TObject);
    procedure dbgCellClick(Column: TColumn);
    procedure FormResize(Sender: TObject);
    procedure btnNextClick(Sender: TObject);
    procedure btnPriorClick(Sender: TObject);
    procedure btnFirstClick(Sender: TObject);
    procedure btnLastClick(Sender: TObject);
    procedure exitControl(Sender: TObject);
    procedure btnGoClick(Sender: TObject);
    procedure cmbGoChange(Sender: TObject);
  private
    procedure Query;
    procedure AdvQuery;
    procedure EnablePageControl;
    procedure Validate(Sender: TWinControl);
  public
    { Public declarations }
  end;

var
  FrmShh: TFrmShh;
  page: TPage;

implementation

{$R *.dfm}

procedure TFrmShh.AdvQuery;
begin

end;

procedure TFrmShh.btnFirstClick(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getFirstPageRowNum]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.btnGoClick(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getRowNum(StrToInt(Trim(edtGo.Text)))]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.btnLastClick(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getLastPageRowNum]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.btnNewClick(Sender: TObject);
begin
  TFrmNew.Create(Application).ShowModal;
end;

procedure TFrmShh.btnNextClick(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getNextPageRowNum]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.btnPriorClick(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getPriorPageRowNum]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.btnQueryClick(Sender: TObject);
begin
  Query;
end;

procedure TFrmShh.cmbGoChange(Sender: TObject);
var
  sql: string;
begin
  sql := Format(SQL_SHH_QUERY, [page.getRowNum(Integer(cmbGo.Items.Objects[cmbGo.ItemIndex]))]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(sql);
  EnablePageControl;
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.dbgCellClick(Column: TColumn);
var
  frmEdit: TFrmEdit;
begin
  frmEdit := TFrmEdit.Create(Application);
  frmEdit.SetId(cds.FieldByName(PRIMARY_KEY_NAME).AsInteger);
  frmEdit.ShowModal;
end;

procedure TFrmShh.EnablePageControl;
begin
  if page.getTotalRows > 0 then
  begin
    dbg.Enabled := True;

    if page.hasFirst then
      btnFirst.Enabled := True
    else
      btnFirst.Enabled := False;

    if page.hasPrior then
      btnPrior.Enabled := True
    else
      btnPrior.Enabled := False;

    if page.hasNext then
      btnNext.Enabled := True
    else
      btnNext.Enabled := False;

    if page.hasLast then
      btnLast.Enabled := True
    else
      btnLast.Enabled := False;

    if page.getTotalPages > 1 then
    begin
      edtGo.Enabled := True;
      btnGo.Enabled := True;
      cmbGo.Enabled := True;
    end
    else
    begin
      edtGo.Enabled := False;
      btnGo.Enabled := False;
      cmbGo.Enabled := False;
    end;
  end
  else
  begin
    dbg.Enabled := False;
    btnFirst.Enabled := False;
    btnPrior.Enabled := False;
    btnNext.Enabled := False;
    btnLast.Enabled := False;
    edtGo.Enabled := False;
    btnGo.Enabled := False;
    cmbGo.Enabled := False;
  end;
end;

procedure TFrmShh.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Action := caFree;
end;

procedure TFrmShh.FormResize(Sender: TObject);
begin
  TCommon.AutoCfgDBGridSize(cds, dbg);
end;

procedure TFrmShh.Query;
var
  sql: String;
  i: Integer;
begin
  page := TPage.Create;
  page.setTotalRows(TSql.ExecuteCount(SQL_SHH_COUNT));
  cmbGo.Items.Clear;
  for i := 1 to page.getTotalPages do
    cmbGo.AddItem(Format(PAGE_NUM, [i]), TObject(i));
  cmbGo.ItemIndex := 0;
  EnablePageControl;
  sql := Format(SQL_SHH_QUERY, [FIRST_ROW_NUM]);
  ShowMessage(sql);
  cds.Data := TSql.ExecuteQuery(Format(SQL_SHH_QUERY, [FIRST_ROW_NUM]));
  TCommon.AutoCfgDBGridSize(cds, dbg);
  sb.Panels[0].Text := page.getTitle;
end;

procedure TFrmShh.Validate(Sender: TWinControl);
begin
  case Sender.TabOrder of
    7:
    begin
      TValidator.ValidFigures(edtGo, GO);
    end;
  end;
end;

procedure TFrmShh.exitControl(Sender: TObject);
begin
  if Sender is TEdit then
    Validate(Sender as TEdit);
end;

end.
