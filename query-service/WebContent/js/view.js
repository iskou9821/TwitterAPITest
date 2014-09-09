$(function() {
	App.IndexController = Ember.ObjectController.extend({
		queryText : "#njslyr",
		queryCount : 15,
		pageTopId : 0,
		pageLastId : 0,
		prevPageTopIds : [],
		pageIndex : 0,
		queryMaxId : 0,
		queryMinId : 0,
		lastPageFlg : false,
		includeMax : false,
		csvHeaderText : 'ID,投稿日時,ユーザー名,内容',
		csvBodyTemplate : '"${item.id}","${item.createdAt?datetime}","${item.user.name}","${item.text}"',
		csvQueryCount : 20,
		actions : {
			doQuery : function() {
				var model = this.get('model');
				var that = this;
				queryLogic.execQuery(
					that.get('queryText'), 
					that.get('queryCount'),
					that.get('queryMaxId'),
					that.get('queryMinId'),
					that.get('includeMax'),
				function(base) {
					model.set('count', base.count);
					model.get('details').clear();
					that.set('pageTopId', base.pageTopId);
					that.set('pageLastId', base.pageLastId);
					that.set('lastPageFlg', base.lastPage == 'true');
				},
				function(dt) {
					var d = App.TwQueryResultDetail.create({
						id : dt.id,
						userName : dt.userName,
						text : dt.text
					});
					model.get('details').pushObject(d);
				},
				function() {
					
				});
			},
			doInitQuery : function() {
				this.set('queryMaxId', 0);
				this.set('queryMinId', 0);
				this.set('pageIndex', 0);
				this.set('includeMax', false);
				this.get('prevPageTopIds').clear();
				this.send('doQuery');							
			},
			doNext : function() {
				if (this.get('lastPageFlg') == true) return;
				var index = this.get('pageIndex');
				var topId = this.get('pageTopId');
				this.set('queryMaxId', this.get('pageLastId'));
				this.set('queryMinId', 0);
				this.set('includeMax', false);
				
				this.get('prevPageTopIds').pushObject(topId);
				this.set('pageIndex', ++index);
				
				this.send('doQuery');
			},
			doPrev : function() {
				var index = this.get('pageIndex'); index--;
				if (index < 0 || index > this.get('prevPageTopIds').get('length')) {
					return;
				}
				var maxId = this.get('prevPageTopIds').objectAt(index);
				this.set('queryMaxId', maxId);
				this.set('queryMinId', 0);
				this.set('includeMax', true);
				
				this.set('pageIndex', index);
				
				this.send('doQuery');
			},
			doDownload : function() {
				queryLogic.execCsvQuery(
					this.get('queryText'),
					this.get('csvQueryCount'),
					this.get('csvHeaderText'),
					this.get('csvBodyTemplate'),
				function(data) {
					var blob = new Blob([ data ], { "type" : "text/plain;charset=UTF-8" });
					var items = $("#download-items");
					$("*", items).remove();
					
					var item = $("<a>");
					item.attr("href", window.URL.createObjectURL(blob));
					item.attr("target", "_blank");
					item.attr("download", "query-result.csv");
					item.text("だうんろーど");
					
					items.append(item);
				},
				function(a, b, c) {
					alert("えらーでーす");
				});
			}
		}
	});
	
	App.IndexRoute = Ember.Route.extend({
		model : function() {
			return App.TwQueryResult.create({
				count : null,
				details : Ember.ArrayProxy.create( {content : []} )
			});
		}
	});
});