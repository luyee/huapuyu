using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using fmall.Dao;
using fmall.Models;

namespace fmall.Controllers
{
    public class IndexController : Controller
    {
        private UserDao userDao { set; get; }

        public ActionResult Index()
        {
            ViewBag.Message = "欢迎使用 ASP.NET MVC!";

            User user = new User();
            user.name = "zhuzhen";
            user.pwd = "123";
            userDao.save(user);

            return View();
        }

        public ActionResult About()
        {
            return View();
        }

    }
}
