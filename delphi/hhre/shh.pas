unit shh;

interface

uses Windows, Classes, Graphics, Forms, Controls, StdCtrls, Dialogs, ComCtrls,
  Grids, DBGrids, ExtCtrls;

type
  TFrmShh = class(TForm)
    Panel1: TPanel;
    DBGrid1: TDBGrid;
    btnNew: TButton;
    btnQuery: TButton;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure btnNewClick(Sender: TObject);
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

procedure TFrmShh.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Action := caFree;
end;

end.
