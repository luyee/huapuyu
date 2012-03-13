#!/usr/bin/perl -w
use strict;
use Mail::Sender;

#open my $DEBUG, ">> /tmp/mail.log" or die "Can't open the debug file: $!\n";
my $sender = new Mail::Sender {
	smtp    => 'smtp.mail.yahoo.com.cn',
	from    => 'huapuyu@yahoo.com.cn',
	auth    => 'LOGIN',
	authid  => 'huapuyu',
	authpwd => '869483811'
};

#发送普通邮件
$sender->MailMsg(
	{
		to      => 'zhuzhen01@baidu.com',
		subject => 'test mail',
		msg     => 'hello word',

		#		debug   => $DEBUG
	}
);

#发送带附件的邮件
#$sender->MailFile(
#	{
#		to      => 'NinGoo@test.com',
#		subject => 'test mail with attached file',
#		msg     => 'hello word',
#		file    => '/tmp/test.txt' ,
#		debug => $DEBUG
#	}
#);
