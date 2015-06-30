using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Spring.Context;

namespace ash.others
{
    public class MyApplicationContextAware : IApplicationContextAware
    {
        public IApplicationContext ApplicationContext
        {
            set { throw new NotImplementedException(); }
        }
    }
}