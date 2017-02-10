object FrmAbout: TFrmAbout
  Left = 445
  Top = 127
  BorderStyle = bsDialog
  Caption = #20851#20110
  ClientHeight = 720
  ClientWidth = 882
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clBlack
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poScreenCenter
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Memo1: TMemo
    Left = 64
    Top = 48
    Width = 665
    Height = 345
    Lines.Strings = (
      'Memo1')
    TabOrder = 0
  end
  object Button1: TButton
    Left = 456
    Top = 400
    Width = 75
    Height = 25
    Caption = 'Button1'
    TabOrder = 1
    OnClick = Button1Click
  end
  object SQLQuery1: TSQLQuery
    Params = <
      item
        DataType = ftUnknown
        ParamType = ptUnknown
      end>
    Left = 304
    Top = 432
  end
end
