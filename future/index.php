<?php
require_once 'init/InitSmarty.inc.php';

$smarty->assign("title", "首页 | Lily");
$smarty->display("index.htm");
?>