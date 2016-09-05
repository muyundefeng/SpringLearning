'use strict';

var self, data, simplePrefs, pageMod;

self = require('sdk/self');
data = self.data;
simplePrefs = require('sdk/simple-prefs');
pageMod = require('sdk/page-mod');

var MarketSession = require('./modules/protocol');
var BrowserSupport = require('./modules/browser_support');
var supports = BrowserSupport.getSupports();

var BrowserStorage = supports.BrowserStorage;
var BrowserTabs = supports.BrowserTabs;

var LOGIN_URL = data.url('login.html');
var OPTIONS_URL = data.url('options.html');



var attachStorageListener = function(worker) {
  worker.port.on('storage', function(message) {
    if (message.cmd === 'get') {
      BrowserStorage.get(message.data, function(items) {
        worker.port.emit('getStorageResponse', items);
      });
    } else if (message.cmd === 'set') {
      BrowserStorage.set(message.data, function() {
        worker.port.emit('setStorageResponse');
      });
    } else if (message.cmd === 'remove') {
      BrowserStorage.remove(message.data, function() {
        worker.port.emit('removeStorageResponse');
      });
    }
  });
};

pageMod.PageMod({
  include: ['http://play.google.com/store/*', 'https://play.google.com/store/*'],
  contentScriptFile: [
    data.url('js/browser_support.js'),
    data.url('js/content_scripts.js')
  ],
  contentScriptOptions: {
    dataUrl: data.url('')
  },
  contentScriptWhen: 'end',
  attachTo: ['existing', 'top'],
  onAttach: function(worker) {
    worker.port.on('getDataUrl', function() {
      worker.port.emit('getDataUrlResponse', data.url(''));
    });

    worker.port.on('message', function(message) {
      if (message.cmd === 'download') {
        MarketSession.download(message.data.packageName, message.data.versionCode, worker);
      }
    });
  }
});

pageMod.PageMod({
  include: OPTIONS_URL,
  contentScriptFile: [
    data.url('js/browser_support.js'),
    data.url('js/utils.js'),
    data.url('js/options.js')
  ],
  contentScriptOptions: {
    dataUrl: data.url('')
  },
  contentScriptWhen: 'end',
  attachTo: ['existing', 'top'],
  onAttach: function(worker) {
    attachStorageListener(worker);
  }
});

pageMod.PageMod({
  include: LOGIN_URL,
  contentScriptFile: [
    data.url('js/browser_support.js'),
    data.url('js/utils.js'),
    data.url('js/login.js')
  ],
  contentScriptOptions: {
    dataUrl: data.url('')
  },
  contentScriptWhen: 'end',
  attachTo: ['existing', 'top'],
  onAttach: function(worker) {
    attachStorageListener(worker);
  }
});

simplePrefs.on('optionsPage', function() {
  BrowserTabs.create({
    url: OPTIONS_URL
  });
});

var loadReason = self.loadReason;

if (loadReason === 'upgrade') {
  BrowserTabs.create({
    url: 'http://codekiem.com/apk-downloader/updated'
  });
} else if (loadReason === 'install') {
  BrowserTabs.create({
    url: 'http://codekiem.com/apk-downloader/thank-you.html'
  });

  BrowserTabs.create({
    url: OPTIONS_URL,
    active: false
  });
}