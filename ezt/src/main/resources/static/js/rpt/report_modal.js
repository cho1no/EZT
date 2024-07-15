// 엔터 막기
$(document).ready(function() {
	//공통 enter 막기
	$('#reportModal input').keydown(function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});

});

// 크기&확장자 제한
$('input[name="uploadFile"]').attr('accept', '.jpeg, .png, .gif, .jpg, .psd, .eps, .ai, .tiff, .bmp, .svg, .jfif');

var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

function checkExtentsion(fileName, fileSize) {
	if (fileSize >= maxSize) {
		sweetModalError("파일 사이즈가 초과되었습니다");
		return false;
	}

	if (regex.test(fileName)) {
		sweetModalError("해당 종류의 파일은 업로드할 수 없습니다.")
		return false;
	}
	return true;
}


// 현재 년월일 표시
function currentDay() {
	date = new Date();
	year = date.getFullYear();
	month = date.getMonth() + 1;
	day = date.getDate();

	$('.cur_year').html(year + '-');
	$('.cur_month').html(month + '-');
	$('.cur_day').html(day);
}

// 모달 초기화
$('#reportModal').on('show.bs.modal', function() {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportModal select option').remove();
	$('#reportModal select').append(`<option selected>유형 선택</option>`);
	$('#reportModal input[name="uploadFile"]').val('');
})
$('#reportModal').on('hide.bs.modal', function() {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	$('#reportModal .uploadResult .row div').remove();
	$('.bigPictureWrapper').hide();
})
$('#reportInfoModal').on('show.bs.modal', function() {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
})
$('#reportInfoModal').on('hide.bs.modal', function() {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportInfoModal .uploadResult .row div').remove();
	$('.bigPictureWrapper').hide();
})

// 모달 닫기 : 등록되지 않은 이미지 제거
function reportCloseAction() {
	if (uploadFiles != '') {
		fileDelete(uploadFiles);
		uploadFiles = '';
	}
}

// 등록된 공사 보고 정보
var modalData = '';
// 첨부 이미지 정보
var uploadFiles = '';

// 파일 첨부 초기화
function fileUploadClick() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult .row div').remove();
};

// 파일 삭제
function fileDelete(uploadFiles) {
	$.ajax({
		url: '/rptFileDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(uploadFiles),
		async: false
	})
}
// 이미지 등록
function fileList() {

	var x = document.getElementById("multiFile");

	if ('files' in x) {
		if (x.files.length > 5) {
			sweetModalError("파일 개수가 초과되었습니다.");
			document.getElementById("multiFile").value = "";
			return;
		}
	}

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	if (uploadFiles != '') {
		fileDelete(uploadFiles);
		uploadFiles = '';
	}

	// formData에 데이터 넣기
	for (var i = 0; i < files.length; i++) {

		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') == -1) {
			sweetModalError("이미지 파일을 등록해 주세요.")
			return false;
		}

		formData.append("uploadFile", files[i]);
	}

	// Ajax
	$.ajax({
		url: '/rptFileInsert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'
		, success: function(result) {
			showUpFile(result);
			if (result != null) {
				uploadFiles = result;
			}
		}
	})
};
// 이미지 표시
function showUpFile(uploadResultArr) {

	if (!uploadResultArr || uploadResultArr.length == 0)
		return;

	var uploadUL = $('#reportModal .uploadResult .row');

	var str = "";

	$(uploadResultArr)
		.each(
			function(i, obj) {
				str += imageshow(obj);
			});

	uploadUL.append(str);
}


// 등록 모달 열기
function reportInsertForm(no) {

	uploadFiles = '';

	$('#reportModal').modal('show');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('data-bs-toggle');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('data-bs-target');

	currentDay(); // 현재 날짜 표시

	$('#reportModal .modal-footer button:eq(0)').html('등록');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('onClick');
	$('#reportModal .modal-footer button:eq(0)').attr('onClick', 'reportInsertAction()');
	$('#reportModal h1 b').html('공사 보고 등록');

	$.ajax({
		url: '/rptDivi?contractNo=' + no,
		type: 'Get',

		success: function(result) {
			$('input[name="contractNo"]').val(result.contractNo);
			selectOption(result); // 공사 유형 표시
		}

	})
}

// 등록
function reportInsertAction() {

	// 유효성 검사
	if (selectData() == false) {
		return false;
	}
	if ($('input[name="title"]').val() == '') {
		sweetModalError("제목을 입력해 주세요");
		return false;
	}
	if ($('textarea[name="detailContent"]').val() == '') {
		sweetModalError("내용을 입력해 주세요");
		return false;
	}
	if (uploadFiles == '') {
		sweetModalError("이미지를 업로드해 주세요");
		return false;
	}
	if (maxLength() == false) {
		sweetModalError("글자수를 초과했습니다. 다시 확인해주세요.");
		return false;
	}

	// 폼 데이터 전송
	$('#reportModal .modal-footer button:eq(0)').attr('data-bs-toggle', "modal");
	$('#reportModal .modal-footer button:eq(0)').attr('data-bs-target', "#reportInfoModal");

	var formData = new FormData(document.reportInsertForm);

	/*for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}*/

	$.ajax({
		url: '/rptInsertInfo',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function() {
			//rptInfo(result); // 상세 정보 가져오기
			if (uploadFiles != '') {
				fileDelete(uploadFiles);
			}
			location.reload();
		}
	})
}
// 상세 정보 가져오기
function rptInfo(no) {

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {
			modalInfo(result); // 상세 내역 표시
			$('#reportInfoModal').modal('show');
		}
	})
}
// 상세 모달 열기
function reportInfoForm(no) {

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {
			modalData = result;
			$('#reportModal').modal('hide');
			modalInfo(result);
			$('#reportInfoModal').modal('show');
		}
	})
}

// 섬네일 이미지 명 가져오기
$('.uploadResult').on("click", "img", function(e) {
	var obj = e.target;

	var newobj = '';
	newobj = obj.src.replace("s_", '');

	showImage(newobj); // 원본 이미지 표시

})
// 공사 보고 삭제
$('#rpt_deleteBtn').on("click", function() {

	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		allowOutsideClick: false,
		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면

			$.ajax({
				url: '/rptDelete',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify(modalData.fileList),
				async: false
			})
			Swal.fire({
				icon: "success",
				title: "삭제 완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.reload();
				}
			});
		}
	});
})

// 수정 모달 열기
$('#rpt_updateBtn').on('click', function() {

	uploadFiles = '';
	$('#reportModal .modal-footer button:eq(0)').removeAttr('data-bs-toggle');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('data-bs-target');

	$.ajax({
		url: '/rptDivi?contractNo=' + $('input[name="contractNo"]').val(),
		type: 'Get',

		success: function(result) {

			selectOption(result);
			$('input[name="contractNo"]').val(result.contractNo);

			$.ajax({
				url: '/rptInfo?CttReportNo=' + $('input[name="cttReportNo"]').val(),
				type: 'Get'

				, success: function(result) {

					modalData = result;
					currentDay();
					modalInsertInfo(result);
					$('#reportModal').modal('show');
				}
			})
		}
	})
})

// 원본 이미지 확인
function showImage(img) {
	$('.bigPictureWrapper').css("display", "flex").show();
	$('.bigPicture').html(`<img src=${img}>`);
}
// 원본 이미지 클릭 시 닫기
$('.bigPictureWrapper').on("click", function() {
	$('.bigPictureWrapper').hide();
})

// 수정
function reportUpdateAction() {

	if (selectData() == false) {
		return false;
	}
	if ($('input[name="title"]').val() == '') {
		sweetModalError("제목을 입력해 주세요");
		return false;
	}
	if ($('textarea[name="detailContent"]').val() == '') {
		sweetModalError("내용을 입력해 주세요");
		return false;
	}
	if (maxLength() == false) {
		sweetModalError("글자수를 초과했습니다. 다시 확인해주세요.");
		return false;
	}

	// 폼 데이터 전송
	var formData = new FormData(document.reportInsertForm);

	if (uploadFiles == '' && modalData.fileList.length != 0) {
		formData.append("fileName", modalData.fileList[0].saveName);
	}
	if (modalData.fileId != 0) {
		formData.append("fileId", modalData.fileId);
	}

	/*for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}*/

	$.ajax({
		url: '/rptUpdate',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function(result) {
			if (uploadFiles != '') {
				fileDelete(uploadFiles);
			}
			modalInfo(result);
			$('#reportModal .modal-footer button:eq(0)').attr('data-bs-toggle', "modal");
			$('#reportModal .modal-footer button:eq(0)').attr('data-bs-target', "#reportInfoModal");
			$('#reportInfoModal').modal('show');
		}
	})

}

// 공사 보고 승인
$('#rpt_approveBtn').on("click", function() {

	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		allowOutsideClick: false,
		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '승인', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			$.ajax({
				url: '/rptApprove?cttReportNo=' + modalData.cttReportNo + '&workerNo=' + workerNo,
				type: 'Get'
				, success: function() {

					Swal.fire({
						icon: "success",
						title: "승인 완료되었습니다",
						allowOutsideClick: false
					}).then(e => {
						// 만약 Promise리턴을 받으면,
						if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
							location.reload();
						}
					});
				}
			})
		}
	});
})

// 공사 보고 요청
$('#rpt_requestBtn').on("click", function() {
	$.ajax({
		url: '/rptRequest?requestNo=' + rno,
		type: 'Get'
		, success: function() {
			sweetModalSuccess("승인 요청 되었습니다");
		}
	})
})

// 셀렉트 데이터 선택
function selectData() {
	if ($('select').val() !== '유형 선택') {
		$('input[name=cttDivision]').val($('select').val());
	} else {
		sweetModalError("유형을 선택해 주세요")
		return false;
	}
}

// 셀렉트 선택
function selectOption(result) {
	for (i = 0; i < result.divi.length; i++) {
		$('select').append(`<option>${result.divi[i]}</option>`);
	}
}
// 글자수 초과 체크
function maxLength() {
	for (i = 0; i < $('.input_warning').length; i++) {
		if ($('.input_warning').html() != '') {
			return false;
		}
	}
}
// 모달 정보 표기
function modalInfo(result) {
	$('#reportModal').modal('hide');

	$('input[name="cttReportNo"]').val(result.cttReportNo);
	$('input[name="contractNo"]').val(result.contractNo);
	$('input[name="fileId"]').val(result.fileId);

	$('#title').html(result.title);
	if (result.accessState == 'N') {
		$('#accessState').html('미승인');
	} else {
		$('#accessState').html('승인');
	}
	$('#cttDivision').html(result.cttDivision);
	$('textarea[name="detailContent"]').val(result.detailContent);
	$('#cttReportDt').html(result.cttReportDt.substring(0, 10));

	if (result.accessState == 'Y') {
		$('#rpt_updateBtn').remove();
		$('#rpt_deleteBtn').remove();
		$('#rpt_approveBtn').remove();
		$('#rpt_requestBtn').remove();
	} else if (user == result.workerInfo) {
		$('#rpt_approveBtn').remove();
	} else if (user == result.requesterInfo) {
		$('#rpt_updateBtn').remove();
		$('#rpt_deleteBtn').remove();
		$('#rpt_requestBtn').remove();
	}

	var uploadUL = $('#reportInfoModal .uploadResult .row');

	var str = "";

	result.fileList.forEach(e => {

		str += imageshow(e);
	})
	uploadUL.append(str);
}

// 모달 수정 정보 표기
function modalInsertInfo(result) {
	$('#reportInfoModal').modal('hide');
	$('input[name="cttReportNo"]').val(result.cttReportNo);
	$('input[name="fileId"]').val(result.fileId);
	$('input[name="title"]').val(result.title);
	$('textarea[name="detailContent"]').val(result.detailContent);
	$('input[name="cttReportDt"]').html(result.cttReportDt.substring(0, 10));

	$('select option:eq(0)').removeAttr('selected');

	for (i = 0; i < $('option').length; i++) {
		if ($('option:eq(' + i + ')').html() === result.cttDivision) {
			$('option:eq(' + i + ')').attr('selected', 'selected');
		}
	}
	var uploadUL = $('#reportModal .uploadResult .row');

	var str = "";

	result.fileList.forEach(e => {

		str += imageshow(e);

	})
	uploadUL.append(str);

	$('#reportModal .modal-footer button:eq(0)').html('수정');
	$('#reportModal .modal-footer button:eq(0)').removeAttr('onClick');
	$('#reportModal .modal-footer button:eq(0)').attr('onClick', 'reportUpdateAction()');
	$('#reportModal h1 b').html('공사 보고 수정');
}

// 첨부 파일 이미지 표시
function imageshow(obj) {
	var str = "";

	var savePath = obj.savePath.replaceAll("\\", "/")

	var fileCallPath = encodeURIComponent(savePath
		+ "/s_" + obj.saveName + "_"
		+ obj.originalFileName + "." + obj.ext);

	str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

	var originPath = fileCallPath;

	originPath = originPath.replace(new RegExp(/\\/g),
		"/");

	str += `<img src='/display?fileName=${originPath}' class="img-fluid img-thumbnail">`;
	str += `</div>`;

	return str;
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
		$(obj).next().html('&emsp;' + max_length + '글자 초과하여 등록할 수 없습니다.');
	} else {
		$(obj).next().html('');
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

function sweetModalSuccess(text) {
	Swal.fire({
		icon: "success",
		title: text
	});
}
