'use strict';

var BrowserMessage = new function() { // jshint ignore:line

  var sendMessage = function(message, callback) {
    self.port.emit('message', message);
    callback.call(null);
  };

  var addMessageListener = function(fn) {
    self.port.on('message', fn);
  };

  this.sendMessage = sendMessage;
  this.addMessageListener = addMessageListener;
};

var BrowserStorage = new function() { // jshint ignore:line
  var get = function(field, callback) {
    self.port.emit('storage', {
      cmd: 'get',
      data: field
    });

    self.port.once('getStorageResponse', callback);
  };

  var set = function(data, callback) {
    self.port.emit('storage', {//????是什么意思？
      cmd: 'set',
      data: data
    });

    self.port.once('setStorageResponse', callback || function() {});
  };

  var remove = function(field, callback) {
    self.port.emit('storage', {
      cmd: 'remove',
      data: field
    });

    self.port.once('removeStorageResponse', callback || function() {});
  };

  this.get = get;
  this.set = set;
  this.remove = remove;
};