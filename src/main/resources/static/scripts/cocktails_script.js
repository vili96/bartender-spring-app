function visualizeCocktail(cocktail) {
	console.log(cocktail);
	var miniMe = $('#cloneMe').clone();
	var editBtn = miniMe.find('.edit-cocktail-btn');
//	var delBtn = miniMe.find('.delete-cocktail-btn');
//	
	editBtn.attr('id', cocktail.id);
//	delBtn.attr('id', cocktail.id);
	
	
//	miniMe.attr('id', cocktail.id);
	miniMe.find('h4').text(cocktail.name);
	miniMe.find('p').html(cocktail.instructions);
	miniMe.find('span').html(cocktail.isAlcoholic);
	miniMe.find('.ingredients').html("cocktail.ingredients");
	
	// get image from the cocktails API
	var imgFromApi=cocktail.image;
	
	$.ajax({
		url : imgFromApi,
		method : "GET",
		success : function(resp) {
			console.log(resp);
			if (resp.drinks != null) {
				console.log( "Thumbb");
				console.log(resp.drinks[0].strDrinkThumb)
				miniMe.find('img').attr("src",resp.drinks[0].strDrinkThumb);
			} else {
				miniMe.find('img').attr("src","images/empty.jpg");
			}
		},
		fail : function() {
			alert("Something went wrong!");
		}
	});
	
	
//	if (me = userId) {
//
//		miniMe.find('button').click(function() {
//			deleteComment(miniMe, id);
//		});
//	}
	miniMe.show();

	$('#contentList').append(miniMe);
};

function addCocktail(name, instructions, isAlcoholic) {
			$.ajax({
				url : "/cocktail/add",
				method : "POST",
				data : {
					name : name,
					instructions : instructions,
					isAlcoholic : isAlcoholic
//					ingredients : ingredients,
				},
				success : function(resp) {
					if (resp) {

						visualizeCocktail(resp);
						$('#add-cocktail-modal').modal('hide');
					} else {
						window.location.href = "login.html";
					}
				},
				fail : function() {
					alert("Something went wrong!");
				}
			});
};

function getAllCocktails(id,city,comment,temp,userId){
	$.ajax({
		url:"/cocktail/get_all",
		method:"GET",
		success: function(resp){
			resp.forEach(function(comm){
				visualizeCocktail(comm);
			});
		},
		fail: function(){
			window.location.href="index.html";
		}
	});
};


function removeCocktail(element){
	var li = element.closest("li")
	li.fadeOut();
};

$(document).ready(function(){
	
	$('#addCocktail').click(function () {
		$('#saveCocktail').show();
		$('#saveEditCocktail').hide();
		$('#add-cocktail-modal').modal();
	});
	
	$('.container').on('click','.edit-cocktail-btn',function () {
		$('#saveCocktail').hide();
		$('#saveEditCocktail').show();
		$('#add-cocktail-modal').modal();
		
		var id = $(this).attr('id');
		$.ajax({
			url:"/cocktail/edit",
			method:"GET",
			data: {
				id:id
			},
			success: function(resp){
				if(resp){
					console.log(resp.alcoholic);
					
					$('#name').val(resp.name);
					$('#instructions').val(resp.instructions);
					$('#isAlcoholic').prop('checked', resp.alcoholic);
					
					
				}else{
					alert("Data for cocktail could not be loaded!");
				}
			},
			error: function(){
				alert("Something went wrong!");
			}
		});
		
		//get closest li
//		var selectedLi = $( "button.edit-cocktail-btn" ).closest( "li" ).attr('id');
//		console.log( "Selected li id" + selectedLi);
		
		
		
		
	});

	$('.container').on('click','.delete-cocktail-btn',function () {
		removeCocktail($(this));		
	});
	
	$('#saveCocktail').click(function(){
			
		var name = $('#name').val();
		var instructions = $('#instructions').val();
		var isAlcoholic = $('#isAlcoholic').val();
		var ingredients = "";

		console.log(name);
		console.log(instructions);
		console.log(isAlcoholic);
		addCocktail(name,instructions,isAlcoholic);
				
	});
	
	$('#saveEditCocktail').click(function(){
		
		var name = $('#name').val();
		var instructions = $('#instructions').val();
		var isAlcoholic = $('#isAlcoholic').val();
		var ingredients = "";

		$.ajax({
			url : "/cocktail/add",
			method : "POST",
			data : {
				name : name,
				instructions : instructions,
				isAlcoholic : isAlcoholic
//				ingredients : ingredients,
			},
			success : function(resp) {
				if (resp) {

					$('#add-cocktail-modal').modal('hide');

				} else {
					window.location.href = "login.html";
				}
			},
			fail : function() {
				alert("Something went wrong!");
			}
		});
		
				
	});
	
	getAllCocktails();
			
	});
	
