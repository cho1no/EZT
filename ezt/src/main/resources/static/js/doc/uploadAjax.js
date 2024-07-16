
$('input[name="uploadFile"]').attr('accept', '.ppt, .pptx, .doc, .docx, .xls, .xlsx, .pdf, .ai, .psd, .hwp, .hwpx, .txt');

// 크기&확장자 제한
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

function checkExtentsion(fileName, fileSize) {
	if (fileSize >= maxSize) {
		sweetAlertError("파일 사이즈 초과");
		return false;
	}

	if (regex.test(fileName)) {
		sweetAlertError("해당 종류의 파일은 업로드할 수 없습니다.")
		return false;
	}
	return true;
}

// 파일 이름 출력
var uploadResult = $('.uploadResult ul');

function nameList() {
	$('.uploadResult ul li').remove();
	$('.uploadResult ul').attr("style", "padding:0");
	var inp = document.getElementById("multiFile");
	for (var i = 0; i < inp.files.length; ++i) {
		var name = inp.files.item(i).name;
		uploadResult.append("<li style='list-style:none'>" + ' - ' + name + "</li>");
	}

}


// 업로드&전송
$('#uploadBtn').on("click", function() {

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	//console.log(files);

	// formData에 데이터 넣기
	for (var i = 0; i < files.length; i++) {
		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') != -1) {
			sweetAlertError("이미지 파일이 포함되어 있습니다.")
			return false;
		}
		if (files[i].type.indexOf('audio') != -1) {
			sweetAlertError("오디오 파일이 포함되어 있습니다.")
			return false;
		}
		formData.append("uploadFile", files[i]);

	}

	formData.append("proposalNo", proposalNo);
	formData.append("requestNo", requestNo);
	formData.append("worker", worker);

	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '승인', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			// Ajax
			$.ajax({
				url: '/uploadAjaxAction',
				processData: false,
				contentType: false,
				data: formData,
				type: 'Post',
				async: false

			})
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
	});
});

// 모달 창 닫혔을 때 폼 초기화
$('#sendModal').on('hidden.bs.modal', function() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult ul li').remove();
});

// 파일&견적서 삭제
$('#deleteBtn').on("click", function() {

	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면

			$.ajax({
				url: '/deleteFile',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify(fileList),
				async: false

			})

			Swal.fire({
				icon: "success",
				title: "삭제 완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.href = '/main';
				}
			});
		}
	});
})

// alert
function sweetAlertError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}
