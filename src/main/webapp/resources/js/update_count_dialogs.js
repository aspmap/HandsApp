setInterval(function() {
    console.log('test')
    $('#update_count_dialogs').load('/update_count_dialogs #update_count_dialogs');
}, 2000);