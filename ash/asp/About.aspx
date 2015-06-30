<%@ Page Title="About Us" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="About.aspx.cs" Inherits="asp.About" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">
    <h2>
        About
    </h2>
    <p>
        Put content here.
        <asp:Button ID="Button1" runat="server" onclick="Button1_Click" Text="Button" />
        <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
    </p>
</asp:Content>
