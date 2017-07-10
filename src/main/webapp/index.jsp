<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
	<h2>Hello springmvc!!</h2>
	<form id="myForm" action="saysth.do" method="post">
		<input name="id" value="1" /><br>
		<input name="name" value="三" /><br> 
		<input type="button" onclick="submits()" value="提交" />
	</form>

	<script type="text/javascript">
		function submits(){
			$.ajax({
				type : 'POST',
				url : 'saysth.do',
				data : $("#myForm").serialize(),
				success : function(data) {
					alert(JSON.stringify(data));
				},
				error : function() {
					alert('Err...');
				}
			});
		}	
				
	</script>


</body>
</html>
