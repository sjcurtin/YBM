
<script>    
    
    if(window.location.href.indexOf("admin") > -1){
        window.location = "http://localhost:8080/YBM/admin/error.jsp";
    }
    
</script>

<div id="loginBox">

    <p class="error">Invalid username or password.</p>

    <p>Return to <strong><a href="/YBM/login">Login</a></strong>.</p>

</div>