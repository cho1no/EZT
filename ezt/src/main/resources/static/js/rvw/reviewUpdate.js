/**
 * smRvUpdate.js
 */

$(document).ready(function(){
	//수정하기 버튼 클릭 시
	$(".update").click(function(e){
		e.preventDefault();
		
		if($(this).hasClass("upBtn")){
			// readonly 속성을 제거
	        $(".writerContent").removeAttr("readonly");
			//input 테두리 생기게
			$(".writerContent").css("border", "1px solid");
	        // 수정 완료로 변경
	        $(this).text("수정 완료");

	        // 클래스명 변경
	        $(this).removeClass("upBtn").addClass("saveBtn");
	        
	        //현재 별점 div숨기기
	        $(".star-rating").css("display", "none");
	        //새로운 별점
	        $(".reStar").css("display", "block");
	        
		}//수정완료 버튼 클릭시
		else{
			
			let info = getReviewInfo();
			
			$.ajax('reviewUpdate',{
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
			
			
			function getReviewInfo(){
				let formData=$('form[name="rvInfoForm"]').serializeArray();
				let objData = {};
				$.each(formData, (idx, input)=>{
					objData[input.name] = input.value;
				});
				
				return objData;
			};
			
			
			//readonly 추가
			$(".writerContent").attr("readonly", true);
			//input 테두리 없애기
			$(".writerContent").css("border", "none");
	        // 수정 완료로 변경
	        $(this).text("수정");
	        // 클래스명 변경
	        $(this).removeClass("saveBtn").addClass("upBtn");

			//별점 다시 보이기 
			$(".star-rating").css("display", "")
			
			$(".reStar").css("display", "none");
			
			location.reload();
		}
	})
	
})
 