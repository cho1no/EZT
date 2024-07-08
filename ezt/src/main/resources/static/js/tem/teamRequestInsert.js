$(document).ready(function(){
    $('#btn-add-row').click(function() {
        let clone = $('#detail-template').clone();
        let index = $('.table tbody tr').length - 1; // 현재 행의 개수
        clone.find('select, input').each(function() {
            let name = $(this).attr('name');
            if (name) {
                let newName = name.replace(/\[\d+\]/, '[' + index + ']');
                $(this).attr('name', newName);
            }
        });
        clone.removeAttr('id');
        clone.appendTo('.table tbody');
    });

    $('#btn-delete-row').click(function(e) {
        if ($('.table tbody tr').length > 2) {
            $('.table tbody tr:last').remove();
        }
    });

    // 게시글 등록 버튼 눌렀을 때 
    $('.insert').click(function() {
        let formData = getRequestInsert();
        $.ajax({
            url: '/team/requestInsert',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(result) {
                if (result) {
                    alert('정상적으로 등록되었습니다.');
                    window.location.href = "/team/requestList";
                } else {
                    alert('정보 등록에 실패하였습니다.');
                }
            },
            error: function(err) {
                console.log(err);
            }
        });
    });

    function getRequestInsert() {
        let formData = $('form[name="insertTeam"]').serializeArray();
        let objData = {
            workCategoryVO: []
        };

        formData.forEach(function(twc) {
            if (twc.name.startsWith('workCategoryVO')) {
                let index = twc.name.match(/\d+/)[0];
                if (!objData.workCategoryVO[index]) {
                    objData.workCategoryVO[index] = {};
                }
                let field = twc.name.replace(/^workCategoryVO\[\d+\]\./, '');
                objData.workCategoryVO[index][field] = twc.value;
            } else {
                objData[twc.name] = twc.value;
            }
        });

        return objData;
    }
});
