/**
 * replyInsert.js
 */

 $(document).ready(function(){
	//댓글 쓰기 버튼 눌렀을 때
	  $('.btnn').click(function() {
			$('<tr/>').append($('<th>').text('내용')
					  .append($('<td>').append($('<textarea>'))));  
	   
	   
	  });
	 
});
