using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Objects.Factory;
using IBatisNet.DataMapper;
using Spring.Context;
using Spring.Objects.Factory.Config;
using IBatisNet.Common.Utilities;

namespace spring.ibatis
{
    class SqlMapClientFactoryBean1 : AbstractFactoryObject, IApplicationContextAware
    {
        private ConfigureHandler configureHandler;

        protected override object CreateInstance()
        {
            throw new NotImplementedException();
        }

        public override Type ObjectType
        {
            get { throw new NotImplementedException(); }
        }

        public IApplicationContext ApplicationContext
        {
            set { throw new NotImplementedException(); }
        }
    }
}
