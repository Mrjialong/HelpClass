/**
 * �ߵ�����js
 */
var map = new AMap.Map('container', {
	resizeEnable : true,
	zoom : 12
});
AMap.service('AMap.Weather', function() {
	var weather = new AMap.Weather();
	// ��ѯʵʱ������Ϣ, ��ѯ�ĳ��е���������ĳ��У��糯������������
	weather.getLive('֥���', function(err, data) {
		if (!err) {
			var str = [];
			str.push('<div style="color: #3366FF;">����ʱ�䣺' + data.reportTime
					+ '</div>');
			str.push('<div>����/����' + data.city + '</div>');
			str.push('<div>������' + data.weather + '</div>');
			str.push('<div>�¶ȣ�' + data.temperature + '��</div>');
			str.push('<div>����' + data.windDirection + '</div>');
			str.push('<div>������' + data.windPower + ' ��</div>');
			document.getElementById('tip').innerHTML = str.join('');
		}
	});
});