/**
 * requestUpdate.js
 */

$(document).ready(function(){
	//수정버튼 클릭시
    $(".upBtn").click(function(e){
        e.preventDefault();
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
    	});
    	
    //견적서 down삼각형 클릭시
    $(".down").click(function(){
		$(".innerT").css("display","inline-block");
		$(this).removeClass("fa-caret-down").addClass("fa-caret-up");
		$(this).removeClass("down").addClass("up");
	});	
	
	 //견적서 down삼각형 클릭시
    $(".up").click(function(){
		$(".innerT").css("display","none");
		$(this).removeClass("fa-caret-up").addClass("fa-caret-down");
		$(this).removeClass("up").addClass("down");
	});		 
});