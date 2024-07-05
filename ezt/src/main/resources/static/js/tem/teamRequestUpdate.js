/**
 * teamRequestUpdate.js 
 */

 $(document).ready(function(){
	//수정버튼 클릭시
    $(".update").click(function(e){
        e.preventDefault();
        if($(this).hasClass("upBtn")){
	        // readonly 속성을 제거
	        $("input[readonly], textarea[readonly]").removeAttr("readonly");
	        // disabled 속성을 제거
	        $("select[disabled]").removeAttr("disabled");
			//input 테두리 생기게
			$(".border").removeClass("border-light");
	        // 수정 완료로 변경
	        $(this).text("수정 완료");
	        // 클래스명 변경
	        $(this).removeClass("upBtn").addClass("saveBtn");

	        
	        
	        
	        
	        
	        
		}//수정완료 버튼 클릭시
		else{
			for(i=0; i < $('tbody #detail' ).length; i++){
				$('tbody #detail:eq('+i+') td:eq(0) select' ).attr('name', 'teamWorkCategoryVO['+i+'].categoryCode')
				$('tbody #detail:eq('+i+') td:eq(1) input' ).attr('name', 'teamWorkCategoryVO['+i+'].headcount')
			}
			
			
			var formData = new FormData(document.infoForm);
			for (const pair of formData.entries()) {
			  console.log(pair[0], pair[1]);
			}
			let info = getRequestInfo();
			
			console.log(info);
			$.ajax('/team/requestUpdate',{
				type:"post",
				contentType: false,
				processData:false,
				data : formData
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
			$("input, textarea").attr("readonly", true);
	        // disabled 속성을 추가
	        $("select").attr("disabled",true);
			//input 테두리 없애기
			$(".border").addClass("border-light");
	        // 수정 완료로 변경
	        $(this).text("수정");
	        // 클래스명 변경
	        $(this).removeClass("saveBtn").addClass("upBtn");

		};
		
    });
});