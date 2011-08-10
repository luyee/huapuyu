/*
 ****************************************************
       *************软电话本地日志**************
 ****************************************************
 */
function SoftLocalLog (){
  this.path = "C:\\SoftLog\\";
  this.Clearpath = "C:\\SoftLog";
  this.fso = new ActiveXObject("Scripting.FileSystemObject");
  this.ClearLog = function(){
  	this.fso.DeleteFolder(this.Clearpath);
  };
  this.info = function(logInfo){
    var d = new Date();
    try{
      var fs = this.fso.OpenTextFile(this.path+d.getFullYear()+"-"+((d.getMonth()*1+1)<10?'0'+(d.getMonth()*1+1):(d.getMonth()*1+1))+"-"+(d.getDate()<10?"0"+d.getDate():d.getDate())+".log",8,true);
      fs.WriteLine("["+((d.getHours()<10)?'0'+d.getHours():d.getHours())+":"+(d.getMinutes()<10?'0'+d.getMinutes():d.getMinutes())+":"+(d.getSeconds()<10?'0'+d.getSeconds():d.getSeconds())+":"+(d.getMilliseconds()<10?'0'+d.getMilliseconds():d.getMilliseconds())+"]"+logInfo);
      fs.close();
    }catch(e){
     //alert(e.description);
    }
    return;
  };
  var d = new Date();
  try{
    if (d.getDate()==1){
  	  this.fso.DeleteFolder(this.Clearpath);
    }
  }catch(e){
  }
  try{
    this.fso.CreateFolder(this.path);
  }catch(e){
  }
}