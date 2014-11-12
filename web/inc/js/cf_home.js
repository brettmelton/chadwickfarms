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
    .animate({'backgroundColor':'#FF9999'}, 1000)
    .delay(250)
    .animate({'backgroundColor':'#FFFFFF'}, 1000)
    .effect('shake', {times:3}, 500)
    .delay(500)
    .hide('explode', {}, 1000)
    .show('bounce', {}, 1000);
});
