using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Mapping.Attributes;

namespace fmall.Models
{
    [Class(Table = "Role")]
    public class Role
    {
        [Id(0, Name = "id", Column = "id")]
        [Generator(1, Class = "identity")]
        public long? id { set; get; }
        [Property(0, Name = "name", Column = "name")]
        public virtual string name { set; get; }
        [Property(0, Name = "role", Column = "role")]
        public virtual string role { set; get; }
        [Set(0,Name="users")]
        [Key(1,Column="role_id")]
        [OneToMany(2,Class="Role")]
        public virtual IList<User> users { set; get; }
    }
}