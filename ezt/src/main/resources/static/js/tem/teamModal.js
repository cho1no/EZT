/**
 * teamModal.js
 */
 $(document).ready(function () {
    $('.btnn').click(function () {
        var teamNo = $(this).data('teamNo');
        var categoryCode = $(this).data('categoryCode');

        // Ajax 요청을 통해 서버에서 데이터를 가져와 모달 창에 출력
        $.ajax({
            type: "GET",
            url: "/team/teamRequestInfo",
            data: {
                teamNo: teamNo,
                categoryCode: categoryCode
            },
            success: function (data) {
                var modalBody = $('#volunteer').find('.modal-body');
                modalBody.empty(); // 모달 body 내용 초기화

                // Thymeleaf에서 받아온 데이터를 반복해서 출력
                $(data).each(function(index, volunteer) {
                    var html = '<tr>';
                    html += '<td>' + (index + 1) + '</td>'; // 순번 처리
                    html += '<td>' + volunteer.worker + '</td>';
                    html += '<td>' + volunteer.career + '</td>';
                    html += '<td><a href="#">자세히 보기</a></td>';
                    html += '</tr>';
                    modalBody.find('tbody').append(html);
                });

                // 모달 열기
                $('#volunteer').modal('show');
            },
            error: function () {
                alert('데이터를 가져오는 중 오류가 발생했습니다.');
            }
        });
    });
});