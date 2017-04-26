jc.data.setup(function (data) {
    var html = '';

    var id = "banner_" + new Date().getTime() + parseInt(Math.random() * 10);
    var list = data.list;

    html += '<div id="' + id + '" class="carousel slide">';
    html += '<ol class="carousel-indicators">';

    /*
    html += '<li data-target="#' + id + '" data-slide-to="0" class="active"></li>';
    html += '<li data-target="#' + id + '" data-slide-to="1"></li>';
    html += '<li data-target="#' + id + '" data-slide-to="2"></li>';
    */

    for (var i = 0, l = list.length; i < l; i++) {
        html += '<li data-target="#' + id + '" data-slide-to="' + i + '" class="' + (i == 0 ? 'active' : '') + '"></li>';
    }



    html += '</ol>';
    html += '<!-- Carousel items -->';
    html += '<div class="carousel-inner">';

    for (var i = 0, l = list.length; i < l; i++) {

        html += '<div class="item ' + (i == 0 ? 'active' : '') + '">';
        var coverImageUrl = list[i].coverImageUrl;
        html += '<a href="javascript:;"><img src="' + (coverImageUrl ? window.serverUploadPath + coverImageUrl : "") + '" /></a>';
        html += '</div>';
    }



    html += '</div>';
    html += '<!-- Carousel nav -->';
    html += '<a class="left carousel-control" href="#' + id + '" data-slide="prev">';
    html += '<span class="glyphicon glyphicon-chevron-left"></span>';
    html += '</a>';
    html += '<a class="right carousel-control" href="#' + id + '" data-slide="next">';
    html += '<span class="glyphicon glyphicon-chevron-right"></span>';
    html += '</a>';
    html += '</div>';


    /*
        <div id="myCarousel" class="carousel slide">
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>
            <!-- Carousel items -->
            <div class="carousel-inner">
                <div class="active item">
                    <a href="javascript:;"><img src="../../static/cache/1.jpg" /></a>
                </div>
                <div class="item">
                    <a href="javascript:;"><img src="../../static/cache/2.jpg" /></a>
                </div>
                <div class="item">
                    <a href="javascript:;"><img src="../../static/cache/3.jpg" /></a>
                </div>
            </div>
            <!-- Carousel nav -->
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>    


    */






    return html;

});