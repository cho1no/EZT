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
        let usersNo = $('#worker').data('usersNo'); 
        let workCode = $('#modalCategoryCode').val();
        let teamNo = $('#teamNo').val();


        if (confirm('해당 작업자를 팀원으로 추가하시겠습니까?')) {
            let data = {
                enrollNo: enrollNo,
                usersNo: usersNo,
                workCode: workCode,
                teamNo: teamNo,

            };

            $.ajax({
                url: '/team/approveMember',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    if (response) {
                        alert("팀원으로 추가 되었습니다.");
                        $('#volunteer').modal('hide');
                    }
                },
                error: function(error) {
                    console.log(error);
                    alert("오류가 발생했습니다.");
                }
            });
        }
    });


	$('.endBtn').click(function(){
		if(confirm('정말 팀원 모집이 완료되었나요?')){
			
		}
	})
});

 function openModal(teamNo, workCode){
	 // span 태그에 workCode 값 설정
    $('#modalCategoryCode').text(workCode);

    // AJAX 요청 보내기
    $.ajax({
        url: '/team/volunteerList',
        method: 'GET',
        data: { teamNo: teamNo, workCode: workCode },
        success: function(result) {
            $('#volunteerBody').html('');
            for (let i = 0; i < result.length; i++) {
                let table = `<tr data-eno="${result[i].enrollNo}" 
                                  data-worker="${result[i].worker}" 
                                  data-usersNo="${result[i].usersNo}" 
                                  data-content="${result[i].content}" 
                                  onclick="detail(this)">
                                <td>${i + 1}</td>
                                <td>${result[i].worker}</td>
                                <td>${result[i].career}</td>
                            </tr>`;
                $('#volunteerBody').append(table);
            }
            if (result.length > 0) {
                $('#volunteerBody').find('tr').eq(0).click();
            } else {
                $('#content').text('');
                $('#denyUser').val('');
                $('#enrollNo').val('');
                $('#worker').text('');
            }
        }
    });
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