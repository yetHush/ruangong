/**
 扩展一个公共工具模块
 **/

layui.define(['jquery', 'constant', 'layer', 'spark_md5'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var $ = layui.$,
        layer = layui.layer,
        spark_md5 = layui.spark_md5,
        constant = layui.constant;
    var obj = {
        login: function (entity) {
            //请求成功后，写入 access_token
            //判断是否保持登录
            if ($('.icon-nocheck').hasClass('icon-check')) {
                layui.data(constant.tablename, {
                    key: "user",
                    value: entity
                });
            } else {
                layui.sessionData(constant.tablename, {
                    key: "user",
                    value: entity
                });
            }
        },
        logout: function () {
            //token失效移除token
            layui.data(constant.tablename, {
                key: 'user',
                remove: true
            });
            layui.sessionData(constant.tablename, {
                key: 'user',
                remove: true
            });
            window.location = '../../../pages/user/login.html';
        },
        // 自定义post解析数据
        post: function (url, data, html = null, successFunc = null, completeFunc = null) {
            $.ajax({
                url: url,
                type: "POST",
                async: true,
                headers: this.getHeaders(),
                data: data,
                // dataType: 'application/json',
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                if (undefined != successFunc && null != successFunc) {
                                    successFunc(result.entity.entity);
                                }
                                layer.msg(result.entity.msg, {time: 3000, icon: 1});
                                if (undefined != html && null != html) {
                                    phtml = '../../../pages/' + html;
                                    //调整到相关的页面
                                    window.location = phtml;
                                }
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        // window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
        },
        post_param: function (url, data, html = null, successFunc = null, completeFunc = null) {
            $.ajax({
                url: url,
                type: "POST",
                async: true,
                headers: this.getHeaders(),
                data: data,
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                layer.msg(result.entity.msg, {time: 3000, icon: 1});
                                if (undefined != successFunc && null != successFunc) {
                                    // window.localStorage.setItem('token', result.message);
                                    successFunc(result.entity.entity);
                                    // window.localStorage.setItem('token', result.message);
                                }
                                if (undefined != html && null != html) {
                                    phtml = '../../../pages/' + html;
                                    //调整到相关的页面
                                    window.location = phtml;
                                }
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        // window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
        },
        post_file: function (url, data, html = null, successFunc = null, completeFunc = null) {
            $.ajax({
                url: url,
                type: "POST",
                async: true,
                headers: this.getHeaders(),
                data: data,
                cache: false,
                processData: false,
                contentType: false,
                // contentType: "application/form-data;charset=UTF-8",
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                layer.msg(result.entity.msg, {time: 3000, icon: 1});
                                if (undefined != successFunc && null != successFunc) {
                                    // window.localStorage.setItem('token', result.message);
                                    successFunc(result.entity.entity);
                                    // window.localStorage.setItem('token', result.message);
                                }
                                if (undefined != html && null != html) {
                                    phtml = '../../../pages/' + html;
                                    //调整到相关的页面
                                    window.location = phtml;
                                }
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        // window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
        },
        post_lfile: async function (url, data, html = null, successFunc = null, completeFunc = null,) {
            $.ajax({
                url: url,
                type: "POST",
                async: true,
                headers: this.getHeaders(),
                data: data,
                cache: false,
                processData: false,
                contentType: false,
                // contentType: "application/form-data;charset=UTF-8",
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                // layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                if (undefined != successFunc && null != successFunc) {
                                    // window.localStorage.setItem('token', result.message);
                                    successFunc(result.entity.entity);
                                    // window.localStorage.setItem('token', result.message);
                                }
                                if (undefined != html && null != html) {
                                    phtml = '../../../pages/' + html;
                                    //调整到相关的页面
                                    window.location = phtml;
                                }
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        // window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
        },
        // 自定义get解析数据
        get: function (url, data, successFunc = null, completeFunc = null) {
            $.ajax({
                url: url,
                type: "GET",
                headers: this.getHeaders(),
                data: data,
                dataType: 'json',
                contentType: "application/json;charset=utf-8",
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                if (undefined != successFunc && null != successFunc) {
                                    successFunc(result.entity.entity);
                                }
                                // layer.msg(result.entity.msg, {time: 3000, icon: 1});
                                // global_result = result.entity;
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
            // return global_result;
        },
        getUser: function () {
            let user = null;
            if (undefined != layui.data(constant.tablename).user &&
                '' != layui.data(constant.tablename).user) {
                user = layui.data(constant.tablename).user;
            }
            if (undefined != layui.sessionData(constant.tablename).user &&
                '' != layui.sessionData(constant.tablename).user) {
                user = layui.sessionData(constant.tablename).user;
            }
            return user;
        },
        setUser: function (user) {
            if (undefined != layui.data(constant.tablename).user &&
                '' != layui.data(constant.tablename).user) {
                layui.data(constant.tablename, {
                    key: "user",
                    value: user
                });
            }
            if (undefined != layui.sessionData(constant.tablename).user &&
                '' != layui.sessionData(constant.tablename).user) {
                layui.sessionData(constant.tablename, {
                    key: "user",
                    value: user
                });
            }
        },
        getToken: function () {
            let token = layui.data(layui.constant.tablename) == undefined ? '' :
                layui.data(layui.constant.tablename).user == undefined ? '' :
                    layui.data(layui.constant.tablename).user.token == undefined ? '' : layui.data(layui.constant.tablename).user.token
            if (token == undefined || token == null || token == '') {
                token = layui.sessionData(layui.constant.tablename) == undefined ? '' :
                    layui.sessionData(layui.constant.tablename).user == undefined ? '' :
                        layui.sessionData(layui.constant.tablename).user.token == undefined ? '' : layui.sessionData(layui.constant.tablename).user.token
            }
            return token;
        },
        checkLogin: function () {
            let token = this.getToken();
            if (token == undefined || token == null || token == '') {
                layer.msg('请登录！', {time: 3000, icon: 1});
                window.location = '../../../pages/user/login.html';
            }
        },
        parseData: function (result, successFunc) {
            if (null != result && '' !== result) {
                // 状态503内部错误信息
                if (503 === result.status) {
                    if ('111' === result.entity.code) {
                        layer.msg(result.entity.msg, {time: 3000, icon: 2});
                        return null;
                    } else if ("001" === result.entity.code) {
                        //token失效，退出登录
                        layer.msg(result.entity.msg, {time: 3000, icon: 2});
                        //token失效移除token
                        layui.data(constant.tablename, {
                            key: 'user',
                            remove: true
                        });
                        layui.sessionData(constant.tablename, {
                            key: 'user',
                            remove: true
                        });
                        window.location = '../../../pages/user/login.html';
                    }
                    //状态200运行成功
                } else if (200 == result.status) {
                    if ('000' == result.entity.code) {
                        //执行想要的操作
                        if (undefined !== successFunc && null != successFunc) {
                            successFunc(result.entity.entity);
                        }
                        // layer.msg(result.entity.msg, {time: 3000, icon: 1});
                        return result.entity;
                    }
                }
            } else {
                layer.msg('出问题了！', function () {
                    window.location = '../../../pages/error/404.html';
                });
            }
        },
        delete: function (url, data, html, successFunc, completeFunc) {
            $.ajax({
                url: url,
                type: "DELETE",
                headers: this.getHeaders(),
                data: data,
                dataType: 'json',
                success: function (result) {
                    if (null != result && '' !== result) {
                        // 状态503内部错误信息
                        if (503 == result.status) {
                            if ('111' == result.entity.code) {
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                            } else if ("001" == result.entity.code) {
                                //token失效，退出登录
                                layer.msg(result.entity.msg, {time: 3000, icon: 2});
                                //token失效移除token
                                layui.data(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                layui.sessionData(constant.tablename, {
                                    key: 'user',
                                    remove: true
                                });
                                window.location = '../../../pages/user/login.html';
                            }
                            //状态200运行成功
                        } else if (200 == result.status) {
                            if ('000' == result.entity.code) {
                                //执行想要的操作
                                if (undefined != successFunc && null != successFunc) {
                                    successFunc(result.entity.entity);
                                }
                                layer.msg(result.entity.msg, {time: 3000, icon: 1});
                                return result.entity;
                            }
                        }
                    }
                },
                error: function () {
                    layer.msg('出问题了！', function () {
                        window.location = '../../../pages/error/404.html';
                    });
                },
                complete: function () {
                    if (undefined != completeFunc && null != completeFunc) {
                        completeFunc();
                    }
                }
            });
        },
        /**
         * 当前是否为与移动端
         * */
        isModile: function () {
            if ($(window).width() <= 768) {
                return true;
            }
            return false;
        },
        hasClass: function (elem, cls) {
            cls = cls || '';
            if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
            return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
        },
        addClass: function (elem, cls) {
            if (!this.hasClass(elem, cls)) {
                elem.className = elem.className == '' ? cls : elem.className + ' ' + cls;
            }
        },
        removeClass: function (elem, cls) {
            if (this.hasClass(elem, cls)) {
                var newClass = ' ' + elem.className.replace(/[\t\r\n]/g, '') + ' ';
                while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                    newClass = newClass.replace(' ' + cls + ' ', ' ');
                }
                elem.className = newClass.replace(/^\s+|\s+$/g, '');
            }
        },
        getUUID: function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0,
                    v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        },
        getFileMd5: function (file, chunkSize) {
            return new Promise((resolve, reject) => {
                let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
                let chunks = Math.ceil(file.size / chunkSize);
                let currentChunk = 0;
                let spark = new SparkMD5.ArrayBuffer();
                let fileReader = new FileReader();

                fileReader.onload = function (e) {
                    spark.append(e.target.result);
                    currentChunk++;
                    if (currentChunk < chunks) {
                        loadNext();
                    } else {
                        let md5 = spark.end();
                        resolve(md5);
                    }
                };

                fileReader.onerror = function (e) {
                    reject(e);
                };

                function loadNext() {
                    let start = currentChunk * chunkSize;
                    let end = start + chunkSize;
                    if (end > file.size) {
                        end = file.size;
                    }
                    fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
                }

                loadNext();
            });
        },
        download: function (url, fileName) {
            fetch(url, {
                method: 'GET',
                headers: this.getHeaders(),
            })
                .then(res => res.blob())
                .then(data => {
                    const blobUrl = window.URL.createObjectURL(data);
                    const a = document.createElement('a');
                    a.download = fileName;
                    a.href = blobUrl;
                    a.click();
                })
                .catch(e => {
                    console.error(e);
                    layer.msg('文件下载失败！', {time: 3000, icon: 2});
                });
        },

        getHeaders() {
            return {
                'token': this.getToken(),
                // 'X-Real-IP': constant.ipaddr,
                // 'Browser': this.getBrowserInfo(),
            }
        },
        getBrowserInfo: function () {
            var agent = navigator.userAgent.toLowerCase();
            var arr = [];
            let system = agent.split(' ')[1].split(' ')[0].split('(')[1];
            arr.push(system.split(';')[0]);
            var regStr_edge = /edge\/[\d.]+/gi;
            var regStr_ie = /trident\/[\d.]+/gi;
            var regStr_ff = /firefox\/[\d.]+/gi;
            var regStr_chrome = /chrome\/[\d.]+/gi;
            var regStr_saf = /safari\/[\d.]+/gi;
            var regStr_opera = /opr\/[\d.]+/gi;
            //IE
            if (agent.indexOf("trident") > 0) {
                arr.push(agent.match(regStr_ie)[0].split('/')[0]);
                arr.push(agent.match(regStr_ie)[0].split('/')[1]);
                return arr;
            }
            //Edge
            if (agent.indexOf('edge') > 0) {
                arr.push(agent.match(regStr_edge)[0].split('/')[0]);
                arr.push(agent.match(regStr_edge)[0].split('/')[1]);
                return arr;
            }
            //firefox
            if (agent.indexOf("firefox") > 0) {
                arr.push(agent.match(regStr_ff)[0].split('/')[0]);
                arr.push(agent.match(regStr_ff)[0].split('/')[1]);
                return arr;
            }
            //Opera
            if (agent.indexOf("opr") > 0) {
                arr.push(agent.match(regStr_opera)[0].split('/')[0]);
                arr.push(agent.match(regStr_opera)[0].split('/')[1]);
                return arr;
            }
            //Safari
            if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
                arr.push(agent.match(regStr_saf)[0].split('/')[0]);
                arr.push(agent.match(regStr_saf)[0].split('/')[1]);
                return arr;
            }
            //Chrome
            if (agent.indexOf("chrome") > 0) {
                arr.push(agent.match(regStr_chrome)[0].split('/')[0]);
                arr.push(agent.match(regStr_chrome)[0].split('/')[1]);
                return arr;
            } else {
                arr.push('非主流浏览器，例如chrome,firefox,opera,safari,IE,Edge!')
                return arr;
            }
        }
    };
    exports('comn', obj);
});
