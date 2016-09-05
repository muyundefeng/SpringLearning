'use strict';

var BrowserMessage = new function() {

  var sendMessage = function(message, callback) {
    self.port.emit('message', message);
    callback.call(null);
  };

  var addMessageListener = function(fn) {
  };

  this.sendMessage = sendMessage;
  this.addMessageListener = addMessageListener;
};

var BrowserStorage = new function() {
  var storage = require('sdk/simple-storage').storage;

  var getFields = function(f) {
    var fields = [];

    if (typeof f === 'string') {
      fields.push(f);
    } else {
      fields = f;
    }

    return fields;
  };

  var get = function(field, callback) {
    var fields = getFields(field);
    var data = {};
    for (var i = 0, size = fields.length; i < size; i++) {
      var key = fields[i];
      data[key] = storage[key];
    }

    callback.call(null, data);
  };

  var set = function(data, callback) {
    for (var key in data) {
      storage[key] = data[key];
    }

    if (typeof callback !== 'undefined') {
      callback.call(null);
    }
  };

  var remove = function(field, callback) {
    var fields = getFields(field);

    for (var i = 0, size = fields.length; i < size; i++) {
      var key = fields[i];
      delete storage[key];
    }

    if (typeof callback !== 'undefined') {
      callback.call(null);
    }
  };

  this.get = get;
  this.set = set;
  this.remove = remove;
};

var BrowserTabs = new function() {
  var tabs = require('sdk/tabs');
  var data = require('sdk/self').data;

  var create = function(options) {
    var tabOptions = {
      url: data.url(options.url)
    };

    if (options.active === false) {
      tabOptions.inBackground = true;
    }

    tabs.open(tabOptions);
  };

  var sendMessage = function(worker, message, callback) {
    worker.port.emit('message', message);
    callback && callback.call(null);
  };

  this.create = create;
  this.sendMessage = sendMessage;
};

var BrowserCookie = new function() {
  const {Cc,Ci} = require('chrome');
  var cookieManager = Cc['@mozilla.org/cookiemanager;1'].getService(Ci.nsICookieManager2),
    ios = Cc['@mozilla.org/network/io-service;1'].getService(Ci.nsIIOService);

  var get = function(details, callback) {
    //var parser = parseUrl(details.url);
  };

  var set = function(details, callback) {
    if (details.url) {
      var uri = ios.newURI(details.url, null, null);
      details.domain = '.' + uri.host;
      details.secure = (uri.scheme === 'https');

      var tmp = uri.path.split('/');
      if (tmp.length === 0) {
        details.path = '/';
      } else {
        tmp.pop();
        details.path = tmp.join('/');
      }
    }
    cookieManager.add(
      details.domain,
      details.path,
      details.name,
      details.value,
      details.secure,
      false,
      false,
      ((Date.now() / 1000) + 6000)
    );

    callback && callback.call(null);
  };

  var remove = function(details, callback) {
    //chrome.cookies.remove(details, callback);
  };

  this.get = get;
  this.set = set;
  this.remove = remove;
};

var BrowserApi = new function() {
  const {Cc,Ci} = require('chrome');

  var API_URL = 'https://android.clients.google.com/market/licensing/LicenseRequest';

  var getDataString = function(data) {
    var str = '';
    for (var key in data) {
      str += key + '=' + encodeURIComponent(data[key]) + '&';
    }

    if (str.length > 0) {
      str = str.substr(0, str.length - 1);
    }
    return str;
  };

  var get = function(url, data, onload, onerror) {
    var xhr = Cc['@mozilla.org/xmlextras/xmlhttprequest;1'].createInstance(Ci.nsIXMLHttpRequest);
    xhr.open('GET', url, true);
    xhr.setRequestHeader('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36');

    xhr.onload = function(e) {
      onload.call(null, xhr);
    };

    xhr.send(getDataString(data));
  };

  var post = function(data, onload, onerror) {
    var xhr = Cc['@mozilla.org/xmlextras/xmlhttprequest;1'].createInstance(Ci.nsIXMLHttpRequest);
    xhr.open('POST', API_URL, true);
    xhr.responseType = 'arraybuffer';
    xhr.setRequestHeader('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function(e) {
      onload.call(null, xhr);
    };

    xhr.send(getDataString(data));
  };

  this.get = get;
  this.post = post;
};

var BrowserGzip = new function() {
  const {Cc,Ci} = require('chrome');

  var uncompress = function(ua) {
    var data = String.fromCharCode.apply(null, ua);

    // Store data in an input stream
    var inputStream = Cc['@mozilla.org/io/string-input-stream;1'].createInstance(Ci.nsIStringInputStream);
    inputStream.setData(data, data.length);

    // Load input stream onto a pump
    var inputPump = Cc['@mozilla.org/network/input-stream-pump;1'].createInstance(Ci.nsIInputStreamPump);
    inputPump.init(inputStream, -1, -1, 0, 0, true);

    // Create a generic stream converter service
    var streamConv = Cc['@mozilla.org/streamConverters;1'].createInstance(Ci.nsIStreamConverterService);

    // Create a stream listener to accept gunzip'ed data
    var gzipListener = {
      first: true,
      data: null,

      onStartRequest: function(request, context) {
      },

      onDataAvailable: function(request, context, inputStream, offset, count) {
        var binInputStream = Cc['@mozilla.org/binaryinputstream;1'].createInstance(Ci.nsIBinaryInputStream);
        binInputStream.setInputStream(inputStream);
        if (this.first) {
          this.data = binInputStream.readBytes(count);
          this.first = false;
        } else {
          this.data += binInputStream.readBytes(count);
        }
        binInputStream.close();
      },

      onStopRequest: function(request, context, statusCode) {}
    };

    // Create a specific gunzipper with my listener
    var converter = streamConv.asyncConvertData('gzip', 'uncompressed', gzipListener, null);

    // sync compress
    converter.onStartRequest(inputPump, null);
    converter.onDataAvailable(inputPump, null, inputStream, 0, inputStream.available());
    converter.onStopRequest(inputPump, null, 0);

    var decodedData = gzipListener.data;
    return decodedData;
  };

  this.uncompress = uncompress;
};

var BrowserDownloads = new function() {
  const {Cu} = require('chrome');
  Cu.import('resource://gre/modules/Downloads.jsm');
  Cu.import('resource://gre/modules/osfile.jsm');
  Cu.import('resource://gre/modules/Task.jsm');

  var createDirs = function(parent, dirs) {
    if (dirs.length === 0) {
      return;
    }
    var fullPath = OS.Path.join(parent, dirs[0]);
    yield OS.File.makeDir(fullPath, {
      ignoreExisting: true
    });
    dirs.shift();
    yield createDirs(fullPath, dirs);
  };

  var download = function(url, filename, location) {
    Task.spawn(function () {
      let list = yield Downloads.getList(Downloads.ALL);
      let downloadDir = yield Downloads.getPreferredDownloadsDirectory();

      if (location) {
        var dirs = location.split('/');
        yield createDirs(downloadDir, dirs.slice(0)); // clone dirs
        for (var i = 0, size = dirs.length; i < size; i++) {
          downloadDir = OS.Path.join(downloadDir, dirs[i]);
        }
      }

      var download = yield Downloads.createDownload({
        source: url,
        target: OS.Path.join(downloadDir, filename),
        saver: 'copy'
      });
      list.add(download);

      var promise = download.start();
      if (location) {
        // if cannot save in subfolder
        promise.then(null, function(ex) {
          if (ex instanceof Downloads.Error && ex.becauseTargetFailed) {
            // remove old download
            Task.spawn(function () {
              yield list.remove(download);
              yield download.finalize(true);
            }).then(null, Cu.reportError);

            // retry without subfolder
            BrowserDownloads.download(url, filename, '');
          }
        });
      }
    }).then(null, Cu.reportError);
  };

  this.download = download;
};

exports.getSupports = function() {
  return {
    BrowserStorage: BrowserStorage,
    BrowserMessage: BrowserMessage,
    BrowserTabs: BrowserTabs,
    BrowserCookie: BrowserCookie,
    BrowserApi: BrowserApi,
    BrowserGzip: BrowserGzip,
    BrowserDownloads: BrowserDownloads
  };
};