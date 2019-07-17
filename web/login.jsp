<%-- 
    Document   : login
    Created on : Mar 30, 2014, 4:37:32 PM
    Author     : W
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DACCRM</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link href='http://fonts.googleapis.com/css?family=Belgrano' rel='stylesheet' type='text/css'/>
<link href="js/jquery-ui-1.10.4.custom/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet" type="text/css"/>
<link rel="icon" href="images/favicon.png" type="image/png"/>
<script src="js/jquery-ui-1.10.4.custom/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js" type="text/javascript"></script>
<script src="js/jquery-validation-1.12.0/dist/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery-validation-1.12.0/dist/additional-methods.js" type="text/javascript"></script>
<script>
$.validator.setDefaults({
	submitHandler: function() { alert("submitted!"); }
});
$().ready(function() {
	// validate the comment form when it is submitted
	$("#loginForm").validate();
        });
</script>
</head>
<body>
<div id="loginpanelwrap">
  	
    <div class="loginheader">
        <div class="logintitle"><img src="images/icons/user.png" alt="" width="20" height="20"/><a href="#"> DACCRM</a></div>
    </div>

    <form class="loginform" id="loginForm" action="LoginServlet?action=logar" method="POST">
        <div class="loginform_row">
        <label>Usuário:</label>
        <input type="text" class="loginform_input" name="usuario" id="usuario" required/>
        </div>
        <div class="loginform_row">
        <label>Senha:</label>
        <input type="password" class="loginform_input" name="" id="senha" required/>
        </div>
        <div class="loginform_row">
        <input type="submit" class="loginform_submit" value="Entrar" name="logar" />
        </div> 
        <div class="clear"></div>
    </form>
</div>
</body>
</html>