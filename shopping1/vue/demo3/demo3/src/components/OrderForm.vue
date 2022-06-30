<template>
  <div style="width:100%; height:100vh;overflow: hidden">
    <div style="width:100%;height:45px;background-color:  lightblue;display: flex">
      <div style="width:200px; font-weight: bold; color:greenyellow; font-size:20px;padding-top:7px">个人订单</div>
      <div style="flex:1"></div>
      <div>
        <el-button icon="el-icon-back" style="background-color: lightblue" @click="returns">返回</el-button>
      </div>
    </div>
    <div style="overflow:auto;width:1000px;height:600px;margin:20px auto; background-color: white;">
      <div style=" border-radius:7%;border:1px solid;width:240px;height:210px;margin:20px 20px;display: inline-block;background-color: wheat"
           v-for="data in tableData">
        <div style="display:flex">
          <div style="padding:1px 1px"><el-image
              style="width:130px;height:208px"
              :src="require('@/'+data.path)">
          </el-image></div>
          <div style="margin-left:20px">
            <ul type="none">
              <li style="margin-top: 10px">{{data.pname}}</li>
              <li style="margin-top: 10px">￥{{data.totals}}</li>
              <li style="margin-top: 20px">{{data.times}}</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "OrderForm",
  data(){
    return{
      tableData:[],
    }
  },
  created() {
    this.load()

  },
  methods:{

    load(){
      request.get("order/getOrder",{
        params:{
          username:this.$cookies.get('user').username
        }
      }).then(res =>{
        this.tableData=res.data
      })
    },
    returns(){
      this.$router.push("/front-end")

    }

  }
}
</script>

<style scoped>

</style>