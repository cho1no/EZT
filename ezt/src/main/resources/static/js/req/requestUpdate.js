/**
 * requestUpdate.js
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
			//input css 흰색으로 변경
			$("input, textarea").css("background-color", "white");
	        // 수정 완료로 변경
	        $(this).text("수정 완료");
	        // 클래스명 변경
	        $(this).removeClass("upBtn").addClass("saveBtn");
	        //우편번호 찾기 버튼 보이게!!
	        $(".postcode").css("display","block");
	        
	        
	        
	        
	        
	        
		}//수정완료 버튼 클릭시
		else{
			let info = getRequestInfo();
			
			$.ajax('request/update',{
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
			$("input, textarea").attr("readonly", true);
	        // disabled 속성을 추가
	        $("select").attr("disabled",true);
			//input css 흰색으로 변경
			$("input, textarea").css("background-color", "#eee");
	        // 수정 완료로 변경
	        $(this).text("수정");
	        // 클래스명 변경
	        $(this).removeClass("saveBtn").addClass("upBtn");
	        //우편번호 찾기 버튼 안보이게!!
	        $(".postcode").css("display","none");
		};
		
    });
    	
    	
    //견적서 down삼각형 클릭시
    $(".fa-solid").click(function(e){
		let table=$(e.target).closest("tr").next().find(".innerT");
		
		if($(this).hasClass("down")){			
			table.css("display","block");
			$(this).removeClass("fa-caret-down").addClass("fa-caret-up");
			$(this).removeClass("down").addClass("up");
		}
		 //견적서 down삼각형 클릭시
	   else{
			table.css("display","none");
			$(this).removeClass("fa-caret-up").addClass("fa-caret-down");
			$(this).removeClass("up").addClass("down");
		};		 
	});	
	
});