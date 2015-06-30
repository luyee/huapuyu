using System;
using System.Collections;
using System.IO;
using System.Web.Mvc;
using NVelocity;

namespace NVelocityEngine
{
    public class NVelocityView : IViewDataContainer, IView
    {
        private ControllerContext _controllerContext;
        private readonly Template _masterTemplate;
        private readonly Template _viewTemplate;

        public NVelocityView(ControllerContext controllerContext, string viewPath, string masterPath)
            : this(controllerContext, NVelocityViewEngine.Default.GetTemplate(viewPath), NVelocityViewEngine.Default.GetTemplate(masterPath))
        {

        }
        public NVelocityView(ControllerContext controllerContext, Template viewTemplate, Template masterTemplate)
        {
            _controllerContext = controllerContext;
            _viewTemplate = viewTemplate;
            _masterTemplate = masterTemplate;
        }

        public Template ViewTemplate
        {
            get { return _viewTemplate; }
        }

        public Template MasterTemplate
        {
            get { return _masterTemplate; }
        }

        private VelocityContext CreateContext(ViewContext context)
        {
            Hashtable entries = new Hashtable(StringComparer.InvariantCultureIgnoreCase);
            if (context.ViewData != null)
            {
                foreach (var pair in context.ViewData)
                {
                    entries[pair.Key] = pair.Value;
                }
            }
            entries["viewdata"] = context.ViewData;
            entries["tempdata"] = context.TempData;
            entries["routedata"] = context.RouteData;
            entries["controller"] = context.Controller;
            entries["httpcontext"] = context.HttpContext;
            entries["viewbag"] = context.ViewData;
            CreateAndAddHelpers(entries, context);

            return new VelocityContext(entries);
        }

        private void CreateAndAddHelpers(Hashtable entries, ViewContext context)
        {
            entries["html"] = entries["htmlhelper"] = new HtmlExtensionDuck(context, this);
            entries["url"] = entries["urlhelper"] = new UrlHelper(context.RequestContext);
            entries["ajax"] = entries["ajaxhelper"] = new AjaxHelper(context, this);
        }

        public void Render(ViewContext viewContext, TextWriter writer)
        {
            this.ViewData = viewContext.ViewData;

            bool hasLayout = _masterTemplate != null;

            VelocityContext context = CreateContext(viewContext);

            if (hasLayout)
            {
                StringWriter sw = new StringWriter();
                _viewTemplate.Merge(context, sw);

                context.Put("childContent", sw.GetStringBuilder().ToString());

                _masterTemplate.Merge(context, writer);
            }
            else
            {
                _viewTemplate.Merge(context, writer);
            }
        }

        private ViewDataDictionary _viewData;
        public ViewDataDictionary ViewData
        {
            get
            {
                if (_viewData == null)
                {
                    return _controllerContext.Controller.ViewData;
                }
                return _viewData;
            }
            set
            {
                _viewData = value;
            }
        }
    }
}
