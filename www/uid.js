var exec = require('cordova/exec');


function UID() {}

// androidd
UID.prototype.getUID = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'UID', 'getUID', []);
};

// ios
UID.prototype.getDeviceID = function (success, error, args) {
    if (args === undefined) {
        args = {};
    }
    cordova.exec(success, error, 'KeychainUUID', 'getDeviceID', [args]);
};
UID.prototype.deleteDeviceID = function (success, error, args) {
    if (args === undefined) {
        args = {};
    }
    cordova.exec(success, error, 'KeychainUUID', 'deleteDeviceID', [args]);
};

module.exports = new UID();
