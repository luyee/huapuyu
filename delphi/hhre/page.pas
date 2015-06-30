unit page;

interface

uses
  constant, SysUtils;

type
  TPage = class
  strict private
    totalRows: Integer;
    currentPageNum: Integer;
    countPerPage: Integer;
  public
    constructor Create;
    function getTotalRows: Integer;
    procedure setTotalRows(const totalRows: Integer);
    function getCurrentPageNum: Integer;
    procedure setCurrentPageNum(const currentPageNum: Integer);
    function getCountPerPage: Integer;
    procedure setCountPerPage(const countPerPage: Integer);

    function hasFirst: Boolean;
    function hasPrior: Boolean;
    function hasNext: Boolean;
    function hasLast: Boolean;

//    function isFirst: Boolean;
//    function isLast: Boolean;

    function getTotalPages: Integer;
    function getTitle: String;

//    function getFirstNum: Integer;
//    function getPriorNum: Integer;
//    function getNextNum: Integer;
//    function getLastNum: Integer;

    function getFirstPageRowNum: Integer;
    function getPriorPageRowNum: Integer;
    function getNextPageRowNum: Integer;
    function getLastPageRowNum: Integer;

    function getRowNum(const pageNum: Integer): Integer;
  end;

implementation

constructor TPage.Create;
begin
  currentPageNum := FIRST_PAGE_NUM;
  countPerPage := COUNT_PER_PAGE;
end;

procedure TPage.setCountPerPage(const countPerPage: Integer);
begin
  Self.countPerPage := countPerPage;
end;

function TPage.getCountPerPage: Integer;
begin
  Result := Self.countPerPage;
end;

procedure TPage.setCurrentPageNum(const currentPageNum: Integer);
begin
  Self.currentPageNum := currentPageNum;
  if currentPageNum < FIRST_PAGE_NUM then
    Self.currentPageNum := FIRST_PAGE_NUM
  else if currentPageNum > getTotalPages then
    Self.currentPageNum := getTotalPages;
end;

function TPage.getCurrentPageNum: Integer;
begin
  Result := Self.currentPageNum;
end;

procedure TPage.setTotalRows(const totalRows: Integer);
begin
  Self.totalRows := totalRows;
end;

function TPage.getTotalRows: Integer;
begin
  Result := Self.totalRows;
end;

function TPage.getTotalPages: Integer;
var
  i: Integer;
begin
  if totalRows < 0 then
    Result := RETURN_ZERO;

  i := totalRows div countPerPage;
  if totalRows mod countPerPage > 0 then
    Inc(i);

  Result := i;
end;

//function TPage.getFirstNum: Integer;
//begin
//  Result := FIRST_PAGE_NUM;
//  setCurrentPageNum(FIRST_PAGE_NUM);
//end;

//function TPage.getPriorNum: Integer;
//begin
//  Result := currentPageNum - 1;
//  setCurrentPageNum(currentPageNum - 1);
//end;

//function TPage.getNextNum: Integer;
//begin
//  Result := currentPageNum + 1;
//  setCurrentPageNum(currentPageNum + 1);
//end;

//function TPage.getLastNum: Integer;
//begin
//  Result := getTotalPages;
//  setCurrentPageNum(getTotalPages);
//end;

function TPage.getRowNum(const pageNum: Integer): Integer;
begin
  setCurrentPageNum(pageNum);
  Result := (getCurrentPageNum - 1) * countPerPage;
end;

function TPage.getFirstPageRowNum: Integer;
begin
  Result := RETURN_ZERO;
  setCurrentPageNum(FIRST_PAGE_NUM);
end;

function TPage.getPriorPageRowNum: Integer;
begin
  Result := (currentPageNum - 2) * countPerPage;
  setCurrentPageNum(currentPageNum - 1);
end;

function TPage.getNextPageRowNum: Integer;
begin
  Result := currentPageNum * countPerPage;
  setCurrentPageNum(currentPageNum + 1);
end;

function TPage.getLastPageRowNum: Integer;
begin
  Result := (getTotalPages - 1) * countPerPage;
  setCurrentPageNum(getTotalPages);
end;

function TPage.hasFirst: Boolean;
begin
  Result := currentPageNum - 1 >= FIRST_PAGE_NUM;
end;

function TPage.hasPrior: Boolean;
begin
  Result := currentPageNum - 1 >= FIRST_PAGE_NUM;
end;

function TPage.hasNext: Boolean;
begin
  Result := currentPageNum + 1 <= getTotalPages;
end;

function TPage.hasLast: Boolean;
begin
  Result := currentPageNum + 1 <= getTotalPages;
end;

function TPage.getTitle: String;
begin
  Result := Format(PAGE_TITLE, [totalRows, getTotalPages, currentPageNum]);
end;

//function TPage.isFirst: Boolean;
//begin
//  Result := currentPageNum = FIRST_PAGE_NUM;
//end;

//function TPage.isLast: Boolean;
//begin
//  Result := currentPageNum = getTotalPages;
//end;

end.
