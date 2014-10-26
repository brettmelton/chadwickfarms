$(document).ready(function(){
  // hide all the content panes when the page loads
  $('#minutes > div').hide();
  
  // uncomment the next line if you'd like the first pane to be visible by default
  $('#minutes > div:first').show();
  
  $('#minutes h3').click(function() {
    $(this).next().animate({
      'height':'toggle'
    }, 'slow', 'easeOutBounce');
  });
});
