function openThisWork(workid, tid) {
	window.location.href = "select_onework.jsp?workid=" + workid + "&id=" + tid;
}
function openThisCheck(workid, tid) {
	window.location.href = "select_onecheck.jsp?checkid=" + workid + "&id="
			+ tid;
}
function window1() {
	document.getElementById('light').style.display = 'block';
	document.getElementById('fade').style.display = 'block';
	gaode();
}
function window2() {
	document.getElementById('light').style.display = 'none';
	document.getElementById('fade').style.display = 'none';

}

function shownumber(checknumber) {
	document.getElementById('checknumber').innerHTML = checknumber;
	document.getElementById('checkform').submit();
}
function winreload() { 
	setTimeout(function(){  //ʹ��  setTimeout���������趨��ʱ2000����
	window.location.reload();//ҳ��ˢ��
	},1000);
}


