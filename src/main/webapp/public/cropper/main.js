var inputImageChange;
var getCroppedCanvas;
$(function () {

  'use strict';

  var console = window.console || { log: function () {} };
  var URL = window.URL || window.webkitURL;
  var $image = $('#image');
  var $download = $('#download');
  var $dataX = $('#dataX');
  var $dataY = $('#dataY');
  var $dataHeight = $('#dataHeight');
  var $dataWidth = $('#dataWidth');
  var $dataRotate = $('#dataRotate');
  var $dataScaleX = $('#dataScaleX');
  var $dataScaleY = $('#dataScaleY');
  var options = {
        aspectRatio: 4 / 3,
        preview: '.img-preview',
        crop: function (e) {
          $dataX.val(Math.round(e.x));
          $dataY.val(Math.round(e.y));
          $dataHeight.val(Math.round(e.height));
          $dataWidth.val(Math.round(e.width));
          $dataRotate.val(e.rotate);
          $dataScaleX.val(e.scaleX);
          $dataScaleY.val(e.scaleY);
        }
      };
  var originalImageURL = $image.attr('src');
  var uploadedImageURL;
  
  getCroppedCanvas = function(){
	  var result;
      result = $("#image").cropper("getCroppedCanvas", {width:800, height:600});
      if (result) {
    	  var img = new Image();
    	  var url = img.src = result.toDataURL('image/jpeg');
    	  $("#imgSrc").val(url);
    	  $(img).css({"width":"300px","display":"block"});
    	  var $img = $(img);
    	  img.onload = function() {
	        URL.revokeObjectURL(url);
	        $('#view').show();
	        $('#view').empty().append($img);
    	  }
      }
	}
  
  // Import image
  var $inputImage = $('#inputImage');
  inputImageChange = function(e){
	  var files = $(e)[0].files;
      var file;
      if (files && files.length) {
        file = files[0];
        if (/^image\/\w+$/.test(file.type)) {
          if (uploadedImageURL) {
            URL.revokeObjectURL(uploadedImageURL);
          }
          uploadedImageURL = URL.createObjectURL(file);
		  $("#image").cropper('destroy').attr('src', uploadedImageURL).cropper(options);
          $inputImage.val('');
        } else {
          window.alert('请上传图片格式文件！');
        }
      }
  }
});
