(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["form"],{6377:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-calendar"}),e._v(" 账号信息\n            ")]),a("el-breadcrumb-item",[e._v("查看与修改账号信息")])],1)],1),a("div",{staticClass:"container"},[a("div",{staticClass:"form-box"},[a("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"账号"}},[a("el-input",{attrs:{disabled:!0},model:{value:e.form.accountname,callback:function(t){e.$set(e.form,"accountname",t)},expression:"form.accountname"}})],1),a("el-form-item",{attrs:{label:"新密码",prop:"password"}},[a("el-input",{attrs:{placeholder:"需要修改密码时再输入"},model:{value:e.form.password,callback:function(t){e.$set(e.form,"password",t)},expression:"form.password"}})],1),a("el-form-item",{attrs:{label:"用户名",prop:"username"}},[a("el-input",{model:{value:e.form.username,callback:function(t){e.$set(e.form,"username",t)},expression:"form.username"}})],1),a("el-form-item",{attrs:{label:"上次登录"}},[a("el-input",{attrs:{disabled:!0,"value-format":"yyyy-MM-dd"},model:{value:e.form.lastlogintime,callback:function(t){e.$set(e.form,"lastlogintime",t)},expression:"form.lastlogintime"}})],1),a("el-form-item",{attrs:{label:"上次修改"}},[a("el-input",{attrs:{disabled:!0},model:{value:e.form.updatetime,callback:function(t){e.$set(e.form,"updatetime",t)},expression:"form.updatetime"}})],1),a("el-form-item",{attrs:{label:"修改账号"}},[a("el-input",{attrs:{disabled:!0},model:{value:e.form.updateuser,callback:function(t){e.$set(e.form,"updateuser",t)},expression:"form.updateuser"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("修改")]),a("el-button",[e._v("取消")])],1)],1)],1)])])},l=[],r=a("bea6"),o={components:{ElFormItem:r["a"]},name:"baseform",data:function(){return{accountname:localStorage.getItem("ms_username"),form:{id:"",accountname:"",password:"",username:"",lastlogintime:"",updatetime:"",updateuser:""},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}]}}},created:function(){this.getaccountmessage()},methods:{getaccountmessage:function(){var e=this;this.$axios.get("/user/getaccountmessage",{params:{accountname:this.accountname}}).then((function(t){e.form.id=t.data.data.id,e.form.accountname=t.data.data.account,e.form.username=t.data.data.user_name,e.form.lastlogintime=t.data.data.last_login_time,e.form.updatetime=t.data.data.update_time,e.form.updateuser=t.data.data.update_user}))},onSubmit:function(){var e=this;this.$refs.form.validate((function(t){var a;t&&(a=""==e.form.password||null==e.form.password?0:1,e.$axios.post("/user/editaccountmessage",e.$qs.stringify({id:e.form.id,password:e.form.password,username:e.form.username,whether_change_password:a})).then((function(t){t.status>=200&&t.status<300?(e.$message.success("修改成功！"),e.getaccountmessage()):console.log(t.message)})))}))}}},s=o,n=a("2877"),u=Object(n["a"])(s,i,l,!1,null,null,null);t["default"]=u.exports},bea6:function(e,t,a){"use strict";var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"el-form-item",class:[{"el-form-item--feedback":e.elForm&&e.elForm.statusIcon,"is-error":"error"===e.validateState,"is-validating":"validating"===e.validateState,"is-success":"success"===e.validateState,"is-required":e.isRequired||e.required,"is-no-asterisk":e.elForm&&e.elForm.hideRequiredAsterisk},e.sizeClass?"el-form-item--"+e.sizeClass:""]},[a("label-wrap",{attrs:{"is-auto-width":e.labelStyle&&"auto"===e.labelStyle.width,"update-all":"auto"===e.form.labelWidth}},[e.label||e.$slots.label?a("label",{staticClass:"el-form-item__label",style:e.labelStyle,attrs:{for:e.labelFor}},[e._t("label",[e._v(e._s(e.label+e.form.labelSuffix))])],2):e._e()]),a("div",{staticClass:"el-form-item__content",style:e.contentStyle},[e._t("default"),a("transition",{attrs:{name:"el-zoom-in-top"}},["error"===e.validateState&&e.showMessage&&e.form.showMessage?e._t("error",[a("div",{staticClass:"el-form-item__error",class:{"el-form-item__error--inline":"boolean"===typeof e.inlineMessage?e.inlineMessage:e.elForm&&e.elForm.inlineMessage||!1}},[e._v("\n          "+e._s(e.validateMessage)+"\n        ")])],{error:e.validateMessage}):e._e()],2)],2)],1)},l=[],r=(a("ac6a"),a("a481"),a("a15e"));function o(e,t,a){this.$children.forEach(i=>{var l=i.$options.componentName;l===e?i.$emit.apply(i,[t].concat(a)):o.apply(i,[e,t].concat([a]))})}var s={methods:{dispatch(e,t,a){var i=this.$parent||this.$root,l=i.$options.componentName;while(i&&(!l||l!==e))i=i.$parent,i&&(l=i.$options.componentName);i&&i.$emit.apply(i,[t].concat(a))},broadcast(e,t,a){o.call(this,e,t,a)}}},n=function(e){for(let t=1,a=arguments.length;t<a;t++){let a=arguments[t]||{};for(let t in a)if(a.hasOwnProperty(t)){let i=a[t];void 0!==i&&(e[t]=i)}}return e};a("2b0e");Object.prototype.hasOwnProperty;function u(){}function m(e,t,a){let i=e;t=t.replace(/\[(\w+)\]/g,".$1"),t=t.replace(/^\./,"");let l=t.split("."),r=0;for(let o=l.length;r<o-1;++r){if(!i&&!a)break;let e=l[r];if(!(e in i)){if(a)throw new Error("please transfer a valid prop path to form item!");break}i=i[e]}return{o:i,k:l[r],v:i?i[l[r]]:null}}var d,c,f={props:{isAutoWidth:Boolean,updateAll:Boolean},inject:["elForm","elFormItem"],render:function(){var e=arguments[0],t=this.$slots.default;if(!t)return null;if(this.isAutoWidth){var a=this.elForm.autoLabelWidth,i={};if(a&&"auto"!==a){var l=parseInt(a,10)-this.computedWidth;l&&(i.marginLeft=l+"px")}return e("div",{class:"el-form-item__label-wrap",style:i},[t])}return t[0]},methods:{getLabelWidth:function(){if(this.$el&&this.$el.firstElementChild){var e=window.getComputedStyle(this.$el.firstElementChild).width;return Math.ceil(parseFloat(e))}return 0},updateLabelWidth:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"update";this.$slots.default&&this.isAutoWidth&&this.$el.firstElementChild&&("update"===e?this.computedWidth=this.getLabelWidth():"remove"===e&&this.elForm.deregisterLabelWidth(this.computedWidth))}},watch:{computedWidth:function(e,t){this.updateAll&&(this.elForm.registerLabelWidth(e,t),this.elFormItem.updateComputedLabelWidth(e))}},data:function(){return{computedWidth:0}},mounted:function(){this.updateLabelWidth("update")},updated:function(){this.updateLabelWidth("update")},beforeDestroy:function(){this.updateLabelWidth("remove")}},h=f,p=a("2877"),b=Object(p["a"])(h,d,c,!1,null,null,null),v=b.exports,g={name:"ElFormItem",componentName:"ElFormItem",mixins:[s],provide:function(){return{elFormItem:this}},inject:["elForm"],props:{label:String,labelWidth:String,prop:String,required:{type:Boolean,default:void 0},rules:[Object,Array],error:String,validateStatus:String,for:String,inlineMessage:{type:[String,Boolean],default:""},showMessage:{type:Boolean,default:!0},size:String},components:{LabelWrap:v},watch:{error:{immediate:!0,handler:function(e){this.validateMessage=e,this.validateState=e?"error":""}},validateStatus:function(e){this.validateState=e}},computed:{labelFor:function(){return this.for||this.prop},labelStyle:function(){var e={};if("top"===this.form.labelPosition)return e;var t=this.labelWidth||this.form.labelWidth;return t&&(e.width=t),e},contentStyle:function(){var e={},t=this.label;if("top"===this.form.labelPosition||this.form.inline)return e;if(!t&&!this.labelWidth&&this.isNested)return e;var a=this.labelWidth||this.form.labelWidth;return"auto"===a?"auto"===this.labelWidth?e.marginLeft=this.computedLabelWidth:"auto"===this.form.labelWidth&&(e.marginLeft=this.elForm.autoLabelWidth):e.marginLeft=a,e},form:function(){var e=this.$parent,t=e.$options.componentName;while("ElForm"!==t)"ElFormItem"===t&&(this.isNested=!0),e=e.$parent,t=e.$options.componentName;return e},fieldValue:function(){var e=this.form.model;if(e&&this.prop){var t=this.prop;return-1!==t.indexOf(":")&&(t=t.replace(/:/,".")),m(e,t,!0).v}},isRequired:function(){var e=this.getRules(),t=!1;return e&&e.length&&e.every((function(e){return!e.required||(t=!0,!1)})),t},_formSize:function(){return this.elForm.size},elFormItemSize:function(){return this.size||this._formSize},sizeClass:function(){return this.elFormItemSize||(this.$ELEMENT||{}).size}},data:function(){return{validateState:"",validateMessage:"",validateDisabled:!1,validator:{},isNested:!1,computedLabelWidth:""}},methods:{validate:function(e){var t=this,a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:u;this.validateDisabled=!1;var i=this.getFilteredRule(e);if((!i||0===i.length)&&void 0===this.required)return a(),!0;this.validateState="validating";var l={};i&&i.length>0&&i.forEach((function(e){delete e.trigger})),l[this.prop]=i;var o=new r["default"](l),s={};s[this.prop]=this.fieldValue,o.validate(s,{firstFields:!0},(function(e,i){t.validateState=e?"error":"success",t.validateMessage=e?e[0].message:"",a(t.validateMessage,i),t.elForm&&t.elForm.$emit("validate",t.prop,!e,t.validateMessage||null)}))},clearValidate:function(){this.validateState="",this.validateMessage="",this.validateDisabled=!1},resetField:function(){var e=this;this.validateState="",this.validateMessage="";var t=this.form.model,a=this.fieldValue,i=this.prop;-1!==i.indexOf(":")&&(i=i.replace(/:/,"."));var l=m(t,i,!0);this.validateDisabled=!0,Array.isArray(a)?l.o[l.k]=[].concat(this.initialValue):l.o[l.k]=this.initialValue,this.$nextTick((function(){e.validateDisabled=!1})),this.broadcast("ElTimeSelect","fieldReset",this.initialValue)},getRules:function(){var e=this.form.rules,t=this.rules,a=void 0!==this.required?{required:!!this.required}:[],i=m(e,this.prop||"");return e=e?i.o[this.prop||""]||i.v:[],[].concat(t||e||[]).concat(a)},getFilteredRule:function(e){var t=this.getRules();return t.filter((function(t){return!t.trigger||""===e||(Array.isArray(t.trigger)?t.trigger.indexOf(e)>-1:t.trigger===e)})).map((function(e){return n({},e)}))},onFieldBlur:function(){this.validate("blur")},onFieldChange:function(){this.validateDisabled?this.validateDisabled=!1:this.validate("change")},updateComputedLabelWidth:function(e){this.computedLabelWidth=e?"".concat(e,"px"):""},addValidateEvents:function(){var e=this.getRules();(e.length||void 0!==this.required)&&(this.$on("el.form.blur",this.onFieldBlur),this.$on("el.form.change",this.onFieldChange))},removeValidateEvents:function(){this.$off()}},mounted:function(){if(this.prop){this.dispatch("ElForm","el.form.addField",[this]);var e=this.fieldValue;Array.isArray(e)&&(e=[].concat(e)),Object.defineProperty(this,"initialValue",{value:e}),this.addValidateEvents()}},beforeDestroy:function(){this.dispatch("ElForm","el.form.removeField",[this])}},y=g,$=Object(p["a"])(y,i,l,!1,null,null,null);t["a"]=$.exports},ec6b:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-calendar"}),e._v(" 表单\n            ")]),a("el-breadcrumb-item",[e._v("基本表单")])],1)],1),a("div",{staticClass:"container"},[a("div",{staticClass:"form-box"},[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"表单名称"}},[a("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),a("el-form-item",{attrs:{label:"选择器"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.form.region,callback:function(t){e.$set(e.form,"region",t)},expression:"form.region"}},[a("el-option",{key:"bbk",attrs:{label:"步步高",value:"bbk"}}),a("el-option",{key:"xtc",attrs:{label:"小天才",value:"xtc"}}),a("el-option",{key:"imoo",attrs:{label:"imoo",value:"imoo"}})],1)],1),a("el-form-item",{attrs:{label:"日期时间"}},[a("el-col",{attrs:{span:11}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{type:"date",placeholder:"选择日期","value-format":"yyyy-MM-dd"},model:{value:e.form.date1,callback:function(t){e.$set(e.form,"date1",t)},expression:"form.date1"}})],1),a("el-col",{staticClass:"line",attrs:{span:2}},[e._v("-")]),a("el-col",{attrs:{span:11}},[a("el-time-picker",{staticStyle:{width:"100%"},attrs:{placeholder:"选择时间"},model:{value:e.form.date2,callback:function(t){e.$set(e.form,"date2",t)},expression:"form.date2"}})],1)],1),a("el-form-item",{attrs:{label:"城市级联"}},[a("el-cascader",{attrs:{options:e.options},model:{value:e.form.options,callback:function(t){e.$set(e.form,"options",t)},expression:"form.options"}})],1),a("el-form-item",{attrs:{label:"选择开关"}},[a("el-switch",{model:{value:e.form.delivery,callback:function(t){e.$set(e.form,"delivery",t)},expression:"form.delivery"}})],1),a("el-form-item",{attrs:{label:"多选框"}},[a("el-checkbox-group",{model:{value:e.form.type,callback:function(t){e.$set(e.form,"type",t)},expression:"form.type"}},[a("el-checkbox",{attrs:{label:"步步高",name:"type"}}),a("el-checkbox",{attrs:{label:"小天才",name:"type"}}),a("el-checkbox",{attrs:{label:"imoo",name:"type"}})],1)],1),a("el-form-item",{attrs:{label:"单选框"}},[a("el-radio-group",{model:{value:e.form.resource,callback:function(t){e.$set(e.form,"resource",t)},expression:"form.resource"}},[a("el-radio",{attrs:{label:"步步高"}}),a("el-radio",{attrs:{label:"小天才"}}),a("el-radio",{attrs:{label:"imoo"}})],1)],1),a("el-form-item",{attrs:{label:"文本框"}},[a("el-input",{attrs:{type:"textarea",rows:"5"},model:{value:e.form.desc,callback:function(t){e.$set(e.form,"desc",t)},expression:"form.desc"}})],1),a("el-form-item",[a("el-switch",{attrs:{"active-text":"按月付费","inactive-text":"按年付费"},model:{value:e.form.value1,callback:function(t){e.$set(e.form,"value1",t)},expression:"form.value1"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("表单提交")]),a("el-button",[e._v("取消")])],1)],1)],1)])])},l=[],r=a("bea6"),o={components:{ElFormItem:r["a"]},name:"baseform",data:function(){return{options:[{value:"guangdong",label:"广东省",children:[{value:"guangzhou",label:"广州市",children:[{value:"tianhe",label:"天河区"},{value:"haizhu",label:"海珠区"}]},{value:"dongguan",label:"东莞市",children:[{value:"changan",label:"长安镇"},{value:"humen",label:"虎门镇"}]}]},{value:"hunan",label:"湖南省",children:[{value:"changsha",label:"长沙市",children:[{value:"yuelu",label:"岳麓区"}]}]}],form:{name:"",region:"",date1:"",date2:"",delivery:!0,type:["步步高"],resource:"小天才",desc:"",options:[],value1:!0}}},methods:{onSubmit:function(){this.$message.success("提交成功！")}}},s=o,n=a("2877"),u=Object(n["a"])(s,i,l,!1,null,null,null);t["default"]=u.exports}}]);