/**
 * rvwImg.js
 */


// 크기&확장자 제한
$('input[name="uploadFile"]').attr('accept', '.jpeg, .png, .gif, .jpg, .psd, .eps, .ai, .tiff, .bmp, .svg, .jfif');

var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

var reviewImg = '';

// 파일 첨부 초기화
function fileUploadClick() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult .row div').remove();
};

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
	
	if(reviewImg != ''){
		deleteFile(reviewImg);
		reviewImg = '';
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
		url: '/review/fileInsert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'
		, success: function(result) {
			showUpFile(result);
			if(result != null){
				reviewImg = result;
			}
		}
	})
};
// 이미지 표시
function showUpFile(uploadResultArr) {

	if (!uploadResultArr || uploadResultArr.length == 0)
		return;

	var uploadUL = $('.uploadResult .row');

	var str = "";

	$(uploadResultArr)
		.each(
			function(i, obj) {
				str += imageshow(obj);
			});

	uploadUL.append(str);
}
// 첨부 파일 이미지 표시
function imageshow(obj) {
	var str = "";

	var savePath = obj.savePath.replaceAll("\\", "/")

	var fileCallPath = encodeURIComponent(savePath
		+ "/s_" + obj.saveName + "_"
		+ obj.originalFileName + "." + obj.ext);

	str += `<div class="col-2 col-lg-2 item pl-4 my-3">`;

	var originPath = fileCallPath;

	originPath = originPath.replace(new RegExp(/\\/g),
		"/");

	str += `<img src='/display?fileName=${originPath}' class="img-fluid img-thumbnail">`;
	str += `</div>`;
	console.log('dfsd');
	return str;
}

function insert(){
	var formData = new FormData(document.rvInsertForm);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/review/insert',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON' 
		, success : function(result){
			console.log(result);
			if(reviewImg != ''){
			deleteFile(reviewImg);
			reviewImg = '';
			}
			Swal.fire({
			  text:'리뷰가 정상적으로 등록되었습니다.',
			  icon:'success'
			});
			location.href="/user/reqList"
		}
	})
}

function deleteReview(rno){
	$.ajax({
		url: '/review/delete?reviewNo=' + rno,
		type: 'Get'
		, success: function() {
			if(uploadImg != null){
				deleteFile(uploadImg);
				
			}Swal.fire({
				   title: '정말 삭제하시겠습니까?',
				   text: '삭제 시 해당 게시글은 복구할 수 없습니다.',
				   icon: 'question',
				   
				   showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
				   confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
				   cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
				   confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
				   cancelButtonText: '취소', // cancel 버튼 텍스트 지정
				   
				   
				   
				}).then(result => {
				   // 만약 Promise리턴을 받으면,
				   if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
				   
				      Swal.fire('정상적으로 삭제되었습니다.', '', 'success');
				   		}
				   })
				   .then(function(){					
						location.href="/review/list";
				});
		}
	})
}
function deleteFile(img){
	$.ajax({
		url: '/review/fileDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(img),
		async: false
	})
}
function sweetModalSuccess(text) {
	Swal.fire({
		icon: "success",
		title: text
	});
}
function sweetModalError(text) {
	Swal.fire({
		icon: "error",
		title: "다시 확인해 주세요",
		text: text
	});
}
