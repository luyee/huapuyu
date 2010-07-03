using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using ash2.Controllers;
using Spring.Core.IO;
using Spring.Objects.Factory;
using Spring.Objects.Factory.Xml;
using MvcContrib.Services;
using MvcContrib.ControllerFactories;

namespace ash2
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");
            routes.IgnoreRoute("{*favicon}", new { favicon = @"(.*/)?favicon.ico(/.*)?" });

            routes.MapRoute(
                "Default", // Route name
                "{controller}/{action}/{id}", // URL with parameters
                new { controller = "Home", action = "Index", id = UrlParameter.Optional } // Parameter defaults
            );
        }

        protected void Application_Start()
        {
            //IResource input = new FileSystemResource(Server.MapPath("~/Config/spring.xml"));
            //IObjectFactory factory = new XmlObjectFactory(input);
            //DependencyResolver.InitializeWith(new SpringDependencyResolver(factory));

            //ControllerBuilder.Current.SetControllerFactory(typeof(IoCControllerFactory));
            ControllerBuilder.Current.SetControllerFactory(typeof(SpringControllerFactory));

            AreaRegistration.RegisterAllAreas();

            RegisterRoutes(RouteTable.Routes);
        }
    }
}