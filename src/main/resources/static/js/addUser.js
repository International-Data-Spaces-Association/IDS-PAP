var userCtrl = function () {

    var oldValue;
    var oldFile;

    var _loadSolutions = function (control) {

        var chosenAffiliation = control.value;
        var url = '/allSolutions';
        var targetElement = $("#solution");
        if (chosenAffiliation != '') {
            url = url + '/' + chosenAffiliation;
            $.ajax({
                url: url,
                context: targetElement
            }).done(function (data) {
                targetElement.empty();
                var newElements = $(data);
                newElements.find("div.checkbox").iCheck({checkboxClass: 'icheckbox_flat-green', radioClass: 'iradio_flat-green'});
                targetElement.append(newElements);

            });
        }
    }

    var _disableAffiliationBox = function(control)	{

        var affiliationBox = document.getElementById('affiliationBox');
        var solutionCheckBox = document.getElementById('solutionsCheckBox');
        if (control.value == 'SUPER_ADMIN') {
            affiliationBox.style.display = 'none';
            solutionCheckBox.style.display = 'none';
        } else if (control.value == 'ADMINISTRATOR'){
            if(affiliationBox != null){
                affiliationBox.style.display = 'block';
            }
            solutionCheckBox.style.display = 'none';
        }
        else if (control.value == 'SOLUTION_DEVELOPER'){
            if(affiliationBox != null){
                affiliationBox.style.display = 'block';
            }
            solutionCheckBox.style.display = 'block';
        }
    }


    var _handleFileSelect = function (evt) {
        var files = evt.target.files; // FileList object

        // files is a FileList of File objects. List some properties.
        var toReturn = true;
        for (var i = 0, f; f = files[i]; i++) {

            if (!f.type.match('image.*')) {
                continue;
                toReturn = false;
            }
            if (f.size <= 1024000) {
                toReturn = true;
                var reader = new FileReader();

                // Closure to capture the file information.
                reader.onload = (function (theFile) {
                    return function (e) {
                        // Render thumbnail.
                        oldFile = files;
                        oldValue = evt.target.value;
                        $("#avatarerror").text("");
                        var image = document.getElementById('image');
                        image.setAttribute("src", e.target.result);
                        image.setAttribute("title", escape(theFile.name));
                    };
                })(f);

                // Read in the image file as a data URL.
                reader.readAsDataURL(f);
            }
            else {
                evt.target.value = "";
                evt.target.files = oldFile;
                $("#avatarerror").text("Image not changed, please choose one < 1MB!");
                return false;
            }
        }
    }
    return {handleFileSelect: _handleFileSelect, loadSolutions: _loadSolutions, disableAffiliationBox: _disableAffiliationBox}
}
();

