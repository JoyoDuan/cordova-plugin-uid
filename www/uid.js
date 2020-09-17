var exec = require('cordova/exec');


module.exports = {
    getUID: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'UID', 'getUID', []);
    }
};
