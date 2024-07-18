// 년원일 표기
function dayInfo(date) {
	var date_num1 = date.indexOf('-');
	var date_num2 = date.indexOf('-', date_num1 + 1)
	$('.cur_year').html(date.substring(0, date_num1) + '년');
	$('.cur_month').html(date.substring(date_num1 + 1, date_num2) + '월');
	$('.cur_day').html(date.substring(date_num2 + 1,) + '일');
}
// 날짜 표기
function dtInfo() {
	if ($('#startDt').html() == '1900-01-01') {
		$('#startDt').html('입력 필요');
		$('#startDt').attr('class', 'text-danger');
	}
	if ($('#endDt').html() == '1900-01-01') {
		$('#endDt').html('입력 필요');
		$('#endDt').attr('class', 'text-danger');
	}
}
// 하자 보수 기간
function repairDayInfo(date) {
	date.setFullYear(date.getFullYear() + 1);
	$('#repairDay').html(date.getFullYear() + "-"
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1))) + "-"
		+ ((date.getDate()) > 9 ? (date.getDate()) : ("0" + (date.getDate()))));
}
// worker 주소 표기
function workerAddr(work_addr) {
	var work_num1 = work_addr.indexOf('_');
	var work_num2 = work_addr.indexOf('_', work_num1 + 1)
	$('.info_worker #input_postcode').html(work_addr.substring(0, work_num1));
	$('.info_worker #input_addr').html(work_addr.substring(work_num1 + 1, work_num2));
	$('.info_worker #input_detailAddr').html(work_addr.substring(work_num2 + 1,));
}
// requester 주소 표기
function requesterAddr(req_addr) {
	var req_num1 = req_addr.indexOf('_');
	var req_num2 = req_addr.indexOf('_', req_num1 + 1)
	$('.info_req #input_postcode').html(req_addr.substring(0, req_num1));
	$('.info_req #input_addr').html(req_addr.substring(req_num1 + 1, req_num2));
	$('.info_req #input_detailAddr').html(req_addr.substring(req_num2 + 1,));
}
// 첨부파일 다운로드
function fileDownload(fileList){	
	for(i = 0; i < fileList.length; i++){
		var path = fileList[i].savePath.replaceAll('\\', '/');
		var orgname = fileList[i].originalFileName;
		var name = fileList[i].saveName;
		var ext = fileList[i].ext;

		var fileCallPath = encodeURIComponent(path
		+ "/" + name + "_"
		+ orgname + "." + ext);
		
		$('#fileDownload').append(`<p><a href="download?fileName=${fileCallPath}">${orgname}.${ext}</a></p> `)
	}
}

// 총 금액
function GetTotalInfo() {
	var total = 0;
	
	for (i = 0; i < $('#con_table tbody tr').length; i++) {
		total += Number($('#con_table tbody tr:eq(' + i + ') td:eq(1)').html().replaceAll(',', '').replace('원',''));
	}

	cttPrice = Number(cttPrice);

	if (cttPrice != total) {
		sweetModalError("금액의 합이 총 공사 금액과 같지 않습니다.");
		console.log(cttPrice)
		console.log(total)
		return false;
	}
	return true
}

// 내역 확인
function historyInfo() {

	for (i = 0; i < $('#con_table tbody tr').length; i++) {
		if($('#con_table tbody tr:eq(' + i + ') td:eq(3)').html().replaceAll(',', '') == ''){
			sweetModalError("내역이 없습니다. 확인해 주세요.");
			return false;
		};
	}
	return true
}


// 보내기, 승인 클릭 이벤트
$('#sendCon_work').on('click', function(event) {

	// 대금 지급 시기 총 금액 확인
	if(!GetTotalInfo()) return;
	
	// 내역 확인
	if(!historyInfo()) return;

	let contractNo = $(this).data('contractno');
	event.stopPropagation();
	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '보내기', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			Swal.fire({
				icon: "success",
				title: "완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.href = '/sendCon?contractNo=' + contractNo + '&type=0';
				}
			});
			$(this).unbind('click').click();
		}
	});
})
$('#approveCon_req').on('click', function(event) {
	let contractNo = $(this).data('contractno');
	event.stopPropagation();
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
			Swal.fire({
				icon: "success",
				title: "승인 완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.href = '/approveCon?contractNo=' + contractNo + '&type=1';
				}
			});
			$(this).unbind('click').click();
		}
	});
})
$('#sendCon_leader').on('click', function(event) {
	
	let contractNo = $(this).data('contractno');
	event.stopPropagation();
	Swal.fire({
		title: '이대로 진행하시겠습니까?',
		text: '다시 되돌릴 수 없습니다.',
		icon: 'warning',

		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
		cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
		confirmButtonText: '보내기', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정

	}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			Swal.fire({
				icon: "success",
				title: "완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.href = '/sendConPtn?contractNo=' + contractNo + '&type=0';
				}
			});
			$(this).unbind('click').click();
		}
	});
})
$('#approveCon_mem').on('click', function(event) {
	let contractNo = $(this).data('contractno');
	event.stopPropagation();
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
			Swal.fire({
				icon: "success",
				title: "승인 완료되었습니다",
				allowOutsideClick: false
			}).then(e => {
				// 만약 Promise리턴을 받으면,
				if (e.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
					location.href = '/approveConPtn?contractNo=' + contractNo + '&type=1';
				}
			});
			$(this).unbind('click').click();
		}
	});
})

