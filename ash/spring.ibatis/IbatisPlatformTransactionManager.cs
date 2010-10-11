using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Objects.Factory;
using Spring.Transaction.Support;
using IBatisNet.DataMapper;
using Spring.Transaction;

namespace spring.ibatis
{
    public class IbatisPlatformTransactionManager : AbstractPlatformTransactionManager, IInitializingObject
    {
        private ISqlMapper sqlMapper { set; get; }

        public IbatisPlatformTransactionManager()
        {
        }

        public IbatisPlatformTransactionManager(ISqlMapper sqlMapper)
        {
            this.sqlMapper = sqlMapper;
        }

        protected override bool IsExistingTransaction(object transaction)
        {
            IbatisTransactionObject obj = (IbatisTransactionObject)transaction;
            return ((obj.sqlMapperHolder != null) && obj.sqlMapperHolder.TransactionActive);
        }

        protected override void DoBegin(object transaction, ITransactionDefinition definition)
        {
            if (this.sqlMapper == null)
            {
                throw new ArgumentException("SqlMapper is required to be set on IbatisPlatformTransactionManager");
            }
            this.sqlMapper.BeginTransaction(); ;
        }

        protected override void DoCommit(DefaultTransactionStatus status)
        {
            this.sqlMapper.CommitTransaction();
        }

        protected override object DoGetTransaction()
        {
            IbatisTransactionObject obj = new IbatisTransactionObject();
            obj.savepointAllowed = base.NestedTransactionsAllowed;
            SqlMapperHolder sqlMapperHolder = (SqlMapperHolder)TransactionSynchronizationManager.GetResource(this.sqlMapper);
            obj.SetSqlMapHolder(sqlMapperHolder, false);
            return obj;
        }

        protected override void DoRollback(DefaultTransactionStatus status)
        {
            this.sqlMapper.RollBackTransaction();
        }

        public void AfterPropertiesSet()
        {
        }

        private class IbatisTransactionObject : IbatisTransactionObjectSupport
        {
            private bool newSqlMapHolder;

            public void SetRollbackOnly()
            {
                base.sqlMapperHolder.RollbackOnly = true;
            }

            public void SetSqlMapHolder(SqlMapperHolder sqlMapperHolder, bool newSqlMap)
            {
                base.sqlMapperHolder = sqlMapperHolder;
                this.newSqlMapHolder = newSqlMap;
            }

            public bool HasTransaction
            {
                get
                {
                    return ((base.sqlMapperHolder != null) && base.sqlMapperHolder.TransactionActive);
                }
            }

            public bool NewSqlMapHolder
            {
                get
                {
                    return this.newSqlMapHolder;
                }
            }

            public bool RollbackOnly
            {
                get
                {
                    return base.sqlMapperHolder.RollbackOnly;
                }
            }
        }
    }
}
