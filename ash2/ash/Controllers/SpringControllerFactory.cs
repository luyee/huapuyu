using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Spring.Objects.Factory;
using System.Web.Mvc;
using System.Web.Routing;
using Spring.Context.Support;

namespace ash.Controllers
{
    public class SpringControllerFactory : IControllerFactory
    {
        private static IObjectFactory _objectFactory;

        static SpringControllerFactory()
        {
            _objectFactory = ContextRegistry.GetContext();
        }

        public static void Configure(IObjectFactory objectFactory)
        {
            _objectFactory = objectFactory;
        }

        public IController CreateController(RequestContext context, string controllerName)
        {
            if (string.IsNullOrEmpty(controllerName))
            {
                throw new ArgumentNullException("controllerName");
            }

            controllerName = GetArea(context) + controllerName + "Controller";

            if (_objectFactory == null)
            {
                throw new ArgumentException("CreateController has been called before Configure.");
            }

            try
            {
                return (IController)_objectFactory.GetObject(controllerName);
            }
            catch (Exception e)
            {
                throw new InvalidOperationException("Failed creating instance of: " +
                                                    controllerName + " using spring.net object factory", e);
            }
        }

        private string GetArea(RequestContext context)
        {
            if (context.RouteData.DataTokens.ContainsKey("area"))
            {
                return context.RouteData.DataTokens["area"].ToString();
            }
            return string.Empty;
        }

        public void ReleaseController(IController controller)
        {
            var disposable = controller as IDisposable;

            if (disposable != null)
            {
                disposable.Dispose();
            }
        }
    }
}