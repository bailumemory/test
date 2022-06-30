<template>
  <div style=" z-index: 999;height: 50px; width: 100%;background:white;line-height: 50px; border-bottom: 1px solid #ccc; display: flex; position: fixed ;margin-bottom:100px">
    <div style="width:200px; font-weight: bold; color: aqua; font-size:18px">后台管理</div>
    <div style="flex:1"></div>
    <div style="width:100px;font-weight: bold; color: aqua">
      <el-dropdown >
        <span class="el-dropdown-link">
          {{user.username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-button @click="over">退出登录</el-button>
          </el-dropdown-item>
          <el-dropdown-item>
          <el-button @click="personal">个人信息</el-button>
          </el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
    </div>
  </div>
</template>


<script>
import router from "@/router";
import user from "./User";
export default {
  name: "Header",
  methods: {
    over(){
      this.$cookies.remove("user")
      this.$router.push("/login")
    },
    personal(){
      this.$router.push("/Personal_information")
    }
  },
  data(){
    return{
      user:'',
    }
  },
  created() {
    this.user=this.$cookies.get("user")
  }
}
</script>

<style scoped>

*{
  margin: 0px;
  padding:0px;
  border: 0px;
}
.el-dropdown-link {
  cursor: pointer;
  color: aqua;
  font-size: 18px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>