/*
* jQuery pager plugin
* Version 1.0 (12/22/2008)
* @requires jQuery v1.2.6 or later
* Download by http://www.codefans.net
* Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
*
* Copyright (c) 2008-2009 Jon Paul Davies
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
* 
* Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
*
* This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
*
* Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickCallback: PagerClickTest });
*
* Where pagenumber is the visible page number
*       pagecount is the total number of pages to display
*       buttonClickCallback is the method to fire when a pager button is clicked.
*
* buttonClickCallback signiture is PagerClickTest = function(pageclickednumber) 
* Where pageclickednumber is the number of the page clicked in the control.
*
* The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
* Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
*/
(function($) {
	
    $.fn.pager = function(options) {
        if(options.pagecount>1)
        {
        	var opts = $.extend({}, $.fn.pager.defaults, options);
            var local = opts.local || "zh_CN";
            local = local in language ? local : "zh_CN";
            
            return this.each(function() {

            // empty out the destination element and then render out the pager with the supplied options
                $(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount), options.buttonClickCallback, local));
                
                // specify correct cursor activity
                $('.pages li').mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
            });
        }
        else
        {
        	$(this).empty();
        }
    };

    // render and return the pager with the supplied options
    function renderpager(pagenumber, pagecount, buttonClickCallback, local) {
        // setup $pager to hold render
        var $pager = $('<ul class="pagination pagination-xs m-top-none pull-right"></ul>');
        
        // add in the previous and next buttons 'first' 'prev'
        $pager.append(renderButton(language[local].first, pagenumber, pagecount, buttonClickCallback, local)).append(renderButton(language[local].prev, pagenumber, pagecount, buttonClickCallback, local));

        // pager currently only handles 10 viewable pages ( could be easily parameterized, maybe in next version ) so handle edge cases
        var startPoint = 1;
        var endPoint = 9;

        if (pagenumber > 4) {
            startPoint = pagenumber - 4;
            endPoint = pagenumber + 4;
        }

        if (endPoint > pagecount) {
            startPoint = pagecount - 8;
            endPoint = pagecount;
        }

        if (startPoint < 1) {
            startPoint = 1;
        }

        // loop thru visible pages and render buttons
        for (var page = startPoint; page <= endPoint; page++) {

            var currentButton = $('<li class="page-number"><a href="#">' + (page) + '</a></li>');

            page == pagenumber ? currentButton.addClass('active') : currentButton.click(function() { buttonClickCallback(this.firstChild.firstChild.data); });
            currentButton.appendTo($pager);
        }

        // render in the next and last buttons before returning the whole rendered control back.  'next'  'last'
        $pager.append(renderButton(language[local].next, pagenumber, pagecount, buttonClickCallback, local)).append(renderButton(language[local].last, pagenumber, pagecount, buttonClickCallback, local));

        return $pager;
    }

    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(buttonLabel, pagenumber, pagecount, buttonClickCallback, local) {

        var $Button = $('<li class="pgNext"><a href="#">' + buttonLabel + '</a></li>');

        var destPage = 1;

        // work out destination page for required button type
        switch (buttonLabel) {
            case language[local].first:
                destPage = 1;
                break;
            case language[local].prev:
                destPage = pagenumber - 1;
                break;
            case language[local].next:
                destPage = pagenumber + 1;
                break;
            case language[local].last:
                destPage = pagecount;
                break;
        }

        // disable and 'grey' out buttons if not needed.
        if (buttonLabel == language[local].first || buttonLabel == language[local].prev) {
            pagenumber <= 1 ? $Button.addClass('disabled') : $Button.click(function() { buttonClickCallback(destPage); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('disabled') : $Button.click(function() { buttonClickCallback(destPage); });
        }

        return $Button;
    }

    // pager defaults. hardly worth bothering with in this case but used as placeholder for expansion in the next version
    $.fn.pager.defaults = {
        pagenumber: 1,
        pagecount: 1,
        local: "zh_CN"
    };
    // pager default language
    var language = $.fn.pager.language = {
    	en:{
    		first: 'first',
        	prev: 'prev',
        	next: 'next',
        	last: 'last'
    	},
    	zh_CN:{
			first: '首页',
        	prev: '上一页',
        	next: '下一页',
        	last: '尾页'
    	}
    };

})(jQuery);





