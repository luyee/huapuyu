using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Transaction;
using System.Data;

namespace spring.ibatis
{
    class IbatisTransactionObjectSupport : ISavepointManager
    {
        public IsolationLevel previousIsolationLevel { set; get; }
        public bool savepointAllowed { set; get; }
        public SqlMapperHolder sqlMapperHolder { set; get; }

        protected IbatisTransactionObjectSupport()
        {
        }

        public void CreateSavepoint(string savepointName)
        {
            throw new NotImplementedException();
        }

        public void ReleaseSavepoint(string savepoint)
        {
            throw new NotImplementedException();
        }

        public void RollbackToSavepoint(string savepoint)
        {
            throw new NotImplementedException();
        }

        public bool HasSqlMapHolder
        {
            get
            {
                return (this.sqlMapperHolder != null);
            }
        }
    }
}
