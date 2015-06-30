<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<System.Collections.IList>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	List
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>List</h2>

    <ul>
        <% foreach (model.Tb_user tb_user in Model) { %>
        <li>
            <%= Html.Encode(tb_user.id)%>
            
            <%= Html.Encode(tb_user.name)%>

            <%= Html.Encode(tb_user.pwd)%>
            
            <%= Html.ActionLink("修改", "Edit", new { id = tb_user.id })%>
            
            <%= Html.ActionLink("删除", "Delete", new { id = tb_user.id })%>
        </li>
        <% } %>
    </ul>
    
    <div>
        <%=Html.ActionLink("Create", "Create") %>
    </div>

</asp:Content>
