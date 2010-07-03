using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ash.Dao.Interf;
using ash.Service;

namespace ash.Controllers
{
    [HandleError]
    public class HomeController : Controller
    {
        public string name { set; get; }

        public ITb_userDao dao { set; get; }

        public ITb_userService service { set; get; }

        public ActionResult Index()
        {
            ViewData["Message"] = "Welcome to ASP.NET MVC!";

            //using (IApplicationContext ctx = ContextRegistry.GetContext())
            //{
            //    ITest i = (ITest)ctx.GetObject("t");
            //    string s = i.printmsg();


            //    ISessionFactory sf = (ISessionFactory)ctx.GetObject("NHibernateSessionFactory");
            //    ISession ss = sf.OpenSession();
            //    ITransaction tx = ss.BeginTransaction();

            //    Tb_user model = new Tb_user();
            //    model.name = "guolili";
            //    model.pwd = "123";

            //    ss.Save(model); 

            //    tx.Commit();


            //}

            //Tb_user t1 = new Tb_user();
            //t1.id = 123;
            //t1.name = "zhuzhen"; 
            //t1.pwd = "321";
            //dao.save(t1);

            //Tb_user t2 = new Tb_user();
            //t2.id = 321;
            //t2.name = "guolili";
            //t2.pwd = "123";
            //dao.save(t2);

            //service.test();

            return View();
        }

        public ActionResult About()
        {
            return View();
        }
    }
}
