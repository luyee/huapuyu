object FrmMain: TFrmMain
  Left = 194
  Top = 111
  Caption = #22831#22831#25151#20135#20013#20171#31649#29702#36719#20214
  ClientHeight = 530
  ClientWidth = 736
  Color = clAppWorkSpace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clBlack
  Font.Height = -11
  Font.Name = 'Default'
  Font.Style = []
  FormStyle = fsMDIForm
  Menu = mmMain
  OldCreateOrder = False
  Position = poDefault
  WindowState = wsMaximized
  PixelsPerInch = 96
  TextHeight = 13
  object sbMain: TStatusBar
    Left = 0
    Top = 511
    Width = 736
    Height = 19
    Margins.Left = 2
    Margins.Top = 2
    Margins.Right = 2
    Margins.Bottom = 2
    Panels = <>
    SimplePanel = True
    ExplicitTop = 517
    ExplicitWidth = 742
  end
  object mmMain: TMainMenu
    AutoHotkeys = maManual
    Left = 256
    Top = 72
    object itemSetting: TMenuItem
      Caption = #31995#32479#35774#32622
      object N1: TMenuItem
        Caption = '-'
      end
      object itemExit: TMenuItem
        Caption = #36864#20986#31995#32479
        OnClick = itemExitClick
      end
    end
    object itemBargain: TMenuItem
      Caption = #25151#23627#20080#21334
      object itemSeller: TMenuItem
        Caption = #21334#23478#31649#29702
      end
      object itemBuyer: TMenuItem
        Caption = #20080#23478#31649#29702
      end
      object itemShh: TMenuItem
        Caption = #20108#25163#25151#31649#29702
        OnClick = itemShhClick
      end
    end
    object itemRent: TMenuItem
      Caption = #25151#23627#31199#36161
      object itemLessor: TMenuItem
        Caption = #20986#31199#20154#31649#29702
      end
      object itemTenant: TMenuItem
        Caption = #25215#31199#20154#31649#29702
      end
      object itemHouse: TMenuItem
        Caption = #20986#31199#25151#31649#29702
      end
    end
    object itemHelp: TMenuItem
      Caption = #24110#21161
      object itemAbout: TMenuItem
        Caption = #20851#20110
        OnClick = itemAboutClick
      end
    end
  end
end
