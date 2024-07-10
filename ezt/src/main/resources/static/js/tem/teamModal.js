/**
 * teamModal.js
 */
 function openModal(teamNo, workCode){
		$.ajax('/team/volunteerList',{
			data : {teamNo, workCode}
		})
		.done(result =>{
			
				$('#volunteerBody').html('');
			for(i=0; i<result.length; i++){
			
				let table = `<tr data-eno="${result[i].enrollNo}" 
								 data-worker="${result[i].worker}" 
								 data-content="${result[i].content}" 
									onClick="detail()">
	                    		<td>${i+1}</td>
	                    		<td>${result[i].worker}</td>
	                    		<td>${result[i].career}</td>
	                    	</tr>`
				$('#volunteerBody').append(table);
            }
			if(result.length>0){
				$('#volunteerBody').find('tr').eq(0).find('td').click();
			}
		})
	}
	
	function detail(){
		$('#content').text($(event.currentTarget).data("content"));
		$('#denyUser').val($(event.currentTarget).data("worker"));
		$('#enrollNo').val($(event.currentTarget).data("eno"));
		$('#worker').text($(event.currentTarget).data("worker"));
	}

 /*$('denyBtn').click(function(e){
	$.ajax('/team/memberDeny'),{
		type:"post",
		contentType:"application/json",
		
	}
 })*/