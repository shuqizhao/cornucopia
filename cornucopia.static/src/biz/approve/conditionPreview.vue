<template>
    <div>
        <center>
            <span>{{expression}}</span>
            <br/>
            <el-button @click="hidePopup" type="primary">关闭</el-button>
        </center>
    </div>
</template>
<script>
export default {
  mounted: function() {
    this.getExpression();
  },
  data() {
    return {
      expression: ""
    };
  },
  methods: {
    hidePopup: function() {
      this.$parent.$parent.hidePopup();
    },
    getExpression: function() {
      var tableData = this.$parent.$parent.getTableData();

      var bools = "1==1 ";
      var isLevelO2Start = false;
      var isLevelO2End = false;
      var isPreLevel01 = true;
      var isCurLevle01 = false;

      for (var i = 0; i < tableData.length; i++) {
        var ac = tableData[i];
        var opOp = "";
        if (ac.operation == 1) {
          opOp = " == ";
        } else if (ac.operation == 2) {
          opOp = " > ";
        } else if (ac.operation == 3) {
          opOp = " >= ";
        } else if (ac.operation == 4) {
          opOp = " < ";
        } else if (ac.operation == 5) {
          opOp = " <= ";
        } else if (ac.operation == 6) {
          opOp = " != ";
        }
        var isPass = ac.var1 + opOp + ac.var2;
        var op = "";
        var levelLeft = " ";
        var levelRight = " ";
        if (ac.level == 1) {
          isCurLevle01 = true;

          if (isPreLevel01 && !isCurLevle01) {
            isLevelO2Start = true;
          } else {
            isLevelO2Start = false;
          }
          if (isCurLevle01 && !isPreLevel01) {
            isLevelO2End = true;
          } else {
            isLevelO2End = false;
          }

          isPreLevel01 = true;
        } else {
          isCurLevle01 = false;

          if (isPreLevel01 && !isCurLevle01) {
            isLevelO2Start = true;
          } else {
            isLevelO2Start = false;
          }
          if (isCurLevle01 && !isPreLevel01) {
            isLevelO2End = true;
          } else {
            isLevelO2End = false;
          }

          isPreLevel01 = false;
        }

        if (isLevelO2Start) {
          levelLeft = " ( ";
          if (i == tableData.length - 1) {
            levelRight = " ) ";
          }
        }
        if (isLevelO2End) {
          levelRight = " ) ";
        }
        if (ac.boolOperation == 1 || i == 0) {
          op = " && ";
        } else {
          op = " || ";
        }
        if (i == tableData.length - 1) {
          bools += op + levelLeft + isPass + levelRight;
        } else {
          bools += levelRight + op + levelLeft + isPass;
        }
      }
      this.expression = bools;
    }
  }
};
</script>
