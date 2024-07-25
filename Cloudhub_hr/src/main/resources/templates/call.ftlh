<!DOCTYPE html>
<html>
  <head>
    <title>Email</title>
    <style>
        .body1{           
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .div1{
            margin-left: 10em;
            align-content: flex-end;
            display: flex;
            justify-content: space-evenly;
        }
        .button1{  
 
            background-color: #1890ff;
            color: white;
            border-width: 0;
            border-color: white;
        }
        .button2{
            background-color: #1890ff;
            color: white;
            border-width: 0;
            border-color: white;
        }
        
    </style>
  </head>
  <body class="body1">
    <form action="/form/submit" action="post"> 
      <textarea name="comment" rows="12" cols="35"></textarea><br>
      <div class="div1">
      <input type="submit" name="submitInfo" value="Accept" class="button1">
      <input type="submit" name="decline" value="Decline" class="button2">
    </div>
    </form>
  </body>
</html>