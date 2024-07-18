
// 현재 년월일 표시
function Curday() {
	date = new Date();
	year = date.getFullYear();
	month = date.getMonth() + 1;
	day = date.getDate();

	$('.cur_year').html(year + '년');
	$('.cur_month').html(month + '월');
	$('.cur_day').html(day + '일');
}

// 통일 계약서 표기
function UnityDoc() {
	var unity_content = unity_origin.replaceAll('\n', '<br />');
	$('.unity p').html(unity_content);
}

// 전화번호
function userPhone(phone) {
	phone = phone.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
	return phone;
}

// 주민번호
function userRnn(Rnn) {
	Rnn = Rnn.replace(/[^0-9]/g, '').replace(/^(\d{0,6})(\d{0,7})$/g, '$1-$2').replace(/-{1,2}$/g, '');
	return Rnn;
}

// 날짜 표기
function dtUpdateInfo() {
	if ($('#startDt').val() == '1900-01-01') {
		$('#startDt').val('');
	}
	if ($('#endDt').val() == '1900-01-01') {
		$('#endDt').val('');
	}
}

// worker 주소 표기
function workerInputAddr(work_addr) {
	var work_num1 = work_addr.indexOf('_');
	var work_num2 = work_addr.indexOf('_', work_num1 + 1)
	$('.info_worker #input_postcode').val(work_addr.substring(0, work_num1));
	$('.info_worker #input_addr').val(work_addr.substring(work_num1 + 1, work_num2));
	$('.info_worker #input_detailAddr').val(work_addr.substring(work_num2 + 1,));
}

// requester 주소 표기
function requesterInputAddr(req_addr) {
	var req_num1 = req_addr.indexOf('_');
	var req_num2 = req_addr.indexOf('_', req_num1 + 1)
	$('.info_req #input_postcode').val(req_addr.substring(0, req_num1));
	$('.info_req #input_addr').val(req_addr.substring(req_num1 + 1, req_num2));
	$('.info_req #input_detailAddr').val(req_addr.substring(req_num2 + 1,));
}

// worker 주소 표기 : p
function workerPAddr(work_addr) {
	var work_num1 = work_addr.indexOf('_');
	var work_num2 = work_addr.indexOf('_', work_num1 + 1)
	$('#input_postcode_work').html(work_addr.substring(0, work_num1));
	$('#input_addr_work').html(work_addr.substring(work_num1 + 1, work_num2));
	$('#input_detailAddr_work').html(work_addr.substring(work_num2 + 1,));
}
// requester 주소 표기 : p
function requesterAddr(req_addr) {
	var req_num1 = req_addr.indexOf('_');
	var req_num2 = req_addr.indexOf('_', req_num1 + 1)
	$('.info_req #input_postcode').html(req_addr.substring(0, req_num1));
	$('.info_req #input_addr').html(req_addr.substring(req_num1 + 1, req_num2));
	$('.info_req #input_detailAddr').html(req_addr.substring(req_num2 + 1,));
}

// worker 은행 표기
function workerBank(workerCode) {
	for (i = 0; i < $('.info_worker option').length; i++) {
		if ($('.info_worker option:eq(' + i + ')').val().includes(workerCode)) {
			$('.info_worker option:eq(' + i + ')').prop("selected", true);
		}
	}
}

// requester 은행 표기
function requesterBank(reqBankCode) {
	for (i = 0; i < $('.info_req option').length; i++) {
		if ($('.info_req option:eq(' + i + ')').val().includes(reqBankCode)) {
			$('.info_req option:eq(' + i + ')').prop("selected", true);
		}
	}
}

// + 버튼 : tbody tr 추가
$('#con_table thead button').on('click', addForm);
function addForm() {
	let today = new Date();
	today = today.toISOString().substring(0, 10);
	let nextday = '';
	var length = $('#con_table tbody tr').length;
	if (length > 0) {
		nextday = $('#con_table tbody tr:eq(' + (length - 1) + ') td:eq(2) input').val();
	} else {
		nextday = today;
	}
	$('.content').append(`<tr>
				<td><input type="text" style="border: 0; width: 100%;" readonly/></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="inputNumberFormat(this);" 
						value="0" maxlength="15" /></td>
				<td><input type="date" style="border: 0; width: 100%;" value =${nextday} min=${today} onChange="dayCheck(this)" /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,200)"/></td>
				<td><button type="button" class="btn btn-outline-dark" name="deleteBtn"
					style="width: 30px; height: 30px; display: flex; align-items: center; justify-content: center;">-</button></td>
			</tr>`);
	addRound();
}
// 날짜 체크
function dayCheck(e) {
	var length = $('#con_table tbody tr').length;
	if (length >= 2) {
		for (i = 0; i < length - 1; i++) {
			var day1 = $('#con_table tbody tr:eq(' + i + ') td:eq(2) input');
			var day2 = $('#con_table tbody tr:eq(' + (i + 1) + ') td:eq(2) input');
			if (day1.val() > day2.val()) {
				day2.val(day1.val());
				sweetModalError("이전 날짜를 선택할 수 없습니다")
			}
		}
	}
}

// 회차 표기
function addRound() {
	for (i = 0; i < $('#con_table tbody tr').length; i++) {
		$('#con_table tbody tr:eq(' + i + ') td:eq(0) input').val(i + 1);
	}
}

// - 버튼 : tbody tr 제거
$(document).on('click', 'button[name=deleteBtn]', function(e) {
	e.target.closest('tr').remove();
	addRound();
});

// 하자 보수 기간 계간
$('#startDt').on('change', function() {
	if ($('#startDt').val() != '') {
		let date = new Date($('#startDt').val());
		repairDay(date);
	}
})

function repairDay(date) {
	date.setFullYear(date.getFullYear() + 1);
	$('#repairDay').val(date.getFullYear() + "-"
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1))) + "-" +
		((date.getDate()) > 9 ? (date.getDate()) : ("0" + (date.getDate()))));
}

function repairDayInfo(date) {
	date.setFullYear(date.getFullYear() + 1);
	$('#repairDay').html(date.getFullYear() + "-"
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1))) + "-"
		+ ((date.getDate()) > 9 ? (date.getDate()) : ("0" + (date.getDate()))));
}


// 이미지&오디오 파일 제약
function onlyFile() {
	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	for (var i = 0; i < files.length; i++) {
		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') != -1) {
			sweetModalError("이미지 파일이 포함되어 있습니다.");
			return false;
		}
		if (files[i].type.indexOf('audio') != -1) {
			sweetModalError("오디오 파일이 포함되어 있습니다.");
			return false;
		}
	}
	return true;
}

// 총 금액
function GetTotal() {
	var total = 0;

	for (i = 0; i < $('#con_table tbody tr').length; i++) {
		total += Number($('#con_table tbody tr:eq(' + i + ') td:eq(1) input').val().replaceAll(',', ''));
	}

	var cttPrice = Number($('input[name="cttPrice"]').val().replaceAll(',', ''));

	if (cttPrice < total) {
		sweetModalError("금액의 합이 총 공사 금액보다 큽니다");
		return false;
	}
	return true
}
// 빈 테이블 제거
function tableNullRemove() {
	var length = $('#con_table tbody tr').length;
	var num = [];
	for (i = 0; i < length; i++) {
		var price = $('#con_table tbody tr:eq(' + i + ') td:eq(1) input').val();
		var history = $('#con_table tbody tr:eq(' + i + ') td:eq(3) input').val();

		if (price == 0 && history == '') {
			num.push(i);
		};
	}
	num.reverse();
	num.forEach(e => {
		$('#con_table tbody tr:eq(' + e + ')').remove();
	})
}


// 폼 데이터 처리
function con_insert_data() {
	$('input[name="workerAddress"]').val($('#input_postcode').val() + '_'
		+ $('#input_addr').val() + '_' + $('#input_detailAddr').val());

	let startDt = $('#startDt');
	let endDt = $('#endDt');

	if (startDt.val() == '') {
		startDt.val('1900-01-01');
	}
	if (endDt.val() == '') {
		endDt.val('1900-01-01');
	}

	for (i = 0; i < $('#con_table tbody tr').length; i++) {
		$('#con_table tbody tr:eq(' + i + ') td:eq(0) input').attr('name', 'dList[' + i + '].round');
		$('#con_table tbody tr:eq(' + i + ') td:eq(1) input').attr('name', 'dList[' + i + '].price');
		$('#con_table tbody tr:eq(' + i + ') td:eq(1) input').val($('#con_table tbody tr:eq(' + i + ') td:eq(1) input').val().replaceAll(',', ''));
		$('#con_table tbody tr:eq(' + i + ') td:eq(2) input').attr('name', 'dList[' + i + '].paymentDt');
		$('#con_table tbody tr:eq(' + i + ') td:eq(3) input').attr('name', 'dList[' + i + '].history');
	}
}
// 공사 대금 데이터 처리
function cttPriceData() {
	var cttPrice = $('input[name="cttPrice"]').val();
	cttPrice = cttPrice.replaceAll(',', '');
	$('input[name="cttPrice"]').val(cttPrice);
}

// 은행 코드 데이터 처리
function workerBankCodeInsert() {
	var bankcode = $('.info_worker select').val();
	var bank_num1 = bankcode.indexOf('(');
	if (bankcode != '은행 선택') {
		$('input[name="workerBankcode"]').val(bankcode.substring(bank_num1 + 1).replace(')', ''));
	} else {
		$('input[name="workerBankcode"]').val(null);
	}
}

function requesterBankCodeInsert() {
	var bankcode = $('.info_req select').val();
	var bank_num1 = bankcode.indexOf('(');
	if (bankcode != '은행 선택') {
		$('input[name="requesterBankcode"]').val(bankcode.substring(bank_num1 + 1).replace(')', ''));
	} else {
		$('input[name="requesterBankcode"]').val(null);
	}
}

function workerBankCodeUpdate(workBankCode) {
	var bankcode = $('.info_worker select').val();
	var bank_num1 = bankcode.indexOf('(');
	if (bankcode == '은행 선택') {
		$('input[name="workerBankcode"]').val(null);
	} else if (bankcode != workBankCode && bankcode != '은행 선택') {
		$('input[name="workerBankcode"]').val(bankcode.substring(bank_num1 + 1).replace(')', ''));
	}
}

function requesterBankCodeUpdate(reqBankCode) {
	var bankcode = $('.info_req select').val();
	var bank_num1 = bankcode.indexOf('(');
	if (bankcode == '은행 선택') {
		$('input[name="requesterBankcode"]').val(null);
	} else if (bankcode != reqBankCode && bankcode != '은행 선택') {
		$('input[name="requesterBankcode"]').val(bankcode.substring(bank_num1 + 1).replace(')', ''));
	}
}

// requester 주소 처리
function requesterAddrData() {
	$('input[name="requesterAddress"]').val($('#input_postcode').val() + '_'
		+ $('#input_addr').val() + '_' + $('#input_detailAddr').val());
}

// alert
function sweetModalError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}

// 숫자 콤마 함수
function comma(str) {
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,').replace(/^0+/, '');
}

function uncomma(str) {
	str = String(str);
	return str.replace(/[^\d]+/g, '');
}

function inputNumberFormat(obj) {
	obj.value = comma(uncomma(obj.value));
}

function onlyNumberFormat(obj) {
	obj.value = uncomma(obj.value);
}

// 입력 길이 제한
function chkword(obj, maxlength) {

	var str = obj.value; // 이벤트가 일어난 컨트롤의 value 값
	var str_length = str.length; // 전체길이

	// 변수초기화
	var max_length = maxlength; // 제한할 글자수 길이
	var i = 0; // for문에 사용
	var ko_byte = 0; // 총 글자 길이
	var li_len = 0; // substring하기 위해서 사용
	var one_char = ""; // 한글자씩 검사한다

	for (i = 0; i < str_length; i++) {
		// 한글자추출
		one_char = str.charAt(i);
		ko_byte += 1;
	}

	// 전체 크기가 max_length를 넘지않으면
	if (ko_byte <= max_length) {
		li_len = i + 1;
	}

	// 전체길이를 초과하면
	if (ko_byte > max_length) {
		str2 = str.substr(0, max_length);
		obj.value = str2;
	}
	obj.focus();
}