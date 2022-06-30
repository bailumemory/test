<template>
  <div style="width:100%;height:100vh">
    <div style="margin-top: 20px;height:95vh;width:700px;margin-left:430px; background: white;background-color:papayawhip;">
      <div style="display:flex;width: 100%;height:60px;background-color: wheat">
        <div><el-avatar style=" width:50px;height:50px;" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar></div>
      </div>
      <div style="width:100%;height:125px;">
        <el-descriptions class="margin-top" title="用户信息" :column="3" :size="size">
          <template slot="extra">
            <el-button type="primary" size="small" icon="el-icon-edit-outline" @click="updateUser">修改信息</el-button>
          </template>
          <el-descriptions-item label="用户名">{{user.username}}</el-descriptions-item>
          <el-descriptions-item label="密码">{{ user.password }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ user.sex }}</el-descriptions-item>
          <el-descriptions-item label="用户等级">{{ user.degrees }}</el-descriptions-item>
          <el-descriptions-item label="是否禁用">{{user.limits}}</el-descriptions-item>
          <el-descriptions-item label="余额">{{ user.moneys }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <div style="width:100%;height:40px;margin-top:20px;">
        <el-link icon="el-icon-s-home" style="margin-left: 20px;font-size: 20px;" @click="toShopping"
                 v-if="this.$cookies.get('user').degrees==='普通用户'">商城
        </el-link>
        <el-link icon="el-icon-s-goods" style="margin-left: 20px;font-size: 20px;" @click="shoppingCar"
                 v-if="this.$cookies.get('user').degrees==='普通用户'">购物车
        </el-link>
        <el-link icon="el-icon-folder-checked" style="margin-left: 20px;font-size: 20px;" @click="toOrder"
                 v-if="this.$cookies.get('user').degrees==='普通用户'">订单
        </el-link>
        <el-link icon="el-icon-back"style="margin-left: 20px;font-size: 20px;" @click="returns"
                 v-if="this.$cookies.get('user').degrees!=='普通用户'">
          返回
        </el-link>
      </div>
      <div style="width: 100%;height:400px;background-color: greenyellow">
      </div>

      <el-dialog title="用户修改" :visible.sync="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="权限:">
            {{form.degrees}}
          </el-form-item>
          <el-form-item label="是否被禁用:">
            {{form.limits}}
          </el-form-item>
          <el-form-item label="余额:">
            {{form.moneys}}
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
           <el-button @click="dialogVisible = false">取 消</el-button>
           <el-button type="primary" @click="save">确 定</el-button>
       </span>
      </el-dialog>


    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Personal_information",
  data(){
    return{
      user:{},
      form:{},
      dialogVisible:false,
      size:''
    }
  },

  created() {
    this.load()
  },

  methods:{
    load(){
      this.user=this.$cookies.get('user')
      this.form=this.$cookies.get('user')
    },
    updateUser(){
      this.dialogVisible=true
    },
    save(){
      request.put("/user/updateUser",this.form).then(res =>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"用户信息修改成功"
          })
        }
        this.dialogVisible=false
        this.$cookies.set('user',JSON.stringify(this.form))
        this.load()

      })
    },
    toShopping(){
      this.$router.push("/front-end")
    },
    shoppingCar(){
      this.$router.push("/Shoppingcar")
    },
    toOrder(){
      this.$router.push("/OrderForm")
    },
    returns(){
      this.$router.push("/layout")
    }


  },

}
</script>

<style scoped>


</style>