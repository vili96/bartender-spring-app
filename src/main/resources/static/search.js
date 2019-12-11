$(document).ready(function (){

    const url = window.location.href;
    let searchParams = new URLSearchParams(window.location.search);
    var cocktailName = searchParams.get("cocktailName");

    console.log(cocktailName);

    if(cocktailName!=null){
        $('#writeItDown').append('TEST: ').append(cocktailName);

        //if no results ->>>> $('#notFound').append('Sorry, cocktail \''+cocktailName+'\' was not found.');
        
    } else {
        window.location.replace("error.html");
    }
    

    // function Cocktail(id, name, type, glass) {
    //     this.id = id;
    //     this.name = name;
    //     this.type = type;
    //     this.glass = glass;
    // }
        
    // $('#getCocktail').click(function() {
    //     cocktailName = $('#cocktailName').val();
    //     var result;
    //     var allDrinks = [];
        
    //     $.ajax({
    //         type: "GET",
    //         url: "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=gin_fizz", //+ cocktailName,
    //         success: function(resp){
    //             $('#writeItDown').empty();
    //             result = resp.drinks;
    //             for (var i = 0; i < result.length; i++) {
    //                 var id = result[i].idDrink;
    //                 var name = result[i].strDrink;
    //                 var type = result[i].strAlcoholic;
    //                 var glass = result[i].strGlass;
    //                 allDrinks.push(new Cocktail(id,name,type,glass));
                    
    //                 $('#writeItDown').append(id).append(':').append(name).append(' type:').append(type).append(' glass:').append(glass).append('<br>');
    //                 $('.item').append($('<div>',{class:'image',id:id,text:name,onmouseover:'hideText();',
    //                 style:'background-image:url(https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg)'}));
    //                 $('.image').append('Read more..');
    //             }
    //             console.log("Result:" + allDrinks[0].name)
    //         }				
    //     });			
        
    // });

    // // Search for a cocktail by name
    // $('#homeForm').submit(function (e) {
    //     e.preventDefault(); //prevents the default event of the form
    //     cocktailName = $('#cocktailName').val();
    //     var result;
    //     var allDrinks = [];

    //     $.ajax({
    //         url:"https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cocktailName, //the action of the form
    //         method:"GET",
    //         // data:{
    //         //     username:$("#username").val(),
    //         //     password:$("#password").val()
    //         // },
    //         success:function(resp){
    //             result = resp.drinks;
    //             for (var i = 0; i < result.length; i++) {
    //                 var id = result[i].idDrink;
    //                 var name = result[i].strDrink;
    //                 var type = result[i].strAlcoholic;
    //                 var glass = result[i].strGlass;
    //                 allDrinks.push(new Cocktail(id,name,type,glass));
    //             }


    //             console.log(allDrinks[0].name)
    //             $('#writeItDown').append('Name: ').append(allDrinks[0].name).append('<br>');

    //             window.location.replace("find_cocktails.html");
    //             console.log(allDrinks[0].name)
    //             $('#writeItDown').append('Name: ').append(allDrinks[0].name).append('<br>');

    //         },
    //         fail:function(){
    //             window.location.href("error.html");
    //         }
    //     });
        
    //     //window.location.replace("http://localhost:9080/home.html");
    // // 		var url = XMLHttpRequest.responseText;
    // // 		console.log(url);
    //     //var address= "http://localhost:9080"+url; 
    //     //window.location = address; 
        
    // });

    
});


