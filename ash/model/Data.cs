using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace model
{
    public class Data
    {
        public virtual long? id { set; get; }
        public virtual string name { set; get; }
        public virtual int? type { set; get; }
        public virtual long? parentId { set; get; }
        public virtual int? enable { set; get; }
    }
}
