(function() {
    CKEDITOR.plugins.add("insertparams", {
        requires: ["dialog"],
        init: function(a) {
            a.addCommand("insertparams", new CKEDITOR.dialogCommand("insertparams"));
            a.ui.addButton("insertparams", {
                label: "插入参数",//调用dialog时显示的名称
                command: "insertparams",
                icon: this.path + "images/logo_ckeditor.png"//在toolbar中的图标
            });
            CKEDITOR.dialog.add("insertparams", this.path + "dialogs/insertparams.js")
        }
    })
})();