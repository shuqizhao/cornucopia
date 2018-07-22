<template>
     <!-- DIRECT CHAT -->
    <div class="box box-info direct-chat direct-chat-info">
        <div class="box-header with-border">
            <i class="fa fa-comments"></i><h3 class="box-title">{{cfg.title||'留言'}}</h3>

            <div class="box-tools pull-right">
            <!-- <span data-toggle="tooltip" title="3 New Messages" class="badge bg-yellow">3</span> -->
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <!-- Conversations are loaded here -->
            <div class="direct-chat-messages" style="height:100%">
                <template v-for="(item,index) in detail.messages">
                    <!-- Message. Default to the left -->
                    <div v-show="!item.isReply" class="direct-chat-msg" :key="index+'i'">
                        <div class="direct-chat-info clearfix">
                        <span class="direct-chat-name pull-left">{{item.name}} <i style="color:red;font-size:14px;">[{{item.action}}]</i></span>
                        <!-- <span class="direct-chat-timestamp pull-right"><i style="color:red;font-size:14px;">[{{item.action}}]</i> {{item.time}}</span> -->
                        </div>
                        <!-- /.direct-chat-info -->
                        <img class="direct-chat-img" src="/src/assets/avatar.jpg" alt="message user image">
                        <!-- /.direct-chat-img -->
                        <div class="direct-chat-text" v-html="item.msg">
                        </div>
                        <div><a :id='"btnReply"+item.name+index' :hidden='isHideReply' @click="replyClick(item,$event,index)">回复</a></div>
                        <div><a :id='"btnSave"+item.name+index' :hidden='true' @click="saveClick(item,$event,index)">保存</a></div>
                        <!-- /.direct-chat-text -->
                    </div>
                    <!-- /.direct-chat-msg -->

                    <!-- Message to the right -->
                    <div v-show="item.isReply" class="direct-chat-msg right" :key="index+'j'">
                        <div class="direct-chat-info clearfix">
                        <span class="direct-chat-name pull-right">{{item.name}} <i style="color:red;font-size:14px;">[{{item.action}}]</i></span>
                        <!-- <span class="direct-chat-timestamp pull-left"><i style="color:red;font-size:14px;">[{{item.action}}]</i> {{item.time}}</span> -->
                        </div>
                        <!-- /.direct-chat-info -->
                        <img class="direct-chat-img" src="/src/assets/avatar.jpg" alt="message user image">
                        <!-- /.direct-chat-img -->
                        <div class="direct-chat-text" v-html="item.msg">
                        </div>
                        <!-- /.direct-chat-text -->
                    </div>
                    <!-- /.direct-chat-msg -->
                </template>
            </div>
            <!--/.direct-chat-messages-->
        </div>
        <!-- /.box-body -->
        <div v-show="cfg.mode!='detail'" class="box-footer">
            <textarea name="message" autocomplete="off" class="form-control message" style="width:100%"/>
        </div>
        <!-- /.box-footer-->
    </div>
    <!--/.direct-chat -->
</template>
<script>
export default {
  props: ["cfg"],
  mounted: function() {
    this.currentName = this.getGlobalData().LoginUser.name;
  },
  methods: {
    getSendMsg: function(action) {
      var msgs = [];
      for (var i = 0; i < this.detail.messages.length; i++) {
        var item = this.detail.messages[i];
        msgs.push({
          msg: item.msg + "",
          action: item.action + "",
          time: item.time + "",
          name: item.name + ""
        });
      }
      var message = $(this.$el)
        .find(".message")
        .val();
      var actionStr ='';
      if(this.currentStep){
          actionStr = this.currentStep + "-" + action;
      }else{
          actionStr = action;
      }
      msgs.push({
        msg: message + "<br/>" + this.getNowFormatDate(),
        action: actionStr,
        time: this.getNowFormatDate(),
        name: this.currentName
      });
      $(this.$el)
        .find(".message")
        .val("");
      this.clearMsgsBeforeCommit(msgs);
      return msgs;
    },
    validateFrom: function(isOuter, callback, item, willCommit) {
      if (callback) {
        callback(this.cfg.name, this.detail.messages);
      }
      return true;
    },
    setMessages: function(msgs) {
      this.clearMsgsBeforeLoad(msgs);
      this.detail.messages = msgs;
    },
    addMessage: function(msg) {
      this.detail.messages.push(msg);
    },
    setCurrentName: function(name) {
      this.currentName = name;
    },
    setCurrentStep: function(name) {
      this.currentStep = name;
    },
    stopSendMsg: function() {
      $(this.$el)
        .find(".box-footer")
        .hide();
    },
    clearMsgsBeforeCommit: function(msgs) {
      for (var i = 0; i < msgs.length; i++) {
        msgs[i].msg = msgs[i].msg.replace(/\<br\/\>/g, "my_br");
        msgs[i].msg = msgs[i].msg.replace(/&nbsp;/g, "my_space");
        msgs[i].msg = msgs[i].msg.replace(/\r\n/g, "my_br"); //IE9、FF、chrome
        msgs[i].msg = msgs[i].msg.replace(/\n/g, "my_br"); //IE7-8
        msgs[i].msg = msgs[i].msg.replace(/\s/g, "my_space"); //空格处理
      }
    },
    clearMsgsBeforeLoad: function(msgs) {
      for (var i = 0; i < msgs.length; i++) {
        msgs[i].msg = msgs[i].msg.replace(/my_br/g, "<br/>");
        msgs[i].msg = msgs[i].msg.replace(/my_space/g, "&nbsp;");
      }
    },
    getNowFormatDate: function() {
      var date = new Date();
      var seperator1 = "/";
      var seperator2 = ":";
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate =
        date.getFullYear() +
        seperator1 +
        month +
        seperator1 +
        strDate +
        " " +
        date.getHours() +
        seperator2 +
        date.getMinutes() +
        seperator2 +
        date.getSeconds();
      return currentdate;
    },
    replyClick:function(item,event,index){
      if(event.currentTarget.innerText=='回复'){
        event.currentTarget.innerText='取消'
        $(event.currentTarget).after('<textarea id="'+item.name+index+'" style="width:100%">'+this.currentName+'回复'+item.name+':</textarea>');
        $('#btnSave'+item.name+index).show();
      }else{
        event.currentTarget.innerText='回复'
        $('#'+item.name+index).remove();
        $('#btnSave'+item.name+index).hide();
      }
      
    },
    saveClick:function(item,event,index){
      var message = $('#'+item.name+index).val();
      var actionStr =this.currentName+'@'+item.name
      this.detail.messages.splice(index+1,0,{
        isReply:true,
        msg: message + "<br/>" + this.getNowFormatDate(),
        action: actionStr,
        time: this.getNowFormatDate(),
        name: ''
      });
      $('#'+item.name+index).remove();
      $('#btnSave'+item.name+index).hide();
      $('#btnReply'+item.name+index).text('回复');
    }
  },
  data() {
    return {
      detail: { messages: [] },
      currentName: "无名",
      currentStep: "",
      isHideReply:true
    };
  }
};
</script>
<style>
.direct-chat-text,
.direct-chat-name {
  font-size: 14px;
}
</style>

