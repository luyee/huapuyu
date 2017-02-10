using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using model;
using IBatisNet.DataMapper;

namespace asp
{
    public partial class About : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            //PollItem model = new PollItem();
            //model.id = 11111;
            //model.name = "hello";
            //model.type = 1;
            //model.parentId = 111;
            //model.enable = 1;

            //sm.Insert("Tb_userInsert", model);

            //Tb_user emp = sm.QueryForObject<Tb_user>("Tb_userGetById", 1);

            //Mapper.Instance().Insert("Tb_userInsert", model);
            //Tb_user emp = Mapper.Instance().QueryForObject("Tb_userGetById", 1) as Tb_user;

            //PollItem data = Mapper.Instance().QueryForObject<PollItem>("DataGetById", 101);
           
            //if(data.enable)
            //    TextBox1.Text = data.name;
        }
    }
}
