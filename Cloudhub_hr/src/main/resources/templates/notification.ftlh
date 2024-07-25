<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Template</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
    </style>
</head>
<body>
    <div style="display: block; margin-top: 100px;">
        <div style="text-align: center;"></div>
        <div style="margin: 0 auto; width: 300px; background-color: #f4f4f4; height: 250px; border: 1px #ccc solid; padding: 50px;">
            <div class="box-2" style="text-align: center;">
                <div style="display: flex; justify-content: space-between;">
                    <div style="text-align: left;">${companyName}</div>
                    <div style="text-align: right;">${receiverName}</div>
                </div>
                <div style="margin-top: 50px;">
                    <p style="text-align: center;">${msg}</p><br />
                    <hr><br />
                </div>
            </div>
        </div>
    </div>
</body>
</html>


