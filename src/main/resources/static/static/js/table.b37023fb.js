(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["table"],{"11b9":function(e,t,a){},"23d3":function(e,t,a){},"3e92":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),e._v(" 基础表格\n            ")])],1)],1),a("div",{staticClass:"container"},[a("div",{staticClass:"handle-box"},[a("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-delete"},on:{click:e.delAllSelection}},[e._v("批量删除")]),a("el-select",{staticClass:"handle-select mr10",attrs:{placeholder:"地址"},model:{value:e.query.address,callback:function(t){e.$set(e.query,"address",t)},expression:"query.address"}},[a("el-option",{key:"1",attrs:{label:"广东省",value:"广东省"}}),a("el-option",{key:"2",attrs:{label:"湖南省",value:"湖南省"}})],1),a("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"用户名"},model:{value:e.query.name,callback:function(t){e.$set(e.query,"name",t)},expression:"query.name"}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("搜索")])],1),a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:e.tableData,border:"","header-cell-class-name":"table-header"},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"id",label:"ID",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"name",label:"用户名"}}),a("el-table-column",{attrs:{label:"账户余额"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("￥"+e._s(t.row.money))]}}])}),a("el-table-column",{attrs:{label:"头像(查看大图)",align:"center"},scopedSlots:e._u([{key:"default",fn:function(e){return[a("el-image",{staticClass:"table-td-thumb",attrs:{src:e.row.thumb,"preview-src-list":[e.row.thumb]}})]}}])}),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),a("el-table-column",{attrs:{label:"状态",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:"成功"===t.row.state?"success":"失败"===t.row.state?"danger":""}},[e._v(e._s(t.row.state))])]}}])}),a("el-table-column",{attrs:{prop:"date",label:"注册时间"}}),a("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v("编辑")]),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){return e.handleDelete(t.$index,t.row)}}},[e._v("删除")])]}}])})],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":e.query.pageIndex,"page-size":e.query.pageSize,total:e.pageTotal},on:{"current-change":e.handlePageChange}})],1)],1),a("el-dialog",{attrs:{title:"编辑",visible:e.editVisible,width:"30%"},on:{"update:visible":function(t){e.editVisible=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"70px"}},[a("el-form-item",{attrs:{label:"用户名"}},[a("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),a("el-form-item",{attrs:{label:"地址"}},[a("el-input",{model:{value:e.form.address,callback:function(t){e.$set(e.form,"address",t)},expression:"form.address"}})],1)],1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.editVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:e.saveEdit}},[e._v("确 定")])],1)],1)],1)},s=[],l=(a("7f7f"),a("365c")),o={name:"basetable",data:function(){return{query:{address:"",name:"",pageIndex:1,pageSize:10},tableData:[],multipleSelection:[],delList:[],editVisible:!1,pageTotal:0,form:{},idx:-1,id:-1}},created:function(){this.getData()},methods:{getData:function(){var e=this;Object(l["b"])(this.query).then((function(t){console.log(t),e.tableData=t.list,e.pageTotal=t.pageTotal||50}))},handleSearch:function(){this.$set(this.query,"pageIndex",1),this.getData()},handleDelete:function(e,t){var a=this;this.$confirm("确定要删除吗？","提示",{type:"warning"}).then((function(){a.$message.success("删除成功"),a.tableData.splice(e,1)})).catch((function(){}))},handleSelectionChange:function(e){this.multipleSelection=e},delAllSelection:function(){var e=this.multipleSelection.length,t="";this.delList=this.delList.concat(this.multipleSelection);for(var a=0;a<e;a++)t+=this.multipleSelection[a].name+" ";this.$message.error("删除了".concat(t)),this.multipleSelection=[]},handleEdit:function(e,t){this.idx=e,this.form=t,this.editVisible=!0},saveEdit:function(){this.editVisible=!1,this.$message.success("修改第 ".concat(this.idx+1," 行成功")),this.$set(this.tableData,this.idx,this.form)},handlePageChange:function(e){this.$set(this.query,"pageIndex",e),this.getData()}}},r=o,n=(a("4d44"),a("2877")),c=Object(n["a"])(r,i,s,!1,null,"07d5d1cb",null);t["default"]=c.exports},"4d44":function(e,t,a){"use strict";var i=a("23d3"),s=a.n(i);s.a},9474:function(e,t,a){"use strict";var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("label",{staticClass:"el-checkbox",class:[e.border&&e.checkboxSize?"el-checkbox--"+e.checkboxSize:"",{"is-disabled":e.isDisabled},{"is-bordered":e.border},{"is-checked":e.isChecked}],attrs:{id:e.id}},[a("span",{staticClass:"el-checkbox__input",class:{"is-disabled":e.isDisabled,"is-checked":e.isChecked,"is-indeterminate":e.indeterminate,"is-focus":e.focus},attrs:{tabindex:!!e.indeterminate&&0,role:!!e.indeterminate&&"checkbox","aria-checked":!!e.indeterminate&&"mixed"}},[a("span",{staticClass:"el-checkbox__inner"}),e.trueLabel||e.falseLabel?a("input",{directives:[{name:"model",rawName:"v-model",value:e.model,expression:"model"}],staticClass:"el-checkbox__original",attrs:{type:"checkbox","aria-hidden":e.indeterminate?"true":"false",name:e.name,disabled:e.isDisabled,"true-value":e.trueLabel,"false-value":e.falseLabel},domProps:{checked:Array.isArray(e.model)?e._i(e.model,null)>-1:e._q(e.model,e.trueLabel)},on:{change:[function(t){var a=e.model,i=t.target,s=i.checked?e.trueLabel:e.falseLabel;if(Array.isArray(a)){var l=null,o=e._i(a,l);i.checked?o<0&&(e.model=a.concat([l])):o>-1&&(e.model=a.slice(0,o).concat(a.slice(o+1)))}else e.model=s},e.handleChange],focus:function(t){e.focus=!0},blur:function(t){e.focus=!1}}}):a("input",{directives:[{name:"model",rawName:"v-model",value:e.model,expression:"model"}],staticClass:"el-checkbox__original",attrs:{type:"checkbox","aria-hidden":e.indeterminate?"true":"false",disabled:e.isDisabled,name:e.name},domProps:{value:e.label,checked:Array.isArray(e.model)?e._i(e.model,e.label)>-1:e.model},on:{change:[function(t){var a=e.model,i=t.target,s=!!i.checked;if(Array.isArray(a)){var l=e.label,o=e._i(a,l);i.checked?o<0&&(e.model=a.concat([l])):o>-1&&(e.model=a.slice(0,o).concat(a.slice(o+1)))}else e.model=s},e.handleChange],focus:function(t){e.focus=!0},blur:function(t){e.focus=!1}}})]),e.$slots.default||e.label?a("span",{staticClass:"el-checkbox__label"},[e._t("default"),e.$slots.default?e._e():[e._v(e._s(e.label))]],2):e._e()])},s=[],l=(a("c5f6"),a("6b54"),a("f6f4")),o={name:"ElCheckbox",mixins:[l["a"]],inject:{elForm:{default:""},elFormItem:{default:""}},componentName:"ElCheckbox",data:function(){return{selfModel:!1,focus:!1,isLimitExceeded:!1}},computed:{model:{get:function(){return this.isGroup?this.store:void 0!==this.value?this.value:this.selfModel},set:function(e){this.isGroup?(this.isLimitExceeded=!1,void 0!==this._checkboxGroup.min&&e.length<this._checkboxGroup.min&&(this.isLimitExceeded=!0),void 0!==this._checkboxGroup.max&&e.length>this._checkboxGroup.max&&(this.isLimitExceeded=!0),!1===this.isLimitExceeded&&this.dispatch("ElCheckboxGroup","input",[e])):(this.$emit("input",e),this.selfModel=e)}},isChecked:function(){return"[object Boolean]"==={}.toString.call(this.model)?this.model:Array.isArray(this.model)?this.model.indexOf(this.label)>-1:null!==this.model&&void 0!==this.model?this.model===this.trueLabel:void 0},isGroup:function(){var e=this.$parent;while(e){if("ElCheckboxGroup"===e.$options.componentName)return this._checkboxGroup=e,!0;e=e.$parent}return!1},store:function(){return this._checkboxGroup?this._checkboxGroup.value:this.value},isLimitDisabled:function(){var e=this._checkboxGroup,t=e.max,a=e.min;return!(!t&&!a)&&this.model.length>=t&&!this.isChecked||this.model.length<=a&&this.isChecked},isDisabled:function(){return this.isGroup?this._checkboxGroup.disabled||this.disabled||(this.elForm||{}).disabled||this.isLimitDisabled:this.disabled||(this.elForm||{}).disabled},_elFormItemSize:function(){return(this.elFormItem||{}).elFormItemSize},checkboxSize:function(){var e=this.size||this._elFormItemSize||(this.$ELEMENT||{}).size;return this.isGroup&&this._checkboxGroup.checkboxGroupSize||e}},props:{value:{},label:{},indeterminate:Boolean,disabled:Boolean,checked:Boolean,name:String,trueLabel:[String,Number],falseLabel:[String,Number],id:String,controls:String,border:Boolean,size:String},methods:{addToStore:function(){Array.isArray(this.model)&&-1===this.model.indexOf(this.label)?this.model.push(this.label):this.model=this.trueLabel||!0},handleChange:function(e){var t,a=this;this.isLimitExceeded||(t=e.target.checked?void 0===this.trueLabel||this.trueLabel:void 0!==this.falseLabel&&this.falseLabel,this.$emit("change",t,e),this.$nextTick((function(){a.isGroup&&a.dispatch("ElCheckboxGroup","change",[a._checkboxGroup.value])})))}},created:function(){this.checked&&this.addToStore()},mounted:function(){this.indeterminate&&this.$el.setAttribute("aria-controls",this.controls)},watch:{value:function(e){this.dispatch("ElFormItem","el.form.change",e)}}},r=o,n=a("2877"),c=Object(n["a"])(r,i,s,!1,null,null,null);t["a"]=c.exports},a849:function(e,t,a){"use strict";var i=a("11b9"),s=a.n(i);s.a},a906:function(e,t,a){"use strict";var i=a("fb3b"),s=a.n(i);s.a},dfba:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),e._v(" 抽检库管理\n            ")])],1)],1),a("div",{staticClass:"container"},[a("div",{staticClass:"handle-box"},[a("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-plus"},on:{click:function(t){e.dialogFormVisible=!0}}},[e._v("添加新的抽检库")]),a("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-upload2"},on:{click:function(t){e.uploadFormVisible=!0}}},[e._v("excel上传抽检库数据")]),a("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"抽检点名称"},model:{value:e.searchname,callback:function(t){e.searchname=t},expression:"searchname"}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("搜索")])],1),a("el-table",{ref:"multipleTable",staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","header-cell-class-name":"table-header"},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[a("el-form-item",{attrs:{label:"辖区"}},[a("span",[e._v(e._s(t.row.jurisdiction))])]),a("el-form-item",{attrs:{label:"类别"}},[a("span",[e._v(e._s(t.row.category))])]),a("el-form-item",{attrs:{label:"抽检点名称"}},[a("span",[e._v(e._s(t.row.ssl_name))])]),a("el-form-item",{attrs:{label:"地址"}},[a("span",[e._v(e._s(t.row.address))])]),a("el-form-item",{attrs:{label:"更新时间"}},[a("span",[e._v(e._s(t.row.last_update_time))])]),a("el-form-item",{attrs:{label:"创建时间"}},[a("span",[e._v(e._s(t.row.create_time))])]),a("el-form-item",{attrs:{label:"包含抽检食品类型"}},[a("span",[e._v(e._s(t.row.foodtype_ids))])])],1)]}}])}),a("el-table-column",{attrs:{sortable:"",prop:"jurisdiction",label:"辖区"}}),a("el-table-column",{attrs:{sortable:"",prop:"category",label:"类别"}}),a("el-table-column",{attrs:{sortable:"",prop:"ssl_name",label:"抽检点名称"}}),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),a("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v("编辑")]),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){return e.handleDelete(t.$index,t.row)}}},[e._v("删除")]),a("el-button",{attrs:{type:"text",icon:"el-icon-guide"},on:{click:function(a){return e.handfoodtype(t.$index,t.row)}}},[e._v("抽检食品类型管理")])]}}])})],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":e.query.pageIndex,"page-size":e.query.pageSize,total:e.pageTotal},on:{"current-change":e.handlePageChange}})],1)],1),a("el-dialog",{directives:[{name:"dialogDrag",rawName:"v-dialogDrag"}],attrs:{title:"编辑",visible:e.editVisible,width:"30%"},on:{"update:visible":function(t){e.editVisible=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"70px"}},[a("el-form-item",{attrs:{label:"辖区"}},[a("el-input",{model:{value:e.form.jurisdiction,callback:function(t){e.$set(e.form,"jurisdiction",t)},expression:"form.jurisdiction"}})],1),a("el-form-item",{attrs:{label:"类别"}},[a("el-input",{model:{value:e.form.category,callback:function(t){e.$set(e.form,"category",t)},expression:"form.category"}})],1),a("el-form-item",{attrs:{label:"抽检点"}},[a("el-input",{model:{value:e.form.ssl_name,callback:function(t){e.$set(e.form,"ssl_name",t)},expression:"form.ssl_name"}})],1),a("el-form-item",{attrs:{label:"地址"}},[a("el-input",{model:{value:e.form.address,callback:function(t){e.$set(e.form,"address",t)},expression:"form.address"}})],1),a("el-form-item",{attrs:{label:"食品类型"}},[a("el-input",{attrs:{disabled:!0},model:{value:e.form.foodtype_ids,callback:function(t){e.$set(e.form,"foodtype_ids",t)},expression:"form.foodtype_ids"}})],1)],1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.editVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:e.saveEdit}},[e._v("确 定")])],1)],1),a("el-dialog",{directives:[{name:"dialogDrag",rawName:"v-dialogDrag"}],attrs:{title:"添加抽检点信息",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-form",{attrs:{model:e.addform,rules:e.rules}},[a("el-form-item",{attrs:{prop:"s_jurisdiction",label:"辖区","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.jurisdiction,callback:function(t){e.$set(e.addform,"jurisdiction",t)},expression:"addform.jurisdiction"}})],1),a("el-form-item",{attrs:{prop:"s_category",label:"类别","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.category,callback:function(t){e.$set(e.addform,"category",t)},expression:"addform.category"}})],1),a("el-form-item",{attrs:{prop:"s_ssl_name",label:"抽检点","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.ssl_name,callback:function(t){e.$set(e.addform,"ssl_name",t)},expression:"addform.ssl_name"}})],1),a("el-form-item",{attrs:{prop:"s_address",label:"地址","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.address,callback:function(t){e.$set(e.addform,"address",t)},expression:"addform.address"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addnewsiinformation()}}},[e._v("确 定")])],1)],1),a("el-dialog",{directives:[{name:"dialogDrag",rawName:"v-dialogDrag"}],attrs:{title:"上传数据",visible:e.uploadFormVisible},on:{"update:visible":function(t){e.uploadFormVisible=t}}},[a("el-form",{attrs:{model:e.addform,rules:e.rules}},[a("el-form-item",{attrs:{prop:"s_account",label:"账号","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.s_account,callback:function(t){e.$set(e.addform,"s_account",t)},expression:"addform.s_account"}})],1),a("el-form-item",{attrs:{prop:"s_password",label:"密码","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.s_password,callback:function(t){e.$set(e.addform,"s_password",t)},expression:"addform.s_password"}})],1),a("el-form-item",{attrs:{prop:"s_username",label:"用户名","label-width":e.formLabelWidth}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:e.addform.s_username,callback:function(t){e.$set(e.addform,"s_username",t)},expression:"addform.s_username"}})],1),a("el-form-item",{attrs:{prop:"whether_participate",label:"是否参与抽检","label-width":e.formLabelWidth}},[a("el-switch",{attrs:{"active-text":"参与","inactive-text":"不参与"},model:{value:e.addform.whether_participate,callback:function(t){e.$set(e.addform,"whether_participate",t)},expression:"addform.whether_participate"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addnewsiinformation()}}},[e._v("确 定")])],1)],1),a("el-dialog",{directives:[{name:"dialogDrag",rawName:"v-dialogDrag"}],attrs:{title:"食品类型管理",visible:e.foodtypeVisible,width:"30%"},on:{"update:visible":function(t){e.foodtypeVisible=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"70px"}},[a("el-form-item",{attrs:{label:"已分配"}},[a("el-checkbox-group",{model:{value:e.againhavedistributenames,callback:function(t){e.againhavedistributenames=t},expression:"againhavedistributenames"}},e._l(e.havedistributenames,(function(t){return a("el-checkbox",{key:t,attrs:{label:t}},[e._v(e._s(t))])})),1)],1),a("el-form-item",{attrs:{label:"请选择"}},[a("el-checkbox-group",{model:{value:e.distributenames,callback:function(t){e.distributenames=t},expression:"distributenames"}},e._l(e.undistributenames,(function(t){return a("el-checkbox",{key:t,attrs:{label:t}},[e._v(e._s(t))])})),1)],1)],1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.foodtypeVisible=!1}}},[e._v("取 消")]),a("el-button",{attrs:{type:"primary"},on:{click:e.saveDistribute}},[e._v("确 定")])],1)],1)],1)},s=[],l=(a("6b54"),a("28a5"),a("7f7f"),a("365c"),a("9474")),o={components:{ElCheckbox:l["a"]},name:"samplinglibrary",data:function(){return{searchname:"",rules:{s_jurisdiction:[{required:!0,message:"请输入辖区",trigger:"blur"}],s_category:[{required:!0,message:"请输入类别",trigger:"blur"}],s_ssl_name:[{required:!0,message:"请输入抽检点名称",trigger:"blur"}],s_address:[{required:!0,message:"请输入抽检点的地址",trigger:"blur"}]},formLabelWidth:"70px",query:{address:"",name:"",pageIndex:1,pageSize:10},tableData:[],multipleSelection:[],delList:[],editVisible:!1,uploadFormVisible:!1,foodtypeVisible:!1,dialogFormVisible:!1,pageTotal:0,form:{},idx:-1,id:-1,addform:{jurisdiction:"",category:"",ssl_name:"",address:""},distributenames:[],undistributenames:[],havedistributenames:[],againhavedistributenames:[],checked:!0}},created:function(){this.getData()},methods:{getData:function(){var e=this;this.$axios.post("/sslibrary/getalllibrary",this.$qs.stringify({pageIndex:this.query.pageIndex,pageSize:this.query.pageSize})).then((function(t){null!=t&&(e.tableData=t.data.data)}))},handleSearch:function(){this.$set(this.query,"pageIndex",1),this.getData()},handleDelete:function(e,t){var a=this;this.$confirm("确定要删除吗？","提示",{type:"warning"}).then((function(){a.$axios.post("/sslibrary/deletesamplinglibrarybyid",a.$qs.stringify({id:t.id})).then((function(t){null!=t&&(a.$message.success("删除成功"),a.tableData.splice(e,1))}))})).catch((function(){}))},handleSelectionChange:function(e){this.multipleSelection=e},delAllSelection:function(){var e=this.multipleSelection.length,t="";this.delList=this.delList.concat(this.multipleSelection);for(var a=0;a<e;a++)t+=this.multipleSelection[a].name+" ";this.$message.error("删除了".concat(t)),this.multipleSelection=[]},handleEdit:function(e,t){this.idx=e,this.form=t,this.editVisible=!0},saveEdit:function(){var e=this;this.editVisible=!1,this.$axios.post("/sslibrary/updatesamplinglibrarybyid",this.$qs.stringify({id:this.form.id,jurisdiction:this.form.jurisdiction,category:this.form.category,ssl_name:this.form.ssl_name,address:this.form.address})).then((function(t){null!=t?(e.$set(e.tableData,e.idx,e.form),e.$message.success("修改成功！")):e.getData()}))},addnewsiinformation:function(){var e=this;this.$axios.post("/ssaccount/insertnewssaccount",this.$qs.stringify({adminaccount:localStorage.getItem("ms_username"),s_account:this.addform.s_account,s_password:this.addform.s_password,s_username:this.addform.s_username,whether_participate:this.addform.whether_participate})).then((function(t){null!=t&&(null!=e.addform.s_account&&null!=e.addform.s_password&&null!=e.addform.s_username?(e.$message.success("添加成功！"),e.getData()):e.$message.error("请填写所需信息"))}))},handlePageChange:function(e){this.$set(this.query,"pageIndex",e),this.getData()},handfoodtype:function(e,t){var a=this;this.idx=e,this.form=t,this.havedistributenames=[];var i=this.form.foodtype_ids;if(null!=i){var s=new Array;s=i.split("-");for(var l=0;l<s.length;l++)this.havedistributenames[l]=s[l]}this.againhavedistributenames=this.havedistributenames,this.$axios.post("/sstype/findalltypebyadminid",this.$qs.stringify({id:this.form.id})).then((function(e){if(null!=e){a.undistributenames=[];for(var t=0,i=0;i<e.data.data.length;i++){for(var s=1,l=e.data.data[i].type_name,o=0;o<a.havedistributenames.length;o++)if(a.havedistributenames[o]==l){s=0;break}1==s&&(a.undistributenames[t++]=l)}a.foodtypeVisible=!0}}))},saveDistribute:function(){var e=this;this.foodtypeVisible=!1,this.$axios.post("/sslibrary/savesamplingtype",this.$qs.stringify({distributenames:this.distributenames.toString(),againhavedistributenames:this.againhavedistributenames.toString(),insaccountid:this.form.id})).then((function(t){null!=t&&(e.getData(),e.$message.success("分配成功"))})),this.distributenames=[]},resetaccount:function(){var e=this;this.$axios.post("/ssaccount/resetsamplingaccount",this.$qs.stringify({adminaccount:localStorage.getItem("ms_username")})).then((function(t){null!=t&&(e.getData(),e.$message.success("重置成功"))}))},randomlyassigned:function(){this.randomVisible=!0},saverandom:function(){var e=this;this.$axios.post("/ssaccount/randomlyassigned",this.$qs.stringify({adminaccount:localStorage.getItem("ms_username")})).then((function(t){null!=t&&(e.getData(),e.$message.success("随机分配成功"))}))}}},r=o,n=(a("a906"),a("a849"),a("2877")),c=Object(n["a"])(r,i,s,!1,null,"2923002b",null);t["default"]=c.exports},f6f4:function(e,t,a){"use strict";function i(e,t,a){this.$children.forEach(s=>{var l=s.$options.componentName;l===e?s.$emit.apply(s,[t].concat(a)):i.apply(s,[e,t].concat([a]))})}t["a"]={methods:{dispatch(e,t,a){var i=this.$parent||this.$root,s=i.$options.componentName;while(i&&(!s||s!==e))i=i.$parent,i&&(s=i.$options.componentName);i&&i.$emit.apply(i,[t].concat(a))},broadcast(e,t,a){i.call(this,e,t,a)}}}},fb3b:function(e,t,a){}}]);