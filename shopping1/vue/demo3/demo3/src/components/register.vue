<template>
  <div style="width:100%; height:100vh; background:lightgoldenrodyellow; overflow: hidden">
    <div style="width:400px; margin: 100px auto;">
      <div style="color: #cccccc; font-size: 30px; text-align: center; padding: 30px 0">欢迎注册</div>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input
              v-model="form.username"
              prefix-icon="el-icon-user-solid"
              placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="form.password"
              prefix-icon="el-icon-lock "
              placeholder="请输入密码"
              show-password>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input
              v-model="form.confirm"
              prefix-icon="el-icon-lock"
              placeholder="请确认密码">
          </el-input>
        </el-form-item>
        <el-form-item >
          <el-button style="width: 100%;" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import router from "@/router";

export default {
  name: "login",
  data(){
    return{
      form:{},
      rules:{
        username:[
          {required:true,message:'请输入用户名',trigger:'blue'},
        ],
        password:[
          {required:true,message:'请输入密码',trigger:'blue'}
        ],
        confirm:[
          {required:true,message:'请确定密码',trigger:'blue'}
        ]
      }
    }
  },
  methods:{
    register(){
      this.$refs['form'].validate((valid) =>{
        if(valid){ //检验输入框内容是否为空
          if(this.form.password!==this.form.confirm){
            this.$message({
              type:'error',
              message:"两次密码不一致"
            })
            return
          }
          request.post("/user/register",this.form).then(res => {
            if(res.code ==='0'){
              this.$message({
                type: "success",
                message: "注册成功",
              })
              this.$router.push("/login")  //登录成功页面跳转
            }
            else{
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>


</style>