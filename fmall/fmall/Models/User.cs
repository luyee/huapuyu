using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace fmall.Models
{
    public class User
    {
        public virtual long? id { set; get; }
        public virtual string name { set; get; }
        public virtual string pwd { set; get; }
    }
}