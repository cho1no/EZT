/**
 * teamRequestInsert.js
 */

 
$(document).ready(function(){
	//카테고리 별 인원 행추가/삭제
	  $('#btn-add-row').click(function() {
		  
	    $('.table #detail:eq(0)').clone().appendTo('.table tbody:last')
	  });
	  
	  $('#btn-delete-row').click(function(e) {
		  $('.table tbody > tr:last').remove();
	  });
	  
	  
	 //게시글 등록버튼 눌렀을 때 

		let info = getRequestInsert();
        $.ajax('/tem/requestInsert',{
				type:"post",
				contentType:"application/json",
				data : JSON.stringify(info)
			})
			.done(result =>{
				if(result){
					alert('정상적으로 등록되었습니다.');
				}else{
					alert('정보 등록에 실패하였습니다.')
				}
			})
			.fail(err => console.log(err));
			
			
			function getRequestInsert(){
				let formData=$('form[name="insertTeam"]').serializeArray();
				let objData = {};
				$.each(formData, (idx, input)=>{
					objData[input.name] = input.value;
				});
				
				return objData;
			};
    });
