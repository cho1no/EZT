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
            Swal.fire({
					  text:'반려사유를 입력해주세요.',
					  icon:'warning'
					});
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
                    Swal.fire({
					  text:'반려 처리되었습니다..',
					  icon:'success'
					});
                    $('#deny').modal('hide');
					$('textarea[name="content"]').val('');
                }
            },
            error: function(error) {
                console.log(error);
                Swal.fire({
					  text:'오류가 발생하였습니다.',
					  icon:'error'
					});
            }
        });
    });
    
    $('#infoBtn').click(function(e){
		let usersNo = $('#usersNo').val();
		
		window.open('/find/workerMoreInfo?usersNo=' + usersNo);
	})
    
      $('.approveBtn').click(function() {
        let enrollNo = $('#enrollNo').val();
        let usersNo = $('#usersNo').val(); 
        let workCode = $('#workCode').val();
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
                        Swal.fire({
					  text:'팀원으로 추가되었습니다.',
					  icon:'success'
					});
                        $('#volunteer').modal('hide');
                    }
                },
                error: function(error) {
                    console.log(error);
                    Swal.fire({
					  text:'오류가 발생하였습니다.',
					  icon:'error'
					});
                }
            });
        }
    });


	$('.endBtn').click(function(){
		Swal.fire({
				   title: '팀원 모집이 완료되었나요?',
				   text: '승인 시 이후 추가 팀원 모집은 불가합니다.',
				   icon: 'question',
				   
				   showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
				   confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
				   cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
				   confirmButtonText: '승인', // confirm 버튼 텍스트 지정
				   cancelButtonText: '취소', // cancel 버튼 텍스트 지정
				   
				   
				   
				}).then(result => {
				   // 만약 Promise리턴을 받으면,
				   if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
				   
				      Swal.fire('팀원모집이 완료되었습니다.', '', 'success');
				   }
				});
				
					})
});

 function openModal(teamNo, workCode, categoryCode){
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
                                  data-usersno="${result[i].usersNo}" 
                                  data-content="${result[i].content}" 
                                  data-workcode="${workCode}"
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
	
	function detail(tr){
		$('#content').text($(tr).data("content"));
		$('#denyUser').val($(tr).data("worker"));
		$('#enrollNo').val($(tr).data("eno"));
		$('#worker').text($(tr).data("worker"));
		$('#workCode').val($(tr).data("workcode"));
		$('#usersNo').val($(tr).data("usersno"));
	}
	
	function openApplyModal(teamNo, categoryCode, workCode) {
    // 모달이 열릴 때 categoryCode를 설정하는 부분
    $('#apply').on('shown.bs.modal', function () {
    $('#categoryCode').val(categoryCode); 
    $('#category').text(workCode);  
            });

}