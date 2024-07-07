/**
 * teamRequestUpdate.js 
 */
$(document).ready(function(){
    // 의뢰 신청 상세 수정 버튼 클릭 시
    $(".deUpBtn").click(function(e){
        e.preventDefault();
        let row = $(this).closest("tr"); // 현재 버튼이 속한 행을 선택

        if($(this).hasClass("deUpBtn")){
            // readonly 속성을 제거
            row.find("input[readonly], textarea[readonly]").removeAttr("readonly");
            // disabled 속성을 제거
            row.find("select[disabled]").removeAttr("disabled");
            // input 테두리 생기게
            row.find(".border").removeClass("border-light");
            // 수정 완료로 변경
            $(this).text("수정 완료");
            // 클래스명 변경
            $(this).removeClass("deUpBtn").addClass("deSaveBtn");
        } else {
            let info = getRequestInfoDetail(row);

            // 유효성 검사
            if (!validateInfoDetail(info)) {
                return;
            }

            $.ajax('/team/teamDetailUpdate', {
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(info)
            })
            .done(result => {
                if (result) {
                    alert('정상적으로 수정되었습니다.');
                } else {
                    alert('정보 수정에 실패하였습니다.');
                }
            })
            .fail(err => console.log(err));

            // readonly 추가
            row.find("input, textarea").attr("readonly", true);
            // disabled 속성을 추가
            row.find("select").attr("disabled", true);
            // input 테두리 없애기
            row.find(".border").addClass("border-light");
            // 수정 완료로 변경
            $(this).text("수정");
            // 클래스명 변경
            $(this).removeClass("deSaveBtn").addClass("deUpBtn");
        }
    });

    // 의뢰 신청 수정 버튼 클릭 시
    $(".update").click(function(e){
        e.preventDefault();
        if($(this).hasClass("upBtn")){
            // readonly 속성을 제거
            $(".team input[readonly], textarea[readonly]").removeAttr("readonly");
            // input 테두리 생기게
            $(".team .border").removeClass("border-light");
            // 수정 완료로 변경
            $(this).text("수정 완료");
            // 클래스명 변경
            $(this).removeClass("upBtn").addClass("saveBtn");
        } else {
            let info = getRequestInfoForm($('form[name="infoForm"]'));

            $.ajax('/team/requestUpdate', {
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(info)
            })
            .done(result => {
                if (result) {
                    alert('정상적으로 수정되었습니다.');
                } else {
                    alert('정보 수정에 실패하였습니다.');
                }
            })
            .fail(err => console.log(err));

            // readonly 추가
            $(".team input, textarea").attr("readonly", true);
            // input 테두리 없애기
            $(".team .border").addClass("border-light");
            // 수정 완료로 변경
            $(this).text("수정");
            // 클래스명 변경
            $(this).removeClass("saveBtn").addClass("upBtn");
        }
    });

    function getRequestInfoDetail(row) {
        let formData = row.find("input, select, textarea").serializeArray();
        let objData = {};
        $.each(formData, (idx, input) => {
            objData[input.name] = input.value;
        });
        return objData;
    }

    function getRequestInfoForm(form) {
        let formData = form.serializeArray();
        let objData = {};
        $.each(formData, (idx, input) => {
            objData[input.name] = input.value;
        });
        return objData;
    }

    function validateInfoDetail(info) {
        // 숫자 유효성 검사
        if (isNaN(info.headcount)) {
            alert("숫자만 입력해주세요.");
            return false;
        }

        // 중복 categoryCode 검사 (서버에서 실제로 중복 여부를 체크해야 함)
        // 이 예제에서는 간단히 현재 form의 categoryCode와 비교
        let existingCodes = [];
        $("[name='categoryCode']").each(function(){
            existingCodes.push($(this).val());
        });

        if (existingCodes.filter(code => code === info.categoryCode).length > 1) {
            alert("이미 존재하는 항목입니다.");
            return false;
        }

        return true;
    }


});
