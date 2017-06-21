/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
        
    var pairs = [];  
    
    initPairs();
    
    var initData = {
        teams: pairs                    
        ,
        results: []
    };

    /* Edit function is called when team label is clicked */
    function edit_fn(container, data, doneCb) {
        var input = $('<input type="text">');
        input.val(data);
        container.html(input);
        input.focus();
        input.blur(function () {
            doneCb(input.val());
        });
        input.keyup(function (e) {
            if ((e.keyCode || e.which) === 13)
                input.blur();
        });
        container.html(input);
        input.focus();
    }

    function render_fn(container, data, score) {
        if (!data.name)
            return container.append(data);
    }

    $(function () {
        $('div#customHandlers .demo').bracket({
            init: initData,
            save: function () {}, /* without save() labels are disabled */
            decorator: {edit: edit_fn,
                render: render_fn}});
    });

    function initPairs() {
        var tmpPair = [];               
        counter = 1;
        i = 0;
        $("table tr > td:nth-child(1)").each(function () {
            tmpPair.push($(this).text());
            if (counter % 2 === 0) {        
                pairs.push(tmpPair);                
                tmpPair.splice(0, 2);
                i++;
            }
            counter++;
        });
        console.log(pairs);        
    }
});
