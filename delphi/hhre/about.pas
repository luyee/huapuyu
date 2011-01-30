unit about;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls,
  Buttons, ExtCtrls, DBXFirebird, DB, SqlExpr, Dialogs, DBXOracle, DBXMySQL,
  Grids, DBGrids, DBClient, Provider, FMTBcd, DBCtrls, SimpleDS, SysUtils, sql,
  ComCtrls, house;

type
  TFrmAbout = class(TForm)
    SQLQuery1: TSQLQuery;
    Memo1: TMemo;
    procedure Button1Click(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmAbout: TFrmAbout;

implementation

{$R *.dfm}

procedure TFrmAbout.Button1Click(Sender: TObject);
//var
//  sqlCon: TSQLConnection;
//  sqlQuery: TSQLQuery;
begin
//  sqlCon := TSQLConnection.Create(Application);
//  sqlCon.DriverName := 'FIREBIRD';
//  sqlCon.Params.Values['User_Name'] := 'sysdba';
//  sqlCon.Params.Values['Password'] := 'masterkey';
//  sqlCon.Params.Values['Database'] := ExtractFilePath(Application.ExeName) + 'system.dll';
  // sqlCon.Params.Values['HostName'] := '127.0.0.1';
//  sqlCon.LoginPrompt := False;
//
//  ShowMessage(ExtractFilePath(Application.ExeName) + 'system.dll');

  // ShowMessage(SQLConnection1.Params.Values['DriverName']);
  // sqlCon.Params.SaveToFile('c:\\ccc.txt');

  // sqlCon.Open;
  //
  // Self.SQLDataSet1.CommandText := 'select * from tb_user';
  // Self.SQLDataSet1.Open;
  //
  // Self.ClientDataSet1.Open;

//  ComboBoxEx1.ItemsEx.Add.ID := 22;
//       ComboBoxEx1.ItemsEx.Add.ID := 23;


//  ComboBox1.Items.AddObject('zhuzhen', TObject(23));
//  ComboBox1.Items.AddObject('guolili', TObject(24));

//  ShowMessage(IntToStr(Integer(ComboBox1.Items.Objects[ComboBox1.ItemIndex])));



//       var a: PString;
//begin
//  New(a);
//  a^ := 'code';
//  // 如果是Integer的话，可直接ComboBox1.Items.AddObject('item1', TObject(1234))，因为Integer是4Bytes
//  ComboBox1.Items.AddObject('item1', TObject(a));
//  ShowMessage(PString(ComboBox1.Items.Objects[0])^);
//end;

//  sqlQuery := TSQLQuery.Create(Application);
//  sqlQuery.SQLConnection := sqlCon;
//  sqlQuery.SQL.Text := 'select * from tb_province';
//  sqlQuery.Open;
//  while not sqlQuery.Eof do
//  begin
//    ShowMessage(sqlQuery.FieldValues['name']);
//    sqlQuery.Next;
//  end;
end;

procedure TFrmAbout.Button2Click(Sender: TObject);
begin
//  self.ClientDataSet1.Data := SimpleDataSet1.data;
//  self.ClientDataSet2.data := SimpleDataSet1.Delta;
end;

procedure TFrmAbout.Button3Click(Sender: TObject);
begin
//  self.ClientDataSet4.Data :=  TSql.ExecuteQuery('select * from test');
end;

procedure TFrmAbout.FormShow(Sender: TObject);
var
  params: TParams;
  t: THouse;
  f: Double;
begin
//  ComboBox1.Items.AddObject('zhuzhen', TObject(23));
//  ComboBox1.Items.AddObject('guolili', TObject(24));

  f := -99;
  if f = -99 then
    ShowMessage('true');


  params := TParams.Create(Application);
  with params.AddParameter do
  begin
    Name := 'id';
    Value := '2';
  end;
  TSql.ExecuteQueryDS('SELECT id, name FROM cfg_area WHERE type = 1 and parent_id = :id', params);


  Self.SQLQuery1.Params.AddParameter;
  Self.SQLQuery1.Params.AddParameter;
  Self.SQLQuery1.Params.AddParameter;
  Self.SQLQuery1.Params.AddParameter;


  t := THouse.Create;
  t.Id := 24;
  t.Name := 'guolili';
  t.Address := 'zhenjiang';

  ShowMessage(IntToStr(t.DistrictId));


  Memo1.Lines.Clear;
  Memo1.Lines.Add(t.DeleteByIdWithSql)  ;
end;

end.
