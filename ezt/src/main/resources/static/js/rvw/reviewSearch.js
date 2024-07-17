/**
 * reviewSearch.js
 */

  var searchForm = $("#searchForm");
 
 $("#searchForm button").on("click", function(e){
	
	if(!searchForm.find("option:selected").val()){
		 Swal.fire({
			  text:'검색 종류를 선택하세요',
			  icon:'warning'
			});
		return false;
	};
	
	if(!searchForm.find("input[name='keyword']").val()){
		 Swal.fire({
			  text:'검색어를 입력하세요',
			  icon:'warning'
			});
		return false;
	}
	
	searchForm.find("input[name='pageNum']").val("1");
	e.preventDefault();
	
	searchForm.submit();
 });