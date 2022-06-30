<template>
  <div >
    <el-menu
        style="width:200px; min-height:calc(100vh - 50px);margin-top: 50px; z-index: 1000;position: fixed"
        default-active="user"
        router
        class="el-menu-vertical-demo">
          <el-submenu index="1">
            <template slot="title">系统管理</template>
            <el-menu-item index="user" v-if="this.$cookies.get('user').degrees!==3">用户管理</el-menu-item>
            <el-menu-item index="product">商品管理</el-menu-item>
            <el-menu-item index="productType">类型管理</el-menu-item>
            <el-menu-item index="order">订单管理</el-menu-item>
          </el-submenu>
        </el-menu>
  </div>

</template>

<script>
export default {
  name: "Aside",

}
</script>

<style scoped>


</style>