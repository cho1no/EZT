
// 크기&확장자 제한
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

var maxSize = 5242880; // 5MB

        function checkExtentsion(fileName, fileSize){
            if (fileSize >= maxSize){
                alert("파일 사이즈 초과");
                return false;
            }

            if (regex.test(fileName)){
                alert("해당 종류의 파일은 업로드할 수 없습니다.")
                return false;
            }
            return true;
        }

// 파일 이름 출력
var uploadResult = $('.uploadResult ul');

function nameList(){
	$('.uploadResult ul li').remove();
	var inp = document.getElementById("multiFile");
	for( var i = 0; i < inp.files.length; ++i){
		var name = inp.files.item(i).name;
		uploadResult.append("<li>" + name + "</li>");
	}
	
}



$('#uploadBtn').on("click", function(e){
	var formData = new FormData();
	
	var inputFile = $("input[name='uploadFile']");
	
	var files = inputFile[0].files;
	
	console.log(files);
	
	// formData에 데이터 넣기
	for(var i = 0; i < files.length; i++){
		if(!checkExtentsion(files[i].name, files[i].size)){
			return false;
		}
		if(files[i].type.indexOf('image') != -1){
			alert("이미지가 포함되어 있습니다.")
			return false;
		}
		formData.append("uploadFile", files[i]);
		
	}
	
	// Ajax
	$.ajax({
		url: '/uploadAjaxAction',
		processData : false,
		contentType : false,
		data : formData,
		type :'Post',
		dataType:'JSON',
		success : function(result){
			console.log(result);
			
			showUploadedFile(result);
		}
	});
	
});

// 모달 창 닫혔을 때 폼 초기화
$('#sendModal').on('hidden.bs.modal', function (e) {   

$('input[name="uploadFile"]').val('');
$('.uploadResult ul li').remove();
});checkExtentsion


