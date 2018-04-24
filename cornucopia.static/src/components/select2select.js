(function ($) {
  
    $.fn.selectToSelect = function (options) {
        if ($.type(options) == "string") {
            var $this = $(this);
            if (options == "getSelectedIds") {
                var ids = "";
                var arr = $("#" + $this.attr("id") + "seReSelect option");
                arr.each(function (i) {
                    if (arr.length - 1 == i) {
                        ids += $(this).attr("id");
                    } else {
                        ids += $(this).attr("id") + ",";
                    }
                });
                return ids;
            }
            return $this;
        }
        var defaults = {
            size: 10,
            opSelect: [],
            opReSelect: [],
            onChange: function (data) { },
            selectNothing: function () { },
            onLoad:function(){},
            beforeSelectAll:function(data){return false;},
            beforeReSelectAll:function(data){return false;},
            beforeSelectOne:function(data){return false;},
            beforeReSelectOne:function(data){return false;}
        };
        var opts = $.extend(defaults, options);
        return this.each(function () {
            // var PleaseSelectAItemLang=$.i18n.map['PleaseSelectAItem']
            var PleaseSelectAItemLang='请选择一项'
            var $this = $(this);
            var str = "<div class='container-fluid'>";
            str += "<div class='col-md-5' style='vertical-align: top;'>";
            str += "<select id='" + $this.attr("id") + "seSelect' multiple='multiple' style='width:100%' size='" + opts.size + "'>";
            for (var i = 0; i < opts.opSelect.length; i++) {
                str += "<option name='" + $this.attr("id") + "opSelect' id='" + opts.opSelect[i].id + "' value='" + opts.opSelect[i].id + "'>" + opts.opSelect[i].name + "</option>"
            }
            str += "</select>";
            str += "</div>";
            str += "<div class='col-md-1' style='vertical-align: top;'> ";
            str += "<table>";
            str += "  <tr>";
            str += '<td><button id="' + $this.attr("id") + 'btnSelectAll" type="button"  class="is-plain el-button el-button--primary el-transfer__button"><span><i class="el-icon-arrow-right"></i><i class="el-icon-arrow-right"></i></span></button></td>'
            // str += "    <td><button class='btn btn-primary' id='" + $this.attr("id") + "btnSelectAll' type='button'>&gt;&gt;</button></td>";
            str += "  </tr>";
            str += "  <tr>";
            str += '<td><button id="' + $this.attr("id") + 'btnSelectOne" type="button"  class="is-plain el-button el-button--primary el-transfer__button"><span><i class="el-icon-arrow-right"></i></span></button></td>'
            // str += "    <td><button class='btn btn-info' id='" + $this.attr("id") + "btnSelectOne' type='button'>&nbsp;&nbsp;&gt;</button></td>";
            str += "  </tr>";
            str += "  <tr><td style=''></td></tr>";
            str += "  <tr> ";
            str += "   <td><button id='" + $this.attr("id") + "btnReSelectOne' type='button' class='is-plain el-button el-button--primary el-transfer__button'><span><i class='el-icon-arrow-left'></i></span></button></td>";
            // str += "   <td><button class='btn btn-info' id='" + $this.attr("id") + "btnReSelectOne' type='button'>&lt;&nbsp;&nbsp;</button></td>";
            str += "  </tr> ";
            str += "  <tr>  ";
            // str += "    <td><button class='btn btn-primary' id='" + $this.attr("id") + "btnReSelectAll' type='button'>&lt;&lt;</button></td>  ";
            str += "   <td><button id='" + $this.attr("id") + "btnReSelectAll' type='button' class='is-plain el-button el-button--primary el-transfer__button'><span><i class='el-icon-arrow-left'></i><i class='el-icon-arrow-left'></i></span></button></td>";
            str += "  </tr> ";
            str += "</table>";
            str += "</div>";
            str += "<div class='col-md-5' style='vertical-align: top;'>";
            str += " <select id='" + $this.attr("id") + "seReSelect' multiple='multiple' style='width:100%' size='" + opts.size + "'> ";
            for (var i = 0; i < opts.opReSelect.length; i++) {
                str += "<option name='" + $this.attr("id") + "opReSelect' id='" + opts.opReSelect[i].id + "' value='" + opts.opReSelect[i].id + "'>" + opts.opReSelect[i].name + "</option>"
            }
            str += " </select>";
            str += "</div>";
            str += "<div class='col-md-1' style='vertical-align: top;'> ";
            str += "  <table>   ";
            str += "    <tr>    ";
            // str += "       <td><button class='btn btn-inverse' id='" + $this.attr("id") + "btnUp' type='button'>↑</button></td> ";
            str += '<td><button id="' + $this.attr("id") + 'btnUp" type="button"  class="is-plain el-button el-button--primary el-transfer__button"><span><i class="el-icon-arrow-up"></i></span></button></td>'
            str += "    </tr> ";
            str += "    <tr>  ";
            // str += "       <td><button class='btn btn-inverse' id='" + $this.attr("id") + "btnDown' type='button'>↓</button></td>  ";
            str += '<td><button id="' + $this.attr("id") + 'btnDown" type="button"  class="is-plain el-button el-button--primary el-transfer__button"><span><i class="el-icon-arrow-down"></i></span></button></td>'
            str += "    </tr> ";
            str += "    <tr><td></td></tr>";
            str += "  </table>";
            str += "</div>";
            str += "</div>";
            $this.html(str);
            opts.onLoad();
            //need juqery ui plugin
            $this.find("button").button();
            //"+$this.attr("id")+"
            $this.find("#" + $this.attr("id") + "btnSelectAll").click(function () {
                if(opts.beforeSelectAll($("option[name='" + $this.attr("id") + "opReSelect']"))){
                    return;
                }
                $this.find("option[name='" + $this.attr("id") + "opSelect']").each(function (i) {
                    $("<option name='" + $this.attr("id") + "opReSelect' id='" + this.id + "' value='" + this.value + "'>" + $(this).text() + "</option>").appendTo("#" + $this.attr("id") + "seReSelect");
                });
                $("#" + $this.attr("id") + "seSelect").empty();
                opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
            });

            $("#" + $this.attr("id") + "btnReSelectAll").click(
                function () {
                    if(opts.beforeReSelectAll($("option[name='" + $this.attr("id") + "opReSelect']"))){
                        return;
                    }
                    $("option[name='" + $this.attr("id") + "opReSelect']").each(function (i) {

                        $("<option name='" + $this.attr("id") + "opSelect' id='" + this.id + "' value='" + this.value + "'>" + $(this).text() + "</option>").appendTo("#" + $this.attr("id") + "seSelect");
                    });
                    $("#" + $this.attr("id") + "seReSelect").empty();
                    opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
                }
            );

            $("#" + $this.attr("id") + "btnSelectOne").click(
                function () {
                    if ($("#" + $this.attr("id") + "seSelect").val()) {
                        if(opts.beforeSelectOne($("#" + $this.attr("id") + "seReSelect option:checked"))){
                            return;
                        }
                        var arrChecked = $("#" + $this.attr("id") + "seSelect option:checked")
                        for (var i = 0; i < arrChecked.length; i++) {
                            $("<option name='" + $this.attr("id") + "opReSelect' id='" + arrChecked[i].id + "' value='" + arrChecked[i].value + "'>" + $(arrChecked[i]).text() + "</option>").appendTo("#" + $this.attr("id") + "seReSelect");
                            $("option[name='" + $this.attr("id") + "opSelect']").each(function (j) {
                                if (this.value == arrChecked[i].value) {
                                    $(this).remove();
                                }
                            });
                        }
                        if(arrChecked.length==0){
                            opts.selectNothing();
                        }else{
                            opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
                        }
                    }
                    else {
                        opts.selectNothing();
                    }
                }
            );

            $("#" + $this.attr("id") + "btnReSelectOne").click(
                function () {
                    if ($("#" + $this.attr("id") + "seReSelect").val()) {
                        if(opts.beforeReSelectOne($("#" + $this.attr("id") + "seReSelect option:checked"))){
                            return;
                        }
                        var arrChecked = $("#" + $this.attr("id") + "seReSelect option:checked");
                        for (var i = 0; i < arrChecked.length; i++) {
                            $("<option name='" + $this.attr("id") + "opSelect' id='" + arrChecked[i].id + "' value='" + arrChecked[i].value + "'>" + $(arrChecked[i]).text() + "</option>").appendTo("#" + $this.attr("id") + "seSelect");
                            $("option[name='" + $this.attr("id") + "opReSelect']").each(function (j) {
                                if (this.value == arrChecked[i].value) {
                                    $(this).remove();
                                }
                            });
                        }
                        if(arrChecked.length==0){
                            opts.selectNothing();
                        }else{
                            opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
                        }
                    }
                    else {
                        opts.selectNothing();
                    }
                }
            );

            $("#" + $this.attr("id") + "btnUp").click(
                function () {
                    if ($("#" + $this.attr("id") + "seReSelect").val() && $("#" + $this.attr("id") + "seReSelect option:checked").length == 1) {
                        var index = $("#" + $this.attr("id") + "seReSelect")[0].selectedIndex;
                        $($("option[name='" + $this.attr("id") + "opReSelect']")[index]).after($("option[name='" + $this.attr("id") + "opReSelect']")[index - 1]);
                        opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
                    }
                    else {
                        opts.selectNothing();
                    }
                }
            );
            $("#" + $this.attr("id") + "btnDown").click(
                function () {
                    if ($("#" + $this.attr("id") + "seReSelect").val() && $("#" + $this.attr("id") + "seReSelect option:checked").length == 1) {
                        var index = $("#" + $this.attr("id") + "seReSelect")[0].selectedIndex;
                        $($("option[name='" + $this.attr("id") + "opReSelect']")[index]).before($("option[name='" + $this.attr("id") + "opReSelect']")[index + 1]);
                        opts.onChange($("option[name='" + $this.attr("id") + "opReSelect']"));
                    }
                    else {
                        opts.selectNothing();
                    }
                }
            );
        });
    };
})(jQuery);