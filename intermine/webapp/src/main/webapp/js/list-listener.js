(function($, Backbone) {
    
    var SuccessNotification = Notification.extend( {
        className: "im-list-event-notification topBar messages",
        appendContent: function() {
            if (this.options.change == null) {
                this.notifyWebAppOfCreation();
                this.notifyOfCreation();
            } else {
                this.notifyOfUpdate();
            }
        },
        addLinkToList: function() {
            var a = $('<a>');
            a.text(this.options.list.name);
            var encoded = encodeURIComponent(this.options.list.name);
            a.attr('href', this.options.list.service.root.replace('service/', 'bagDetails.do?bagName=' + encoded));
            this.$('p').append(a);
        },
        notifyWebAppOfCreation: function() {
            var encodedName = encodeURIComponent(this.options.list.name);
            jQuery.post(this.options.list.service.root.replace('service/', 'updateProfileWithBag.do?name=' + encodedName
            + "&id=" + this.options.list.id + "&type=" + this.options.list.type + "&osId=" + this.options.list.osId));
        },
        notifyOfCreation: function() {
            this.$('p').append('<span>Created a new list</span>');
            this.addLinkToList();
            this.$('p').append('<span>( ' + this.options.list.size + ' ' + this.options.list.type + 's)</span>');
        },
        notifyOfUpdate: function() {
            this.addLinkToList();
            this.$('p').append(' <span>successfully updated.</span> ');
            this.$('p').append(((this.options.change > 0) ? 'Added' : 'Removed') + ' ');
            this.$('p').append(Math.abs(this.options.change) + ' items.');
        }
    } );

    var failuriser = function(msg) {
        var notification = new FailureNotification({message: msg});
        notification.render();
    };
    var successifier = function(list, change) {
        var notification = new SuccessNotification({list: list, change: change});
        notification.render();
    };

    this.LIST_EVENTS = {
        "list-creation:success": successifier,
        "list-creation:failure": failuriser,
        "list-update:success": successifier,
        "list-update:failure": failuriser
    };
}).call(window, jQuery, Backbone);
