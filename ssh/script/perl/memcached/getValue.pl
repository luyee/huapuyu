#!/usr/bin/perl

use Cache::Memcached;

my $mem = new Cache::Memcached(
	{
		'servers'            => ["192.168.2.101:11211"],
		'compress_threshold' => 10_000
	}
);

my $name = $mem->get('name');
print "Name is $name!\n";
