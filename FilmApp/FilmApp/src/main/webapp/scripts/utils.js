$(function() {
	
	// Buttons on the page
	$("#allFilms").click(function(){showAllFilms('json')});
	$("#getFilms").click(function(){
		let search = $('#filmTitle').val();
		let format = $('#getFilmFormat').val();
		getFilms(format, search);
	});
	
	$("#json").click(function(){showAllFilms('json')});
	
	$("#xml").click(function(){showAllFilms('xml')});

	$("#string").click(function(){showAllFilms('text')});
	// When the newFilm button is clicked,
	// a dialog with form will pop up
	$("#newFilm").click(function(){dialog.dialog("open");});
	
	$("#deleteFilm").click(function(){
		let id = $("#filmId").val();
		deleteFilm(id);
	});
	
	$("#editFilm").click(function(){
		let id = $("#fId").val();
		let title = $("#fTitle").val();
		let year = $("#fYear").val();
		let director = $("#fDirector").val();
		let stars = $("#fStars").val();
		let review = $("#fReview").val();
		editFilm(id, title, year, director, stars, review);
	});

	// Setup for the dialog that insert new film data into the database
	// Declare variables
	var dialog, form,
	id = $("#id"),
	title = $("#title"),
	year = $("#year"),
	director = $("#director"),
	stars= $("#stars"),
	review = $("#review"),
	allFields = $([]).add(id).add(title).add(year).add(director).add(stars).add(review),
	tips = $(".validateTips");

	function updateTips(t) {
		tips
		.text(t)
		.addClass("ui-state-highlight");
		setTimeout(function(){
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}
	// function for checking the input length
	function checkLength( o, n, min, max ) {
	    if ( o.val().length > max || o.val().length < min ) {
	      o.addClass( "ui-state-error" );
	      updateTips( "Length of " + n + " must be between " +
	        min + " and " + max + "." );
	      return false;
	    } else {
	      return true;
	    }
	 }
	// function to insert new film data
	function addFilm(){
		// let valid be true
		var valid=true;
		allFields.removeClass("ui-state-error");
		// validation for the inputs
		valid = valid && checkLength(id,"id",5,5);
		valid = valid && checkLength(title,"title",1,200);
		valid = valid && checkLength(year,"year",4,4);
		valid = valid && checkLength(director,"director",1,160);
		valid = valid && checkLength(stars,"stars",1,250);
		valid = valid && checkLength(review,"review",1,750);
		// send data to server if inputs are valid
		if(valid){
			var param = {
					id: id.val(),
					title: title.val(),
					year: year.val(),
					director: director.val(),
					stars: stars.val(),
					review: review.val()
					};
			$.ajax({
				url : "insertFilm",
				type: "POST",
				data: param, // params that send to the server
				success: function() {
						// close dialog when all is done
						dialog.dialog("close");
						// and also refresh the table 
						showAllFilms('json');
					}
				});
		}
		return valid;
	}
	// setting up the dialog
	dialog = $( "#new-form" ).dialog({
	    autoOpen: false, // to prevent instant pop up when page is loaded
	    height: 400,
	    width: 350,
	    modal: true,
	    buttons: {
	      "Create a film": addFilm,
	      Cancel: function() {
	        dialog.dialog( "close" );
	      }
	    },
	    close: function() {
	      form[ 0 ].reset();
	      allFields.removeClass( "ui-state-error" );
	    }
	  });
	// setting up the form in the dialog
	form = dialog.find( "#new-film-form" ).on( "submit", function( event ) {
	    event.preventDefault();
	    addFilm();
	  });
	// pop up the dialog when newFilm button is click
	  $( "#newFilm" ).button().on( "click", function() {
	    dialog.dialog( "open" );
	  });
	// other functions
	  // To show all films
	  function showAllFilms(format) {
	  	// URL address
	  	var address = "getAllFilms";
	  	var param = {format: format};
	  	$.ajax({
	  		url: address,
	  		type: "GET",
	  		data: param,
	  		dataType: format, // format is set so it will do the parsing automatically
	  		success : function(text){
	  			let data = parseData(text, format);
	  			console.log(data)
	  			// draw the table according to the result
	  			drawTable(data);
	  		},
	  		// print out error message in console for debug
	  		error: function(err){
	  			console.log(err.responseText)
	  		}
	  	});	
	  }
	  // To get film by title entered by user
	  function getFilms(format, title) {
	  	console.log("getfilm triggered");
	  	var address = "getFilm";
	  	var param = {format: format, title: title};// turn param into object
	  	$.ajax({
	  		url: address,
	  		type: "GET",
	  		data: param,
	  		dataType: format,
	  		success : function(text){
	  			let data = parseData(text, format);
	  			// draw table according to the response
	  			drawTable(data);
	  		},
	  		// print out error message for debug
	  		error: function(err){
	  			console.log(err.responseText)
	  		}
	  	});	
	  }

	  //function to draw table
	  function drawTable(data){
		// Delete the previous table so new table can be created
	  	$('#dataTable').DataTable().destroy();
	  	// Create the table with data received from server-side
	  	$('#dataTable').DataTable({
	  		data: data,
	  		columns:[
	  			{data: 'id'},
	  			{data: 'title'},
	  			{data: 'year'},
	  			{data: 'director'},
	  			{data: 'stars'},
	  			{data: 'review'},
	  			// set up buttons for commands
	  			// P.S: They are useless at the moment
	  			{
	  				data: null,
	  				defaultContent: "<button id=\"edit\">Edit</button><button id=\"delete\">Delete</button>"
	  			}
	  		]
	  	});
	  }
	  
	  // parse data that is received
	  function parseData(data,format){
	  	switch(format){
	  		case "json":
	  			return data;
	  			break;
	  			
	  		case "xml":
	  			var $films = $(data).find("film");
	  			// Create a new array to store processed XML datd
	  			let newData = [];
	  			// loop through each film object 
	  			$films.each(function(index, film){
	  				var $film = $(film);
	  				// Save the data into the array
	  				newData.push({
	  					// .text() is used to extract information
	  					id: $film.children("id").text(),
	  					title: $film.children("title").text(),
	  					year: $film.children("year").text(),
	  					director: $film.children("director").text(),
	  					stars: $film.children("stars").text(),
	  					review: $film.children("review").text()
	  				});
	  			})
	  			return newData;
	  			break;
	  			
	  		case "text":
	  			// Split the string receive with '\n'
	  			let result = data.split("\n");
	  			
	  			let final = [];
	  			// for loop used to go through every line
	  			for(line of result){
	  				if (line.length>0){
	  					// using '|' as delimiter
	  					let temp = line.split("|");
	  					final.push({
	  						id: temp[0],
	  						title: temp[1],
	  						year: temp[2],
	  						director: temp[3],
	  						stars: temp[4],
	  						review: temp[5]
	  					});
	  				}
	  			}
	  			return final;	
	  			break;
	  			
	  		default:
	  			console.log("invalid format");
	  		return [];
	  			break;
	  	}
	  }
	  // delete film
	  function deleteFilm(filmId){
		  	console.log("Deleting");
		  	$.ajax({
		  		// have to change URL because of a bug
		  		url: "deleteFilm?"+$.param({id: filmId}),
		  		type: "DELETE",
		  		// if request success refresh table
		  		success : function(){
		  			showAllFilms('json');
		  		},
		  		// print out error message for debug
		  		error: function(err){
		  			console.log(err.responseText)
		  		}
		  	});	
	  }
	  // edit film
	  function editFilm(fid, ftitle, fyear, fdirector, fstars, freview){
		  console.log("Editing...");
		  $.ajax({
			 url: "updateFilm?"+$.param({id: fid, title: ftitle, year: fyear, director: fdirector, stars: fstars, review: freview}),
			 type: "PUT",
			 // if success refresh table
			 success: function(){
				 showAllFilms('json');
			 },
			 // print out error message for debug
			 error: function(err){
				 console.log(err.responseText)
			 }
		  });
	  }
});
