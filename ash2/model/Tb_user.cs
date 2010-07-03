using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace model
{
    public class Tb_user
    {
        public virtual int? id { set; get; }
        public virtual string name { set; get; }
        public virtual string pwd { set; get; }
    }
}
