

// 현재 년월일 표시
date = new Date();
year = date.getFullYear();
month = date.getMonth() + 1;
day = date.getDate();

$('.cur_year').html(year + '년');
$('.cur_month').html(month + '월');
$('.cur_day').html(day + '일');

// 통일 계약서 표기
var unity_content = unity_origin.replaceAll('\n', '<br />');
$('.unity p').html(unity_content);

// + 버튼 : tbody tr 추가
$('table thead button').on('click', addForm);
let num = 1;
function addForm(e) {
	$('.content').append(`<tr>
				<td><input type="text" style="border: 0; width: 100%;" /></td>
				<td><input type="text" style="border: 0; width: 100%;" 
						onKeyup="this.value=this.value.replace(/[^-0-9]/g,'').replace('-','').replace(/^0+/, '');" 
						value="0" maxlength="12" class="price_${num}" /></td>
				<td><input type="text" style="border: 0; width: 100%;" value='1900-01-01' /></td>
				<td><input type="text" style="border: 0; width: 100%;" /></td>
				<td><button type="button" class="btn btn-outline-dark" name="deleteBtn"
					style="width: 30px; height: 30px; display: flex; align-items: center; justify-content: center;">-</button></td>
			</tr>`);
	num += 1;
	addRound();
}
// 회차 표기
function addRound() {
	for (i = 0; i < $('tbody tr').length; i++) {
		$('tbody tr:eq(' + i + ') td:eq(0) input').val(i + 1);
	}
}

// - 버튼 : tbody tr 제거
$(document).on('click', 'button[name=deleteBtn]', function(e) {
	e.target.closest('tr').remove();
	//GetTotal();
	addRound();
});

// 하자 보수 기간 계간
$('#startDt').on('change', function() {
	if ($('#startDt').val() != '') {
		let date = new Date($('#startDt').val());

		date.setFullYear(date.getFullYear() + 1);
		$('#repairDay').val(date.getFullYear() + "-"
			+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1))) + "-" +
			((date.getDate()) > 9 ? (date.getDate()) : ("0" + (date.getDate()))));
	}
})

function onlyFile() {
	// 이미지&오디오 파일 제약
	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;


	for (var i = 0; i < files.length; i++) {
		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') != -1) {
			sweetModalError("이미지 파일이 포함되어 있습니다.")
			return false;
		}
		if (files[i].type.indexOf('audio') != -1) {
			sweetModalError("오디오 파일이 포함되어 있습니다.")
			return false;
		}
	}
	return true;
}


function con_insert_data() {
	// 폼 데이터 처리
	$('input[name="workerAddress"]').val($('#input_postcode').val() + '_'
		+ $('#input_addr').val() + '_' + $('#input_detailAddr').val());

	var bankcode = $('select').val();
	var bank_num1 = bankcode.indexOf('(');
	if (bankcode != '은행 선택') {
		$('input[name="workerBankcode"]').val(bankcode.substring(bank_num1 + 1).replace(')', ''));
	} else {
		$('input[name="workerBankcode"]').val(null);
	}


	$('input[name="cttPrice"]').val($('input[name="cttPrice"]').val().replace(',', ''));

	let startDt = $('#startDt');
	let endDt = $('#endDt');

	if (startDt.val() == '') {
		startDt.val('1900-01-01');
	}
	if (endDt.val() == '') {
		endDt.val('1900-01-01');
	}


	for (i = 0; i < $('tbody tr').length; i++) {
		$('tbody tr:eq(' + i + ') td:eq(0) input').attr('name', 'dList[' + i + '].round');
		$('tbody tr:eq(' + i + ') td:eq(1) input').attr('name', 'dList[' + i + '].price');
		$('tbody tr:eq(' + i + ') td:eq(2) input').attr('name', 'dList[' + i + '].paymentDt');
		$('tbody tr:eq(' + i + ') td:eq(3) input').attr('name', 'dList[' + i + '].history');
	}
}

// alert
function sweetModalError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}
