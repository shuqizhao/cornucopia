<template>
     <!-- DIRECT CHAT -->
    <div class="box box-info direct-chat direct-chat-info">
        <div class="box-header with-border">
            <i class="fa fa-comments"></i><h3 class="box-title">留言</h3>

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
                        <span class="direct-chat-timestamp pull-right">{{item.time}}</span>
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
                        <span class="direct-chat-timestamp pull-left">{{item.time}}</span>
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
        <div class="box-footer">
            <div class="input-group">
                <input type="text" name="message" autocomplete="off" placeholder="输入消息 ..." class="form-control message">
                <span class="input-group-btn">
                    <button type="button" @click="sendMsg" class="btn btn-warning btn-flat">发送</button>
                    </span>
            </div>
        </div>
        <!-- /.box-footer-->
    </div>
    <!--/.direct-chat -->
</template>
<script>
export default {
  props: ["cfg"],
  mounted: function() {},
  methods: {
    sendMsg: function() {
      var myDate = new Date();
      var message = $(this.$el)
        .find(".message")
        .val();
      this.detail.messages.push({
        msg: message,
        time: myDate.toLocaleString(),
        name: "shuqizhao"
      });
      $(this.$el)
        .find(".message")
        .val("");
    },
    validateFrom: function(isOuter, callback) {
      if (callback) {
        callback(this.cfg.name, this.detail.messages);
      }
      return true;
    },
    setMessages:function(msgs){
        this.detail.messages=msgs;
    },
    addMessage:function(msg){
         this.detail.messages.push(msg);
    }
  },
  data() {
    return {
      detail: { messages: [] }
    };
  }
};
</script>
