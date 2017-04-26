jc.data.setup(function (data) {
    var html = '';

    if (!data) return html;

    html += '<div class="m_title">';
    html += '<div class="t_txt">' + data[0].parent.name + '</div>';
    html += '</div>';

    html += '<div class="m_list">';

    for (var i = 0, l = data.length; i < l; i++) {

        var currentClass = "";

        var curData = data[i];
        var curDataId = curData.id;
        var curDataName = curData.name;

        if (curDataId == columnListId) {
            currentClass = "current";
        }

        html += '<div class="l_item ' + (currentClass) + '">';
        html += '<div class="m_default"><a onclick="window.router(\'menuAndTextlist\',{ rootColumnId:\'' + window.rootColumnId + '\',columnListId:\'' + curDataId + '\' },true);" href="javascript:;">' + (this.getString(curDataName)) + '</a></div>';
        html += '</div>';
    }


    html += '</div>';



    return html;

});