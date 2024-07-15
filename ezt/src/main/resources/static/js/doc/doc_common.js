// 엔터 막기
$(document).ready(function() {
	//공통 enter 막기
	$('input').keydown(function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});

});

// + 버튼 : tbody tr 추가
$('table thead button').on('click', addForm);
let num = 1;
function addForm(e) {
	$('.content').append(`<tr>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,50);" /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,20);" /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,20);" /></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="inputNumberFormat(this); GetSum(${num});" 
						value="0" maxlength="4" class="qty_${num}" /></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="inputNumberFormat(this); GetSum(${num});" 
						value="0" maxlength="8" class="price_${num}" /></td>
				<td><input type="text" style="border: 0; width: 100%;" value="0" class="result_${num}" readonly /></td>
				<td><input type="text" style="border: 0; width: 100%;" onkeyup="chkword(this,500);" /></td>
				<td><button type="button" class="btn btn-outline-dark" name="deleteBtn"
					style="width: 30px; height: 30px; display: flex; align-items: center; justify-content: center;">-</button></td>
			</tr>`);
	num += 1;
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


	for (i = 0; i < $('tbody tr').length; i++) {
		$('tbody tr:eq(' + i + ') td:eq(0) input').attr('name', 'list[' + i + '].product');
		$('tbody tr:eq(' + i + ') td:eq(1) input').attr('name', 'list[' + i + '].division');
		$('tbody tr:eq(' + i + ') td:eq(2) input').attr('name', 'list[' + i + '].unit');
		$('tbody tr:eq(' + i + ') td:eq(3) input').attr('name', 'list[' + i + '].qty');
		$('tbody tr:eq(' + i + ') td:eq(3) input').val(
			$('tbody tr:eq(' + i + ') td:eq(3) input').val().replaceAll(',', ''));
		$('tbody tr:eq(' + i + ') td:eq(4) input').attr('name', 'list[' + i + '].unitprice');
		$('tbody tr:eq(' + i + ') td:eq(4) input').val(
			$('tbody tr:eq(' + i + ') td:eq(4) input').val().replaceAll(',', ''));
		$('tbody tr:eq(' + i + ') td:eq(6) input').attr('name', 'list[' + i + '].comments');
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

	for (i = 0; i < $('tbody tr').length; i++) {
		total += Number($('tbody tr:eq(' + i + ') td:eq(5) input').val().replaceAll(',', ''));
	}

	var comTotal = total.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	$('.total').val(comTotal);
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