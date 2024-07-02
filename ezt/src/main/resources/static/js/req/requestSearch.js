/**
 * requestSearch.js
 */
$(document).ready(function(){
    var searchForm = $("#searchForm");

    $(".insertBtn").on("click", function(e){
        e.preventDefault();

        if (!login) {
            alert("로그인을 하세요");
            location.href = '/login'; // 로그인페이지로 이동
        } else {
            location.href = '/requestInsert';
        }
    });

    $("#searchForm button[type='submit']").on("click", function(e){
        if(!searchForm.find("option:selected").val()){
            alert("검색 종류를 선택하세요");
            return false;
        };
        
        if(!searchForm.find("input[name='keyword']").val()){
            alert("검색어를 입력하세요");
            return false;
        }
        
        searchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();
        
        searchForm.submit();
    });
});