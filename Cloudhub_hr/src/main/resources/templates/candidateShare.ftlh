<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>HR</title>
    <style>
        body {
            background-color: #F6F6F6;
            margin: 0;
            padding: 0;
        }

        h1, h2, h3, h4, h5, h6 {
            margin: 0;
            padding: 0;
        }

        p {
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin-right: auto;
            margin-left: auto;
        }

        .brand-section {
            background-color: rgb(0, 21, 255);
            padding: 36px 0px 0px 0px;
        }

        .logo {
            width: 50%;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
        }

        .col-6 {
            width: 50%;
            flex: 0 0 auto;
        }

        .text-white {
            color: #fff;
            font-size:16px;
        }

        .company-details {
            float: right;
            text-align: right;
        }

        .body-section {
            padding: 16px;
            border: 1px solid gray;
        }

        .heading {
            font-size: 20px;
            margin-bottom: 08px;
        }

        .sub-heading {
            color: #262626;
            margin-bottom: 05px;
        }

        table {
            background-color: #fff;
            width: 100%;
            border-collapse: collapse;
        }

        table thead tr {
            border: 1px solid #111;
            background-color: #f2f2f2;
        }

        table td {
            vertical-align: middle !important;
            text-align: center;
        }

        table th, table td {
            padding-top: 08px;
            padding-bottom: 08px;
        }

        .table-bordered {
            box-shadow: 0px 0px 5px 0.5px gray;
        }

        .table-bordered td, .table-bordered th {
            border: 1px solid #dee2e6;
        }

        .text-right {
            text-align: end;
        }

        .w-20 {
            width: 20%;
        }

        .float-right {
            float: right;
        }
        .text-white1{
         color: #fff;
         font-size: 5vw;
         align-content:center:
        }
		.shopprTitleHeader {
            display: flex;
            flex-wrap: wrap;
        }

        .shprLogo {
            width: 35%;
            flex: 0 0 auto;
        }
		 .shprText {
            width: 30%;
            flex: 0 0 auto;
        }
		.shprCompDetls {
            width: 34%;
            flex: 0 0 auto;
        }
    </style>
</head>
<body>


<div class='container'>
    <div class='brand-section'>
        <div class='shopprTitleHeader'>
		
            <div  class='shprText'>
                <p class='text-white1'>HR</p>
            </div>
            <div class='shprCompDetls'>
               
            </div>
        </div>
    </div>

    <div class='body-section'>
        <h3 class='heading'>Candidate Lists</h3>
        <br>
        <table class='table-bordered'>
      
            <#list root as key>
           	 	<tr>
           	 	<#list key as r>
           	 	<#if key?index==0>
           	 	
           	 		<th>
            		 ${r}
            		 </th>
            		 <#else>
            		 	<#if r?contains("https://") >
            		 		<td>
            		 		<a href =${r}>Download </a>
            		 		</td>
            		 	<#else>
            		 		 <td>
            		 		${r}
            			    </td>
            				</#if>
            		 </#if>
            	</#list>
           		 </tr>         
             </#list>             
        
        
        </table>
        <br>


    </div>
</div>
</body>
</html>