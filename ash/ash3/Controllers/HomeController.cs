using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ash3.Dao.Hibernate;
using ash3.Service;
using model;
using Spring.Context.Support;
using System.Globalization;

namespace ash3.Controllers
{
    public class HomeController : Controller
    {
        private UserDao userDao { set; get; }
        private IUserService userService { set; get; }
        private ResourceSetMessageSource messageSource { set; get; }

        public ActionResult Index()
        {
            User user = new User();
            user.name = "zhuzhen";
            user.pwd = "123";
            userService.save(user);

            IList<User> userList = userService.getAll();
            System.Console.WriteLine(userList.Count);

            user = userService.getById(user.id);
            System.Console.WriteLine(user.name);

            user.pwd = "123456";
            userService.update(user);

            userService.delete(user);

            ViewBag.Message = messageSource.GetMessage("name", CultureInfo.CurrentUICulture);

            return View();
        }

        public ActionResult About()
        {
            return View();
        }
    }
}
