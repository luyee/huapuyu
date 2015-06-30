using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Mapping.Attributes;

namespace fmall.Models
{
    public class User : BaseBO
    {
        public virtual string userName { set; get; }
        public virtual string password { set; get; }
        public virtual string name { set; get; }
        public virtual string email { set; get; }
    }
}