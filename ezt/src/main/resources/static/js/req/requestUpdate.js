/**
 * requestUpdate.js
 */

$(document).ready(function () {
  //수정버튼 클릭시
  $(".update").click(function (e) {
    e.preventDefault();
    if ($(this).hasClass("upBtn")) {
      // readonly 속성을 제거
      $("input[readonly], textarea[readonly]").removeAttr("readonly");
      // disabled 속성을 제거
      $("select[disabled]").removeAttr("disabled");
      //input css 흰색으로 변경
      $("input, textarea").css("background-color", "white");
      // 수정 완료로 변경
      $(this).text("수정 완료");
      //datepicker 사용을 위한 id추가
      $('input[name="hopeCttStartDt"]' ).attr('id','startDt');
      $('input[name="hopeCttEndDt"]' ).attr('id','endDt');
      // 클래스명 변경
      $(this).removeClass("upBtn").addClass("saveBtn");
      //우편번호 찾기 버튼 보이게!!
      $(".postcode").css("display", "block");
      
      
      
      // 시작날짜, 마감날짜 datepicker
	$('#startDt').datepicker({
		 dateFormat: 'yy-mm-dd',
		 // 0 : 오늘 날짜
		  minDate: 0
		})
		.on( "change", function() {
			$( "#endDt" ).datepicker( "option", "minDate", getDate( this ) )
		});
	$( "#endDt" ).datepicker({
		dateFormat: 'yy-mm-dd',
  	  	minDate: 0
        })
    	.on( "change", function() {
    	$('#startDt').datepicker( "option", "maxDate", getDate( this ) );
    });
	function getDate( element ) {

	      var date;
	      try {
	        date = element.value
	      } catch( error ) {
	        date = null;
	      }
	 
	      return date;
	    }
    } //수정완료 버튼 클릭시
    else {
      let info = getRequestInfo();
      console.log(info);
      $.ajax("/request/update", {
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(info),
      })
        .done((result) => {
          if (result) {
            Swal.fire({
			  text:'정상적으로 수정되었습니다.',
			  icon:'success'
			});
          } else {
            Swal.fire({
			  text:'수정에 실패하였습니다.',
			  icon:'error'
			});
          }
        })
        .fail((err) => console.log(err));

      function getRequestInfo() {
        let formData = $('form[name="infoForm"]').serializeArray();
        let objData = {};
        $.each(formData, (idx, input) => {
          objData[input.name] = input.value;
        });

        return objData;
      }
      //readonly 추가
      $("input, textarea").attr("readonly", true);
      // disabled 속성을 추가
      $("select").attr("disabled", true);
      //input css 흰색으로 변경
      $("input, textarea").css("background-color", "#eee");
      //datepicker 사용을 막기 위한 id제거
      $('input[name="hopeCttStartDt"]' ).removeAttr('id');
      $('input[name="hopeCttEndDt"]' ).removeAttr('id');
      // 수정 완료로 변경
      $(this).text("수정");
      // 클래스명 변경
      $(this).removeClass("saveBtn").addClass("upBtn");
      //우편번호 찾기 버튼 안보이게!!
      $(".postcode").css("display", "none");
    }
  });

  //견적서 down삼각형 클릭시
  $(".fa-solid").click(function (e) {
    if ($(this).hasClass("fa-caret-down")) {
      $(this).removeClass("fa-caret-down").addClass("fa-caret-up");
    } else {
      $(this).removeClass("fa-caret-up").addClass("fa-caret-down");
    }
  });
});
