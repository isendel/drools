<html>
	<head>
		<meta name='gwt:module' content='org.drools.brms.JBRMS'>		
		<link rel='stylesheet' href='JBRMS.css'>
		<title>JBoss Business Rules Management System</title>
	    <link rel="shortcut icon" href="images/drools.gif" type="image/gif">
	    <link rel="icon" href="images/drools.gif" type="image/gif">		
	</head>
	<body>
	
	    <% if (request.getRemoteUser() != null) { %>
		    <div id=user_info> User: <%=request.getRemoteUser() %> <a href='/drools-jbrms/logout.jsp'>[Sign Out]</a> </div>
		<% } %>    
	    <div class="headerBarblue"><img src="images/jbossrules_hdrlogo.png" width="279" height="70" /></d
		<!-- This script is the bootstrap stuff that simply must be there; it is sent down uncompressed -->
		<script language='javascript' src='gwt.js'></script>
		<iframe id='__gwt_historyFrame' style='width:0;height:0;border:0'></iframe>
	</body>
</html>