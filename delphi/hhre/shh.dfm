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
    Height = 51
    Align = alTop
    BevelInner = bvLowered
    TabOrder = 0
    DesignSize = (
      742
      51)
    object btnNew: TButton
      Left = 13
      Top = 13
      Width = 75
      Height = 25
      Caption = #26032#24314
      TabOrder = 0
      OnClick = btnNewClick
    end
    object btnQuery: TButton
      Left = 94
      Top = 13
      Width = 75
      Height = 25
      Caption = #26597#35810#20840#37096
      TabOrder = 1
      OnClick = btnQueryClick
    end
    object btnAdvQry: TButton
      Left = 175
      Top = 13
      Width = 75
      Height = 25
      Caption = #39640#32423#26597#35810
      TabOrder = 2
    end
    object btnGo: TButton
      Left = 678
      Top = 13
      Width = 51
      Height = 25
      Anchors = [akTop, akRight]
      Caption = #36339#36716
      Enabled = False
      TabOrder = 8
      OnClick = btnGoClick
    end
    object edtGo: TEdit
      Left = 621
      Top = 15
      Width = 51
      Height = 21
      Anchors = [akTop, akRight]
      Enabled = False
      TabOrder = 7
      OnExit = exitControl
    end
    object btnLast: TButton
      Left = 564
      Top = 13
      Width = 51
      Height = 25
      Anchors = [akTop, akRight]
      Caption = #23614#39029
      Enabled = False
      TabOrder = 6
      OnClick = btnLastClick
    end
    object btnNext: TButton
      Left = 507
      Top = 13
      Width = 51
      Height = 25
      Anchors = [akTop, akRight]
      Caption = #19979#19968#39029
      Enabled = False
      TabOrder = 5
      OnClick = btnNextClick
    end
    object btnPrior: TButton
      Left = 452
      Top = 13
      Width = 49
      Height = 25
      Anchors = [akTop, akRight]
      Caption = #19978#19968#39029
      Enabled = False
      TabOrder = 4
      OnClick = btnPriorClick
    end
    object btnFirst: TButton
      Left = 396
      Top = 13
      Width = 50
      Height = 25
      Anchors = [akTop, akRight]
      Caption = #39318#39029
      Enabled = False
      TabOrder = 3
      OnClick = btnFirstClick
    end
    object cmbGo: TComboBox
      Left = 328
      Top = 15
      Width = 62
      Height = 21
      Style = csDropDownList
      Enabled = False
      TabOrder = 9
      OnChange = cmbGoChange
    end
  end
  object dbg: TDBGrid
    Left = 0
    Top = 51
    Width = 742
    Height = 506
    Align = alClient
    DataSource = ds
    Enabled = False
    Options = [dgTitles, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit, dgTitleClick, dgTitleHotTrack]
    TabOrder = 1
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'Tahoma'
    TitleFont.Style = []
    OnCellClick = dbgCellClick
  end
  object sb: TStatusBar
    Left = 0
    Top = 557
    Width = 742
    Height = 19
    Panels = <
      item
        Alignment = taCenter
        Width = 50
      end>
    ExplicitLeft = 440
    ExplicitTop = 480
    ExplicitWidth = 0
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
