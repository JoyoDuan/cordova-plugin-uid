var exec = require('cordova/exec');


function UID() {}

UID.prototype.getUID = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'UID', 'getUID', []);
};
module.exports = new UID();