#!/usr/bin/perl -w
use strict;
#use Net::SMTP;#Net::SMTP不行，必须用Net::SMTP_auth
use Net::SMTP_auth;
my $mailhost = "smtp.mail.yahoo.com.cn";    # SMTP服务器
my $mailfrom = 'huapuyu@yahoo.com.cn';      # 你的EMAIL地址
my @mailto =
  ( 'zhuzhen01@baidu.com', 'zhuzhen@china.com.cn' );    # 收信人地址
my $subject = "subject";
my $text    = "hell world";
my $smtp    = Net::SMTP_auth->new( $mailhost, Timeout => 120, Debug => 1 )
  ;    #为0时，不输出调试信息

# anth login, type your user name and password here
$smtp->auth( 'LOGIN', 'huapuyu', '869483811' );

foreach my $mailto (@mailto) {

	# Send the From and Recipient for the mail servers that require it
	$smtp->mail($mailfrom);
	$smtp->to($mailto);

	# Start the mail
	$smtp->data();

	# Send the header
	$smtp->datasend("To: $mailto\n");
	$smtp->datasend("From: $mailfrom\n");
	$smtp->datasend("Subject: $subject\n");
	$smtp->datasend("\n");

	# Send the message
	$smtp->datasend("$text\n\n");

	# Send the termination string
	$smtp->dataend();
}
$smtp->quit;
