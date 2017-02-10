using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace model
{
    public class PollItem
    {
        public virtual long? id { set; get; }
        public virtual String title { set; get; }
        public virtual Boolean enable { set; get; }
        public virtual Poll poll { set; get; }
    }
}
