webpackJsonp([8],{"4fko":function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var s=e("dLd/"),c=e.n(s),i=e("s8Ph"),n=e("iNxE"),l={name:"BlogAllCategoryTag",created:function(){this.getCategorys(),this.getTags()},data:function(){return{defaultAvatar:c.a,categorys:[],tags:[],currentActiveName:"category"}},computed:{activeName:{get:function(){return this.currentActiveName=this.$route.params.type},set:function(t){this.currentActiveName=t}},categoryTagTitle:function(){return"category"==this.currentActiveName?"文章分类 - 阿果":(console.info("dddd"),"标签 - 阿果")}},methods:{view:function(t){this.$router.push({path:"/"+this.currentActiveName+"/"+t})},getCategorys:function(){var t=this;Object(i.b)().then(function(a){t.categorys=a.data}).catch(function(a){"error"!==a&&t.$message({type:"error",message:"文章分类加载失败",showClose:!0})})},getTags:function(){var t=this;Object(n.b)().then(function(a){t.tags=a.data}).catch(function(a){"error"!==a&&t.$message({type:"error",message:"标签加载失败",showClose:!0})})}},beforeRouteEnter:function(t,a,e){window.document.body.style.backgroundColor="#fff",e()},beforeRouteLeave:function(t,a,e){window.document.body.style.backgroundColor="#f5f5f5",e()}},r={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{directives:[{name:"title",rawName:"v-title"}],staticClass:"me-allct-body",attrs:{"data-title":t.categoryTagTitle}},[e("el-container",{staticClass:"me-allct-container"},[e("el-main",[e("el-tabs",{model:{value:t.activeName,callback:function(a){t.activeName=a},expression:"activeName"}},[e("el-tab-pane",{attrs:{label:"文章分类",name:"category"}},[e("ul",{staticClass:"me-allct-items"},t._l(t.categorys,function(a){return e("li",{key:a.id,staticClass:"me-allct-item",on:{click:function(e){return t.view(a.id)}}},[e("div",{staticClass:"me-allct-content"},[e("a",{staticClass:"me-allct-info"},[e("img",{staticClass:"me-allct-img",attrs:{src:a.avatar?a.avatar:t.defaultAvatar}}),t._v(" "),e("h4",{staticClass:"me-allct-name"},[t._v(t._s(a.categoryName))]),t._v(" "),e("p",{staticClass:"me-allct-description"},[t._v(t._s(a.description))])]),t._v(" "),e("div",{staticClass:"me-allct-meta"},[e("span",[t._v(t._s(a.articles)+" 文章")])])])])}),0)]),t._v(" "),e("el-tab-pane",{attrs:{label:"标签",name:"tag"}},[e("ul",{staticClass:"me-allct-items"},t._l(t.tags,function(a){return e("li",{key:a.id,staticClass:"me-allct-item",on:{click:function(e){return t.view(a.id)}}},[e("div",{staticClass:"me-allct-content"},[e("a",{staticClass:"me-allct-info"},[e("img",{staticClass:"me-allct-img",attrs:{src:a.avatar?a.avatar:t.defaultAvatar}}),t._v(" "),e("h4",{staticClass:"me-allct-name"},[t._v(t._s(a.tagName))])]),t._v(" "),e("div",{staticClass:"me-allct-meta"},[e("span",[t._v(t._s(a.articles)+"  文章")])])])])}),0)])],1)],1)],1)],1)},staticRenderFns:[]};var o=e("VU/8")(l,r,!1,function(t){e("waa8")},null,null);a.default=o.exports},waa8:function(t,a){}});
//# sourceMappingURL=8.9f6bd0f9ebf3491e3caf.js.map