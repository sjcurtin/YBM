<script>    
    
    if(window.location.href.indexOf("admin") > -1){
        window.location = "http://localhost:8080/YBM/admin/login.jsp";
    }
    
</script>

<h1> Login to YBM! </h1>





<form action="j_security_check" method=post>
    <div id="loginBox">
        <p><strong>Email:</strong>
            <input type="text" size="20" name="j_username"></p>

        <p><strong>Password:</strong>
            <input type="password" size="20" name="j_password"></p>

        <p><input type="submit" value="Login"></p>
    </div>
</form>

<p>Not a member? <a href="/YBM/register">Register Here</a></p>