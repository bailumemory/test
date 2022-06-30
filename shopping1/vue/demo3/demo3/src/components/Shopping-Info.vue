<template>
  <div class="div1" style="padding: 10px; margin-top: 50px;margin-left: 200px;background-color:floralwhite">
    <div style="margin:10px 0px">
      <el-input
          style="width:30%"
          placeholder="请输入关键字"
          v-model="search"
          clearable>
      </el-input>
      <el-button type="primary" style="margin-left: 10px" @click="load" icon="el-icon-search">搜索</el-button>
    </div>
    <div>
      <div v-for="site in sites" style="border-radius:10px;border:1px solid;display: inline-block;width:210px;height:275px;margin:20px;">
        <div style="width:210px;height:180px;" >
          <el-image
          :src="require('@/'+site.path)"
          style="width:206px;height:178px;padding: 2px 2px">
          </el-image>
        </div>
        <div style="margin-top:5px;">
          <p style="text-align: center;color: aqua">{{site.pname}}</p>
          <p style="text-align: center;color: aqua">￥:{{site.valuse}}元</p>
          <div style="display: flex">
            <el-button
                type="primary"
                size="small"
                style="font-size: 14px;margin-left:45px;margin-top:10px;"
                @click="add(site)">
              加入购物车</el-button>
            <el-link  @click="open(site)" style="font-size: 12px;margin-top:19px;margin-left:5px;color: greenyellow">了解详情</el-link>
          </div>
        </div>
        <el-dialog title="商品详情" :visible.sync="dialogVisible" width="40%">
          <div style="width:100%;height:400px;display: flex">
            <div style="width:40%;height:95%;background-color: greenyellow">
              <el-image style="width:100%;height:70%"
                        :src="location">
              </el-image>
              <p style="font-size: 16px;margin-left:2%;margin-top: 3%">{{date.pname}}</p>
              <p style="font-size: 16px;margin-left:2%;margin-top: 3%">￥:{{date.valuse}}元</p>
              <p style="font-size: 16px;margin-left:2%;margin-top: 3%">库存:{{date.pnum}}</p>
              <div>
              </div>
            </div>
            <div style="width:55%;height:95%;margin-left:2%">
              <p style="font-size:20px;">{{date.information}}</p>
            </div>
          </div>

        </el-dialog>
      </div>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";
import bus from "../router/EventBus"


export default {
  name: "Shopping-Info",
  created() {
    bus.$on("type",(val)=>{
      this.type=val
      this.load()
    })
    this.load()
  },
  data(){
    return{
      search:'',
      sites:'',
      site:'',
      type:'',
      dialogVisible:false,
      date:'',
      location:'',

    }

  },
  methods:{
    open(site){
      this.dialogVisible=true
      console.log(this.date)
      this.date=site
      this.location=require('@/'+this.date.path)

    },
    load() {
      request.get("/products/searchProducts",{
        params:{
          search:this.search,
          type:this.type
        }
      }).then(res =>{
        this.sites=res.data
        this.search=''
      })
    },
    add(site){
      if(this.$cookies.get('user'))
      {
        request.post("/shoppingcar/insertcar",site,{
          params:{
            username:this.$cookies.get('user').username
          }
        }).then(res =>{
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"加入购物车成功成功"
            })
          }

        })
      }
      else
      {
        this.$message({
          type:"error",
          message:"请先登陆"
        })

      }
    },
  }
}
</script>

<style scoped>
.div1{
  background-image: url("../pictrues/30.jpg") ;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
</style>