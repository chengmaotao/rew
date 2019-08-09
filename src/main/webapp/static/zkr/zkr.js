// 限制输入内容
function testCode(obj){
	// 只能输入 英数字 
	obj.value=obj.value.replace(/[^0-9]/g,'');
}

function testId(obj){
	// 只能输入 英数字 
	obj.value=obj.value.replace(/[^A-Za-z0-9_-]/g,'');
}

//清空所有必输项边框
function clearInputRequired(obj){
	
	$(obj).css('outline','0px none');
}

function changeGroupSon(obj,url){
	var tempGroupId = obj.value;
	$.ajax({  
          type: "post",
          url: url, 
          data: {"groupId" : tempGroupId},
          success: function(tempRes){
        	  var tempLen = tempRes.length;
        	  if(tempLen > 0){
        		  $("#radionButtonsId").empty();
        		  for(var i = 0; i < tempLen; i++){  
        			  var temid = "groupSonId" + i;
        			  var temVal = tempRes[i].id;
        			  var tempLblNm = tempRes[i].groupsonname;            			 
        			  var radtemp = "<span><input id='" + temid + "' class='required' type='radio' value="+ temVal +" name='groupSonId'></input><label for='"+temid+"'>"+tempLblNm+"</label></span>";
        			  $("#radionButtonsId").append(radtemp);
        		  }
        		  
    			  var tempFont = "<span class='help-inline' ><font color='red'>*</font> </span>"
        		  $("#radionButtonsId").append(tempFont);
    			  
        		  $("#zyzxzId").css("display","");
        	  }else{
        		  $("#zyzxzId").css("display","none")
        		   $("#radionButtonsId").empty();
        		   var radtemp = "<span><input type='radio' name='groupSonId' value='999999999' checked='checked'></input></span>";
        		   $("#radionButtonsId").append(radtemp);
        	  }

          },
          error: function (XMLHttpRequest, textStatus, errorThrown) { 
                      alert(errorThrown); 
          }
       }); 
	
}

