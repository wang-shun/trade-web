/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	   config.uiColor = '#66a82b';
	// config.extraPlugins="insertparams";
	   config.toolbar = [
		          { name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source','Maximize'] },
		          { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		          { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll' ] },
		          { name: 'forms', items: ['Checkbox', 'Radio', 'Button'] },
		          { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
		          { name: 'links', items: [ 'Link', 'Unlink'] },
		          { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		          { name: 'about', items: [ 'About' ] },
		          '/',
		          { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent','-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl' ] },
		          { name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak' ] },
		          { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] }
		          ]
};
