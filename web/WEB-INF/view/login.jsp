<script>    
    
    if(window.location.href.indexOf("admin") > -1){
        window.location = "http://localhost:8080/YBM/admin/login.jsp";
    }
    
</script>

<style>
  h1   {color:white}
  h1   {font-family: "Fantasy", Papyrus}
  p   {font-family: "Fantasy", Papyrus}
  p     {color:white}
</style>

<h1>Customer Login </h1>

<form action="j_security_check" method=post>
    <div id="loginBox">
        <p><strong>Email:</strong>
            <input type="text" size="20" name="j_username"></p>

        <p><strong>Password:</strong>
            <input type="password" size="20" name="j_password"></p>

        <p><input type="submit" value="submit"></p>
    </div>
</form>

<p>Not a member? <a href="/YBM/register">Register Here</a></p>