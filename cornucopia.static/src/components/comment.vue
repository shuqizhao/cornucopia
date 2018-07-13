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
                    <div v-show="index%2==0" class="direct-chat-msg" :key="index+'i'">
                        <div class="direct-chat-info clearfix">
                        <span class="direct-chat-name pull-left">{{item.name}}</span>
                        <span class="direct-chat-timestamp pull-right"><i style="color:red;">[{{item.action}}]</i> {{item.time}}</span>
                        </div>
                        <!-- /.direct-chat-info -->
                        <img class="direct-chat-img" src="/src/assets/avatar.jpg" alt="message user image">
                        <!-- /.direct-chat-img -->
                        <div class="direct-chat-text">
                            {{item.msg}}
                        </div>
                        <!-- /.direct-chat-text -->
                    </div>
                    <!-- /.direct-chat-msg -->

                    <!-- Message to the right -->
                    <div v-show="index%2==1" class="direct-chat-msg right" :key="index+'j'">
                        <div class="direct-chat-info clearfix">
                        <span class="direct-chat-name pull-right">{{item.name}}</span>
                        <span class="direct-chat-timestamp pull-left"><i style="color:red;">[{{item.action}}]</i> {{item.time}}</span>
                        </div>
                        <!-- /.direct-chat-info -->
                        <img class="direct-chat-img" src="/src/assets/avatar.jpg" alt="message user image">
                        <!-- /.direct-chat-img -->
                        <div class="direct-chat-text">
                            {{item.msg}}
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
      this.currentName = this.getCookie("loginUser");
  },
  methods: {
    sendMsg: function(action) {
      var message = $(this.$el)
        .find(".message")
        .val();
      var myDate = new Date();
      this.detail.messages.push({
        msg: message+'\r\n'+myDate.toLocaleString(),
        action:action,
        time: myDate.toLocaleString(),
        name: this.currentName
      });
      $(this.$el)
        .find(".message")
        .val("");
    },
    getSendMsg: function(action) {
      var message = $(this.$el)
        .find(".message")
        .val();
      var myDate = new Date();
      this.detail.messages.push({
        msg: message+'\r\n'+myDate.toLocaleString(),
        action:action,
        time: myDate.toLocaleString(),
        name: this.currentName
      });
      $(this.$el)
        .find(".message")
        .val("");
    return this.detail.messages;
    },
    validateFrom: function(isOuter, callback,item,willCommit) {
      if (callback) {
        callback(this.cfg.name, this.detail.messages);
      }
      return true;
    },
    setMessages: function(msgs) {
      this.detail.messages = msgs;
    },
    addMessage: function(msg) {
      this.detail.messages.push(msg);
    },
    setCurrentName:function(name){
        this.currentName = name;
    },
    stopSendMsg:function(){
        $(this.$el)
        .find(".box-footer")
        .hide();
    }
  },
  data() {
    return {
      detail: { messages: [] },
      currentName: "无名"
    };
  }
};
</script>
