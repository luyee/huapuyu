import smtplib 
from email.mime.text import MIMEText 
 
mailToList = ["zhuzhen01@baidu.com", "zhuzhen@china.com.cn"] 
mailHost = "smtp.mail.yahoo.com.cn" 
mailUser = "huapuyu" 
mailPass = "869483811" 
mailSuffix = "yahoo.com.cn" 
mailFrom = mailUser + "<" + mailUser + "@" + mailSuffix + ">" 
 
def sendMail(mailToList, sub, context): 
    msg = MIMEText(context) 
    msg['Subject'] = sub 
    msg['From'] = mailFrom 
    msg['To'] = ";".join(mailToList) 
    try: 
        sendSmtp = smtplib.SMTP() 
        sendSmtp.connect(mailHost) 
        sendSmtp.login(mailUser, mailPass) 
        sendSmtp.sendmail(mailFrom, mailToList, msg.as_string()) 
        sendSmtp.close() 
        return True 
    except Exception, e: 
        print(str(e)) 
        return False 
         
if __name__ == '__main__':
    if (True == sendMail(mailToList, "subject", "context")): 
        print ("success") 
    else: 
        print ("fail") 
