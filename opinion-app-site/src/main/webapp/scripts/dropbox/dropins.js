if(!window.Dropbox){window.Dropbox={}}Dropbox.baseUrl="https://www.dropbox.com";Dropbox.blockBaseUrl="https://dl.dropbox.com";Dropbox.addListener=function(c,b,a){if(c.addEventListener){c.addEventListener(b,a,false)}else{c.attachEvent("on"+b,a)}};Dropbox.removeListener=function(c,b,a){if(c.removeEventListener){c.removeEventListener(b,a,false)}else{c.detachEvent("on"+b,a)}};Dropbox._chooserUrl=function(e){var h,f,b,a,c,g,d;c=encodeURIComponent(window.location.protocol+"//"+window.location.host);h=encodeURIComponent(this.appKey);b=encodeURIComponent(e.linkType||"");g=encodeURIComponent(e._trigger||"js");a=Boolean(e.multiselect);f=encodeURIComponent(e.extensions||"");d=""+this.baseUrl+"/chooser?origin="+c+"&app_key="+h+"&link_type="+b;d+="&trigger="+g+"&multiselect="+a+"&extensions="+f;return d};Dropbox._handleMessageEvent=function(a,d,b){var c;c=JSON.parse(a.data);switch(c.method){case"ready":if(typeof b.ready==="function"){b.ready()}break;case"files_selected":case"files_saved":if(typeof d==="function"){d()}if(typeof b.success==="function"){b.success(c.params)}break;case"progress":if(typeof b.progress==="function"){b.progress(c.params)}break;case"close_dialog":if(typeof d==="function"){d()}if(typeof b.cancel==="function"){b.cancel()}break;case"web_session_error":if(typeof d==="function"){d()}if(typeof b.webSessionFailure==="function"){b.webSessionFailure()}break;case"web_session_unlinked":if(typeof d==="function"){d()}if(typeof b.webSessionUnlinked==="function"){b.webSessionUnlinked()}break;case"resize":if(typeof b.resize==="function"){b.resize(c.params)}break;case"error":if(typeof b.error==="function"){b.error(c.params)}}};Dropbox._createIEFrame=function(){var a;a=document.createElement("iframe");a.setAttribute("id","dropbox_xcomm");a.setAttribute("src",Dropbox.baseUrl+"/fp/xcomm");a.style.display="none";document.getElementsByTagName("body")[0].appendChild(a);return Dropbox._ieframe=a};Dropbox.createChooserWidget=function(a){var b,c=this;b=this._createWidgetElement(this._chooserUrl(a));b._handler=function(d){if(d.source===b.contentWindow&&d.origin===c.baseUrl){c._handleMessageEvent(d,null,a)}};this.addListener(window,"message",b._handler);return b};Dropbox.createWidget=Dropbox.createChooserWidget;Dropbox.cleanupWidget=function(a){if(!a._handler){throw"Invalid widget!"}this.removeListener(window,"message",a._handler);delete a._handler};Dropbox.cleanupChooserWidget=Dropbox.cleanupWidget;Dropbox._popupDimensionsString=function(a,b){var d,c;d=(window.screenX||window.screenLeft)+((window.outerWidth||document.documentElement.offsetWidth)-a)/2;c=(window.screenY||window.screenTop)+((window.outerHeight||document.documentElement.offsetHeight)-b)/2;return"width="+a+",height="+b+",left="+d+",top="+c};Dropbox.init=function(a){this.appKey=a.appKey};Dropbox._createWidgetElement=function(a){var b;b=document.createElement("iframe");b.src=a;b.style.display="block";b.style.width="660px";b.style.height="440px";b.style.backgroundColor="white";b.style.border="none";return b};Dropbox.choose=function(c){var e,g,f,b,d,a,h;if(c==null){throw"You must pass in options"}if(c.iframe){h=Dropbox._createWidgetElement(this._chooserUrl(c));d=document.createElement("div");d.style.position="fixed";d.style.left=d.style.right=d.style.top=d.style.bottom="0px";d.style.zIndex="1000";e=document.createElement("div");e.style.position="absolute";e.style.left=e.style.right=e.style.top=e.style.bottom="0px";e.style.backgroundColor="rgb(160, 160, 160)";e.style.opacity="0.2";e.style.filter="progid:DXImageTransform.Microsoft.Alpha(Opacity=20)";b=document.createElement("div");b.style.position="relative";b.style.width="660px";b.style.margin="125px auto 0px auto";b.style.border="1px solid #ACACAC";b.style.boxShadow="rgba(0, 0, 0, .2) 0px 4px 16px";b.appendChild(h);d.appendChild(e);d.appendChild(b);document.body.appendChild(d);f=function(i){if(i.source===h.contentWindow){Dropbox._handleMessageEvent(i,(function(){document.body.removeChild(d);Dropbox.removeListener(window,"message",f)}),c)}};Dropbox.addListener(window,"message",f)}else{g=this._popupDimensionsString(660,440);a=window.open(this._chooserUrl(c),"dropbox",""+g+",resizable=yes,location=yes");a.focus();f=function(i){if(i.source===a||i.source===Dropbox._ieframe.contentWindow){Dropbox._handleMessageEvent(i,(function(){a.close();Dropbox.removeListener(window,"message",f)}),c)}};Dropbox.addListener(window,"message",f)}};Dropbox.attach=Dropbox.choose;(function(){var a,b,c,d;if(!Dropbox.appKey){Dropbox.appKey=(d=document.getElementById("dropboxjs"))!=null?d.getAttribute("data-app-key"):void 0}b=document.createElement("style");b.type="text/css";c=".dropbox-chooser { width: 152px; height: 25px; cursor: pointer;\n  background-image: url('"+Dropbox.baseUrl+"/static/images/widgets/chooser-button-sprites.png'); }\n.dropbox-chooser:hover { background-position: 0 -25px; }\n.dropbox-chooser:active { background-position: 0 -50px; }\n.dropbox-chooser-used { background-position: 152px 0; }\n.dropbox-chooser-used:hover { background-position: 152px -25px; }\n.dropbox-chooser-used:active { background-position: 152px -50px; }";if(b.styleSheet){b.styleSheet.cssText=c}else{b.textContent=c}document.getElementsByTagName("head")[0].appendChild(b);a=function(){var f,h,i,g,e;Dropbox._createIEFrame();if(document.removeEventListener){document.removeEventListener("DOMContentLoaded",a,false)}else{if(document.detachEvent){document.detachEvent("onreadystatechange",a)}}h=document.getElementsByTagName("input");i=function(k){var j;if(k.getAttribute("type")==="dropbox-chooser"){j=document.createElement("div");j.className="dropbox-chooser";k.style.display="none";Dropbox.addListener(j,"click",function(m){var l;Dropbox.choose({success:function(p){var o,q,n;n=(function(){var t,s,r;r=[];for(t=0,s=p.length;t<s;t++){q=p[t];r.push(q.link)}return r})();k.value=n.join(", ");k.files=p;if(document.createEvent){o=document.createEvent("Event");o.initEvent("DbxChooserSuccess",true,false);o.files=p;k.dispatchEvent(o)}j.className="dropbox-chooser dropbox-chooser-used"},cancel:function(){var n;if(document.createEvent){n=document.createEvent("Event");n.initEvent("DbxChooserCancel",true,true);k.dispatchEvent(n)}},linkType:k.getAttribute("data-link-type")||"preview",multiselect:((l=k.getAttribute("data-multiselect"))==="True"||l==="true"||l==="1")||false,extensions:k.getAttribute("data-extensions")||"",_trigger:"button"})});k.parentNode.insertBefore(j,k)}};for(g=0,e=h.length;g<e;g++){f=h[g];i(f)}};if(document.readyState==="complete"){setTimeout(a,0)}else{if(document.addEventListener){document.addEventListener("DOMContentLoaded",a,false)}else{document.attachEvent("onreadystatechange",a)}}})();