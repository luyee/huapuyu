<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<model.Tb_user>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Create
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Create</h2>

    <%= Html.ValidationSummary("Create was unsuccessful. Please correct the errors and try again.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Fields</legend>
            <p>
                <label for="id">id:</label>
                <%= Html.TextBox("id") %>
                <%= Html.ValidationMessage("id", "*") %>
            </p>
            <p>
                <label for="name">name:</label>
                <%= Html.TextBox("name") %>
                <%= Html.ValidationMessage("name", "*") %>
            </p>
            <p>
                <label for="pwd">pwd:</label>
                <%= Html.TextBox("pwd") %>
                <%= Html.ValidationMessage("pwd", "*") %>
            </p>
            <p>
                <input type="submit" value="Create" />
            </p>
        </fieldset>

    <% } %>

    <div>
        <%=Html.ActionLink("Back to List", "List") %>
    </div>

</asp:Content>
