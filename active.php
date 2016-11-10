<?php
header("Content-Type:text/html; charset=utf-8");
$mysql_server_name='ap-cdbr-azure-east-c.cloudapp.net'; //改成自己的mysql資料庫伺服器
$mysql_username='bd01f428fb61c7'; //改成自己的mysql資料庫用戶名
$mysql_password='e51416bb'; //改成自己的mysql資料庫密碼
$mysql_database='acsm_71b6f96c6037ce6'; //改成自己的mysql資料庫名
    
    
     $active=(intval($_POST['active']));
     //$alarm=1;
     echo $active;
     $conn=mysqli_connect($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); //連接資料庫
     if(!$conn){
                  echo('Error connecting to the database: ' . $conn->error());
                       exit();
                       }  
              else{echo 'connect to database</br>';} 

         $conn->query("SET NAMES 'UTF8'"); //資料庫輸出編碼
         date_default_timezone_set('Asia/Taipei');
       $tmp1 = date("Y-m-d H:i:s");
      $sql = "INSERT INTO active_num (activenum,updatetime) VALUES (".$active.",'".$tmp1."')";
       
          //$result =  $conn->query($sql);
       if(!mysqli_query($conn,$sql))
                      {
                       echo('Error : ' . mysqli_error($conn));
                       exit();
                       }        
        

        $conn->close(); //關閉MySQL連接
?>