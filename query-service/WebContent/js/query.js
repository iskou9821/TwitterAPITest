window.queryLogic = {};

queryLogic.execQuery = execQuery = function(
	txt, cnt, maxId, minId, includeMax, baseCallback, detailCallback, errorCallback) {
	  $(".btn").attr("disabled", "disabled");
	  $(".searching").show();
	  var encStr = Base64.encode(txt);
      $.ajax({
          type : 'GET',
          contentType: "application/json",
          url : "./rest/tweetModels" + 
          			"?query-text=" + encStr + 
          			"&query-count="+cnt + 
          			"&query-max-id="+maxId + 
          			"&query-min-id="+minId + 
          			"&include-max-id="+includeMax,
          dataType : 'json'
      }).success(function(data) {
      	baseCallback(data);
      	if (data.details != null) {
      		if (data.details instanceof Array) {
      			$.each(data.details, function(i, val) {
      				detailCallback(val);
      			});
      		} else {
      			detailCallback(data.details);
      		}
      	}
      	$(".btn").removeAttr("disabled");
	    $(".searching").hide();
      }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
      	errorCallback(XMLHttpRequest, textStatus, errorThrown);
      	$(".btn").removeAttr("disabled");
	    $(".searching").hide();
      });
	};

queryLogic.execCsvQuery = function(txt, cnt, hd, bd, successCallback, failCallback) {
    $(".btn").attr("disabled", "disabled");
	$(".searching-csv").show();
	$.ajax({
      type : 'POST',
      contentType: "application/json",
      url : "./rest/tweetCsvQuery",
      data : JSON.stringify({
		queryText : txt,
		count : cnt,
		headerText : hd, 
		tweetParseTemplate : bd
      }),
      dataType : 'text'						
	}).success(function(data){
		successCallback(data);
    	$(".btn").removeAttr("disabled");
	    $(".searching-csv").hide();
	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		failCallback(XMLHttpRequest, textStatus, errorThrown);
      	$(".btn").removeAttr("disabled");
	    $(".searching-csv").hide();
	});
};
