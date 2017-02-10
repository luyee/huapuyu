using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace fmall.Models
{
    public abstract class BaseBO
    {
        public virtual long? id { set; get; }
        public virtual bool? enabled { set; get; }
        public virtual User addUser { set; get; }
        //public virtual DateTime addTime { set { addTime = value; } get { return DateTime.Now; } }
        public virtual DateTime addTime { set; get; }
        public virtual User updateUser { set; get; }
        public virtual DateTime updateTime { set; get; }
        public virtual int? version { set; get; }
    }
}