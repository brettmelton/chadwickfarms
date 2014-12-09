$(document).ready(function(){
  // hide all the content panes when the page loads
  $('#announcments > div').hide();
  // this sets the first pane to be visible by default
  $('#announcments > div:first').show();
  // this sets the animation effect for clicking on a pane
  $('#announcments h3').click(function() {
    $(this).next().animate({
      'height':'toggle'
    }, 'slow', 'easeOutBounce');
  });  

  $('#Attention')
    .delay(2000)
    .animate({'backgroundColor':'#FF9999'}, 750)
    .delay(250)
    .animate({'backgroundColor':'#FFFFFF'}, 750)
    .delay(1000)
    .animate({'backgroundColor':'#FF9999'}, 750)
    .delay(250)
    .animate({'backgroundColor':'#FFFFFF'}, 750);
});
