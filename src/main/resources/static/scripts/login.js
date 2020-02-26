var currentUserId;
var currentUserRole;
var loggedUsername;

function getCurrentUser(){
	$.ajax({
		url:"/get_logged_user",
		method:"GET",
		success: function(resp){
			currentUserId = resp;
			console.log(currentUserId);
		},
		error: function(){
			window.location.href="login.html";
		}
	});
};

function getCurrentUserRole(){
	$.ajax({
		url:"/get_logged_user_role",
		method:"GET",
		success: function(resp){
			currentUserRole = resp;
			console.log(currentUserId);
		},
		error: function(){
			window.location.href="login.html";
		}
	});
};

function getLoggedUsername(){
	$.ajax({
		url:"/get_logged_username",
		method:"GET",
		success: function(resp){
			console.log(resp);
			if(resp){
				loggedUsername = resp;
				$('#loggedUser').text(loggedUsername);
				$('#tabRegister').hide();
				$('#tabLogin').hide();
				
				
			}else{
				console.log("No user is logged in");
				$('#iconLogged').hide();
				$('#tabFavorites').hide();
				$('#tabLogout').hide();
			}
			
		},
		error: function(){
			console.log("Failed to retrieve username");
		}
	});
};


$(document).ready(function (){
    getLoggedUsername();
    getCurrentUser();
    // if (currentUserRole == 2) {	
    //     console.log("REGULAR USER LOGGED");
    //     $('.edit-cocktail-btn').css('display','none');
    // }
});
