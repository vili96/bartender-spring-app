function getImageFromCocktailApi(element, imgFromApi) {
	$.ajax({
		url: imgFromApi,
		method: "GET",
		success: function (resp) {
			if (resp.drinks != null) {
				element.find('img').attr("src", resp.drinks[0].strDrinkThumb);
			} else {
				element.find('img').attr("src", "images/default-no-image.jpg");
			}
		},
		error: function () {
			alert("Something went wrong with the image!");
		}
	});
};

function visualizeCocktail(cocktail) {
	console.log(cocktail);
	var miniMe = $('#cloneMe').clone();
	var editBtn = miniMe.find('.edit-cocktail-btn');
	var delBtn = miniMe.find('.delete-cocktail-btn');

	editBtn.attr('id', cocktail.id);
	delBtn.attr('id', "d" + cocktail.id);

	miniMe.find('h4').text(cocktail.name);
	miniMe.find('p').html(cocktail.instructions);
	// miniMe.find('span').html(cocktail.isAlcoholic);

	console.log(cocktail);
	console.log("ingrrr");console.log(cocktail.ingredients);
	cocktail.ingredients.forEach(function (ingredient) {

		miniMe.find('.ingredients').append(ingredient.name).append("<br>");
	});

	// get image from the cocktails API
	getImageFromCocktailApi(miniMe, cocktail.image);


		
	//
	//		miniMe.find('button').click(function() {
	//			deleteComment(miniMe, id);
	//		});
	
	miniMe.show();

	$('#contentList').append(miniMe);
};

function showNoResults(resp) {
	if(resp.length === 0){
		//$('.heading')//.append("<div>","-- No results --")
		$('.heading').append('<div id="noResId">-- No results --</div>')
	} else{
		if($('#noResId')){
			$('#noResId').remove();
		}
		
	}
};

function findCocktailsByNameContaining(searchText) {	
	$.ajax({
		url: "/cocktail/search_name",
		method: "GET",
		data: {
			name: searchText
		},
		success: function (resp) {
			console.log(resp);
			resp.forEach(function (cocktail) {
				console.log(cocktail);
				visualizeCocktail(cocktail);
			});
			showNoResults(resp);
		},
		error: function () {
			alert("Something went wrong!");
		}
	});
};

function findCocktailsByIsAlcoholic(isAlcoholic) {

	$.ajax({
		url: "/cocktail/search_by_alcoholic",
		method: "GET",
		data: {
			isAlcoholic: isAlcoholic
		},
		success: function (resp) {
			resp.forEach(function (cocktail) {
				visualizeCocktail(cocktail);
			});
			showNoResults(resp);
		},
		error: function () {
			alert("Something went wrong!");
		}
	});
};

function findCocktailsByIngredients(ingredientIds) {
	$.ajax({
		url: "/cocktail/search_by_ingredients",
		method: "GET",
		data: {
			ingredients: ingredientIds
		},
		traditional: true,
		success: function (resp) {
			resp.forEach(function (cocktail) {
				visualizeCocktail(cocktail);
			});
			showNoResults(resp);
		},
		error: function () {
			alert("Something went wrong!");
		}
	});
};

// search by two criteria
function findCocktailsByIngredientsAndIsAlcoholic(ingredientIds,isAlcoholic) {
	$.ajax({
		url: "/cocktail/search_by_ingredients_and_alcoholic",
		method: "GET",
		data: {
			ingredients: ingredientIds,
			isAlcoholic: isAlcoholic
		},
		traditional: true,
		success: function (resp) {
			resp.forEach(function (cocktail) {
				visualizeCocktail(cocktail);
			});
			showNoResults(resp);
		},
		error: function () {
			alert("Something went wrong!");
		}
	});
};

function changeLayout(){
	$('li#cloneMe').toggleClass('layout-to-change');
	$('ul#contentList').toggleClass('ulDisplayInline');
	$('.toHide').toggleClass('toHideAndShow');
}

