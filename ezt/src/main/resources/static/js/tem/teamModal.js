/**
 * teamModal.js
 */


$(document).ready(function() {
    $('#denyBtn').click(function() {
        let enrollNo = $('#enrollNo').val();
        let writer = $('#writer').val();
        let content = $('textarea[name="content"]').val();
        
         // content가 null이면 alert
        if (content == null ) {
            alert("반려 사유를 입력해주세요.");
            return;
        }

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
					$('textarea[name="content"]').val('');
                }
            },
            error: function(error) {
                console.log(error);
                alert("오류가 발생했습니다.");
            }
        });
    });
    
    $('.approveBtn').click(function() {
        let enrollNo = $('#enrollNo').val();
        let usersNo = $('#writer').val();
        let workCode = $('textarea[name="content"]').val();
        let teamNo = $('#writer').val();

        
         // content가 null이면 alert
        if (content == null ) {
            alert("반려 사유를 입력해주세요.");
            return;
        }

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
					$('textarea[name="content"]').val('');
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
				if (result.length > 0) {
	            $('#volunteerBody').find('tr').eq(0).find('td').click();
	        }else{
				$('#content').text(''); // 상세 내용 초기화
	            $('#denyUser').val(''); // 신청인 초기화
	            $('#enrollNo').val(''); // enrollNo 초기화
	            $('#worker').text(''); // worker 초기화
			}
		})
	}
	
	function detail(){
		$('#content').text($(event.currentTarget).data("content"));
		$('#denyUser').val($(event.currentTarget).data("worker"));
		$('#enrollNo').val($(event.currentTarget).data("eno"));
		$('#worker').text($(event.currentTarget).data("worker"));
	}
	
	function openApplyModal(teamNo, categoryCode, workCode) {
    // 모달이 열릴 때 categoryCode를 설정하는 부분
    $('#apply').on('shown.bs.modal', function () {
        $('#categoryCode').val(categoryCode);  // 모달 내의 입력 필드에 카테고리 코드 설정
        $('#workCode').val(workCode);  
            });

}