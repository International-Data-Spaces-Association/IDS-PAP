var solutionCtrl = function () {

    var _listToAdd =  [];
    var _users;

    function filterNotInSolution(users) {
        var indices2Remove = [];
        var selectedUsers = [];
        $("#users").children().each(function(){
            var idOfSelectElement = this.id;
            selectedUsers.push(idOfSelectElement.slice('option_user_'.length));
        });
        for (var i in users) {
           var userId = users[i].id +"";
            if(selectedUsers.indexOf(userId)!=-1)
            {
                indices2Remove.push(i);
            }
        }
        indices2Remove.sort().reverse().forEach(function (index) {
            users.splice(index,1);
        });
        return users;
    }

    function _loadUsers() {
    	if(document.getElementById('selectedAffiliation') != null)
		{
			var selectedAffiliation = document.getElementById('selectedAffiliation').value;
		}
    	
		$.getJSON("/user?affiliationId=" + selectedAffiliation, function (data) {
            _users = data;
            _listToAdd = [];
            _addToList(filterNotInSolution(data));
        });
    }

     function _addToList(users) {
         $("#userList").empty();
        if(users.length != 0) {
            $.addTemplateFormatter("UserIdFormatter",
                function (value, template) {
                    return "user_" + value;
                });
            $("#userList").loadTemplate("/templates/user_in_solution_page.html", users, {
                success: function () {
                    var allButtons = $("#userList").find("button");
                    allButtons.click(function (event) {
                        var button = $(this);
                        button.toggleClass("btn-success");
                        var id = button[0].id;
                        var indexOfUser = $.inArray(id, _listToAdd);
                        if (indexOfUser != -1) {
                            _listToAdd.splice(indexOfUser, 1);
                        }
                        else {
                            _listToAdd.push(id);
                        }
                    });
                }
            });

        }
        else{
            $("#userList").append( $("<p style='padding:5px;'>All users are already assigned to this solution</p>"));
        }

    }

    function getElementWithUserId(id) {
        return document.getElementById('user_' + id);
    }

    function _removeUser(id) {
        var user_element = getElementWithUserId(id);
        user_element.parentNode.removeChild(user_element);
        var user_element = document.getElementById('option_user_' + id);
        user_element.parentNode.removeChild(user_element);
    }

    function _addAll(){
        _listToAdd.forEach(function(user_id){
            var profileElement = getElementWithUserId(user_id);
            profileElement.parentNode.removeChild(profileElement);
            var selectButton2removeButton = $(profileElement).find("button");
            selectButton2removeButton.removeClass("btn btn-success btn-round").addClass("transparent fas fa-trash-alt").empty();
            selectButton2removeButton.off();
            selectButton2removeButton.click(function(){
               _removeUser(user_id) ;
            });

            $("#updated_user_list").append($(profileElement));
            $(profileElement).removeClass("col-md-6 col-sm-6").addClass("col-md-12 col-sm-12");
            $("#users").append($("<option id='option_user_"+user_id+"' value='"+ user_id +"' selected='selected' class='hidden'>"+user_id+"</option>"))
        });
    }


    var oldValue;
    var oldFile;

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
                $("#avatarerror").text("The image you choose exceeds the maximum file size of 1MB. Please choose a different image.");
                return false;
            }
        }
    }
    return {handleFileSelect: _handleFileSelect, removeUser: _removeUser, loadUsers : _loadUsers, addSelected : _addAll}
}
();

