var currentElementToEdit;
var editElementName;

var inputName = $('#name');
var inputInstructions = $('#instructions');
var inputAlcoholic = $('#isAlcoholic');
var inputIngredients = $('#ingredients');

function addCocktail(name, instructions, isAlcoholic, ingredients) {
	console.log(ingredients);
	$.ajax({
		url: "/cocktail/add",
		method: "POST",
		data: {
			name: name,
			instructions: instructions,
			isAlcoholic: isAlcoholic,
			ingredients: ingredients
		},
		success: function (resp) {
			if (resp) {
				visualizeCocktail(resp);
				$('#add-cocktail-modal').modal('hide');
			} else {
				window.location.href = "login.html";
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 401) {
				alert("Login first!");
				window.location.href = "login.html";
			} else {
				alert("Something went wrong!");
			}
		}
	});
};

function editCocktail(name, instructions, isAlcoholic, ingredients) {

	var paramName = name;
	$.ajax({
		url: "/cocktail/edit",
		method: "POST",
		data: {
			id: currentElementToEdit,
			name: name,
			instructions: instructions,
			isAlcoholic: isAlcoholic,
			ingredients: ingredients
		},
		success: function (resp, textStatus, jqXHR) {
			console.log(resp);
			if (resp) {
				currentElementToEdit = $('#' + currentElementToEdit);
				var li = currentElementToEdit.closest("li")
				// currentElementToEdit.replaceWith( "<div>" + $( this ).text() + "</div>" );

				li.find('h4').text(resp.name);
				li.find('p').html(resp.instructions);
				li.find('span.is-alcoholic').html(resp.alcoholic);

				var ingredientsLabel = li.find('.ingredients');
				ingredientsLabel.text("");
				resp.ingredients.forEach(function (ingredient) {
					ingredientsLabel.append(ingredient.name).append("<br>");
				});

				if (editElementName !== resp.name) {

					getImageFromCocktailApi(li, resp.image);
				}

				$('#add-cocktail-modal').modal('hide');

			} else if (jqXHR.status == 304) {
				alert("Name must be unique!");
			} else {
				alert("Edit unsuccessfull!");
			}
		},
		error: function () {
			alert("Something went wrong!");
		}
	});
};

function getAllCocktails(id, city, comment, temp, userId) {
	$.ajax({
		url: "/cocktail/get_all",
		method: "GET",
		success: function (resp) {
			resp.forEach(function (comm) {
				visualizeCocktail(comm);
			});
		},
		error: function () {
			window.location.href = "index.html";
		}
	});
};

function removeCocktail(element) {
	var li = element.closest("li");
	var delBtnId = element.closest("button.delete-cocktail-btn");
	var id = delBtnId.attr("id");
	id = id.substr(1);

	$.ajax({
		url: "/cocktail/delete",
		method: "POST",
		data: {
			id: id
		},
		success: function (resp) {
			if (resp) {
				li.fadeOut();
			} else {
				alert("Delete not successfull!");
			}
		},
		error: function () {
			alert("Something went wrong!");
		}
	});

};

function clearModalFields() {
	$('#name').replaceWith(inputName.val('').clone(true));
	$('#instructions').replaceWith(inputInstructions.val('').clone(true));
	$('#ingredients').replaceWith(inputIngredients.val('').clone(true));
	$('#isAlcoholic').replaceWith(inputAlcoholic).prop('checked', false).clone(true);
};

function stringifyIngredients(ingredients) {
	var ingredientsString = "";
	ingredients.forEach(function (ingredient) {
		ingredientsString += ingredient + ",";
	});
	return ingredientsString;
};


$(document).ready(function () {

	$('#addCocktail').click(function () {

		$('#saveCocktail').show();
		$('#saveEditCocktail').hide();
		$('#add-cocktail-modal').modal();

	});

	$('#add-cocktail-modal').on('hidden.bs.modal', function (e) {
		clearModalFields();
	});

	$('.container').on('click', '.edit-cocktail-btn', function () {
		console.log("Editttttt");
		$('#saveCocktail').hide();
		$('#saveEditCocktail').show();
		$('#add-cocktail-modal').modal();

		var id = $(this).attr('id');
		currentElementToEdit = id;

		$.ajax({
			url: "/cocktail/edit",
			method: "GET",
			data: {
				id: id
			},
			success: function (resp) {
				if (resp) {

					editElementName = resp.name;
					$('#name').val(resp.name);
					$('#instructions').val(resp.instructions);
					$('#isAlcoholic').prop('checked', resp.alcoholic);

					var ingrIds = [];
					resp.ingredients.forEach(function (ingredient) {
						ingrIds.push(ingredient.id);
					});
					$('#ingredients').val(ingrIds );


				} else {
					alert("Data for cocktail could not be loaded!");
				}
			},
			error: function () {
				alert("Something went wrong!");
			}
		});

		//get closest li
		//		var selectedLi = $( "button.edit-cocktail-btn" ).closest( "li" ).attr('id');
		//		console.log( "Selected li id" + selectedLi);

	});

	$('.container').on('click', '.delete-cocktail-btn', function () {
		removeCocktail($(this));
	});

	$(document).on('submit', 'form#add-form', function (e) {
		e.preventDefault();

		// if save is visible
		if ($('#saveEditCocktail').is(":hidden")) {
			//create
			var name = $('#name').val();
			var instructions = $('#instructions').val();
			var isAlcoholic = $('#isAlcoholic').val();
			var ingredients = $('#ingredients').val();
			var ingredientsString = stringifyIngredients(ingredients);
			addCocktail(name, instructions, isAlcoholic, ingredientsString);

		} else {
			//edit
			var name = $('#name').val();
			var instructions = $('#instructions').val();
			var isAlcoholic = $('#isAlcoholic').val();
			var ingredients = $('#ingredients').val();
			var ingredientsString = stringifyIngredients(ingredients);
			editCocktail(name, instructions, isAlcoholic, ingredientsString);
		}
		clearModalFields();
	});

	getAllCocktails();

});