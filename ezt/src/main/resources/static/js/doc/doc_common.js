// 엔터 막기
$(document).ready(function() {
	//공통 enter 막기
	$('.pps input').keydown(function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});

});


// + 버튼 : tbody tr 추가
$('#pps_table thead button').on('click', addForm);
let num = 1;
function addForm() {
	$('.content').append(`<tr>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,50);" onClick='keydown(this)' /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,20);" onClick='keydown(this)' /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,20);" onClick='keydown(this)' /></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="inputNumberFormat(this); GetSum(${num});" onClick='keydown(this)'
						value="0" maxlength="4" class="qty_${num}" /></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="inputNumberFormat(this); GetSum(${num});" onClick='keydown(this)'
						value="0" maxlength="8" class="price_${num}" /></td>
				<td><input type="text" style="border: 0; width: 100%;" value="0" 
						onClick='keydown(this)' class="result_${num}" readonly /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,500);" onClick='keydown(this)' /></td>
				<td><button type="button" class="btn btn-outline-dark" name="deleteBtn"
					style="width: 30px; height: 30px; display: flex; align-items: center; justify-content: center;">-</button></td>
			</tr>`);
	num += 1;
}

function keydown(e){
	$(e).keydown(function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});
}

// - 버튼 : tbody tr 제거
$(document).on('click', 'button[name=deleteBtn]', function(e) {
	e.target.closest('tr').remove();
	GetTotal();
});

// 데이터 처리
function dataProcessPps() {
	let startDt = $('#startDt');
	let endDt = $('#endDt');

	if (startDt.val() == '') {
		startDt.val('1900-01-01');
	}
	if (endDt.val() == '') {
		endDt.val('1900-01-01');
	}


	for (i = 0; i < $('#pps_table tbody tr').length; i++) {
		$('#pps_table tbody tr:eq(' + i + ') td:eq(0) input').attr('name', 'list[' + i + '].product');
		$('#pps_table tbody tr:eq(' + i + ') td:eq(1) input').attr('name', 'list[' + i + '].division');
		$('#pps_table tbody tr:eq(' + i + ') td:eq(2) input').attr('name', 'list[' + i + '].unit');
		$('#pps_table tbody tr:eq(' + i + ') td:eq(3) input').attr('name', 'list[' + i + '].qty');
		$('#pps_table tbody tr:eq(' + i + ') td:eq(3) input').val(
			$('#pps_table tbody tr:eq(' + i + ') td:eq(3) input').val().replaceAll(',', ''));
		$('#pps_table tbody tr:eq(' + i + ') td:eq(4) input').attr('name', 'list[' + i + '].unitprice');
		$('#pps_table tbody tr:eq(' + i + ') td:eq(4) input').val(
			$('#pps_table tbody tr:eq(' + i + ') td:eq(4) input').val().replaceAll(',', ''));
		$('#pps_table tbody tr:eq(' + i + ') td:eq(6) input').attr('name', 'list[' + i + '].comments');
	}
}
// 테이블 체크
function tableNullCheck(){
	var length = $('#pps_table tbody tr').length;
	for (i = 0; i < length; i++) {
		
		var product = $('#pps_table tbody tr:eq(' + i + ') td:eq(0) input').val();
		var division = $('#pps_table tbody tr:eq(' + i + ') td:eq(1) input').val();
		var unit = $('#pps_table tbody tr:eq(' + i + ') td:eq(2) input').val();
		var qty = $('#pps_table tbody tr:eq(' + i + ') td:eq(3) input').val();
		var unitprice = $('#pps_table tbody tr:eq(' + i + ') td:eq(4) input').val();
		var comments = $('#pps_table tbody tr:eq(' + i + ') td:eq(6) input').val();

		if( product == '' || division == '' || unit == '' || qty  == 0 || unitprice == 0 || comments == '' ) {
			sweetModalError("작성되지 않은 내용이 있습니다. 확인해 주세요.");
			return false;
		};
	}
	return true;
}

function tableNullRemove(){
	var length = $('#pps_table tbody tr').length;
	for (i = Number(length-1); i < 0; i--) {
		var product = $('#pps_table tbody tr:eq(' + i + ') td:eq(0) input').val();
		var division = $('#pps_table tbody tr:eq(' + i + ') td:eq(1) input').val();
		var unit = $('#pps_table tbody tr:eq(' + i + ') td:eq(2) input').val();
		var qty = $('#pps_table tbody tr:eq(' + i + ') td:eq(3) input').val();
		var unitprice = $('#pps_table tbody tr:eq(' + i + ') td:eq(4) input').val();
		var comments = $('#pps_table tbody tr:eq(' + i + ') td:eq(6) input').val();
		
		if( product == '' && division == '' && unit == '' && qty == 0 && unitprice == 0 && comments == '' ) {
			$('#pps_table tbody tr:eq(' + i + ')').remove();
		};
	}
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


// 계산 함수
// 수량 * 단가 = 금액
function GetSum(e) {
	var result = 0;
	var firstNum = 0;
	var secondNum = 0;

	firstNum = Number($('.qty_' + e).val().replaceAll(',', ''));
	secondNum = Number($('.price_' + e).val().replaceAll(',', ''));
	result = (firstNum * secondNum).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	$('.result_' + e).val(result);

	GetTotal();
}
// 총 금액
function GetTotal() {
	var total = 0;

	for (i = 0; i < $('#pps_table tbody tr').length; i++) {
		total += Number($('#pps_table tbody tr:eq(' + i + ') td:eq(5) input').val().replaceAll(',', ''));
	}

	var comTotal = total.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	$('.total').val(comTotal);
}

// 총 금액 == 희망 공사 예산
function totalCheck(){
	var total = 0;

	for (i = 0; i < $('#pps_table tbody tr').length; i++) {
		total += Number($('#pps_table tbody tr:eq(' + i + ') td:eq(5) input').val().replaceAll(',', ''));
	}
		
	if(hopePrice != total){
		sweetModalError("총 예상금액이 희망 공사 예산 보다 많습니다");
		return false;
	}
	return true;
}

// 전화번호
function userPhone(){
	phone = phone.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
	$('#user_phone').html(phone);
}
// 첨부 파일 다운로드 
function fileDownload(fileList) {
	for (i = 0; i < fileList.length; i++) {
		var path = fileList[i].savePath.replaceAll('\\', '/');
		var orgname = fileList[i].originalFileName;
		var name = fileList[i].saveName;
		var ext = fileList[i].ext;
		console.log(path + '/' + name);
		$('#fileDownload').append(`<p><a href="download?fileName=${path}/${name}_${orgname}.${ext}">${orgname}.${ext}</a></p> `)
	}
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

// alert
function sweetModalError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}