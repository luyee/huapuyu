using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace model
{
    public class User
    {
        public virtual long? id { set; get; }
        public virtual string name { set; get; }
        public virtual string pwd { set; get; }
    }
}
