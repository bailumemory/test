<template>
  <div  class="div1"style="width:100%; height:100vh;overflow: hidden">
    <div style="width:100%;height:45px;background-color:  lightblue;display: flex">
      <div style="width:200px; font-weight: bold; color:greenyellow; font-size:20px;padding-top:7px">购物车</div>
      <div style="flex:1"></div>
      <div>
        <el-button icon="el-icon-back" style="background-color: lightblue" @click="returns">返回</el-button>
      </div>
    </div>
    <div style="width:900px;height:600px;margin:20px auto; background-color: white">
      <el-table
      :data="tableData"
      ref="multipleTable"
      tooltip-effect="dark"
      height="550"
      border
      stripe
      style="width:100%;height:100%"
      @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="productid"
            label="商品编号"
            sortable>
        </el-table-column>
        <el-table-column
            prop="pname"
            label="商品名"
        ></el-table-column>
        <el-table-column
            prop="valuse"
            label="单价"
        ></el-table-column>
        <el-table-column
            prop="nums"
            label="数量"
        ></el-table-column>
        <el-table-column
            prop="totals"
            label="总价"
        ></el-table-column>
        <el-table-column
            prop="limits"
            label="有效"
        ></el-table-column>
        <el-table-column
           fixed="right"
           label="操作"
           width="100">
          <template slot-scope="scope" >
            <el-button style="margin-left: 10px" @click="add(scope.row)" type="text">+</el-button>
            <el-button style="margin-left: 10px" @click="reduce(scope.row)" type="text">-</el-button>
            <el-popconfirm
            style="margin-left: 10px"
            title="确定删除吗?"
            @confirm="handleDelete(scope.row.productid,$cookies.get('user').username)"
            >
              <el-button slot="reference" type="text">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-left:240px">
        <el-button @click="selectAll" type="primary" style="margin-left:40px;">全选</el-button>
        <el-button @click="deselect" type="primary" style="margin-left:40px;">取消选择</el-button>
        <el-button @click="settleAccounts" type="primary" style="margin-left:40px;">结算</el-button>
      </div>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "ShoppingCar",
  data(){
    return {
      tableData:[],
      multipleSelection: [],
      times:''
    }
  },
  created() {
    this.load()
  },
  methods:{

    returns(){
      this.$router.push("/front-end")
    },
    selectAll(){
      this.$refs.multipleTable.toggleAllSelection();

    },
    deselect(){
        this.$refs.multipleTable.clearSelection();
    },
    settleAccounts(){
      request.post("/order/insertShop",this.$refs.multipleTable.selection,{
        params:{
          id:this.$cookies.get('user').id
        }
      }).then(res =>{
          if(res.code!=='0'){
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          else
          {
            this.$message({
              type:"success",
              message:"生成订单成功"
            })
            this.$cookies.set("user",JSON.stringify(res.data))
            this.load()
          }

      })
    },
    handleSelectionChange(val){
      this.multipleSelection=val
    },
    add(shop){
      request.put("/shoppingcar/addNums",shop).then(res =>{
        this.load()
      })
    },
    reduce(shop){
      request.put("/shoppingcar/reduceNums",shop).then(res =>{
        if(res.code!=='0'){
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })
    },
    load(){
      request.get("/shoppingcar/searchcar",{
        params:{
          name:this.$cookies.get("user").username
        }
      }).then(res =>{
        this.tableData=res.data
      })
    },
    handleDelete(id,username){
      request.delete("/shoppingcar/deleteCar",{
        params:{
          id:id,
          username:username,
        }
      }).then(res =>{
        if(res.code==='0')
        {
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }
        else
        {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load() //刷新购物车页面
      })
    }
  },

}
</script>

<style scoped>
.div1{
  background-image: url("../pictrues/30.jpg") ;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

</style>