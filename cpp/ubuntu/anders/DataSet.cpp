#include "DataSet.h"

void DataSet::SetValue(int rowNum, string fieldName, string fieldValue)
{
	ROW row;
	DATA_SET::iterator it = ds.find(rowNum);
	if (it == ds.end())
	{
		ds.insert(pair<int, ROW>(rowNum, row));
	}
	else
	{
		row = it->second;
	}
	
	row.insert(pair<string, string>(fieldName, fieldValue));
}

string DataSet::GetValue(int rowNum, string fieldName)
{
	string returnValue;
	ROW row;
	DATA_SET::iterator it = ds.find(rowNum);
	if (it != ds.end())
	{
		ROW::iterator rowIt = it->second.find(fieldName);
		if (rowIt != it->second.end())
			returnValue = rowIt->second;
	}

	return returnValue;
}

int DataSet::GetSize()
{
	if (ds.empty())
		return -1;
	else
		return ds.size();
}