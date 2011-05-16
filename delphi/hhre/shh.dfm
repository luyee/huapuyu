object FrmShh: TFrmShh
  Left = 197
  Top = 117
  Caption = #20108#25163#25151#31649#29702
  ClientHeight = 576
  ClientWidth = 742
  Color = clBtnFace
  ParentFont = True
  FormStyle = fsMDIChild
  OldCreateOrder = False
  Position = poScreenCenter
  Visible = True
  WindowState = wsMaximized
  OnClose = FormClose
  OnResize = FormResize
  PixelsPerInch = 96
  TextHeight = 13
  object pnlControl: TPanel
    Left = 0
    Top = 0
    Width = 742
    Height = 65
    Align = alTop
    BevelOuter = bvNone
    TabOrder = 0
    ExplicitWidth = 748
    object btnNew: TButton
      Left = 16
      Top = 21
      Width = 75
      Height = 25
      Caption = #26032#24314
      TabOrder = 0
      OnClick = btnNewClick
    end
    object btnQuery: TButton
      Left = 104
      Top = 21
      Width = 75
      Height = 25
      Caption = #26597#35810#20840#37096
      TabOrder = 1
      OnClick = btnQueryClick
    end
    object btnAdvQry: TButton
      Left = 192
      Top = 21
      Width = 75
      Height = 25
      Caption = #39640#32423#26597#35810
      TabOrder = 2
    end
  end
  object dbg: TDBGrid
    Left = 0
    Top = 65
    Width = 742
    Height = 511
    Align = alClient
    DataSource = ds
    Options = [dgTitles, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit, dgTitleClick, dgTitleHotTrack]
    TabOrder = 1
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'Tahoma'
    TitleFont.Style = []
    OnCellClick = dbgCellClick
  end
  object ds: TDataSource
    DataSet = cds
    Left = 392
    Top = 264
  end
  object cds: TClientDataSet
    Aggregates = <>
    Params = <>
    Left = 440
    Top = 264
  end
end
