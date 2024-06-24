
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
		//console.log(element.value);
	      var date;
	      try {
	        date = element.value
	      } catch( error ) {
	        date = null;
	      }
	 
	      return date;
	    }