﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ash.Service;
using System.Collections;

namespace ash.Controllers
{
    public class Tb_userController : Controller
    {
        public ITb_userService service { set; get; }

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult List()
        {
            //IList<model.Tb_user> models = service.getAll() as IList<model.Tb_user>;
            IList models = service.getAll();

            return View(models);
        }


        public ActionResult Create()
        {
            model.User m = new model.User();

            return View("Create", m);
        }

        [AcceptVerbs(HttpVerbs.Post)]
        //[OutputCache(Location = OutputCacheLocation.None)] 
        public ActionResult Create(model.User m)
        {
            //model.Tb_user model = new model.Tb_user();

            //UpdateModel<model.Tb_user>(model);

            if (ModelState.IsValid)
            {
                service.save(m);
            }

            return RedirectToAction("List");
        }


        public ActionResult Edit(int id)
        {
            model.User m = service.get(id);

            return View(m);
        }

        [AcceptVerbs(HttpVerbs.Post)]
        //[OutputCache(Location = OutputCacheLocation.None)] 
        public ActionResult Edit(int id, FormCollection formValues)
        {
            model.User m = service.get(id);

            UpdateModel(m);

            service.update(m);

            return RedirectToAction("List");
        }
    }
}
