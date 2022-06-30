<template>
  <div >
    <el-menu
        style=" background-color:floralwhite;width:200px; min-height:calc(100vh - 50px); margin-top: 50px; z-index: 1000;position: fixed"
        default-active="user"
        router
        class="el-menu-vertical-demo">
      <el-submenu index="1">
        <el-menu-item style="color:darkblue;font-size:16px" @click="select('all')">全部</el-menu-item>
        <div v-for="type in typeData">
          <el-menu-item style="color:darkblue;font-size:16px" @click="select(type.tname)">{{type.tname}}</el-menu-item>
        </div>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";
import bus from '../router/EventBus'


export default {
name: "left_lable",
  data(){
  return{
    typeData:[],
  }
  },
  created() {
  this.loadType()
  },
  methods:{
  select(value){
    bus.$emit("type",value);
  },

  loadType(){
    request.get("/type/searchType",{
      params:{
        limit:1
      }
    }).then(res =>{
      this.typeData=res.data
    })
  }

  },
}
</script>

<style scoped>

</style>