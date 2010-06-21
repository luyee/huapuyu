<?php
require_once 'dao/BaseDao.php';
require_once 'init/InitLog.inc.php';
require_once 'model/Tb_test.php';

echo "ffss";
$t = new BaseDao ();
$t->ExecuteQuery ( "SELECT * FROM tb_test" );
echo "ffssfff";
$m = new Tb_test();
$m->setId(123);
$m->setName("helloworld");
echo "afadsfasdfas";
echo $m->toString();

?>