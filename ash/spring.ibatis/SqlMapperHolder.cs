using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Transaction.Support;
using IBatisNet.DataMapper;

namespace spring.ibatis
{
    class SqlMapperHolder : ResourceHolderSupport
    {
        private ISqlMapper currentSqlMapper;
        private ISqlMapSession currentSqlMapSession;
        private bool transactionActive = false;

        public SqlMapperHolder(ISqlMapper sqlMapper, ISqlMapSession transaction)
        {
            this.currentSqlMapper = sqlMapper;
            this.currentSqlMapSession = transaction;
        }

        public override void Clear()
        {
            base.Clear();
            this.transactionActive = false;
        }

        public bool HasSqlMap
        {
            get
            {
                return (this.currentSqlMapper != null);
            }
        }

        public ISqlMapper SqlMap
        {
            get
            {
                return this.currentSqlMapper;
            }
            set
            {
                this.currentSqlMapper = value;
            }
        }

        public ISqlMapSession Transaction
        {
            get
            {
                return this.currentSqlMapSession;
            }
            set
            {
                this.currentSqlMapSession = value;
            }
        }

        public bool TransactionActive
        {
            get
            {
                return this.transactionActive;
            }
            set
            {
                this.transactionActive = value;
            }
        }
    }
}
