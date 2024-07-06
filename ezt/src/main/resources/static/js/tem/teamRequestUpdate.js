/**
 * teamRequestUpdate.js 
 */

 $(document).ready(function(){
	//수정버튼 클릭시
    $(".update").click(function(e){
        e.preventDefault();
        if($(this).hasClass("upBtn")){
	        // readonly 속성을 제거
	        $(".team input[readonly], textarea[readonly]").removeAttr("readonly");
			//input 테두리 생기게
			$(".team .border").removeClass("border-light");
	        // 수정 완료로 변경
	        $(this).text("수정 완료");
	        // 클래스명 변경
	        $(this).removeClass("upBtn").addClass("saveBtn");

    
		}//수정완료 버튼 클릭시
		else{
			let info = getRequestInfo();
				
				$.ajax('/team/requestUpdate',{
					type:"post",
					contentType:"application/json",
					data : JSON.stringify(info)
				})
				.done(result =>{
					if(result){
						alert('정상적으로 수정되었습니다.');
					}else{
						alert('정보 수정에 실패하였습니다.')
					}
				})
				.fail(err => console.log(err));
				
				
				function getRequestInfo(){
					let formData=$('form[name="infoForm"]').serializeArray();
					let objData = {};
					$.each(formData, (idx, input)=>{
						objData[input.name] = input.value;
					});
					
					return objData;
				};
				
			//readonly 추가
			$(".team input, textarea").attr("readonly", true);
			//input 테두리 없애기
			$(".team .border").addClass("border-light");
	        // 수정 완료로 변경
	        $(this).text("수정");
	        // 클래스명 변경
	        $(this).removeClass("saveBtn").addClass("upBtn");

		};
		
    });
});