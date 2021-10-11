/* layui_dropdown v2.2.0 by Microanswer,doc:http://test.microanswer.cn/page/dropdown.html */
layui.define(["jquery","laytpl"],function(i){"use strict";function e(i,o){"string"==typeof i&&(i=s(i)),this.$dom=i,this.option=s.extend({downid:String(Math.random()).split(".")[1],filter:i.attr("lay-filter")},t,o),20<this.option.gap&&(this.option.gap=20),this.init()}var s=layui.jquery||layui.$,n=layui.laytpl,d=(s(window.document.body),"a"),a={},h=window.MICROANSWER_DROPDOWAN||"dropdown",r="<div tabindex='0' class='layui-anim layui-anim-upbit dropdown-root' "+h+"-id='{{d.downid}}' style='z-index: {{d.zIndex}}'>{{# if (d.arrow){ }}<div class='dropdown-pointer'></div>{{# } }}<div class='dropdown-content' style='margin: {{d.gap}}px {{d.gap}}px;background-color: {{d.backgroundColor}};min-width: {{d.minWidth}}px;min-height: {{d.minHeight}}px;max-height: {{d.maxHeight}}px;overflow: auto;'>",c="</div></div>",l=r+"{{# layui.each(d.menus, function(index, item){ }}{{# if ('hr' === item) { }}<hr>{{# } else if (item.header) { }}{{# if (item.withLine) { }}<fieldset class=\"layui-elem-field layui-field-title menu-header\" style=\"margin-left:0;margin-bottom: 0;margin-right: 0\"><legend>{{item.header}}</legend></fieldset>{{# } else { }}<div class='menu-header' style='text-align: {{item.align||'left'}}'>{{item.header}}</div>{{# } }}{{# } else { }}<div class='menu-item'><a href='javascript:;' lay-event='{{item.event}}'>{{# if (item.layIcon){ }}<i class='layui-icon {{item.layIcon}}'></i>&nbsp;{{# } }}<span>{{item.txt}}</span></a></div>{{# } }}{{# }); }}"+c,t={showBy:"click",align:"left",minWidth:80,minHeight:10,maxHeight:300,zIndex:891,gap:8,onHide:function(i,o){},onShow:function(i,o){},scrollBehavior:"follow",backgroundColor:"#FFF",cssLink:"https://cdn.jsdelivr.net/gh/microanswer/layui_dropdown@2.2.0/dist/dropdown.css",immed:!1,arrow:!0};function p(i,n){s(i||"[lay-"+h+"]").each(function(){var i=s(this),o=new Function("return "+(i.attr("lay-"+h)||"{}"))();i.removeAttr("lay-"+h);var t=i.data(h)||new e(i,s.extend({},o,n||{}));i.data(h,t)})}layui.link(window[h+"_cssLink"]||t.cssLink,function(){},h+"_css"),e.prototype.init=function(){var o=this;if(o.option.menus&&0<o.option.menus.length)n(l).render(o.option,function(i){o.$down=s(i),o.$dom.after(o.$down),o.initSize(),o.initEvent(),o.onSuccess()});else if(o.option.template){var i;i=-1===o.option.template.indexOf("#")?"#"+o.option.template:o.option.template;var t=s.extend(s.extend({},o.option),o.option.data||{});n(r+s(i).html()+c).render(t,function(i){o.$down=s(i),o.$dom.after(o.$down),o.initSize(),o.initEvent(),o.onSuccess()})}else layui.hint().error("下拉框目前即没配置菜单项，也没配置下拉模板。[#"+(o.$dom.attr("id")||"")+",filter="+o.option.filter+"]")},e.prototype.initSize=function(){this.$down.find(".dropdown-pointer").css("width",2*this.option.gap),this.$down.find(".dropdown-pointer").css("height",2*this.option.gap)},e.prototype.initPosition=function(){var i,o,t,n,e=this.$dom.offset(),s=this.$dom.outerHeight(),d=this.$dom.outerWidth(),a=e.left,h=e.top-window.pageYOffset,r=this.$down.outerHeight(),c=this.$down.outerWidth();t="right"===this.option.align?(i=a+d-c+this.option.gap,-Math.min(c-2*this.option.gap,d)/2):"center"===this.option.align?(i=a+(d-c)/2,(c-2*this.option.gap)/2):(i=a-this.option.gap,Math.min(c-2*this.option.gap,d)/2),o=s+h;var l=this.$arrowDom||(this.$arrowDom=this.$down.find(".dropdown-pointer"));n=-this.option.gap,0<t?(l.css("left",t),l.css("right","unset")):(l.css("left","unset"),l.css("right",-1*t)),i+c>=window.innerWidth&&(i=window.innerWidth-c+this.option.gap),o+r>=window.innerHeight?(o=h-r,n=r-this.option.gap,l.css("top",n).addClass("bottom")):l.css("top",n).removeClass("bottom"),this.$down.css("left",i),this.$down.css("top",o)},e.prototype.show=function(){var t,i,o=this;o.initPosition(),o.opening=!0,setTimeout(function(){o.$down.focus()},100),o.$down.addClass("layui-show"),o.opened=!0,t=o,i=a[d]||[],s.each(i,function(i,o){o(t)}),o.option.onShow&&o.option.onShow(o.$dom,o.$down)},e.prototype.hide=function(){this.fcd=!1,this.$down.removeClass("layui-show"),this.opened=!1,this.option.onHide&&this.option.onHide(this.$dom,this.$down)},e.prototype.hideWhenCan=function(){this.mic||this.opening||this.fcd||this.hide()},e.prototype.toggle=function(){this.opened?this.hide():this.show()},e.prototype.onSuccess=function(){this.option.success&&this.option.success(this.$down),this.option.immed&&this.show()},e.prototype._onScroll=function(){var i=this;i.opened&&("follow"===this.option.scrollBehavior?setTimeout(function(){i.initPosition()},1):this.hide())},e.prototype.initEvent=function(){var i,o,t,n=this;o=function(i){i!==n&&n.hide()},(t=a[i=d]||[]).push(o),a[i]=t,n.$dom.mouseenter(function(){n.mic=!0,"hover"===n.option.showBy&&(n.fcd=!0,n.$down.focus(),n.show())}),n.$dom.mouseleave(function(){n.mic=!1}),n.$down.mouseenter(function(){n.mic=!0,n.$down.focus()}),n.$down.mouseleave(function(){n.mic=!1}),"click"===n.option.showBy&&n.$dom.on("click",function(){n.fcd=!0,n.toggle()}),s(window).on("scroll",function(){n._onScroll()}),n.$dom.parents().on("scroll",function(){n._onScroll()}),s(window).on("resize",function(){n.initPosition()}),n.$dom.on("blur",function(){n.fcd=!1,n.hideWhenCan()}),n.$down.on("blur",function(){n.fcd=!1,n.hideWhenCan()}),n.$down.on("focus",function(){n.opening=!1}),n.option.menus&&s("["+h+"-id='"+n.option.downid+"']").on("click","a",function(){var i=(s(this).attr("lay-event")||"").trim();i?(layui.event.call(this,h,h+"("+n.option.filter+")",i),n.hide()):layui.hint().error("菜单条目["+this.outerHTML+"]未设置event。")})},p(),i(h,{suite:p,onFilter:function(i,o){layui.onevent(h,h+"("+i+")",function(i){o&&o(i)})},hide:function(i){s(i).each(function(){var i=s(this).data(h);i&&i.hide()})},show:function(o,t){s(o).each(function(){var i=s(this).data(h);i?i.show():(layui.hint().error("警告：尝试在选择器【"+o+"】上进行下拉框show操作，但此选择器对应的dom并没有初始化下拉框。"),(t=t||{}).immed=!0,p(o,t))})},version:"2.2.0"})});