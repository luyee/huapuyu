using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Objects.Factory.Config;
using Spring.Core.IO;
using IBatisNet.Common.Utilities;
using System.IO;
using IBatisNet.DataMapper;
using IBatisNet.DataMapper.Configuration;
using Spring.Context;

namespace spring.ibatis
{
    public abstract class SqlMapperFactoryObject : AbstractFactoryObject, IApplicationContextAware
    {
        private IResource Config { set; get; }
        private ConfigureHandler configureHandler { set; get; }
        private DomSqlMapBuilder sqlMapBuilder;
        private IApplicationContext applicationContext;

        protected override object CreateInstance()
        {
            if (this.Config == null)
            {
                return this.CreateUsingDefaultConfig();
            }
            return this.CreateUsingCustomConfig();
        }

        public override Type ObjectType
        {
            get
            {
                return typeof(ISqlMapper);
            }
        }

        public DomSqlMapBuilder SqlMapBuilder
        {
            get
            {
                if (this.sqlMapBuilder == null)
                {
                    this.sqlMapBuilder = new DomSqlMapBuilder();
                }
                return this.sqlMapBuilder;
            }
            set
            {
                this.sqlMapBuilder = value;
            }
        }

        protected string GetConfigFileName()
        {
            FileInfo file = null;
            try
            {
                file = this.Config.File;
            }
            catch (IOException)
            {
                throw new ArgumentException("Can not find IBatis config file.");
            }
            return file.Name;
        }

        private object CreateUsingCustomConfig()
        {
            if (this.configureHandler != null)
            {
                return this.SqlMapBuilder.ConfigureAndWatch(this.Config.File, this.configureHandler);
            }
            using (Stream stream = this.Config.InputStream)
            {
                return this.SqlMapBuilder.Configure(stream);
            }
        }

        private object CreateUsingDefaultConfig()
        {
            if (this.configureHandler != null)
            {
                return this.SqlMapBuilder.ConfigureAndWatch(this.configureHandler);
            }
            return this.SqlMapBuilder.Configure();
        }

        public IApplicationContext ApplicationContext
        {
            set { this.applicationContext = value; }
        }
    }
}
