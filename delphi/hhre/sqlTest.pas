unit sqlTest;

interface

uses
  TestFramework, DB, DBXFirebird, constant, sql, SysUtils, DBClient, SimpleDS, Variants, utils,
  DBXCommon, SqlExpr;

type
  TestTSql = class(TTestCase)
  strict private
    FSql: TSql;
  public
    procedure SetUp; override;
    procedure TearDown; override;
  published
    procedure TestExecuteSql;
    procedure TestExecuteSqlNoTrans;
    procedure TestExecuteQuery;
    procedure TestExecuteCount;
  end;

implementation

procedure TestTSql.SetUp;
begin
  FSql := TSql.Create;
end;

procedure TestTSql.TearDown;
begin
  FSql.Free;
  FSql := nil;
end;

procedure TestTSql.TestExecuteSql;
var
  ReturnValue: Integer;
  sql: string;
begin
  // TODO: Setup method call parameters
  ReturnValue := FSql.ExecuteSql('select * from test');
  // TODO: Validate method results
end;

procedure TestTSql.TestExecuteSqlNoTrans;
var
  ReturnValue: Integer;
  sql: string;
begin
  // TODO: Setup method call parameters
  ReturnValue := FSql.ExecuteSqlNoTrans('select * from test');
  // TODO: Validate method results
end;

procedure TestTSql.TestExecuteQuery;
var
  ReturnValue: OleVariant;
  sql: string;
begin
  // TODO: Setup method call parameters
  ReturnValue := FSql.ExecuteQuery('select * from test');
  // TODO: Validate method results
end;

procedure TestTSql.TestExecuteCount;
var
  ReturnValue: Integer;
  fieldAsName: string;
  sql: string;
begin
  // TODO: Setup method call parameters
  ReturnValue := FSql.ExecuteCount('select count(*) c from test');
  // TODO: Validate method results
end;

initialization
  // Register any test cases with the test runner
  RegisterTest(TestTSql.Suite);
end.

