// 防钓鱼处理
if( window != top )
{
	top.location.href = window.location.href;
}