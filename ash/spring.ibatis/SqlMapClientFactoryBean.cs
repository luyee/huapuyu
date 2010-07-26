using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Objects.Factory;
using IBatisNet.DataMapper;

namespace spring.ibatis
{
    class SqlMapClientFactoryBean : IFactoryObject, IInitializingObject, System.IDisposable
    {
        private ISqlMapper sqlMapper;
        
        public object GetObject()
        {
            return this.sqlMapper;
        }

        public bool IsSingleton
        {
            get
            {
                return true;
            }
        }

        public Type ObjectType
        {
            get 
            { 
                return (this.sqlMapper != null) ? this.sqlMapper.GetType() : typeof(ISqlMapper); 
            }
        }

        public void Dispose()
        {
        }



        public void AfterPropertiesSet()
        {
            throw new NotImplementedException();
        }
    }
}
