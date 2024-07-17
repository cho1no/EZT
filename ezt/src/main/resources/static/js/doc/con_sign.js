
function DataCompress(){
	// 서명 데이터를 JSON 형식으로 가져오기
	var signatureData = $('.sigPad').signaturePad().getSignatureString();

	// 서명 데이터를 압축
	compressedData = LZString.compressToBase64(signatureData);

	// 압축된 데이터를 숨겨진 input에 저장
	$('input.output').val(compressedData);
}

function signView(compressedSignatureData, num) {
    //console.log("compressedSignatureData = " + compressedSignatureData);

    // 압축된 데이터 복원
    let decompressedSignatureData 
              = LZString.decompressFromBase64(compressedSignatureData);

    // 복원된 서명을 JSON화
    let signatureData = JSON.parse(decompressedSignatureData);

    // 서명 그리기
    drawSignature(signatureData, num);
}

function drawSignature(data, num) {
    // Canvas / 컨텍스트
    let canvas;
    
    if(num == 0 || num == 2){
    	canvas = document.getElementById('signatureCanvas_req');
    }else if(num == 1 || num == 3){
    	canvas = document.getElementById('signatureCanvas_work');
    }
    
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
    if(num == 3 || num == 2){
        $('#sendModal').modal('hide');
    }
    
}