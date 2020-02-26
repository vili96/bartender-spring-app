$(document).ready(function (){
        
    $(document).on('submit', 'form#homeForm', function (e) {
        e.preventDefault();

        var searchText = $('#cocktailName').val();
        console.log("NAMEMEMFCSDNKg");
        console.log(searchText);
        findCocktailsByNameContaining(searchText);
    });
    
    
    // logout
	$('#tabLogout').on('click',function(){
		$.ajax({
			method: "POST",
			url: "/logout",
			success: function(resp){
				console.log(resp);
			
				if(resp){
					window.location.href="/login.html";
				} else{
					alert("Should be logged in first!");
				}
			},
			error: function(){
				alert("Logout unsuccessfull!");
			}
		});		
	});

	
	$('#changeLayBtn').on('click',function(){
		changeLayout();
	});

    //search 
    $('#searchCocktails').on('click',function(){

        $('li').not(':first').remove();
        var isAlcoholic;
        var selectedLen = $("select[name='ingredients'] option:selected").length;
        var optionsSelected = $('#ingredients').val();
        var optionsToInt=[];

        optionsSelected.forEach(function(opt){
            optionsToInt.push(parseInt(opt));
        });

        if($('#isAlcoholic').is(':checked') === $('#isNonAlcoholic').is(':checked')){
            if( selectedLen!==0 ){
                findCocktailsByIngredients(optionsToInt);
            }
            console.log("both ARE SAME");
        } else {
            console.log("both ARE NOT same");
            if($('#isAlcoholic').is(':checked') ){
                isAlcoholic = true;
            }
            if($('#isNonAlcoholic').is(':checked') ){
                isAlcoholic = false;
            }
            console.log(isAlcoholic);
            if(selectedLen===0) {
                findCocktailsByIsAlcoholic(isAlcoholic);
            } else{
                findCocktailsByIngredientsAndIsAlcoholic(optionsToInt,isAlcoholic);
            }
        }
    });
    
});

// function hideText() {
//     $('.image').css('opacity:1');
//     $('.image').hover(function() {
//         $(this).animate({opacity: 0.3}, 300);
//     }, function() {
//         $(this).animate({opacity: 1}, 300);
//     });
// };


