
$(document).ready(function() { 

  $('.surveyresults tbody tr:even').addClass('AccessListItemsEven');
  $('.surveyresults tbody tr:odd').addClass('AccessListItemsOdd');

  $('.surveyresults tbody tr').hover( function() {
    $(this).addClass('AccessListItemsHover');
  }, function() {
    $(this).removeClass('AccessListItemsHover');
  });

 });

