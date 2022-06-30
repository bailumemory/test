<template>
  <div style=" z-index: 999;height: 50px; width: 100%;background: lightblue;line-height: 50px; border-bottom: 1px solid #ccc; display: flex; position: fixed ;margin-bottom:100px">
    <div style="width:200px; font-weight: bold; color: aqua; font-size:20px;padding-left: 20px">商城</div>
    <div style="flex:1"></div>
    <div style="width:100px;font-weight: bold; color: aqua">
      <el-dropdown @command="handleCommand">
        <el-avatar style="margin-top:5px;"src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a" icon="el-icon-switch-button">{{logins}}</el-dropdown-item>
          <el-dropdown-item command="b" v-if="this.$cookies.get('user')!==null" icon="el-icon-user-solid">个人信息</el-dropdown-item>
          <el-dropdown-item command="c" v-if="this.$cookies.get('user')!==null" icon="el-icon-s-goods">购物车</el-dropdown-item>
          <el-dropdown-item command="d" v-if="this.$cookies.get('user')!==null" icon="el-icon-folder-checked">个人订单</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>

</template>

<script>
export default {
  name: "Daohangs",
  data(){
    return{
      logins:''
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      if(this.$cookies.get('user'))
      {
        this.logins='退出登录'
      }
      else
      {
        this.logins='登录'
      }
    },

    handleCommand(command){
      if(command==='a'){
        if(this.$cookies.get('user')===null)
        {
          this.$router.push("/login")
        }
        else
        {
          this.$cookies.remove('user')
          this.$router.push("/front-end")

        }
      }
      if(command==='b'){
        this.$router.push("/Personal_information")
      }
      if(command==='c'){
        this.$router.push("/Shoppingcar")
      }
      if(command==='d'){
        this.$router.push("/OrderForm")
      }
    }
  }
}
</script>

<style scoped>

</style>