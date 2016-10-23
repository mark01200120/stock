<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Ex Machina by TEMPLATED</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

<link
	href='http://fonts.googleapis.com/css?family=Roboto+Condensed:700italic,400,300,700'
	rel='stylesheet' type='text/css'>
<!--[if lte IE 8]><script src="js/html5shiv.js"></script><![endif]-->
 <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<link rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.css" />

<script src="js/skel.min.js"></script>
<script src="js/skel-panels.min.js"></script>
<script src="js/init.js"></script>
<script src="js/functions.js"></script>
<style>
ul#comments {
  list-style-type: none;
  font-family: "Helvetica Neue",arial,sans-serif;
  font-size: 15px;
  color: #a5b2b9;
}
ul#comments li { margin-bottom: 30px; position: relative;}
ul#comments li a { font-size:1em;text-decoration: none; color: #a8a744; }
.avatar-container { width: 60px; box-sizing: border-box; }
.avatar { width:60px;height:60px;border-radius: 50px; float: left; margin: 5px; }
.post-container {padding-top: 3px; margin-left: 70px;  box-sizing: border-box; }
.post-container p { color:#6e3a60;font-size:1em;font-weight:500;}
.author_name { font-weight:bold;font-size:1.2em; }
.timeago, .posted { 
  font-weight: 500;
  font-size: 1em;
  color: #a5b2b9;
  color: rgba(0,39,59,.35);
}
.bullet {
  padding: 0 2px;
  font-size: 1em;
  color: #ccc;
  line-height: 1.4;
}
</style>


<noscript>
	<link rel="stylesheet" href="css/skel-noscript.css" />
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" href="css/style-desktop.css" />
</noscript>
<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body class="homepage">

	<!-- Header -->
	<div id="header">
		<div style="float: right; margin-right: 20px">
			<a href="<c:url value="/Html/registered.jsp"/>">註冊</a> <a
				href="<c:url value="/Html/login.jsp"/>">登入</a>
		</div>
		<!-- 標題 -->
		<div class="container">

			<!-- Logo -->
			<div id="logo">
				<h1>
					<a href="http://localhost:8080/Html/index.jsp">S_Assistantsss</a>
				</h1>
			</div>
			<div id="fdw">
				<nav>
				<ul>
					<li><a href="http://localhost:8080/Html/basic.jsp">基礎概念</a></li>
					<li><a href="http://localhost:8080/Html/info.jsp">股市資訊<span
							class="arrow"></span></a>
					<li><a href="http://localhost:8080/Html/manage.jsp">投資管理<span
							class="arrow"></span></a>
						<ul style="display: none;" class="sub_menu">
							<li><a href="http://localhost:8080/Html/manage1.jsp">現有股票</a></li>
							<li><a href="http://localhost:8080/Html/manage2.jsp">交易記錄</a></li>
						</ul></li>
					<li><a href="http://localhost:8080/Html/news.jsp">股市新聞</a>
					<li><a href="http://localhost:8080/Html/member.jsp">會員專區<span
							class="arrow"></span></a>
						<ul style="display: none;" class="sub_menu">
							<li><a href="http://localhost:8080/Html/info1.jsp">個股查詢</a></li>
							<li><a href="http://localhost:8080/Html/info2.jsp">即時行情</a></li>
							<li><a href="http://localhost:8080/Html/member1.jsp">帳號管理</a></li>
							<li><a href="http://localhost:8080/Html/member2.jsp">我的關注股</a></li>
						</ul></li>
					<li><a href="http://localhost:8080/Html/contact.jsp">contact</a></li>
				</ul>
				</nav>
			</div>
			</div>
			<!-- Nav -->
		</div>
		<!-- 標題 -->

		<!-- 圖片 -->
		<div id="banner">
			<div class="container"></div>
		</div>
		<!-- /圖片 -->
		<!-- Featured -->


			
		<div  id="searchform" style="border: black 5px solid;">
			<div style="background: white">
				<h1>HI 你好</h1>
				<h2>IIII</h2>
				<button id="move_up">Move up</button>
				</div>
			
		</div>
	

 <div id="disqus_thread"></div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootstrap/js/bootstrap.js"></script>
<script id="dsq-count-scr" src="//markchen-2.disqus.com/count.js" async></script>	

<script type="text/javascript">
$(document).ready(function(){
	$("#move_up").click(function(){
		$("h1").hide("slow");
		$("h2").show("fast");
		$("img").slideUp();
	});
});
</script>
<script>

/**
*  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
*  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables*/
/*
var disqus_config = function () {
this.page.url = PAGE_URL;  // Replace PAGE_URL with your page's canonical URL variable
this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
};
*/



(function() { // DON'T EDIT BELOW THIS LINE
var d = document, s = d.createElement('script');
s.src = '//markchen-2.disqus.com/embed.js';
s.setAttribute('data-timestamp', +new Date());
(d.head || d.body).appendChild(s);
})();
</script>
<noscript>Please enable JavaScript to view the 
<a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a>
</noscript>       
</body>

</html>