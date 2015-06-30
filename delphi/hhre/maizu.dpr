program maizu;

uses
  Forms,
  main in 'main.pas' {FrmMain},
  shh in 'shh.pas' {FrmShh},
  about in 'about.pas' {FrmAbout},
  buyer in 'buyer.pas' {FrmBuyer},
  seller in 'seller.pas' {FrmSeller},
  shhEdit in 'shhEdit.pas' {FrmEdit},
  shhNew in 'shhNew.pas' {FrmNew},
  config in 'config.pas',
  common in 'common.pas',
  validator in 'validator.pas',
  sqlScript in 'sqlScript.pas',
  page in 'page.pas',
  Vcl.Themes,
  Vcl.Styles;

{$R *.RES}

begin
  Application.Initialize;
  Application.Title := '卖猪房地产软件';
  Application.CreateForm(TfrmMain, frmMain);
  Application.CreateForm(TfrmAbout, frmAbout);
  Application.Run;
end.
