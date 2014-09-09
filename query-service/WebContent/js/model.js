$(function() {
	App.TwQueryResult = Ember.Object.extend({
		count : null,
		details : Ember.ArrayProxy.create( { content:[] } )
	});
	App.TwQueryResultDetail = Ember.Object.extend({
		userName : null,
		text : null
	});
});