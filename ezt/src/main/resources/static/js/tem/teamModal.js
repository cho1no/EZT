/**
 * teamModal.js
 */


$(document).ready(function() {
    $('#denyBtn').click(function() {
        let enrollNo = $('#enrollNo').val();
        let writer = $('#writer').val();
        let content = $('textarea[name="content"]').val();

        let data = {
            enrollNo: enrollNo,
            writer: writer,
            content: content
        };

        $.ajax({
            url: '/team/memberDeny',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                if (response) {
                    alert("반려 처리되었습니다.");
                    $('#deny').modal('hide');
                } else {
                    alert("반려 처리에 실패했습니다.");
                }
            },
            error: function(error) {
                console.log(error);
                alert("오류가 발생했습니다.");
            }
        });
    });
});

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

