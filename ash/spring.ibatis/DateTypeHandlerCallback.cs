using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using IBatisNet.DataMapper.TypeHandlers;

namespace spring.ibatis
{
    public class DateTypeHandlerCallback : ITypeHandlerCallback
    {
        public object GetResult(IResultGetter getter)
        {
            if ((getter.Value == null) || (getter.Value == DBNull.Value))
            {
                return DateTime.MinValue;
            }
            return getter.Value;
        }

        public object NullValue
        {
            get
            {
                return DBNull.Value;
            }
        }

        public void SetParameter(IParameterSetter setter, object parameter)
        {
            if (parameter is DateTime)
            {
                DateTime time = (DateTime)parameter;
                if (time == DateTime.MinValue)
                {
                    setter.Value = null;
                    return;
                }
            }
            setter.Value = parameter;
        }

        public object ValueOf(string s)
        {
            return DateTime.Parse(s);
        }
    }
}
