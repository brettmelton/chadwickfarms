
$(document).ready(function() { 

  // Header Image
//  $('#header').fadeIn(2000);

  // Navigation
  $('nav a.nav').hover(function(){
    $(this).animate({
      fontSize: '+=2px'
    }, 500);
  }, function(){
    $(this).animate({
      fontSize: '-=2px'
    }, 500);
  });

  // Index Page
//  $('#Attention')
//    .animate({'backgroundColor':'#FF9999'}, 3000)
//    .delay(2000)
//    .animate({'backgroundColor':'#FFFFFF'}, 3000);
  
});
    