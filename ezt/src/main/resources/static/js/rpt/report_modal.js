
// 크기&확장자 제한
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 1048576; // 5MB

function checkExtentsion(fileName, fileSize) {
	if (fileSize >= maxSize) {
		alert("파일 사이즈 초과");
		return false;
	}

	if (regex.test(fileName)) {
		alert("해당 종류의 파일은 업로드할 수 없습니다.")
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

// 모달
$('#reportModal').on('show.bs.modal', function(e) {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportModal select option').remove();
	$('#reportModal select').append(`<option selected>유형 선택</option>`);
	$('#reportModal input[name="uploadFile"]').val('');
})
$('#reportModal').on('hide.bs.modal', function(e) {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	$('#reportModal .uploadResult .row div').remove();
})
$('#reportInfoModal').on('show.bs.modal', function(e) {
	$('.smoothscroll.scroll-top').attr('style', 'display:none');
})
$('#reportInfoModal').on('hide.bs.modal', function(e) {
	$('.smoothscroll.scroll-top').removeAttr('style', 'display:none');
	$(this).find('form')[0].reset();
	$('#reportInfoModal .uploadResult .row div').remove();
})

var modalData = '';

// 첨부 이미지 업로드
var uploadFiles = '';

function fileUploadClick() {

	$('input[name="uploadFile"]').val('');
	$('.uploadResult .row div').remove();
};

function fileList() {


	var x = document.getElementById("multiFile");
	var txt = "";
	if ('files' in x) {
		if (x.files.length > 5) {
			alert("파일 개수가 초과되었습니다.");
			document.getElementById("multiFile").value = "";
			return;
		}
	}

	console.log(uploadFiles);

	if (uploadFiles != '') {
		$.ajax({
			url: '/rptFileDelete',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(uploadFiles),
			async: false

		})
	}

	//console.log("file");

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	// formData에 데이터 넣기
	for (var i = 0; i < files.length; i++) {

		if (!checkExtentsion(files[i].name, files[i].size)) {
			return false;
		}
		if (files[i].type.indexOf('image') == -1) {
			alert("이미지 파일을 등록해 주세요.")
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

		,
		success: function(result) {
			//console.log(result);
			showUpFile(result);
			uploadFiles = result;

		}
	})
};

function showUpFile(uploadResultArr) {

	console.log(uploadResultArr);

	if (!uploadResultArr || uploadResultArr.length == 0)
		return;

	var uploadUL = $('.uploadResult .row');

	var str = "";

	$(uploadResultArr)
		.each(
			function(i, obj) {

				var savePath = obj.savePath.replaceAll("\\", "/")

				var fileCallPath = encodeURIComponent(savePath
					+ "/s_" + obj.saveName + "_"
					+ obj.originalFileName + "." + obj.ext);

				str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

				var originPath = fileCallPath;

				originPath = originPath.replace(new RegExp(/\\/g),
					"/");

				str += `<img src='/display?fileName=${originPath}' class="img-fluid">`;
				str += `</div>`;

			});

	uploadUL.append(str);
}


function reportInsertForm(no) {

	$('#reportModal').modal('show');

	currentDay();


	$.ajax({
		url: '/rptDivi?contractNo=' + no,
		type: 'Get',

		success: function(result) {

			for (i = 0; i < result.divi.length; i++) {
				$('select').append(`<option>${result.divi[i]}</option>`);
			}
				$('input[name="contractNo"]').val(result.contractNo);
		}

	})
}

function reportInsertAction() {
	if ($('select').val() !== '유형 선택') {
		$('input[name=cttDivision]').val($('select').val());
	} else {
		$('input[name=cttDivision]').val(null);
	}

	// 폼 데이터 전송
	var formData = new FormData(document.reportInsertForm);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/rptInsertInfo',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function(result) {
			rptInfo(result)

		}
	})
}

function rptInfo(no) {

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {

			$('#reportModal').modal('hide');

			$('input[name="cttReportNo"]').val(result.cttReportNo);
			$('input[name="contractNo"]').val(result.contractNo);

			$('#title').html(result.title);
			if (result.accessState == 'N') {
				$('#accessState').html('미승인');
			} else {
				$('#accessState').html('승인');
			}
			$('#cttDivision').html(result.cttDivision);
			$('textarea[name="detailContent"]').val(result.detailContent);
			$('#cttReportDt').html(result.cttReportDt.substring(0, 10));

			var uploadUL = $('#reportInfoModal .uploadResult .row');

			var str = "";

			result.fileList.forEach(e => {

				var savePath = e.savePath.replaceAll("\\", "/")

				var fileCallPath = encodeURIComponent(savePath
					+ "/s_" + e.saveName + "_"
					+ e.originalFileName + "." + e.ext);

				str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

				var originPath = fileCallPath;

				originPath = originPath.replace(new RegExp(/\\/g),
					"/");

				str += `<img src='/display?fileName=${originPath}' class="img-fluid">`;
				str += `</div>`;

			})
			uploadUL.append(str);
		}
	})
}

function reportInfoForm(no) {

	$('#reportInfoModal').modal('show')

	$.ajax({
		url: '/rptInfo?CttReportNo=' + no,
		type: 'Get'

		, success: function(result) {

			modalData = result;

			$('#reportModal').modal('hide');
			console.log(result);

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

			var uploadUL = $('#reportInfoModal .uploadResult .row');

			var str = "";

			result.fileList.forEach(e => {
				console.log(e)


				var savePath = e.savePath.replaceAll("\\", "/")

				var fileCallPath = encodeURIComponent(savePath
					+ "/s_" + e.saveName + "_"
					+ e.originalFileName + "." + e.ext);

				str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

				var originPath = fileCallPath;

				originPath = originPath.replace(new RegExp(/\\/g),
					"/");

				str += `<img src='/display?fileName=${originPath}' class="img-fluid">`;
				str += `</div>`;


			})
			uploadUL.append(str);
		}
	})
}


$('.uploadResult').on("click", "img", function(e) {
	var obj = e.target;

	var newobj = '';
	newobj = obj.src.replace("s_", '');

	showImage(newobj);

})

function showImage(img) {

	$('.bigPictureWrapper').css("display", "flex").show();
	$('.bigPicture').html(`<img src=${img}>`);
}

$('.bigPictureWrapper').on("click", function(e) {
	$('.bigPictureWrapper').hide();
})


$('#rpt_deleteBtn').on("click", function(e) {

	$.ajax({
		url: '/rptDelete',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(modalData.fileList),
		async: false

	})
	location.href = '/test';
})

$('#rpt_updateBtn').on('click', function(e) {

	$.ajax({
		url: '/rptDivi?contractNo=' + $('input[name="contractNo"]').val(),
		type: 'Get',

		success: function(result) {

			for (i = 0; i < result.divi.length; i++) {
				$('select').append(`<option>${result.divi[i]}</option>`);

			}
				$('input[name="contractNo"]').val(result.contractNo);

			$.ajax({
				url: '/rptInfo?CttReportNo=' + $('input[name="cttReportNo"]').val(),
				type: 'Get'

				, success: function(result) {

					modalData = result;

					currentDay();

					$('#reportInfoModal').modal('hide');
					$('input[name="cttReportNo"]').val(result.cttReportNo);
					$('input[name="fileId"]').val(result.fileId);
					$('input[name="title"]').val(result.title);
					$('textarea[name="detailContent"]').val(result.detailContent);
					$('input[name="cttReportDt"]').html(result.cttReportDt.substring(0, 10));
					
					$('select option:eq(0)').removeAttr('selected');
					
					for(i=0; i<$('option').length; i++){
						if($('option:eq('+i+')').html() === result.cttDivision){
							$('option:eq('+i+')').attr('selected', 'selected');
						}
					}

					var uploadUL = $('#reportModal .uploadResult .row');

					var str = "";

					result.fileList.forEach(e => {

						var savePath = e.savePath.replaceAll("\\", "/")

						var fileCallPath = encodeURIComponent(savePath
							+ "/s_" + e.saveName + "_"
							+ e.originalFileName + "." + e.ext);

						str += `<div class="col-4 col-lg-4 item pl-4 my-3">`;

						var originPath = fileCallPath;

						originPath = originPath.replace(new RegExp(/\\/g),
							"/");

						str += `<img src='/display?fileName=${originPath}' class="img-fluid">`;
						str += `</div>`;

					})
					uploadUL.append(str);
					
					$('.modal-footer button:eq(0)').html('수정');
					$('.modal-footer button:eq(0)').removeAttr('onClick');
					$('.modal-footer button:eq(0)').attr('onClick', 'reportUpdateAction()');
					$('h1 b').html('공사 보고 수정');
					
				}

			})
		}
	})
	
})

function reportUpdateAction(){
	if ($('select').val() !== '유형 선택') {
		$('input[name=cttDivision]').val($('select').val());
	} else {
		$('input[name=cttDivision]').val(null);
	}

	// 폼 데이터 전송
	var formData = new FormData(document.reportInsertForm);

	for (const pair of formData.entries()) {
		console.log(pair[0], pair[1]);
	}

	$.ajax({
		url: '/rptUpdate',
		processData: false,
		contentType: false,
		data: formData,
		type: 'Post',
		dataType: 'JSON'

		, success: function(result) {
			rptInfo($('input[name="cttReportNo"]').val());

		}
	})
	
}
