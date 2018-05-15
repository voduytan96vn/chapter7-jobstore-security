(function($) {
	var JobStore = {};
	window.JobStore = JobStore;

	var template = function(name) {
		return $('#' + name + '-template').html();
	};

	JobStore.IndexView = Backbone.View.extend({
		render : function() {
			$.ajax('api/v1/patients', {
				method : 'GET',
				success : function(data) {
					$("#patients").empty();
					$("#patientView").empty();
					if (null != data) {
						_.each(data, function(json) {
							var patientHTML = Mustache.to_html(
									template("patient"), json);
							$("#patients").append(patientHTML);
						});
					}
				}
			});
		}
	});

	JobStore.AllergyView = Backbone.View.extend({
		initialize : function(options) {
			this.patientId = options.patientId;
		},
		render : function() {
			$("#patientView").empty();
			$("#patientView").html(Mustache.to_html(template("allergies"), {
				patientId : this.patientId
			}));
			$.ajax('api/v1/patients/' + this.patientId + '/allergies', {
				method : 'GET',
				success : function(data) {
					if (null != data) {
						_.each(data, function(json) {
							var allergyHTML = Mustache.to_html(
									template("allergy"), json);
							$("#patientView").append(allergyHTML);
						});
					}
				}
			});
		}
	});

	JobStore.InfoPatient = Backbone.View.extend({
		initialize : function(options) {
			this.patientId = options.patientId;
		},
		render : function() {
			$("#patientView").empty();
			$.ajax('api/v1/patients/' + this.patientId, {
				mothod : 'GET',
				success : function(data) {
					_.each(data, function(json) {
						var patientHTML = Mustache.to_html(
								template("patient-form"), json);
						$("#patientView").append(patientHTML);
					});
				}
			});
		}
	});

	JobStore.PatientFormView = Backbone.View.extend({
		el : $("body"),
		events : {
			'submit' : 'savePatient'
		},
		render : function() {
			$("#patientView").html(template("patient-form"));
			return this;
		},

		savePatient : function(event) {
			console.log('in savePatient()');
			event.preventDefault();
			var name = $('input[name=name]').val();
			var description = $('#description').val();
			var contactEmail = $('input[name=contactEmail]').val();
			var data = {
				name : name,
				description : description,
				contactEmail : contactEmail
			};
			$.ajax({
				type : "POST",
				url : "api/v1/patients",
				data : JSON.stringify(data),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					console.log(data);
					router.navigate("home", {
						trigger : true
					})
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
				}
			});
		}
	});

	JobStore.AllergyFormView = Backbone.View.extend({
		el : $("body"),

		initialize : function(options) {
			this.patientId = options.patientId;
		},

		events : {
			'submit' : 'saveAllergy'
		},

		render : function() {
			$("#patientView").html(Mustache.to_html(template("allergies"), {
				patientId : this.patientId
			}));
			$("#patientView").append(template("allergy-form"));
			return this;
		},

		saveAllergy : function(event) {
			console.log('in saveAllergy()');
			event.preventDefault();
			var title = $('input[name=title]').val();
			var description = $('#description').val();
			var data = {
				title : title,
				description : description
			};
			var that = this;
			$.ajax({
				type : "POST",
				url : "api/v1/patients/" + this.patientId + "/allergies",
				data : JSON.stringify(data),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					console.log(data);
					router.navigate(
							"patients/" + that.patientId + "/allergies", {
								trigger : true
							})
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
				}
			});
		}

	});

	JobStore.Router = Backbone.Router.extend({
		currentView : null,

		routes : {
			"" : "showAllPatients",
			"home" : "showAllPatients",
			"patients/:patientId/allergies" : "listAllergiesForPatient",
			"patients/new" : "newPatient",
			"patients/:patientId" : "showInfoPatient",
			"patients/:patientId/allergies/new" : "newAllergy"
		},

		changeView : function(view) {
			if (null != this.currentView) {
				this.currentView.undelegateEvents();
				this.currentView = null;
			}
			this.currentView = view;
			this.currentView.render();
		},

		showAllPatients : function() {
			console.log("in showAllPatients()...");
			this.changeView(new JobStore.IndexView());
		},

		listAllergiesForPatient : function(patientId) {
			console.log("in allergiesForAPatient()...");
			this.changeView(new JobStore.AllergyView({
				patientId : patientId
			}));
		},

		newPatient : function() {
			console.log("in newPatient()...");
			this.changeView(new JobStore.PatientFormView());
		},

		newAllergy : function(patientId) {
			console.log("in newAllergy()...");
			this.changeView(new JobStore.AllergyFormView({
				patientId : patientId
			}));
		},

		showInfoPatient : function(patientId) {
			console.log("in InfoPatient()...");
			this.changeView(new JobStore.InfoPatient({
				patientId : patientId
			}));
		}
	});

	var router = new JobStore.Router();
	Backbone.history.start();

})(jQuery);