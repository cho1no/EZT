// 서명
var compressedData;

$('.sigPad').signaturePad({ drawOnly: true });

$('#saveModal').click(function(event) {
	event.preventDefault(); // 폼의 기본 제출 동작을 방지

	// 서명 데이터를 JSON 형식으로 가져오기
	var signatureData = $('.sigPad').signaturePad().getSignatureString();

	// 서명 데이터를 압축
	compressedData = LZString.compressToBase64(signatureData);

	// 압축된 데이터를 숨겨진 input에 저장
	$('input.output').val(compressedData);

	// 압축된 데이터 출력 (선택 사항)
	//console.log(compressedData);

	signView(compressedData);
});


function drawSignature(data) {
	// Canvas / 컨텍스트
	let canvas = document.getElementById('signatureCanvas');
	let ctx = canvas.getContext('2d');

	// 캔버스 초기화
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	ctx.beginPath();
	for (let i = 0; i < data.length; i++) {
		let point = data[i];
		ctx.moveTo(point.mx, point.my);
		ctx.lineTo(point.lx, point.ly);
	}
	ctx.stroke();
	$('#sendModal').modal('hide');
}

function signView(compressedSignatureData) {
	//console.log("compressedSignatureData = " + compressedSignatureData);

	// 압축된 데이터 복원
	let decompressedSignatureData
		= LZString.decompressFromBase64(compressedSignatureData);

	// 복원된 서명을 JSON화
	let signatureData = JSON.parse(decompressedSignatureData);

	// 서명 그리기
	drawSignature(signatureData);
}
// 서명