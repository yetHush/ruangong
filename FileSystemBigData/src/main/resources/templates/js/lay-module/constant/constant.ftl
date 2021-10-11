/**
 扩展一个常量模块
 **/

layui.define(function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var obj = {
        backurl: "http://"+ "${backUrl}" +"/",  //根据自己项目的端口而定
        // RSA 公钥配置
        publickey: '-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsfJnSuGP48zOkj3JjaFDyVpdzu7HiTKCL05s7xE9IFjmnG7AWb4OJ0IEu5UtCrd2KeKv2ZKx1rE5DLjmyVkHKcWRHXaE1gboo3OwW1RYtIFR8steEi54YO8BxiuYTzhKoRrgEoNZ5ipxBoQBPvic4d6vc9jMHVIj/m7aHXCIWLa0j4ZpwA4D6p2GshLYUeqmhkvdKqYA2e5nVxan+lvbIoym4LCRhnHqhyffK4oTbwbT46zY8S7JrGQS6dzBoRLeH8vN2jtalb268NytioVDqLtbOarHccu2eX4mFS9BslhNWezV87lFmZRNV1drcmBMKhyQy369643eIi9Y+SsZMQIDAQAB-----END PUBLIC KEY-----',
        // 系统名
        tablename: "bdfs",
    };
    exports('constant', obj);
});
